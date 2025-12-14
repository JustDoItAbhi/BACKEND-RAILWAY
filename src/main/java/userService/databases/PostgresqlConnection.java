//package userService.databases;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//
//        entityManagerFactoryRef = "pgEntityManagerFactory",
//        transactionManagerRef = "pgTransactionManager"
//)
//public class PostgresqlConnection {
//
//    @Value("${spring.datasource.password}")
//    private String pgPassword;
//
//    @Bean
//    @Primary
//    public DataSource pgDataSource() {
//        return DataSourceBuilder.create()
//                .driverClassName("org.postgresql.Driver")
//                .url("jdbc:postgresql://localhost:5432/scpms")
//                .username("postgres")
//                .password(pgPassword)
//                .build();
//    }
//
//    @Bean
//    @Primary
//    public LocalContainerEntityManagerFactoryBean pgEntityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(pgDataSource());
//        em.setPackagesToScan("userService.registrations");
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        return em;
//    }
//
//    @Bean
//    @Primary
//    public JpaTransactionManager pgTransactionManager() {
//        return new JpaTransactionManager(pgEntityManagerFactory().getObject());
//    }
//
//    @Bean(name = "pgJdbcTemplate")
//    public JdbcTemplate pgJdbcTemplate() {
//        return new JdbcTemplate(pgDataSource());
//    }
//}