//package userService.databases;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        basePackages = "userService.registrations.mysqlrepos", // MySQL repos
//        entityManagerFactoryRef = "mysqlEntityManagerFactory",
//        transactionManagerRef = "mysqlTransactionManager"
//)
//public class MysqlConnection {
//
//    @Value("${spring.mysql.password}")
//    private String mysqlPassword;
//
//    @Bean
//    public DataSource mysqlDataSource() {
//        return DataSourceBuilder.create()
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                .url("jdbc:mysql://localhost:3306/userService_test")
//                .username("root")
//                .password(mysqlPassword)
//                .build();
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(mysqlDataSource());
//        em.setPackagesToScan("userService.registrations.mysqlentities"); // MySQL entities
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//
//        Properties properties = new Properties();
//        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
//        properties.put("hibernate.hbm2ddl.auto", "update");
//        properties.put("hibernate.show_sql", "true");
//
//        em.setJpaProperties(properties);
//        return em;
//    }
//
//    @Bean
//    public JpaTransactionManager mysqlTransactionManager() {
//        return new JpaTransactionManager(mysqlEntityManagerFactory().getObject());
//    }
//
//    @Bean(name = "mysqlJdbcTemplate")
//    public JdbcTemplate mysqlJdbcTemplate() {
//        return new JdbcTemplate(mysqlDataSource());
//    }
//}
