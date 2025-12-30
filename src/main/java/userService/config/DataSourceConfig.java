//package userService.registrations.config;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DataSourceConfig {
//
////    @Bean
//////    @Primary
////    public DataSource dataSource() {
////        HikariConfig config = new HikariConfig();
////
////        String jdbcUrl = "jdbc:mysql://mysql-ldkb.railway.internal:3306/railway?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
////        System.out.println("Connecting to: " + jdbcUrl);
////        config.setJdbcUrl(jdbcUrl);
////        config.setUsername("root");
////        config.setPassword("bzBJhmqIzMybcHzxBORnXmmLYafEotMm");
////        config.setConnectionTimeout(30000);
////        config.setMaximumPoolSize(5);
////
////        return new HikariDataSource(config);
////    }
//
//
//    @Bean
////    @Primary
//    @ConfigurationProperties("spring.datasource.primary")
//    public DataSource primaryDataSource() {
//        HikariConfig config = new HikariConfig();
//
//        // Check if we're on Railway
//        String railwayHost = System.getenv("MYSQLHOST");
//        String railwayPassword = System.getenv("MYSQLPASSWORD");
//
//        if (railwayHost != null && railwayPassword != null) {
//            // Use Railway MySQL
//            System.out.println("🚀 PRIMARY: Using Railway MySQL");
//            String jdbcUrl = "jdbc:mysql://" + railwayHost + ":3306/railway?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
//            config.setJdbcUrl(jdbcUrl);
//            config.setUsername("root");
//            config.setPassword(railwayPassword);
//            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        } else {
//            // Use H2 as fallback
//            System.out.println("📦 PRIMARY: Using H2 in-memory database");
//            config.setJdbcUrl("jdbc:h2:mem:primarydb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
//            config.setUsername("sa");
//            config.setPassword("password");
//            config.setDriverClassName("org.h2.Driver");
//        }
//        config.setConnectionTimeout(30000);
//        config.setMaximumPoolSize(5);
//        return new HikariDataSource(config);
//    }
//
////    @Bean
////    @Primary
////    @ConfigurationProperties("spring.datasource.local")
////    public DataSource localDataSource() {
////        HikariConfig config = new HikariConfig();
////
////        System.out.println("💻 SECONDARY: Local MySQL DataSource (for development)");
////        config.setJdbcUrl("jdbc:mysql://localhost:3306/userService?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
////        config.setUsername("root");
////        config.setPassword("bunty"); // Change this to your local MySQL password
////        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
////        config.setConnectionTimeout(30000);
////        config.setMaximumPoolSize(3);
////        config.setPoolName("LocalMySQLPool");
////        return new HikariDataSource(config);
////    }
//}