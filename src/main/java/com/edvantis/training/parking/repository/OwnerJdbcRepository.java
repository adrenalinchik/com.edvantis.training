package com.edvantis.training.parking.repository;

import com.edvantis.training.parking.models.Owner;

import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/9/2017.
 */
public interface OwnerJdbcRepository {

    Owner getById(int id);

    void insert(Owner owner);

    void update(int ownerId, Owner owner);

    void delete(int ownerId);

    Set<Owner> getAllOwnersFromDb();

    Owner getOwnerByLastName(String lastName);

    Owner getOwnerByVehicleNymber(String vehicleNumber);

    int getOwnerIdByLastName(String ownerLastName);


}
