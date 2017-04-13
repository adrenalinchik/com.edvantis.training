package com.edvantis.training.parking.repository;

import com.edvantis.training.parking.models.Parking;

import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/14/2017.
 */
public interface ParkingRepository {

    Parking getById(long Id);

    Set<Parking> getAll();

    void insert(Parking parking);

    void update(long Id, Parking parking);

    void delete(long Id);

}
