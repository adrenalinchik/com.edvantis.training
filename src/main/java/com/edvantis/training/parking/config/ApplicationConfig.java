package com.edvantis.training.parking.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

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

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/web/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    private EntityManagerFactory getEntityManagerFactoryInstance() {
        return Persistence.createEntityManagerFactory("mysql");
    }
}
