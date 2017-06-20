package com.edvantis.training.parking.api;

import com.edvantis.training.parking.models.ActivityLog;
import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.services.DashboardService;
import com.edvantis.training.parking.services.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by taras.fihurnyak on 6/15/2017.
 */

@RestController
@RequestMapping("/parking/api")
public class DashboardEndpoint {

    private final DashboardService dashboardService;

    @Autowired
    public DashboardEndpoint(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @RequestMapping(value = "/activities", method = GET)
    public List<ActivityLog> getAllActivities() {
        return dashboardService.getAllActivities();
    }

    @RequestMapping(value = "/activity/create", method = POST)
    public void addNewOwner(@RequestBody ActivityLog activity) {
        activity.setCreatedDate(new Date());
        dashboardService.addNewActivity(activity);
    }

    @RequestMapping(value = "/activities/delete", method = DELETE)
    public void deleteActivities() {
        dashboardService.deleteActivities();
    }
}
