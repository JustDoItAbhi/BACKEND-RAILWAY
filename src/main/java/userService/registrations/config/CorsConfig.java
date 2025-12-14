package userService.registrations.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        System.out.println("REQUEST RECEIVED cors");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();


        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://127.0.0.1:5173");
        config.addAllowedOrigin("https://unvocalized-irretrievably-roman.ngrok-free.dev");
        config.addAllowedOrigin("https://scpms-frontend.onrender.com");
        config.addAllowedOrigin("http://scpms-env.eba-pbtypyrj.us-east-1.elasticbeanstalk.com");
        config.addAllowedHeader("*");
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);
        System.out.println("REQUEST RECEIVED"+config);
        return new CorsFilter(source);
    }
}