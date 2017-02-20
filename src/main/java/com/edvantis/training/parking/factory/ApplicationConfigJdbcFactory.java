package com.edvantis.training.parking.factory;

import com.edvantis.training.parking.repository.GarageJdbcRepository;
import com.edvantis.training.parking.repository.OwnerJdbcRepository;
import com.edvantis.training.parking.repository.ParkingJdbcRepository;
import com.edvantis.training.parking.repository.VehicleJdbcRepository;
import com.edvantis.training.parking.repository.impl.GarageJdbcRepositoryImp;
import com.edvantis.training.parking.repository.impl.OwnerJdbcRepositoryImp;
import com.edvantis.training.parking.repository.impl.ParkingJdbcRepositoryImp;
import com.edvantis.training.parking.repository.impl.VehicleJdbcRepositoryImp;
import com.edvantis.training.parking.services.ParkingService;
import com.edvantis.training.parking.services.impl.ParkingServiceImp;

/**
 * Created by taras.fihurnyak on 2/16/2017.
 */
public class ApplicationConfigJdbcFactory {

    public ApplicationConfigJdbcFactory() {
    }

    public OwnerJdbcRepository getOwnerRepository(String dbName, String login, String password) {
        return new OwnerJdbcRepositoryImp(dbName, login, password);
    }

    public VehicleJdbcRepository getVehicleRepository(String dbName, String login, String password) {
        return new VehicleJdbcRepositoryImp(dbName, login, password);
    }

    public ParkingJdbcRepository getParkingRepository(String dbName, String login, String password) {
        return new ParkingJdbcRepositoryImp(dbName, login, password);
    }

    public GarageJdbcRepository getGarageRepository(String dbName, String login, String password) {
        return new GarageJdbcRepositoryImp(dbName, login, password);
    }

    public ParkingService getParkingService(OwnerJdbcRepository ownerJdbcRepository, VehicleJdbcRepository vehicleJdbcRepository,GarageJdbcRepository garageJdbcRepository, ParkingJdbcRepository parkingJdbcRepository ) {
        return new ParkingServiceImp(ownerJdbcRepository, vehicleJdbcRepository,garageJdbcRepository,parkingJdbcRepository);
    }

}
