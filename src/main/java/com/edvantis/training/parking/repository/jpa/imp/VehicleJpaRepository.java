package com.edvantis.training.parking.repository.jpa.imp;

import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.repository.VehicleRepository;
import com.edvantis.training.parking.repository.jpa.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/24/2017.
 */
public class VehicleJpaRepository extends CrudRepository<Vehicle> implements VehicleRepository {

    private final Logger logger = LoggerFactory.getLogger(ParkingJpaRepository.class);

    public VehicleJpaRepository(EntityManagerFactory factory) {
        super(factory);
    }

    @Override
    public Vehicle getById(long id) {
        return findById(Vehicle.class, id);
    }

    @Override
    public Set<Vehicle> getAll() {
        return findAll(Vehicle.class);
    }

    @Override
    public void insert(Vehicle vehicle) {
        save(vehicle);
        logger.info("Vehicle id={} is saved to db successfully.", vehicle.getId());
    }

    @Override
    public void update(int id, Vehicle vehicle) {
        edit(vehicle);
        logger.info("Vehicle id={} updated successfully.", vehicle.getId());
    }

    public void update(Vehicle vehicle) {
        edit(vehicle);
        logger.info("Vehicle id={} updated successfully.", vehicle.getId());
    }

    @Override
    public void delete(long id) {
        remove(Vehicle.class, id);
        logger.info("Vehicle id={} deleted successfully.", id);
    }
}
