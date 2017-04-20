package com.edvantis.training.parking.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * Created by taras.fihurnyak on 2/16/2017.
 */
@Configuration
@ComponentScan("com.edvantis.training.parking")
@Import({MvcConfig.class})
public class ApplicationConfig {

    @PersistenceContext
    private EntityManagerFactory emFactory;

    private Flyway flywayInstance;

    public ApplicationConfig() {

    }

    @Bean
    public EntityManagerFactory getInstance() {
        return emFactory = getEntityManagerFactoryInstance();
    }

    public Flyway getFlywayInstance(String dbName, String login, String password) {
        flywayInstance = new Flyway();
        flywayInstance.setDataSource(dbName, login, password);
        return flywayInstance;
    }

    private EntityManagerFactory getEntityManagerFactoryInstance() {
        return Persistence.createEntityManagerFactory("entityManager");
    }
}
