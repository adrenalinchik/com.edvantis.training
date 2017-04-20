package com.edvantis.training.parking.controllers;

import com.edvantis.training.parking.models.GarageType;
import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.models.Reservation;
import com.edvantis.training.parking.services.ParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by taras.fihurnyak on 4/13/2017.
 */
@RestController
@RequestMapping("/parking")
public class ParkingController {

    Logger logger = LoggerFactory.getLogger(ParkingController.class);

    @Autowired
    private ParkingService parkingService;

    @RequestMapping(value = "/owner/{ownerId}", method = RequestMethod.GET)
    public Owner getOwner(@PathVariable("ownerId") long ownerId) {
        logger.info("Getting owner with id = {}", ownerId);
        return parkingService.getOwner(ownerId);
    }

    @RequestMapping(value = "/owner", method = RequestMethod.GET)
    public Owner getOwnerByLastName(@RequestParam(value = "name") String name) {
        return parkingService.getOwnerByLastName(name);
    }

    @RequestMapping(value = "/reservation/owner/{ownerId}", method = RequestMethod.GET)
    public Reservation makeReservation(@PathVariable("ownerId") long ownerId,
                                       @RequestParam(value = "from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                       @RequestParam(value = "to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
                                       @RequestParam(value = "garageType", required = false) GarageType type) {
        logger.info("Reservation for {}", ownerId);
        return type == null ? parkingService.makeReservation(from, to, ownerId) : parkingService.makeReservation(from, to, type, ownerId);
    }

    @RequestMapping(value = "/createOwner", method = RequestMethod.POST)
    public Owner addNewOwner(@RequestBody Owner owner) {
        parkingService.addNewOwner(owner);
        return parkingService.getOwnerByLastName(owner.getLastName());
    }

    @RequestMapping(value = "/updateOwner", method = RequestMethod.POST)
    public Owner updateOwner(@RequestBody Owner owner) {
        return parkingService.updateOwner(owner.getId(), owner);
    }

}
