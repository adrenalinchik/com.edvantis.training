package com.edvantis.training.parking.repository.jpa;

import com.edvantis.training.parking.jpa.JpaUtility;
import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.GarageType;
import com.edvantis.training.parking.models.Reservation;
import com.edvantis.training.parking.models.Reservation_;
import com.edvantis.training.parking.repository.ReservationRepository;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Created by taras.fihurnyak on 3/7/2017.
 */
public class ReservationJpaRepository implements ReservationRepository {

    private final Logger logger = Logger.getLogger(ReservationJpaRepository.class);

    @Override
    public Reservation getById(int id) {
        EntityManager em = null;
        Reservation reservation = null;
        try {
            em = JpaUtility.getEntityManager();
            reservation = em.find(Reservation.class, id);
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return reservation;
    }

    @Override
    public void insert(Reservation reservation) {
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            em.getTransaction().begin();
            em.persist(reservation);
            em.getTransaction().commit();
            logger.info("Reservation " + reservation.getId() + " is saved to db successfully.");
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

    }

    @Override
    public void update(int reservationId, Reservation reservation) {
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            em.getTransaction().begin();
            em.merge(reservation);
            em.getTransaction().commit();
            logger.info("Reservation " + reservation.getId() + " updated successfully.");
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

    }

    public void update(Reservation reservation) {
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            em.getTransaction().begin();
            em.merge(reservation);
            em.getTransaction().commit();
            logger.info("Reservation " + reservation.getId() + " updated successfully.");
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void delete(int reservationId) {
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            em.getTransaction().begin();
            em.remove(em.find(Garage.class, reservationId));
            em.getTransaction().commit();
            logger.info("Reservation " + reservationId + " deleted successfully.");
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

    }

    public void delete(Reservation reservation) {
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            em.getTransaction().begin();
            em.remove(em.find(Garage.class, reservation.getId()));
            em.getTransaction().commit();
            logger.info("Garage " + reservation.getId() + " deleted successfully.");
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    //get all garages that don't exist in reservation table
    public Set<Garage> getGaragesByParkingId(long parkingId) {
        Set<Garage> garages = new HashSet<>();
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            String makeQuery = "SELECT t1.ID\n" +
                    "FROM test.garage as t1\n" +
                    "LEFT JOIN test.reservation as t2 ON t2.GARAGE_ID = t1.ID\n" +
                    "WHERE t2.GARAGE_ID IS NULL and t1.PARKING_ID = " + parkingId;
            ArrayList<Integer> garageIdList = new ArrayList<>(em.createNativeQuery(makeQuery).getResultList());
            int[] garagesId = garageIdList.stream().mapToInt(i -> i).toArray();
            GarageJpaRepository garageJpaRepository = new GarageJpaRepository();
            for (int i = 0; i < garagesId.length; i++) {
                garages.add(garageJpaRepository.getById(garagesId[i]));
            }
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return garages;
    }

    public Set<Reservation> getAllReservationsByParking(long parkingId) {
        EntityManager em = null;
        Set<Reservation> reservationsSet = null;
        try {
            em = JpaUtility.getEntityManager();
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<Reservation> cq = builder.createQuery(Reservation.class);
            Root<Reservation> reservationRoot = cq.from(Reservation.class);
            cq.where(builder.equal(reservationRoot.get(Reservation_.parkingId), parkingId));
            reservationsSet = new HashSet<>(em.createQuery(cq).getResultList());
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return reservationsSet;

    }

    //Todo:3/10/2017 - makeReservation method is not finished. Finish makeReservation method
    private void makeReservation(Date from, Date to, GarageType type) {
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            em.getTransaction().begin();
            String makeReservationQuery = "";
            em.createQuery(makeReservationQuery);
            em.getTransaction().commit();
            // logger.info("Garage "+reservation.getId()+" deleted successfully.");
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public Set<Garage> getAvailableGarages(Date from, Date to, long parkingId) {
        Set<Garage> garagesSet = new HashSet<>();
        HashMap<Reservation, Boolean> reservations = new HashMap<>();
        Set<Reservation> reservationsList = getAllReservationsByParking(parkingId);
        for (Reservation i : reservationsList) {
            Date reservBegine = i.getBegin();
            Date reservEnd = i.getEnd();
            if (from.before(reservBegine) && (to.before(reservBegine) || to.equals(reservBegine))) {
                reservations.put(i, true);
            } else reservations.put(i, false);
            if (to.after(reservEnd) && (from.after(reservEnd) || from.equals(reservEnd))) {
                reservations.put(i, true);
            } else reservations.put(i, false);
        }
        reservations = deleteDublicates(reservations);
        reservations.forEach((k, v) -> {
            if (v) garagesSet.add(new GarageJpaRepository().getById(k.getGarageId()));
        });
        garagesSet.addAll(getGaragesByParkingId(parkingId));
        return garagesSet;
    }

    private HashMap<Reservation, Boolean> deleteDublicates(HashMap<Reservation, Boolean> mapReservations) {
        Map<Reservation, Boolean> reservationsFalseValue = new HashMap<>();
        mapReservations.forEach((k, v) -> {
            if (!v) reservationsFalseValue.put(k, v);
        });
        reservationsFalseValue.forEach((k, v) -> {
            long garageId = k.getGarageId();
            for (Iterator<Map.Entry<Reservation, Boolean>> it = mapReservations.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<Reservation, Boolean> entry = it.next();
                if (entry.getKey().getGarageId() == garageId && entry.getValue()) {
                    it.remove();
                }
            }
        });
        return mapReservations;
    }

}
