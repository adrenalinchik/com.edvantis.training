package com.edvantis.training.parking.services;

import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.models.VehicleType;
import com.edvantis.training.parking.repository.OwnerServiceJdbcRepositoryImp;
import com.edvantis.training.parking.repository.VehicleServiceJdbcRepositoryImp;

import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/11/2017.
 */
public class ParkingServiceImp implements ParkingService {
    @Override
    public void addNewOwner(String dbName, String login, String password, Owner owner) {
        OwnerServiceJdbcRepositoryImp.getInstance().insert(dbName, login, password, owner);

    }

    @Override
    public Set<Owner> getAllOwners(String dbName, String login, String password) {
        return OwnerServiceJdbcRepositoryImp.getInstance()
                .getAllOwnersFromDb(dbName, login, password);

    }

    @Override
    public Owner getOwnerByLastName(String dbName, String login, String password, String ownerLastName) {
        return OwnerServiceJdbcRepositoryImp.getInstance().getOwnerByLastName(dbName, login, password, ownerLastName);

    }

    @Override
    public Vehicle getVehicleByNumber(String dbName, String login, String password, String vehicleNumber) {

        return VehicleServiceJdbcRepositoryImp.getInstance().getVehicleByNumber(dbName, login, password, vehicleNumber);
    }

    @Override
    public Owner getOwnerByVehicleNumber(String dbName, String login, String password, String vehicleNumber) {
        return OwnerServiceJdbcRepositoryImp.getInstance().getOwnerByVehicleNymber(dbName, login, password, vehicleNumber);

    }

    @Override
    public Set<Vehicle> getAllVehicleByType(String dbName, String login, String password, VehicleType vehicleType) {
        return VehicleServiceJdbcRepositoryImp.getInstance().getAllVehiclesByType(dbName, login, password, vehicleType);

    }

    @Override
    public void addVehicleToOwner(String dbName, String login, String password, Owner owner, Vehicle vehicle) {
        int ownerId = OwnerServiceJdbcRepositoryImp.getInstance()
                .getOwnerIdByLastName(dbName, login, password, owner.getLastName());

        int vehicleId = VehicleServiceJdbcRepositoryImp.getInstance()
                .getVehicleIdByNumber(dbName, login, password, vehicle.getNumber());

        VehicleServiceJdbcRepositoryImp.getInstance().addOwnerIdToVehicle(dbName, login, password, ownerId, vehicleId);

        owner.addVehicleToOwner(vehicle);

    }
}
