package com.edvantis.training.parking.repository.jpa.imp;

import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.repository.VehicleRepository;
import com.edvantis.training.parking.repository.jpa.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
    public Set<Vehicle> getOwnerVehicles(long ownerId) {
        Set<Vehicle> vehicles = new HashSet<>();
        EntityManager em = emFactory.createEntityManager();
        String makeQuery = "SELECT t1.ID " +
                "FROM vehicle as t1 " +
                "WHERE t1.OWNER_ID = " + ownerId;
        List<? extends BigInteger> vehicleIdList = em.createNativeQuery(makeQuery).getResultList();
        vehicleIdList.forEach(i -> vehicles.add(getById(i.longValue()))
        );
//        for (BigInteger i : vehicleIdList) {
//            vehicles.add(getById(i.longValue()));
//        }
        if (em.isOpen()) em.close();
        return vehicles;
    }
}
