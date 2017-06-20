package com.edvantis.training.parking.controllers;

import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.services.OwnerService;
import com.edvantis.training.parking.services.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by taras.fihurnyak on 4/13/2017.
 */
@Controller
@RequestMapping("/parking")
public class OwnerController {

    Logger logger = LoggerFactory.getLogger(OwnerController.class);

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RequestMapping(value = "/owners", method = GET)
    public String getAllOwners(Model model) {
        List<Owner> owners = ownerService.getAllOwners();
        model.addAttribute("owners", owners);
        return "allOwners";
    }
}
