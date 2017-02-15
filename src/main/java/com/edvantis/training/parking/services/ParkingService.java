package com.edvantis.training.parking.services;

import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.models.VehicleType;

import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/11/2017.
 */
public interface ParkingService {

    void addNewOwner(String dbName, String login, String password, Owner owner);

    Set<Owner> getAllOwners(String dbName, String login, String password);

    Owner getOwnerByLastName(String dbName, String login, String password, String ownerLastName);

    Vehicle getVehicleByNumber(String dbName, String login, String password, String vehicleNumber);

    Owner getOwnerByVehicleNumber(String dbName, String login, String password, String vehicleNumber);

    Set<Vehicle> getAllVehicleByType(String dbName, String login, String password, VehicleType vehicleType);

    void addVehicleToOwner(String dbName, String login, String password, Owner owner, Vehicle vehicle);
}
