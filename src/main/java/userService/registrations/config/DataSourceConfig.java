package userService.registrations.config;

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
        HikariDataSource dataSource = new HikariDataSource();

        // Use Railway's internal hostname format
        String host = getEnv("MYSQLHOST", "containers-us-west-190.railway.app");
        String port = getEnv("MYSQLPORT", "3306");
        String database = getEnv("MYSQLDATABASE", "railway");
        String username = getEnv("MYSQLUSER", "root");
        String password = getEnv("MYSQLPASSWORD", "");

        String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + database +
                "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

        System.out.println("Connecting to: " + jdbcUrl);
        System.out.println("Username: " + username);

        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        return dataSource;
    }

    private String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        System.out.println("Env " + key + ": " + (value != null ? value : "null"));
        return value != null ? value : defaultValue;
    }
}