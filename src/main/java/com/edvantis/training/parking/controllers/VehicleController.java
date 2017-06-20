package com.edvantis.training.parking.controllers;

import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.services.OwnerService;
import com.edvantis.training.parking.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by taras.fihurnyak on 6/14/2017.
 */
public class VehicleController {
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @RequestMapping(value = "/owner/{ownerId}/vehicles", method = GET)
    public String getAllOwnerVehicles(@PathVariable("ownerId") long ownerId, ModelMap model) {
        List<Vehicle> vehicles = vehicleService.getOwnerVehicles(ownerId);
        model.addAttribute("vehicles", vehicles);
        return "ownerVehicles";
    }
}
