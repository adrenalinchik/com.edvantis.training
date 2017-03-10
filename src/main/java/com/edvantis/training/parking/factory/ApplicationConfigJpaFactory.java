package com.edvantis.training.parking.factory;

import com.edvantis.training.parking.repository.*;
import com.edvantis.training.parking.repository.jpa.*;
import com.edvantis.training.parking.services.ParkingService;
import com.edvantis.training.parking.services.impl.ParkingServiceImp;

/**
 * Created by taras.fihurnyak on 2/16/2017.
 */
public class ApplicationConfigJpaFactory {

    public ApplicationConfigJpaFactory() {
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
        return new ReservationJpaRepository();
    }

    public ParkingService getParkingService(OwnerRepository ownerRepository, VehicleRepository vehicleRepository,GarageRepository garageRepository, ParkingRepository parkingRepository, ReservationRepository reservationRepository ) {
        return new ParkingServiceImp(ownerRepository, vehicleRepository,garageRepository,parkingRepository,reservationRepository );
    }

}
