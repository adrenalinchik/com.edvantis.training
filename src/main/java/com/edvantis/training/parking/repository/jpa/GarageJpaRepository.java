package com.edvantis.training.parking.repository.jpa;

import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.GarageType;
import com.edvantis.training.parking.models.Garage_;
import com.edvantis.training.parking.repository.CrudRepository;
import com.edvantis.training.parking.repository.GarageRepository;
import org.apache.log4j.Logger;

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
public class GarageJpaRepository implements GarageRepository, CrudRepository<Garage> {

    private final Logger logger = Logger.getLogger(GarageJpaRepository.class);

    private EntityManagerFactory emFactory;

    public GarageJpaRepository(EntityManagerFactory entityManagerFactory) {
        emFactory = entityManagerFactory;
    }

    @Override
    public Garage getById(long id) {
        return findById(emFactory, Garage.class, id);
    }

    @Override
    public Set<Garage> getAll() {
        return findAll(emFactory, Garage.class);
    }

    @Override
    public void insert(Garage garage) {
        save(emFactory, garage);
        logger.info(String.format("Garage %s is saved to db successfully.", garage.getId()));
    }

    @Override
    public void update(int id, Garage garage) {
        edit(emFactory, garage);
        logger.info(String.format("Garage %s updated successfully.", garage.getId()));
    }

    public void update(Garage garage) {
        edit(emFactory, garage);
        logger.info(String.format("Garage %s updated successfully.", garage.getId()));
    }

    @Override
    public void delete(long id) {
        remove(emFactory, Garage.class, id);
        logger.info(String.format("Garage %s deleted successfully.", id));
    }

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
