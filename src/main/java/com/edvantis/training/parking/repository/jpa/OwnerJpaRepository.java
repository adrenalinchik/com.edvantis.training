package com.edvantis.training.parking.repository.jpa;

import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.models.Owner_;
import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.models.Vehicle_;
import com.edvantis.training.parking.repository.CrudRepository;
import com.edvantis.training.parking.repository.OwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/24/2017.
 */
public class OwnerJpaRepository extends CrudRepository<Owner> implements OwnerRepository {

    private final Logger logger = LoggerFactory.getLogger(OwnerJpaRepository.class);

    public OwnerJpaRepository() {
       super();
    }

    @Override
    public Owner getById(long id) {
        return findById(Owner.class, id);
    }

    @Override
    public Set<Owner> getAll() {
        return findAll(Owner.class);
    }

    @Override
    public void insert(Owner owner) {
        save(owner);
        logger.info("Owner {} {} saved to db successfully.", owner.getFirstName(), owner.getFirstName());
    }

    @Override
    public void update(int id, Owner owner) {
        edit(owner);
        logger.info("Owner with id={} id updated successfully.", owner.getId());
    }

    @Override
    public void delete(long id) {
        remove(Owner.class, id);
        logger.info("Owner with id={} id removed from db successfully.",id);
    }

    public void update(Owner owner) {
        edit(owner);
        logger.info("Owner with id={} id updated successfully.", owner.getId());
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
