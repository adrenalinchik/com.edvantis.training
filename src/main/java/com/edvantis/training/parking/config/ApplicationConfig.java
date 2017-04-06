package com.edvantis.training.parking.config;

import com.edvantis.training.parking.repository.*;
import com.edvantis.training.parking.repository.jpa.*;
import com.edvantis.training.parking.services.ParkingService;
import com.edvantis.training.parking.services.impl.ParkingServiceImp;
import org.flywaydb.core.Flyway;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by taras.fihurnyak on 2/16/2017.
 */
public class ApplicationConfig {

    private EntityManagerFactory emFactory;
    private Flyway flywayInstance;

    public ApplicationConfig() {

    }

    public EntityManagerFactory getInstance() {
        if (emFactory == null) {
            emFactory = getEntityManagerFactoryInstance();
        }
        return emFactory;
    }

    public Flyway getFlywayInstance(String dbName, String login, String password) {
        if (flywayInstance == null) {
            flywayInstance = new Flyway();
            flywayInstance.setDataSource(dbName, login, password);
        }
        return flywayInstance;
    }

    private EntityManagerFactory getEntityManagerFactoryInstance() {
        return Persistence.createEntityManagerFactory("entityManager");
    }

    public OwnerRepository getOwnerRepository() {
        return new OwnerJpaRepository();
    }

    public VehicleRepository getVehicleRepository() {
        return new VehicleJpaRepository();
    }

    public ParkingRepository getParkingRepository() {
        return new ParkingJpaRepository();
    }

    public GarageRepository getGarageRepository() {
        return new GarageJpaRepository();
    }

    public ReservationRepository getReservationRepository() {
        return new ReservationJpaRepository(getInstance());
    }

    public ParkingService getParkingService(OwnerRepository ownerRepository, VehicleRepository vehicleRepository, GarageRepository garageRepository, ParkingRepository parkingRepository, ReservationRepository reservationRepository) {
        return new ParkingServiceImp(ownerRepository, vehicleRepository, garageRepository, parkingRepository, reservationRepository);
    }

}
