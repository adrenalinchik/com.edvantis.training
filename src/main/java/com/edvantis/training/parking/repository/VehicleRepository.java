package com.edvantis.training.parking.repository;

import com.edvantis.training.parking.models.ModelState;
import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.models.Vehicle;

import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/9/2017.
 */
public interface VehicleRepository {

    Vehicle getById(long Id);

    Set<Vehicle> getAll();

    void insert(Vehicle vehicle);

    void update(long Id, Vehicle vehicle);

    void delete(long Id);

    Set<Vehicle> getOwnerVehicles(long id);

    Set<Vehicle> getActiveOrInactive(ModelState status);

    Vehicle getVehicleByNumber(String number);
}
