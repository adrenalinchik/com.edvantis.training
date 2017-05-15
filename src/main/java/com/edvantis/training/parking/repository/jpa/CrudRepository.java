package com.edvantis.training.parking.repository.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 4/4/2017.
 */
public abstract class CrudRepository<T> {

    protected EntityManagerFactory emFactory;
    protected Class<T> classType;
    private final Logger logger = LoggerFactory.getLogger(CrudRepository.class);

    protected CrudRepository(EntityManagerFactory emFactory, Class<T> classType) {
        this.emFactory = emFactory;
        this.classType = classType;
    }

    public T getById(long id) {
        EntityManager em = emFactory.createEntityManager();
        T entity = em.find(classType, id);
        if (em.isOpen()) em.close();
        return entity;
    }

    public Set<T> getAll() {
        EntityManager em = emFactory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(classType);
        Root<T> reservationRoot = cq.from(classType);
        Set<T> set = new HashSet<>(em.createQuery(cq).getResultList());
        if (em.isOpen()) em.close();
        return set;
    }

    public void insert(T t) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
        logger.info("{} is saved to db successfully.", t.getClass().getName());
        if (em.isOpen()) em.close();
    }

    public void update(long id, T t) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(t);
        em.getTransaction().commit();
        logger.info("{} id={} is updated successfully.",t.getClass().getName(), id);
        if (em.isOpen()) em.close();
    }

    public void delete(long id) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        T t = em.find(classType, id);
        em.remove(t);
        em.getTransaction().commit();
        logger.info("{} id={} is deleted successfully.",t.getClass().getName(), id);
        if (em.isOpen()) em.close();
    }
}
