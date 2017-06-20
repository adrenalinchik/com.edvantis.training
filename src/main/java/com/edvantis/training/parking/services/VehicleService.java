package com.edvantis.training.parking.services;

import com.edvantis.training.parking.models.Vehicle;

import java.util.List;

/**
 * Created by taras.fihurnyak on 6/14/2017.
 */
public interface VehicleService {

    List<Vehicle> getAllVehicles();

    List<Vehicle> getAllActiveVehicles();

    List<Vehicle> getAllInactiveVehicles();

    void deleteVehicle(long id);

    Vehicle getVehicle(long id);

    Vehicle updateVehicle(long id, Vehicle vehicle);

    void addNewVehicle(Vehicle vehicle);

    Vehicle getVehicleByNumber(String number);

    List<Vehicle> getOwnerVehicles(long ownerId);
}
