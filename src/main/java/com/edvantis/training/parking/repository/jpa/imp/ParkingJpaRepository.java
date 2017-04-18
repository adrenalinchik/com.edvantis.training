package com.edvantis.training.parking.repository.jpa.imp;

import com.edvantis.training.parking.models.Parking;
import com.edvantis.training.parking.repository.ParkingRepository;
import com.edvantis.training.parking.repository.jpa.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;

/**
 * Created by taras.fihurnyak on 2/24/2017.
 */

@Repository
public class ParkingJpaRepository extends CrudRepository<Parking> implements ParkingRepository {

    @Autowired
    public ParkingJpaRepository(EntityManagerFactory factory) {
        super(factory, Parking.class);
    }

}