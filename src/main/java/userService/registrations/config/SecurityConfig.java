package userService.registrations.config;

public class SecurityConfig {


//    private final KeyPair keyPair;
//
//    public SecurityConfig() {
//        this.keyPair = generateRsaKey();
//    }

    // Make the key pair available as beans
//    @Bean
//    public KeyPair keyPair() {
//        return keyPair;
//    }
//
//    @Bean
//    public RSAPublicKey rsaPublicKey() {
//        return (RSAPublicKey) keyPair.getPublic();
//    }
//
//    @Bean
//    public RSAPrivateKey rsaPrivateKey() {
//        return (RSAPrivateKey) keyPair.getPrivate();
//    }

//    @Bean
//    public JwtTokenService jwtTokenService(RSAPrivateKey privateKey) {
//        return new JwtTokenService(privateKey);
//    }
//
//    @Bean
//    public JWKSource<SecurityContext> jwkSource(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
//        RSAKey rsaKey = new RSAKey.Builder(publicKey)
//                .privateKey(privateKey)
//                .keyID(UUID.randomUUID().toString())
//                .build();
//        JWKSet jwkSet = new JWKSet(rsaKey);
//        return new ImmutableJWKSet<>(jwkSet);
//    }
//
//    @Bean
//    @Order(1)
//    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
//            throws Exception {
//        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
//                OAuth2AuthorizationServerConfigurer.authorizationServer();
//
//        http
//                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
//                .cors(withDefaults())
//                .with(authorizationServerConfigurer, (authorizationServer) ->
//                        authorizationServer
//                                .oidc(withDefaults())    // Enable OpenID Connect 1.0
//                )
//                .authorizeHttpRequests((authorize) ->
//                        authorize
//                                .anyRequest().authenticated()
//                )
//                // Redirect to the login page when not authenticated from the
//                // authorization endpoint
////                .oauth2Login(oauth->
////                        oauth.defaultSuccessUrl("/hello",true))
//
//                .exceptionHandling((exceptions) -> exceptions
//                        .defaultAuthenticationEntryPointFor(
//                                new LoginUrlAuthenticationEntryPoint("/login"),
//                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
//                        )
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    @Order(2)
//    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
//            throws Exception {
//
//        System.out.println("REQUEST RECEIVED PERMISSIONS");
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
//                )
//
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
//                )
//
////                .oauth2Login(oauth->oauth.defaultSuccessUrl("/hello",true));
//
////                .formLogin(withDefaults());
//                .formLogin(form->form
//                        .loginProcessingUrl("/api/auth/login")
//                        .usernameParameter("email")
//                        .passwordParameter("password")
//                        .successHandler(authenticationSuccessHandler(jwtTokenService(rsaPrivateKey())))
//                        .failureHandler(authenticationFailureHandler())
//                        .permitAll())
//                .logout(logout->logout.logoutUrl("/auth/auth/logout")
//                        .logoutSuccessHandler(logoutSuccessHandler())
//                                .permitAll())
//                        .sessionManagement(session->session
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//
//
//        return http.build();
//    }
//
//
//    @Bean
//    public AuthenticationSuccessHandler authenticationSuccessHandler(JwtTokenService jwtTokenService){
//        return new AuthenticationSuccessHandler() {
//            @Override
//            public void onAuthenticationSuccess(HttpServletRequest request,
//                                                HttpServletResponse response,
//                                                Authentication authentication) throws IOException, ServletException {
//                CustomUsersDetails usersDetals= (CustomUsersDetails) authentication.getPrincipal();
//             String JwtToken= jwtTokenService.generateToken(usersDetals);
//
//             response.setStatus(HttpStatus.OK.value());
//
//                response.setContentType("application/json;charset=UTF-8");
//             String responseBody=String.format(
//                     "{\"message\":\"Login Successful\", \"token\":\"%s\", \"user\": {\"id\":\"%s\", \"email\":\"%s\", \"username\":\"%s\"}}",
//                     JwtToken,
//                     usersDetals.getUserId(),
//                     usersDetals.getUserEmail(),
//                     usersDetals.getUsername()
//                     );
//             response.getWriter().write(responseBody);
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
//
////    @Bean
////    public AuthenticationFailureHandler authenticationFailureHandler() {
////        return new SimpleUrlAuthenticationFailureHandler() {
////            @Override
////            public void onAuthenticationFailure(HttpServletRequest request,
////                                                HttpServletResponse response,
////                                                AuthenticationException exception) throws IOException {
////                response.setStatus(HttpStatus.UNAUTHORIZED.value());
////                response.getWriter().write("{\"error\": \"Invalid credentials\"}");
////            }
////        };
////    }
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
//    public CorsConfigurationSource corsConfigurationSource() {
//        System.out.println("REQUEST RECEIVED SPRING SECURITY CORS");
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList(
//                "http://localhost:5173",
//                "http://127.0.0.1:5173",
//                "https://oauth.pstmn.io",
//                "https://unvocalized-irretrievably-roman.ngrok-free.dev",
//                "https://scpms-frontend.onrender.com",
//                "https://backend-railway-production-8bf7.up.railway.app/"
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
//    public JWKSource<SecurityContext> jwkSource() {
//        KeyPair keyPair = generateRsaKey();
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//        RSAKey rsaKey = new RSAKey.Builder(publicKey)
//                .privateKey(privateKey)
//                .keyID(UUID.randomUUID().toString())
//                .build();
//        JWKSet jwkSet = new JWKSet(rsaKey);
//        return new ImmutableJWKSet<>(jwkSet);
//    }

//    private  KeyPair generateRsaKey() {
//        KeyPair keyPair;
//        try {
//            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//            keyPairGenerator.initialize(2048);
//            keyPair = keyPairGenerator.generateKeyPair();
//        } catch (Exception ex) {
//            throw new IllegalStateException(ex);
//        }
//        return keyPair;
//    }

//    @Bean
//    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
//        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
//    }
//
//    @Bean
//    public AuthorizationServerSettings authorizationServerSettings() {
//        return AuthorizationServerSettings.builder().build();
//    }
//
//    @Bean
//    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
//        return context -> {
//            if (context.getPrincipal() != null && context.getPrincipal().getPrincipal() instanceof CustomUsersDetails userDetails) {
//                List<String> roles = userDetails.getAuthorities()
//                        .stream()
//                        .map(GrantedAuthority::getAuthority)
//                        .toList();
//
//                context.getClaims().claim("roles", roles);
//                context.getClaims().claim("id", userDetails.getUserId());
//            }
//        };
//    }
//
//    // Separate class for TokenInterceptor
//    public static class TokenInterceptor implements ClientHttpRequestInterceptor {
//        @Override
//        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
//            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            String token = jwt.getTokenValue();
//
//            // Set the Authorization header with Bearer Token
//            request.getHeaders().setBearerAuth(token);
//
//            return execution.execute(request, body);
//        }
//    }
}