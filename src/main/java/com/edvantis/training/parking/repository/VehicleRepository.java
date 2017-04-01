package com.edvantis.training.parking.repository;

import com.edvantis.training.parking.models.Vehicle;

import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/9/2017.
 */
public interface VehicleRepository {

    Vehicle getById(long Id);

    Set<Vehicle> getAll();

    void insert(Vehicle vehicle);

    void update(int Id, Vehicle vehicle);

    void update(Vehicle vehicle);

    void delete(long Id);
}
