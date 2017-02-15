package com.edvantis.training.parking.repository;

import com.edvantis.training.parking.models.Parking;

/**
 * Created by taras.fihurnyak on 2/14/2017.
 */
public interface ParkingServiceJdbcRepository {


    Parking getById(String dbName, String login, String password, int parkingId);

    void insert(String dbName, String login, String password, Parking parking);

    void update(String dbName, String login, String password, int parkingId, Parking parking);

    void delete(String dbName, String login, String password, int parkingId);
}
