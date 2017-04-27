package com.edvantis.training.parking.config;

import com.edvantis.training.parking.services.ParkingService;
import org.mockito.Mockito;
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
        "com.edvantis.training.parking.controllers"
})
public class TestControllerContext {

    @Bean
    public EntityManagerFactory getInstance() {
        return getEntityManagerFactoryInstance();
    }

    @Bean
    public ParkingService parkingService() {
        return Mockito.mock(ParkingService.class);
    }

    private EntityManagerFactory getEntityManagerFactoryInstance() {
        return Persistence.createEntityManagerFactory("h2");
    }
}
