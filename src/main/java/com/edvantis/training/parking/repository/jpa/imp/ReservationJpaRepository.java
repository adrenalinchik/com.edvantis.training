package com.edvantis.training.parking.repository.jpa.imp;

import com.edvantis.training.parking.config.ApplicationConfig;
import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.GarageType;
import com.edvantis.training.parking.models.Reservation;
import com.edvantis.training.parking.models.Reservation_;
import com.edvantis.training.parking.repository.GarageRepository;
import com.edvantis.training.parking.repository.ReservationRepository;
import com.edvantis.training.parking.repository.jpa.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 3/7/2017.
 */
public class ReservationJpaRepository extends CrudRepository<Reservation> implements ReservationRepository {

    private final Logger logger = LoggerFactory.getLogger(ReservationJpaRepository.class);

    public ReservationJpaRepository(EntityManagerFactory factory) {
        super(factory);
    }

    @Override
    public Reservation getById(Long id) {
        return findById(Reservation.class, id);
    }

    @Override
    public Set<Reservation> getAll() {
        return findAll(Reservation.class);
    }

    @Override
    public void insert(Reservation reservation) {
        save(reservation);
        logger.info("Reservation id={} is saved to db successfully.", reservation.getId());
    }

    @Override
    public void update(int id, Reservation reservation) {
        edit(reservation);
        logger.info("Reservation id={} updated successfully.", reservation.getId());
    }

    public void update(Reservation reservation) {
        edit(reservation);
        logger.info("Reservation id={} updated successfully.", reservation.getId());
    }

    @Override
    public void delete(long id) {
        remove(Reservation.class, id);
        logger.info("Reservation id={} deleted successfully.", id);
    }

    //get all garages by parking id that don't exist in reservation table
    @Override
    public Set<Garage> getGaragesByParkingId(long parkingId) {
        Set<Garage> garages = new HashSet<>();
        EntityManager em = emFactory.createEntityManager();
        String makeQuery = "SELECT t1.ID " +
                "FROM garage as t1 " +
                "LEFT JOIN reservation as t2 ON t2.GARAGE_ID = t1.ID " +
                "WHERE t2.GARAGE_ID IS NULL and t1.PARKING_ID = " + parkingId;
        List<? extends BigInteger> garageIdList = em.createNativeQuery(makeQuery).getResultList();
        GarageRepository garageJpaRepository = new ApplicationConfig().getGarageRepository();
        for (BigInteger i : garageIdList) {
            garages.add(garageJpaRepository.getById(i.longValue()));
        }
        if (em.isOpen()) {
            em.close();
        }
        return garages;
    }

    //get all garages by garage type that don't exist in reservation table
    @Override
    public Set<Garage> getGaragesByType(GarageType garageType) {
        Set<Garage> garages = new HashSet<>();
        EntityManager em = emFactory.createEntityManager();
        String makeQuery = "SELECT t1.ID " +
                "FROM garage as t1 " +
                "LEFT JOIN reservation as t2 ON t2.GARAGE_ID = t1.ID " +
                "WHERE t2.GARAGE_ID IS NULL and t1.TYPE = ?1";
        List<? extends BigInteger> garageIdList = em.createNativeQuery(makeQuery)
                .setParameter(1, garageType.toString())
                .getResultList();
        if (garageIdList.size() > 0) {
            GarageRepository garageJpaRepository = new ApplicationConfig().getGarageRepository();
            for (BigInteger i : garageIdList) {
                garages.add(garageJpaRepository.getById(i.longValue()));
            }
        }
        if (em.isOpen()) {
            em.close();
        }
        return garages;
    }

    //get all garages that don't exist in reservation table
    @Override
    public Set<Garage> getAllGarages() {
        Set<Garage> garages = new HashSet<>();
        EntityManager em = emFactory.createEntityManager();
        String makeQuery = "SELECT t1.ID " +
                "FROM garage as t1 " +
                "LEFT JOIN reservation as t2 ON t2.GARAGE_ID = t1.ID " +
                "WHERE t2.GARAGE_ID IS NULL";
        List<? extends BigInteger> garageIdList = em.createNativeQuery(makeQuery).getResultList();
        if (garageIdList.size() > 0) {
            GarageRepository garageJpaRepository = new ApplicationConfig().getGarageRepository();
            garageIdList.forEach((i) -> garages.add(garageJpaRepository.getById(i.longValue())));
        }
        if (em.isOpen()) em.close();
        return garages;
    }

    @Override
    public Reservation getLastReservation() {
        EntityManager em = emFactory.createEntityManager();
        String makeQuery = "SELECT r FROM Reservation r order by r.id DESC";
        Reservation lastReservation = (Reservation) em.createQuery(makeQuery)
                .setMaxResults(1)
                .getSingleResult();
        if (em.isOpen()) {
            em.close();
        }
        return lastReservation;
    }

    @Override
    public Set<Reservation> getAllReservations() {
        EntityManager em = emFactory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Reservation> cq = builder.createQuery(Reservation.class);
        Root<Reservation> reservationRoot = cq.from(Reservation.class);
        Set<Reservation> reservationsSet = new HashSet<>(em.createQuery(cq).getResultList());
        if (em.isOpen()) em.close();
        return reservationsSet;
    }

    @Override
    public Set<Reservation> getAllReservationsByParking(long parkingId) {
        EntityManager em = emFactory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Reservation> cq = builder.createQuery(Reservation.class);
        Root<Reservation> reservationRoot = cq.from(Reservation.class);
        cq.where(builder.equal(reservationRoot.get(Reservation_.parkingId), parkingId));
        Set<Reservation> reservationsSet = new HashSet<>(em.createQuery(cq).getResultList());
        if (em.isOpen()) {
            em.close();
        }
        return reservationsSet;
    }

    @Override
    public Set<Reservation> getAllReservationsByGarageType(GarageType garageType) {
        Set<Reservation> reservationsSet = new HashSet<>();
        EntityManager em = emFactory.createEntityManager();
        List garageIdListFinal = new ArrayList<>();
        String makeQueryToReservation = "SELECT GARAGE_ID FROM reservation";
        List garageIdList = em.createNativeQuery(makeQueryToReservation).getResultList();
        if (garageIdList.size() > 0) {
            for (Object i : garageIdList) {
                String makeQueryToGarage = "select t1.ID from garage as t1 " +
                        "where t1.ID=?1 and t1.TYPE=?2";
                List list = em.createNativeQuery(makeQueryToGarage)
                        .setParameter(1, i)
                        .setParameter(2, garageType.toString())
                        .getResultList();
                if (!list.isEmpty()) {
                    garageIdListFinal.add(list.get(0));
                } else continue;
            }
        }
        Set<String> garageIdSetFinal = new HashSet<>();
        garageIdListFinal.forEach((i) -> garageIdSetFinal.add(i.toString()));
        CriteriaBuilder builder = emFactory.createEntityManager().getCriteriaBuilder();
        CriteriaQuery<Reservation> cq = builder.createQuery(Reservation.class);
        Root<Reservation> reservationRoot = cq.from(Reservation.class);
        if (garageIdListFinal.size() > 0) {
            for (Object i : garageIdSetFinal) {
                cq.where(builder.equal(reservationRoot.get(Reservation_.garageId), i));
                List list = em.createQuery(cq).getResultList();
                list.forEach((k) -> reservationsSet.add((Reservation) k));
            }
        }
        if (em.isOpen()) {
            em.close();
        }
        return reservationsSet;
    }
}