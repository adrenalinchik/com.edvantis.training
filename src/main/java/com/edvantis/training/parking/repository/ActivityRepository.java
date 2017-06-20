package com.edvantis.training.parking.repository;

import com.edvantis.training.parking.models.ActivityLog;
import com.edvantis.training.parking.models.Garage;

import java.util.Set;

/**
 * Created by taras.fihurnyak on 6/14/2017.
 */
public interface ActivityRepository {

    ActivityLog getById(long id);

    Set<ActivityLog> getAll();

    void insert(ActivityLog activity);

    void update(long Id, ActivityLog activity);

    void delete(long id);
}
