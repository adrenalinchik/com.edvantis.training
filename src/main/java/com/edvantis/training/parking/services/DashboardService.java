package com.edvantis.training.parking.services;

import com.edvantis.training.parking.models.ActivityLog;
import com.edvantis.training.parking.models.Owner;

import java.util.List;

/**
 * Created by taras.fihurnyak on 6/15/2017.
 */
public interface DashboardService {

    void addNewActivity(ActivityLog activity);

    List<ActivityLog> getAllActivities();

    void deleteActivities();
}
