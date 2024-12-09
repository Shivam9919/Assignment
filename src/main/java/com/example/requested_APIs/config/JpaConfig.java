//package com.example.requested_APIs.config;
//
//import jakarta.persistence.EntityManagerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import javax.sql.DataSource;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//
//@Configuration
//@EnableJpaRepositories(basePackages = "com.example.requested_APIs.repo")  // Adjust the package path
//public class JpaConfig {
//
//    // DataSource Bean
//    @Bean
//    public DataSource dataSource() {
//        return DataSourceBuilder.create()
//                .url("jdbc:postgresql://localhost:5432/postgres")
//                .username("postgres")
//                .password("postgres")
//                .driverClassName("org.postgresql.Driver")
//                .build();
//    }
//
//    // EntityManagerFactory Bean
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource) {
//        return builder
//                .dataSource(dataSource)
//                .packages("com.example.requested_APIs.model") // Set your entity package path
//                .persistenceUnit("myJpaUnit")
//                .build();
//    }
//
//    // TransactionManager Bean
//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//}
