package com.edvantis.training.parking.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Without mvc config
 */
@Configuration
@ComponentScan(basePackages = {
        "com.edvantis.training.parking.models",
        "com.edvantis.training.parking.repository",
        "com.edvantis.training.parking.services",
        "com.edvantis.training.parking.controllers"
})
//@Import({MvcConfig.class}) - for web mvc different approach should be used
public class ApplicationTestConfig {

    public ApplicationTestConfig() {
    }

    @Bean
    public EntityManagerFactory getInstance() {
        return getEntityManagerFactoryInstance();
    }

    public Flyway getFlywayInstance(String dbName, String login, String password) {
        Flyway flywayInstance = new Flyway();
        flywayInstance.setDataSource(dbName, login, password);
        return flywayInstance;
    }

    private EntityManagerFactory getEntityManagerFactoryInstance() {
        return Persistence.createEntityManagerFactory("h2");
    }

}
