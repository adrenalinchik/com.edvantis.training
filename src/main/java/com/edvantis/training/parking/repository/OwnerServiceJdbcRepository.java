package com.edvantis.training.parking.repository;

import com.edvantis.training.parking.models.Owner;

/**
 * Created by taras.fihurnyak on 2/9/2017.
 */
public interface OwnerServiceJdbcRepository {

    Owner getById(String dbName, String login, String password,int id);

    void insert(String dbName, String login, String password, Owner owner);

    void update(String dbName, String login, String password, int ownerId, Owner owner);

    void delete(String dbName, String login, String password, int ownerId);

}
