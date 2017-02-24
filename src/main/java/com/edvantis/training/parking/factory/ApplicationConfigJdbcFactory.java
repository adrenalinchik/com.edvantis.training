package com.edvantis.training.parking.factory;

import com.edvantis.training.parking.repository.GarageRepository;
import com.edvantis.training.parking.repository.OwnerRepository;
import com.edvantis.training.parking.repository.ParkingRepository;
import com.edvantis.training.parking.repository.VehicleRepository;
import com.edvantis.training.parking.repository.jpa.GarageJpaRepository;
import com.edvantis.training.parking.repository.jpa.OwnerJpaRepository;
import com.edvantis.training.parking.repository.jpa.ParkingJpaRepository;
import com.edvantis.training.parking.repository.jpa.VehicleJpaRepository;
import com.edvantis.training.parking.services.ParkingService;
import com.edvantis.training.parking.services.impl.ParkingServiceImp;

/**
 * Created by taras.fihurnyak on 2/16/2017.
 */
public class ApplicationConfigJdbcFactory {

    public ApplicationConfigJdbcFactory() {
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

    public ParkingService getParkingService(OwnerRepository ownerRepository, VehicleRepository vehicleRepository,GarageRepository garageRepository, ParkingRepository parkingRepository ) {
        return new ParkingServiceImp(ownerRepository, vehicleRepository,garageRepository,parkingRepository);
    }

}
