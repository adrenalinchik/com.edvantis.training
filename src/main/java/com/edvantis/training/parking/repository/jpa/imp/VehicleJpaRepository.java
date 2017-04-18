package com.edvantis.training.parking.repository.jpa.imp;

import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.repository.VehicleRepository;
import com.edvantis.training.parking.repository.jpa.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;

/**
 * Created by taras.fihurnyak on 2/24/2017.
 */

@Repository
public class VehicleJpaRepository extends CrudRepository<Vehicle> implements VehicleRepository {

    @Autowired
    public VehicleJpaRepository(EntityManagerFactory factory) {
        super(factory, Vehicle.class);
    }
}
