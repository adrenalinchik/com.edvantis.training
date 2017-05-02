package com.edvantis.training.parking.controllers;

import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.services.ParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by taras.fihurnyak on 4/13/2017.
 */
@Controller
@RequestMapping("/parking")
public class ParkingController {

    Logger logger = LoggerFactory.getLogger(ParkingController.class);

    private final ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @RequestMapping(value = "/owners", method = GET)
    public String getAllOwners(Model model) {
        ArrayList<Owner> owners = parkingService.getAllOwners();
        model.addAttribute("owners", owners);
        return "allOwners";//new ModelAndView("allOwners","model", model);
    }

    @RequestMapping(value = "/owner/{ownerId}/vehicles", method = GET)
    public String getAllOwnerVehicles(@PathVariable("ownerId") long ownerId, ModelMap model) {
        ArrayList<Vehicle> vehicles = parkingService.getOwnerVehicles(ownerId);
        model.addAttribute("vehicles",vehicles);
        return "ownerVehicles";
    }
}
