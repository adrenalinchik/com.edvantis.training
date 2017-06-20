package com.edvantis.training.parking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by taras.fihurnyak on 4/21/2017.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.edvantis.training.parking.models",
        "com.edvantis.training.parking.repository",
        "com.edvantis.training.parking.controllers",
        "com.edvantis.training.parking.api",
        "com.edvantis.training.parking.services"
})
public class TestControllerContext {

    @Bean
    public EntityManagerFactory getInstance() {
        return getEntityManagerFactoryInstance();
    }

//    @Bean
//    public HelpService parkingService() {
//        return Mockito.mock(HelpService.class);
//    }

    private EntityManagerFactory getEntityManagerFactoryInstance() {
        return Persistence.createEntityManagerFactory("h2");
    }
}
