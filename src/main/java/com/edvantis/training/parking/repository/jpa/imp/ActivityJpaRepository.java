package com.edvantis.training.parking.repository.jpa.imp;

import com.edvantis.training.parking.models.ActivityLog;
import com.edvantis.training.parking.repository.ActivityRepository;
import com.edvantis.training.parking.repository.jpa.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;

/**
 * Created by taras.fihurnyak on 6/14/2017.
 */
@Repository
public class ActivityJpaRepository extends CrudRepository<ActivityLog> implements ActivityRepository {

    @Autowired
    public ActivityJpaRepository(EntityManagerFactory factory) {
        super(factory, ActivityLog.class);
    }

    @Override
    public ActivityLog getById(long id) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void update(long id, ActivityLog activityLog) {
        throw new UnsupportedOperationException("Not supported");
    }

}
