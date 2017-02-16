package com.edvantis.training.parking.repository;

import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.models.VehicleType;

import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/9/2017.
 */
public interface VehicleServiceJdbcRepository {

    Vehicle getById(int vehicleId);

    void insert(Vehicle vehicle);

    void update(int vehicleId, Vehicle vehicle);

    void delete(int vehicleId);

    Set<Vehicle> getAllVehiclesByType(VehicleType vehicleType);

    Vehicle getVehicleByNumber(String vehicleNumber);

    void addOwnerIdToVehicle(int ownerId, int vehicleId);

    int getVehicleIdByNumber(String vehicleNumber);
}
