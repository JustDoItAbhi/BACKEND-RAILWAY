//package userService.registrations.config;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import userService.registrations.security.customization.CustomUsersDetails;
//
//import java.io.IOException;
//import java.security.interfaces.RSAPrivateKey;
//import java.util.Arrays;
//
//public class CutomizedSpringSecurityFileteChain {
//    @Autowired
//    private RSAPrivateKey rsaPrivateKey;
//    private JwtTokenService tokenService;
//
//    public SecurityFilterChain directOauth2Method(HttpSecurity http){
//        //        System.out.println("REQUEST RECEIVED PERMISSIONS");
//        http
//                .csrf().disable()
//
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers(
//
//                                "/login",
//                                "/api/auth/**",
//                                "/oauth2/**",
//                                "/api/user/me",
//                                "/.well-known/**",
//                                "/error",
//                                "/client/register",
//                                "/client/updateRegisterClient/**",
//                                "/api/user/createUser",
//                                "/roles/createRole",
//                                "/api/debug/cors",
//                                "/api/user/StudentSignUp",
//                                "/api/user/allUsers"
//                        ).permitAll()
//                        .requestMatchers("/api/user/ConfirmStudentSignUp/otp/**").permitAll()
//                        .requestMatchers("/api/subject/").permitAll()
//                        .requestMatchers("/api/teachers/finishSignUP/{id}").hasRole("TEACHER")
//                        .requestMatchers("/api/students/completeStundentSignUp/{stId}").hasRole("STUDENT")
//
//                        .requestMatchers("/api/user/createUser").permitAll()
//                        .requestMatchers("/api/user/session-info").authenticated()
//                        .anyRequest().authenticated()
//
//                )
//                        .oauth2ResourceServer(oauth2 -> oauth2
//                                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
//                        )
//                .formLogin(form->form
//                        .loginProcessingUrl("/api/auth/login")
//                        .usernameParameter("email")
//                        .passwordParameter("password")
//                        .successHandler(authenticationSuccessHandler(tokenService(RSAPrivateKey)))
//                        .failureHandler(authenticationFailureHandler())
//                        .permitAll())
//                .logout(logout->logout.logoutUrl("/auth/auth/logout")
//                        .logoutSuccessHandler(logoutSuccessHandler())
//                                .permitAll())
//                        .sessionManagement(session->session
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
////
////
////
//        return http.build();
//    }
//
//
//
//    @Bean
//    public AuthenticationFailureHandler authenticationFailureHandler() {
//        return new SimpleUrlAuthenticationFailureHandler() {
//            @Override
//            public void onAuthenticationFailure(HttpServletRequest request,
//                                                HttpServletResponse response,
//                                                AuthenticationException exception) throws IOException {
//                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                response.getWriter().write("{\"error\": \"Invalid credentials\"}");
//            }
//        };
//    }
//
//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
//        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
//
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
//        return jwtAuthenticationConverter;
//    }
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        System.out.println("REQUEST RECEIVED SPRING SECURITY CORS");
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList(
//                "http://localhost:5173",
//                "http://127.0.0.1:5173",
//                "https://oauth.pstmn.io",
//                "https://unvocalized-irretrievably-roman.ngrok-free.dev",
//                "https://scpms-frontend.onrender.com",
//                "https://backend-railway-production-30b6.up.railway.app"
//        ));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList( "Authorization",
//                "Content-Type",
//                "X-Requested-With",
//                "Accept",
//                "Origin",
//                "Access-Control-Request-Method",
//                "Access-Control-Request-Headers"));
//        configuration.setExposedHeaders(Arrays.asList(
//                "Authorization",
//                "Content-Type"
//        ));
//        configuration.setAllowCredentials(true);
//        configuration.setMaxAge(3600L);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//    @Bean
//    public AuthenticationSuccessHandler authenticationSuccessHandler(JwtTokenService jwtTokenService){
//        return new AuthenticationSuccessHandler() {
//            @Override
//            public void onAuthenticationSuccess(HttpServletRequest request,
//                                                HttpServletResponse response,
//                                                Authentication authentication) throws IOException, ServletException {
//                CustomUsersDetails usersDetals= (CustomUsersDetails) authentication.getPrincipal();
//                String JwtToken= jwtTokenService.generateToken(usersDetals);
//
//                response.setStatus(HttpStatus.OK.value());
//
//                response.setContentType("application/json;charset=UTF-8");
//                String responseBody=String.format(
//                        "{\"message\":\"Login Successful\", \"token\":\"%s\", \"user\": {\"id\":\"%s\", \"email\":\"%s\", \"username\":\"%s\"}}",
//                        JwtToken,
//                        usersDetals.getUserId(),
//                        usersDetals.getUserEmail(),
//                        usersDetals.getUsername()
//                );
//                response.getWriter().write(responseBody);
//            }
//        };
//    }
//
//    @Bean
//    public LogoutSuccessHandler logoutSuccessHandler() {
//        return (request, response, authentication) -> {
//            response.setStatus(HttpStatus.OK.value());
//            response.getWriter().write("{\"message\": \"Logout successful\"}");
//        };
//    }
//
//}
