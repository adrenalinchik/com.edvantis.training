package com.edvantis.training.parking.repository.jpa;

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

    protected CrudRepository(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    public T findById(Class<T> classType, Long id) {
        EntityManager em = emFactory.createEntityManager();
        T entity = em.find(classType, id);
        if (em.isOpen()) em.close();
        return entity;
    }

    public Set<T> findAll(Class<T> classType) {
        EntityManager em = emFactory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(classType);
        Root<T> reservationRoot = cq.from(classType);
        Set<T> reservationsSet = new HashSet<>(em.createQuery(cq).getResultList());
        if (em.isOpen()) em.close();
        return reservationsSet;
    }

    public void save(T t) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
        if (em.isOpen()) em.close();
    }

    public void edit(T t) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(t);
        em.getTransaction().commit();
        if (em.isOpen()) em.close();
    }

    public void remove(Class<T> classType, Long id) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(classType, id));
        em.getTransaction().commit();
        if (em.isOpen()) em.close();
    }
}
