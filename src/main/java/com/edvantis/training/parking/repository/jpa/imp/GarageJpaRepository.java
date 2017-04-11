package com.edvantis.training.parking.repository.jpa.imp;

import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.GarageType;
import com.edvantis.training.parking.models.Garage_;
import com.edvantis.training.parking.repository.GarageRepository;
import com.edvantis.training.parking.repository.jpa.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/22/2017.
 */
public class GarageJpaRepository extends CrudRepository<Garage> implements GarageRepository {

    private final Logger logger = LoggerFactory.getLogger(GarageJpaRepository.class);

    public GarageJpaRepository(EntityManagerFactory factory) {
        super(factory);
    }

    @Override
    public Garage getById(long id) {
        return findById(Garage.class, id);
    }

    @Override
    public Set<Garage> getAll() {
        return findAll(Garage.class);
    }

    @Override
    public void insert(Garage garage) {
        save(garage);
        logger.info("Garage id={} is saved to db successfully.", garage.getId());
    }

    @Override
    public void update(int id, Garage garage) {
        edit(garage);
        logger.info("Garage id={} updated successfully.", garage.getId());
    }

    public void update(Garage garage) {
        edit(garage);
        logger.info("Garage id={} updated successfully.", garage.getId());
    }

    @Override
    public void delete(long id) {
        remove(Garage.class, id);
        logger.info("Garage id={} deleted successfully.", id);
    }

    @Override
    public Set<Garage> getAllGaragesByType(GarageType garageType) {
        EntityManager em = emFactory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Garage> cq = builder.createQuery(Garage.class);
        Root<Garage> garage = cq.from(Garage.class);
        cq.where(builder.equal(garage.get(Garage_.garageType), garageType));
        Set<Garage> garageSet = new HashSet<>(em.createQuery(cq).getResultList());
        if (em.isOpen()) {
            em.close();
        }
        return garageSet;
    }
}
