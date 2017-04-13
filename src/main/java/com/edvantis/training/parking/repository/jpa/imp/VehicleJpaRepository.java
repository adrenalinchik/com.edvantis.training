package com.edvantis.training.parking.repository.jpa.imp;

import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.repository.VehicleRepository;
import com.edvantis.training.parking.repository.jpa.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/24/2017.
 */

@Repository
public class VehicleJpaRepository extends CrudRepository<Vehicle> implements VehicleRepository {

    private final Logger logger = LoggerFactory.getLogger(ParkingJpaRepository.class);

    public VehicleJpaRepository(EntityManagerFactory factory) {
        super(factory, Vehicle.class);
    }
}
