package userService.registrations.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        String jdbcUrl = "jdbc:mysql://mysql-ldkb.railway.internal:3306/railway?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        System.out.println("Connecting to: " + jdbcUrl);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername("root");
        config.setPassword("bunty");
        config.setConnectionTimeout(30000);
        config.setMaximumPoolSize(5);

        return new HikariDataSource(config);
    }
}