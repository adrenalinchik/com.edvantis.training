package com.edvantis.training.parking.repository.jpa.imp;

import com.edvantis.training.parking.models.*;
import com.edvantis.training.parking.models.enums.ModelState;
import com.edvantis.training.parking.repository.VehicleRepository;
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
 * Created by taras.fihurnyak on 2/24/2017.
 */

@Repository
public class VehicleJpaRepository extends CrudRepository<Vehicle> implements VehicleRepository {

    @Autowired
    public VehicleJpaRepository(EntityManagerFactory factory) {
        super(factory, Vehicle.class);
    }

    @Override
    public Set<Vehicle> getActiveOrInactive(ModelState state) {
        EntityManager em = emFactory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Vehicle> cq = builder.createQuery(Vehicle.class);
        Root<Vehicle> vehicleRoot = cq.from(Vehicle.class);
        cq.where(builder.equal(vehicleRoot.get(Vehicle_.state), state));
        Set<Vehicle> vehicles = new HashSet<>(em.createQuery(cq).getResultList());
        if (em.isOpen()) {
            em.close();
        }
        return vehicles;
    }

    @Override
    public Set<Vehicle> getOwnerVehicles(long ownerId) {
        Set<Vehicle> vehicles = new HashSet<>();
        EntityManager em = emFactory.createEntityManager();
        String makeQuery = "SELECT t1.ID " +
                "FROM VEHICLE as t1 " +
                "WHERE t1.OWNER_ID = " + ownerId +
                " AND t1.STATE = 'ACTIVE'";
        List<? extends BigInteger> vehicleIdList = em.createNativeQuery(makeQuery).getResultList();
        vehicleIdList.forEach(i -> vehicles.add(getById(i.longValue())));
        if (em.isOpen()) em.close();
        return vehicles;
    }

    @Override
    public Vehicle getVehicleByNumber(String number) {
        EntityManager em = emFactory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Vehicle> cq = builder.createQuery(Vehicle.class);
        Root<Vehicle> vehicleRoot = cq.from(Vehicle.class);
        cq.where(builder.equal(vehicleRoot.get(Vehicle_.number), number));
        Vehicle vehicle = em.createQuery(cq).getSingleResult();
        if (em.isOpen()) {
            em.close();
        }
        return vehicle;
    }
}
