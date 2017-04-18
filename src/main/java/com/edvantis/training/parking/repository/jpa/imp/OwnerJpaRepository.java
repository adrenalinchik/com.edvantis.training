package com.edvantis.training.parking.repository.jpa.imp;

import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.models.Owner_;
import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.models.Vehicle_;
import com.edvantis.training.parking.repository.OwnerRepository;
import com.edvantis.training.parking.repository.jpa.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;

/**
 * Created by taras.fihurnyak on 2/24/2017.
 */

@Repository
public class OwnerJpaRepository extends CrudRepository<Owner> implements OwnerRepository {

    @Autowired
    public OwnerJpaRepository(EntityManagerFactory factory) {
        super(factory,Owner.class);
    }

    @Override
    public Owner getByLastName(String lastName) {
        EntityManager em = emFactory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Owner> cq = builder.createQuery(Owner.class);
        Root<Owner> ownerRoot = cq.from(Owner.class);
        cq.where(builder.equal(ownerRoot.get(Owner_.lastName), lastName));
        Owner owner = em.createQuery(cq).getSingleResult();
        if (em.isOpen()) {
            em.close();
        }
        return owner;
    }

    @Override
    public Owner getByVehicleNumber(String vehicleNumber) {
        EntityManager em = emFactory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Owner> cq = builder.createQuery(Owner.class);
        Root<Owner> ownerRoot = cq.from(Owner.class);
        SetJoin<Owner, Vehicle> products = ownerRoot.join(Owner_.userVehicles);
        Predicate predicate = builder.equal(products.get(Vehicle_.number), vehicleNumber);
        cq.where(predicate);
        Owner owner = em.createQuery(cq).getSingleResult();
        if (em.isOpen()) {
            em.close();
        }
        return owner;
    }
}
