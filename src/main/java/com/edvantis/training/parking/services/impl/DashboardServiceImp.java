package com.edvantis.training.parking.services.impl;

import com.edvantis.training.parking.models.ActivityLog;
import com.edvantis.training.parking.repository.ActivityRepository;
import com.edvantis.training.parking.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by taras.fihurnyak on 6/15/2017.
 */
@Service
public class DashboardServiceImp implements DashboardService {

    @Autowired
    private ActivityRepository activityRepo;

    @Autowired
    public DashboardServiceImp(ActivityRepository activityRepo) {
        this.activityRepo = activityRepo;
    }

    @Override
    public void addNewActivity(ActivityLog activity) {
        activityRepo.insert(activity);
    }

    @Override
    public List<ActivityLog> getAllActivities() {
        List<ActivityLog> logList = new ArrayList<>(activityRepo.getAll());
        logList.sort(Comparator.comparing(ActivityLog::getCreatedDate).reversed());
        return logList;
    }

    @Override
    public void deleteActivities() {
        List<ActivityLog> list = getAllActivities();
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date yesterday = cal.getTime();
        for (ActivityLog i : list) {
            Date activityDate = i.getCreatedDate();
            if (activityDate.before(yesterday))
                activityRepo.delete(i.getId());
        }
        if (list.size() > 15) {
            System.out.println("15 index: " + list.get(15));
            for (int i = 15; i <= list.size() - 1; i++) {
                activityRepo.delete(list.get(i).getId());
            }
        }
    }
}
