package com.edvantis.training.parking.repository.jpa;

import com.edvantis.training.parking.models.Parking;
import com.edvantis.training.parking.repository.CrudRepository;
import com.edvantis.training.parking.repository.ParkingRepository;
import org.apache.log4j.Logger;

import javax.persistence.EntityManagerFactory;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/24/2017.
 */
public class ParkingJpaRepository implements ParkingRepository, CrudRepository<Parking> {

    private final Logger logger = Logger.getLogger(ParkingJpaRepository.class);
    private EntityManagerFactory emFactory;

    public ParkingJpaRepository(EntityManagerFactory entityManagerFactory) {
        emFactory = entityManagerFactory;
    }

    @Override
    public Parking getById(long id) {
        return findById(emFactory, Parking.class, id);
    }

    @Override
    public Set<Parking> getAll() {
        return findAll(emFactory, Parking.class);
    }

    @Override
    public void insert(Parking parking) {
        save(emFactory, parking);
        logger.info(String.format("Parking with %s id saved to db successfully.", parking.getId()));
    }

    @Override
    public void update(int id, Parking parking) {
        edit(emFactory, parking);
        logger.info(String.format("Parking with %s id updated successfully.", parking.getId()));
    }

    public void update(Parking parking) {
        edit(emFactory, parking);
        logger.info(String.format("Parking with %s id updated successfully.", parking.getId()));
    }

    @Override
    public void delete(long id) {
        remove(emFactory, Parking.class, id);
        logger.info(String.format("Parking with %s id removed from db successfully.", id));
    }
}