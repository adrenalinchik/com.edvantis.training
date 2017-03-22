package com.edvantis.training.parking.factory;

import com.edvantis.training.parking.repository.*;
import com.edvantis.training.parking.repository.jpa.*;
import com.edvantis.training.parking.services.ParkingService;
import com.edvantis.training.parking.services.impl.ParkingServiceImp;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by taras.fihurnyak on 2/16/2017.
 */
public class ApplicationConfig {

    private EntityManagerFactory emFactory;

    public ApplicationConfig() {

    }

    public EntityManagerFactory getInstance() {
        if (emFactory == null) {
            emFactory = getEntityManagerFactoryInstance();
        }
        return emFactory;
    }

    private EntityManagerFactory getEntityManagerFactoryInstance() {
        return Persistence.createEntityManagerFactory("entityManager");
    }

    public OwnerRepository getOwnerRepository() {
        return new OwnerJpaRepository(getInstance());
    }

    public VehicleRepository getVehicleRepository() {
        return new VehicleJpaRepository(getInstance());
    }

    public ParkingRepository getParkingRepository() {
        return new ParkingJpaRepository(getInstance());
    }

    public GarageRepository getGarageRepository() {
        return new GarageJpaRepository(getInstance());
    }

    public ReservationRepository getReservationRepository() {
        return new ReservationJpaRepository(getInstance());
    }

    public ParkingService getParkingService(OwnerRepository ownerRepository, VehicleRepository vehicleRepository, GarageRepository garageRepository, ParkingRepository parkingRepository, ReservationRepository reservationRepository) {
        return new ParkingServiceImp(ownerRepository, vehicleRepository, garageRepository, parkingRepository, reservationRepository);
    }

}
