package com.edvantis.training.parking.services;

import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.models.VehicleType;

import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/11/2017.
 */
public interface ParkingService {

    void addNewOwner(Owner owner);

    Set<Owner> getAllOwners();

    Owner getOwnerByLastName(String ownerLastName);

    Vehicle getVehicleByNumber(String vehicleNumber);

    Owner getOwnerByVehicleNumber(String vehicleNumber);

    Set<Vehicle> getAllVehicleByType(VehicleType vehicleType);

    void addVehicleToOwner(Owner owner, Vehicle vehicle);
}
