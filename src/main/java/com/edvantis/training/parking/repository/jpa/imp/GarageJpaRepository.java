package com.edvantis.training.parking.repository.jpa.imp;

import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.GarageType;
import com.edvantis.training.parking.models.Garage_;
import com.edvantis.training.parking.repository.GarageRepository;
import com.edvantis.training.parking.repository.jpa.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/22/2017.
 */
@Repository
public class GarageJpaRepository extends CrudRepository<Garage> implements GarageRepository {

    @Autowired
    public GarageJpaRepository(EntityManagerFactory factory) {
        super(factory, Garage.class);
    }

    @Override
    public Set<Garage> getGaragesByParking(long parkingId) {
        Set<Garage> garages = new HashSet<>();
        EntityManager em = emFactory.createEntityManager();
        String makeQuery = "SELECT t1.ID " +
                "FROM garage as t1 " +
                "WHERE t1.PARKING_ID = ?1";
        List<? extends BigInteger> garageIdList = em.createNativeQuery(makeQuery)
                .setParameter(1, parkingId)
                .getResultList();
        garageIdList.stream().forEach(i-> garages.add(getById(i.longValue())));
//        if (garageIdList.size() > 0) {
//            for (BigInteger i : garageIdList) {
//                garages.add(getById(i.longValue()));
//            }
//        }
        if (em.isOpen()) {
            em.close();
        }
        return garages;
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
