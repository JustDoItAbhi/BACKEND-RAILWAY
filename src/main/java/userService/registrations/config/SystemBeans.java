package userService.registrations.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SystemBeans {
    @Bean
    public BCryptPasswordEncoder PasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
        @Value("${sendgrid.api.key}")
        private String sendGridApiKey;

    @Bean
    @ConditionalOnProperty(name = "sendgrid.api.key")
    public SendGrid sendGrid(@Value("${sendgrid.api.key}") String sendGridApiKey) {
        if (sendGridApiKey == null || sendGridApiKey.isEmpty() || sendGridApiKey.contains("${sendgrid.api.key}")) {
            throw new IllegalStateException("SendGrid API key is not configured. Please set SENDGRID_API_KEY environment variable.");
        }
        return new SendGrid(sendGridApiKey);
    }
}
