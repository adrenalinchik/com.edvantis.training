package com.edvantis.training.parking.repository.jpa.imp;

import com.edvantis.training.parking.models.Parking;
import com.edvantis.training.parking.models.Parking_;
import com.edvantis.training.parking.models.Reservation;
import com.edvantis.training.parking.models.Reservation_;
import com.edvantis.training.parking.models.enums.ModelState;
import com.edvantis.training.parking.repository.ParkingRepository;
import com.edvantis.training.parking.repository.jpa.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/24/2017.
 */

@Repository
public class ParkingJpaRepository extends CrudRepository<Parking> implements ParkingRepository {

    @Autowired
    public ParkingJpaRepository(EntityManagerFactory factory) {
        super(factory, Parking.class);
    }

    @Override
    public Set<Parking> getActiveOrInactive(ModelState state) {
        EntityManager em = emFactory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Parking> cq = builder.createQuery(Parking.class);
        Root<Parking> parkingRoot = cq.from(Parking.class);
        cq.where(builder.equal(parkingRoot.get(Parking_.state), state));
        Set<Parking> parking = new HashSet<>(em.createQuery(cq).getResultList());
        if (em.isOpen()) {
            em.close();
        }
        return parking;
    }

    @Override
    public Parking getParkingByAddress(String address) {
        EntityManager em = emFactory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Parking> cq = builder.createQuery(Parking.class);
        Root<Parking> parkingRoot = cq.from(Parking.class);
        cq.where(builder.equal(parkingRoot.get(Parking_.address), address));
        Parking parking = em.createQuery(cq).getSingleResult();
        if (em.isOpen()) {
            em.close();
        }
        return parking;

    }
}