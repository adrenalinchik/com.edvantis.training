package com.edvantis.training.parking.repository;

import com.edvantis.training.parking.models.Parking;

/**
 * Created by taras.fihurnyak on 2/14/2017.
 */
public interface ParkingRepository {


    Parking getById(int parkingId);

    void insert(Parking parking);

    void update(int parkingId, Parking parking);

    void delete(int parkingId);

    void getAllGarages(Parking parking);
}
