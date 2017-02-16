package com.edvantis.training.parking.factory;

import com.edvantis.training.parking.jdbc.ParkingJdbcService;
import com.edvantis.training.parking.jdbc.ParkingJdbcServiceImpl;
import com.edvantis.training.parking.repository.GarageServiceJdbcRepository;
import com.edvantis.training.parking.repository.OwnerServiceJdbcRepository;
import com.edvantis.training.parking.repository.ParkingServiceJdbcRepository;
import com.edvantis.training.parking.repository.VehicleServiceJdbcRepository;
import com.edvantis.training.parking.repository.impl.GarageServiceJdbcRepositoryImp;
import com.edvantis.training.parking.repository.impl.OwnerServiceJdbcRepositoryImp;
import com.edvantis.training.parking.repository.impl.ParkingServiceJdbcRepositoryImp;
import com.edvantis.training.parking.repository.impl.VehicleServiceJdbcRepositoryImp;
import com.edvantis.training.parking.services.ParkingService;
import com.edvantis.training.parking.services.ParkingServiceImp;

/**
 * Created by taras.fihurnyak on 2/16/2017.
 */
public class ApplicationConfigFactory {

    private OwnerServiceJdbcRepository ownerRepository = null;
    private VehicleServiceJdbcRepository vehicleRepository = null;
    private GarageServiceJdbcRepository garageRepository = null;
    private ParkingServiceJdbcRepository parkingRepository = null;
    private ParkingService parkingService = null;
    private ParkingJdbcService parkingJdbcService = null;

    private String dbName;
    private String login;
    private String password;

    public ApplicationConfigFactory(String dbName, String login, String password) {
        this.dbName = dbName;
        this.login = login;
        this.password = password;
    }

    public OwnerServiceJdbcRepository getOwnerRepository() {
        if (ownerRepository == null) {
            ownerRepository = new OwnerServiceJdbcRepositoryImp(dbName, login, password);
        }
        return ownerRepository;
    }

    public VehicleServiceJdbcRepository getVehicleRepository() {
        if (vehicleRepository == null) {
            vehicleRepository = new VehicleServiceJdbcRepositoryImp(dbName, login, password);
        }
        return vehicleRepository;
    }

    public ParkingServiceJdbcRepository getParkingRepository() {
        if (parkingRepository == null) {
            parkingRepository = new ParkingServiceJdbcRepositoryImp(dbName, login, password);
        }
        return parkingRepository;
    }

    public GarageServiceJdbcRepository getGarageRepository() {
        if (garageRepository == null) {
            garageRepository = new GarageServiceJdbcRepositoryImp(dbName, login, password);
        }
        return garageRepository;
    }

    public ParkingService getParkingService() {
        if (parkingService == null) {
            parkingService = new ParkingServiceImp(getOwnerRepository(), getVehicleRepository());
        }
        return parkingService;
    }

    public ParkingJdbcService getParkingJdbcService() {
        if (parkingJdbcService == null) {
            parkingJdbcService = new ParkingJdbcServiceImpl(dbName, login, password);
        }
        return parkingJdbcService;
    }


}
