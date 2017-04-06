package com.edvantis.training.parking.repository.jpa;

import com.edvantis.training.parking.models.Parking;
import com.edvantis.training.parking.repository.CrudRepository;
import com.edvantis.training.parking.repository.ParkingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/24/2017.
 */
public class ParkingJpaRepository extends CrudRepository<Parking> implements ParkingRepository {

    private final Logger logger = LoggerFactory.getLogger(ParkingJpaRepository.class);

    public ParkingJpaRepository() {
        super();
    }

    @Override
    public Parking getById(long id) {
        return findById(Parking.class, id);
    }

    @Override
    public Set<Parking> getAll() {
        return findAll(Parking.class);
    }

    @Override
    public void insert(Parking parking) {
        save(parking);
        logger.info("Parking with id={} id saved to db successfully.", parking.getId());
    }

    @Override
    public void update(int id, Parking parking) {
        edit(parking);
        logger.info("Parking with id={} id updated successfully.", parking.getId());
    }

    public void update(Parking parking) {
        edit(parking);
        logger.info("Parking with id={} id updated successfully.", parking.getId());
    }

    @Override
    public void delete(long id) {
        remove(Parking.class, id);
        logger.info("Parking with id={} id removed from db successfully.", id);
    }
}