package com.edvantis.training.parking.config;

import com.edvantis.training.parking.repository.*;
import com.edvantis.training.parking.repository.jpa.imp.*;
import com.edvantis.training.parking.services.ParkingService;
import com.edvantis.training.parking.services.impl.ParkingServiceImp;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * Created by taras.fihurnyak on 2/16/2017.
 */
@Configuration
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

    @Bean
    public OwnerRepository getOwnerRepository() {
        return new OwnerJpaRepository(getInstance());
    }

    @Bean
    public VehicleRepository getVehicleRepository() {
        return new VehicleJpaRepository(getInstance());
    }

    @Bean
    public ParkingRepository getParkingRepository() {
        return new ParkingJpaRepository(getInstance());
    }

    @Bean
    public GarageRepository getGarageRepository() {
        return new GarageJpaRepository(getInstance());
    }

    @Bean
    public ReservationRepository getReservationRepository() {
        return new ReservationJpaRepository(getInstance());
    }

    @Bean
    public ParkingService getParkingService(OwnerRepository ownerRepository, VehicleRepository vehicleRepository, GarageRepository garageRepository, ParkingRepository parkingRepository, ReservationRepository reservationRepository) {
        return new ParkingServiceImp(ownerRepository, vehicleRepository, garageRepository, parkingRepository, reservationRepository);
    }

}
