package com.edvantis.training.parking.serviceJdbcRepository;

import com.edvantis.training.parking.models.Vehicle;

/**
 * Created by taras.fihurnyak on 2/9/2017.
 */
public interface VehicleServiceJdbcRepository {

    Vehicle getById(String dbName, String login, String password, int vehicleId);

    void insert(String dbName, String login, String password, Vehicle vehicle);

    void update(String dbName, String login, String password, int vehicleId, Vehicle vehicle);

    void delete(String dbName, String login, String password, int vehicleId);

}
