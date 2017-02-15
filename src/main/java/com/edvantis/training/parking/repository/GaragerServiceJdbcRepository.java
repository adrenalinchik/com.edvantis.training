package com.edvantis.training.parking.repository;

import com.edvantis.training.parking.models.Garage;

/**
 * Created by taras.fihurnyak on 2/13/2017.
 */
public interface GaragerServiceJdbcRepository {

    Garage getById(String dbName, String login, String password, int id);

    void insert(String dbName, String login, String password, Garage garage);

    void update(String dbName, String login, String password, int garageId, Garage garage);

    void delete(String dbName, String login, String password, int garageId);
}
