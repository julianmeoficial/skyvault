package com.skyvault.backend.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.skyvault.backend.repository")
@EntityScan(basePackages = "com.skyvault.backend.model")
@EnableTransactionManagement
public class JpaConfig {

    // Spring Boot Auto-configuration maneja la mayoría de la configuración JPA
    // Esta clase está aquí para configuraciones personalizadas futuras

    // Ejemplo de configuraciones que se podrían agregar:

    // @Bean
    // public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    //     return new JpaTransactionManager(entityManagerFactory);
    // }

    // @Bean
    // public DataSource dataSource() {
    //     // Configuración personalizada de DataSource si fuera necesaria
    // }

    // @Bean
    // public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    //     // Configuración personalizada del EntityManager si fuera necesaria
    // }
}
