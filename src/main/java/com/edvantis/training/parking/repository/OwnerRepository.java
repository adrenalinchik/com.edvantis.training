package com.edvantis.training.parking.repository;

import com.edvantis.training.parking.models.Owner;

import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/9/2017.
 */
public interface OwnerRepository {

    Owner getById(long id);

    void insert(Owner owner);

    void update(long Id, Owner owner);

    void delete(long Id);

    Set<Owner> getAll();

    Owner getByLastName(String lastName);

    Owner getByVehicleNumber(String vehicleNumber);
}
