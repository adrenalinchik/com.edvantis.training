package com.edvantis.training.parking.repository.jpa;

import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.repository.CrudRepository;
import com.edvantis.training.parking.repository.VehicleRepository;
import org.apache.log4j.Logger;

import javax.persistence.EntityManagerFactory;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/24/2017.
 */
public class VehicleJpaRepository implements VehicleRepository, CrudRepository<Vehicle> {

    private final Logger logger = Logger.getLogger(ParkingJpaRepository.class);

    private EntityManagerFactory emFactory;

    public VehicleJpaRepository(EntityManagerFactory entityManagerFactory) {
        emFactory = entityManagerFactory;
    }

    @Override
    public Vehicle getById(long id) {
        return findById(emFactory, Vehicle.class, id);
    }

    @Override
    public Set<Vehicle> getAll() {
        return findAll(emFactory, Vehicle.class);
    }

    @Override
    public void insert(Vehicle vehicle) {
        save(emFactory, vehicle);
        logger.info(String.format("Vehicle %s is saved to db successfully.", vehicle.getId()));
    }

    @Override
    public void update(int id, Vehicle vehicle) {
        edit(emFactory, vehicle);
        logger.info(String.format("Vehicle %s updated successfully.", vehicle.getId()));
    }

    public void update(Vehicle vehicle) {
        edit(emFactory, vehicle);
        logger.info(String.format("Vehicle %s updated successfully.", vehicle.getId()));
    }

    @Override
    public void delete(long id) {
        remove(emFactory, Vehicle.class, id);
        logger.info(String.format("Vehicle %s deleted successfully.", id));
    }
}
