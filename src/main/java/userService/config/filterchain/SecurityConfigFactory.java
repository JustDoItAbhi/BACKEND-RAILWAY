//package userService.registrations.config.filterchain;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import org.springframework.web.cors.CorsConfigurationSource;
//import userService.registrations.config.JwtTokenService;
//import userService.registrations.security.customization.CustomUsersDetails;
//
//@Component
//public class SecurityConfigFactory {
//
//    @Autowired
//    private JwtTokenService jwtTokenService;
//
//    @Autowired
//    private CorsConfigurationSource corsConfigurationSource;
//
//    public SecurityFilterChain createLocalSecurityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.configurationSource(corsConfigurationSource))
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/api/auth/**", "/public/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                // Local development - Form login only
//                .formLogin(form -> form
//                        .loginProcessingUrl("/api/auth/login")
//                        .usernameParameter("email")
//                        .passwordParameter("password")
//                        .successHandler(authenticationSuccessHandler())
//                        .failureHandler(authenticationFailureHandler())
//                        .permitAll()
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .build();
//    }
//
//    public SecurityFilterChain createProductionSecurityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.configurationSource(corsConfigurationSource))
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/api/auth/**", "/public/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                // Production - OAuth2 Resource Server only
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .build();
//    }
//
//    public SecurityFilterChain createHybridSecurityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.configurationSource(corsConfigurationSource))
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/api/auth/**", "/public/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                // Hybrid - Both form login and OAuth2
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
//                )
//                .formLogin(form -> form
//                        .loginProcessingUrl("/api/auth/login")
//                        .usernameParameter("email")
//                        .passwordParameter("password")
//                        .successHandler(authenticationSuccessHandler())
//                        .failureHandler(authenticationFailureHandler())
//                        .permitAll()
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .build();
//    }
//
//    private JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
//        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
//
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
//        return jwtAuthenticationConverter;
//    }
//
//    private AuthenticationSuccessHandler authenticationSuccessHandler() {
//        return (request, response, authentication) -> {
//            CustomUsersDetails userDetails = (CustomUsersDetails) authentication.getPrincipal();
//            String jwtToken = jwtTokenService.generateToken(userDetails);
//
//            response.setStatus(HttpStatus.OK.value());
//            response.setContentType("application/json");
//            response.getWriter().write(String.format(
//                    "{\"token\": \"%s\", \"user\": {\"id\": \"%s\", \"email\": \"%s\"}}",
//                    jwtToken, userDetails.getUserId(), userDetails.getUserEmail()
//            ));
//        };
//    }
//
//    private AuthenticationFailureHandler authenticationFailureHandler() {
//        return (request, response, exception) -> {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            response.setContentType("application/json");
//            response.getWriter().write("{\"error\": \"Invalid credentials\"}");
//        };
//    }
//}
