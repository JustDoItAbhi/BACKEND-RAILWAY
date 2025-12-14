package userService.registrations.config.filterchain;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import userService.registrations.config.JwtTokenService;
import userService.registrations.security.customization.CustomUsersDetails;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    private final JwtTokenService jwtTokenService;
    @Lazy
    public SecurityConfigurations(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }


    @Bean
    public KeyPair keyPair() {
        return generateRsaKey();
    }

    @Bean
    public RSAPublicKey rsaPublicKey() {
        return (RSAPublicKey) keyPair().getPublic();
    }

    @Bean
    public RSAPrivateKey rsaPrivateKey() {
        return (RSAPrivateKey) keyPair().getPrivate();
    }

    private KeyPair generateRsaKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = new RSAKey.Builder(rsaPublicKey())
                .privateKey(rsaPrivateKey())
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                OAuth2AuthorizationServerConfigurer.authorizationServer();

        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, (authorizationServer) ->
                        authorizationServer
                                .oidc(Customizer.withDefaults())	// Enable OpenID Connect 1.0
                )
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .anyRequest().authenticated()
                )
                // Redirect to the login page when not authenticated from the
                // authorization endpoint
                .exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                );

        return http.build();
    }

    // FIXED: Local mode should use form login (default)
    @Bean
    @Order(2)
    @ConditionalOnProperty(name = "app.security.mode", havingValue = "local", matchIfMissing = true)
    public SecurityFilterChain localSecurityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Loading LOCAL security configuration (Form Login + OAuth2 Resource Server)");
        return buildFormLoginSecurity(http);
    }

    @Bean
    @Order(2)
    @ConditionalOnProperty(name = "app.security.mode", havingValue = "production")
    public SecurityFilterChain productionSecurityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Loading PRODUCTION security configuration (OAuth2 Resource Server Only)");
        return buildOAuth2SecurityForLocal(http);
    }

    private SecurityFilterChain buildOAuth2SecurityForLocal(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/login",
                                "/api/auth/**",
                                "/oauth2/**",
                                "/api/user/me",
                                "/.well-known/**",
                                "/error",
                                "/client/register",
                                "/client/updateRegisterClient/**",
                                "/api/user/createUser",
                                "/roles/createRole",
                                "/api/debug/cors",
                                "/api/user/StudentSignUp",
                                "/api/user/allUsers",
                                "/api/user/ConfirmStudentSignUp/otp/**",
                                "/api/subject/**"
                        ).permitAll()
                        .requestMatchers("/api/teachers/finishSignUP/{id}").hasRole("TEACHER")
                        .requestMatchers("/api/students/completeStundentSignUp/{stId}").hasRole("STUDENT")
                        .requestMatchers("/api/user/session-info").authenticated()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .formLogin(withDefaults());
        return http.build();
    }


    private SecurityFilterChain buildFormLoginSecurity(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/login",
                                "/api/auth/**",
                                "/oauth2/**",
                                "/api/user/me",
                                "/.well-known/**",
                                "/error",
                                "/client/register",
                                "/client/updateRegisterClient/**",
                                "/api/user/createUser",
                                "/roles/createRole",
                                "/api/debug/cors",
                                "/api/user/StudentSignUp",
                                "/api/user/allUsers",
                                "/api/user/ConfirmStudentSignUp/otp/**",
                                "/api/subject/**",
                                "/api/user/test-brevo"
                        ).permitAll()
                        .requestMatchers("/api/teachers/finishSignUP/{id}").hasRole("TEACHER")
                        .requestMatchers("/api/students/completeStundentSignUp/{stId}").hasRole("STUDENT")
                        .requestMatchers("/api/user/session-info").authenticated()
                        .anyRequest().permitAll()
                )
                // Form Login for local development

                .formLogin(form->form
                        .loginProcessingUrl("/api/auth/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(authenticationSuccessHandler())
                        .failureHandler(authenticationFailureHandler())
                        .permitAll())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(logoutSuccessHandler())
                        .permitAll()
                )
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request,
                                                HttpServletResponse response,
                                                AuthenticationException exception) throws IOException {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Invalid credentials\"}");
            }
        };
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
                                                HttpServletResponse response,
                                                Authentication authentication) throws IOException {
                CustomUsersDetails userDetails = (CustomUsersDetails) authentication.getPrincipal();
                String jwtToken = jwtTokenService.generateToken(userDetails);

                response.setStatus(HttpStatus.OK.value());
                response.setContentType("application/json;charset=UTF-8");
                String responseBody = String.format(
                        "{\"message\":\"Login Successful\", \"token\":\"%s\", \"user\": {\"id\":\"%s\", \"email\":\"%s\", \"username\":\"%s\"}}",
                        jwtToken,
                        userDetails.getUserId(),
                        userDetails.getUserEmail(),
                        userDetails.getUsername()
                );
                response.getWriter().write(responseBody);
            }
        };
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Logout successful\"}");
        };
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource());
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            if (context.getPrincipal() != null && context.getPrincipal().getPrincipal() instanceof CustomUsersDetails userDetails) {
                List<String> roles = userDetails.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();

                context.getClaims().claim("roles", roles);
                context.getClaims().claim("id", userDetails.getUserId());
            }
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        System.out.println("Loading CORS configuration");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",
                "http://127.0.0.1:5173",
                "https://oauth.pstmn.io",
                "https://unvocalized-irretrievably-roman.ngrok-free.dev",
                "https://scpms-frontend.onrender.com",
                "http://scpms-env.eba-pbtypyrj.us-east-1.elasticbeanstalk.com"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
        ));
        configuration.setExposedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type"
        ));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}