package com.edvantis.training.parking.repository.jpa;

import com.edvantis.training.parking.factory.ApplicationConfig;
import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.GarageType;
import com.edvantis.training.parking.models.Reservation;
import com.edvantis.training.parking.models.Reservation_;
import com.edvantis.training.parking.repository.GarageRepository;
import com.edvantis.training.parking.repository.ReservationRepository;
import org.apache.log4j.Logger;

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
public class ReservationJpaRepository implements ReservationRepository {

    private final Logger logger = Logger.getLogger(ReservationJpaRepository.class);

    private EntityManagerFactory emFactory;

    public ReservationJpaRepository(EntityManagerFactory entityManagerFactory) {
        emFactory = entityManagerFactory;
    }

    @Override
    public Reservation getById(Integer id) {
        EntityManager em = null;
        Reservation reservation = null;
        try {
            em = emFactory.createEntityManager();
            reservation = em.find(Reservation.class, id.longValue());
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
            em = emFactory.createEntityManager();
            em.getTransaction().begin();
            em.persist(reservation);
            em.getTransaction().commit();
            logger.info("Reservation " + reservation.getId() + " is saved to db successfully." + " gargeId: " + reservation.getGarageId() + " parkingId: " + reservation.getParkingId());
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
            em = emFactory.createEntityManager();
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
            em = emFactory.createEntityManager();
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
            em = emFactory.createEntityManager();
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

    public Reservation getLastReservation() {
        Reservation lastReservation = null;
        EntityManager em = null;
        try {
            em = emFactory.createEntityManager();
            String makeQuery = "SELECT r FROM Reservation r order by r.id DESC";
            lastReservation = (Reservation) em.createQuery(makeQuery)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return lastReservation;
    }

    public void delete(Reservation reservation) {
        EntityManager em = null;
        try {
            em = emFactory.createEntityManager();
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


    //get all garages by parking id that don't exist in reservation table
    public Set<Garage> getGaragesByParkingId(long parkingId) {
        Set<Garage> garages = new HashSet<>();
        EntityManager em = null;
        try {
            em = emFactory.createEntityManager();
            String makeQuery = "SELECT t1.ID " +
                    "FROM test.garage as t1 " +
                    "LEFT JOIN test.reservation as t2 ON t2.GARAGE_ID = t1.ID " +
                    "WHERE t2.GARAGE_ID IS NULL and t1.PARKING_ID = " + parkingId;
            List<? extends BigInteger> garageIdList = em.createNativeQuery(makeQuery).getResultList();
            GarageRepository garageJpaRepository = new ApplicationConfig().getGarageRepository();
            for (BigInteger i : garageIdList) {
                garages.add(garageJpaRepository.getById(i.longValue()));
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

    //get all garages by garage type that don't exist in reservation table
    public Set<Garage> getGaragesByType(GarageType garageType) {
        Set<Garage> garages = new HashSet<>();
        EntityManager em = null;
        try {
            em = emFactory.createEntityManager();
            String makeQuery = "SELECT t1.ID " +
                    "FROM test.garage as t1 " +
                    "LEFT JOIN test.reservation as t2 ON t2.GARAGE_ID = t1.ID " +
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
            em = emFactory.createEntityManager();
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

    public Set<Reservation> getAllReservationsByGarageType(GarageType garageType) {
        EntityManager em = null;
        Set<Reservation> reservationsSet = new HashSet<>();
        try {
            em = emFactory.createEntityManager();
            List garageIdListFinal = new ArrayList<>();
            String makeQueryToReservation = "SELECT GARAGE_ID FROM test.reservation";
            List garageIdList = em.createNativeQuery(makeQueryToReservation).getResultList();
            if (garageIdList.size() > 0) {
                for (Object i : garageIdList) {
                    String makeQueryToGarage = "select t1.ID from test.garage as t1 " +
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
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return reservationsSet;
    }

}
