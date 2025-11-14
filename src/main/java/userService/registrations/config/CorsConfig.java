package userService.registrations.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {


    // Method 1b: Using CorsFilter (more flexible)
//    @Bean
//    public CorsFilter corsFilter() {
//        System.out.println("REQUEST RECEIVED cors");
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("http://localhost:5173");
//        config.addAllowedOrigin("http://127.0.0.1:5173");
////        config.addAllowedOrigin("https://scpms-frontend.onrender.com");
//        config.addAllowedOrigin("https://unvocalized-irretrievably-roman.ngrok-free.dev");
//        config.addAllowedOrigin("https://scpms-frontend.onrender.com");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//
//        source.registerCorsConfiguration("/**", config);
//        System.out.println("REQUEST RECEIVED"+config);
//        return new CorsFilter(source);
//    }

    // Method 1a: Using WebMvcConfigurer (for Spring MVC)
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        System.out.println("REQUEST RECEIVED WEBMVC");
//        System.out.println("REQUEST RECEIVED");
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/api/**")
//                        .allowedOrigins(frontendUrl,
//                                "http://localhost:5173",
//                                "http://127.0.0.1:5173")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                        .allowedHeaders("*")
//                        .allowCredentials(true)
//                        .maxAge(3600);
////                "http://localhost:5173",
////                        "http://127.0.0.1:5173"
////                                "https://oauth.pstmn.io"
////                                "https://scpms-frontend.onrender.com",
////                                "https://unvocalized-irretrievably-roman.ngrok-free.dev"
//                registry.addMapping("/oauth2/**")
//                        .allowedOrigins(frontendUrl)
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                        .allowedHeaders("*")
//                        .allowCredentials(true);
//            }
//        };
//
//    }

    // Method 1b: Using CorsFilter (more flexible)
    @Bean
    public CorsFilter corsFilter() {
        System.out.println("REQUEST RECEIVED cors");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://127.0.0.1:5173");
//        config.addAllowedOrigin("https://scpms-frontend.onrender.com");
        config.addAllowedOrigin("https://unvocalized-irretrievably-roman.ngrok-free.dev");
        config.addAllowedOrigin("https://scpms-frontend.onrender.com");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);
        System.out.println("REQUEST RECEIVED"+config);
        return new CorsFilter(source);
    }

}