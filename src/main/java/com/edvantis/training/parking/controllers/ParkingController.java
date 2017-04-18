package com.edvantis.training.parking.controllers;

import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.services.ParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by taras.fihurnyak on 4/13/2017.
 */
@RestController
public class ParkingController {

    Logger logger = LoggerFactory.getLogger(ParkingController.class);

    @Autowired
    private ParkingService parkingService;

    @RequestMapping(value = "parking/owner/{ownerId}", method = RequestMethod.GET)
    @ResponseBody
    public Owner getOwner(@PathVariable("ownerId") long ownerId) {
        logger.info("Getting owner with id = {}", ownerId);
        //return parkingService.getOwner(ownerId);
        Owner owner = new Owner();
        owner.setFirstName("aa");
        return owner;

    }

//    @RequestMapping(value = "/owner", method = RequestMethod.GET)
//    public Owner getOwnerByLastName(@RequestParam(value = "name") String name) {
//        return parkingService.getOwnerByLastName(name);
//    }

//    @RequestMapping(value = "/reservation/owner/{ownerId}", method = RequestMethod.GET)
//    public Reservation makeReservation(@PathVariable("ownerId") long ownerId,
//                                       @RequestParam(value = "from") Date from,
//                                       @RequestParam(value = "to") Date to,
//                                       @RequestParam(value = "garageType", required = false) GarageType type) {
//        return type == null ? parkingService.makeReservation(from, to, ownerId) : parkingService.makeReservation(from, to, type, ownerId);
//    }

//    @RequestMapping(value = "/createOwner/owner/{ownerId}", method = RequestMethod.POST)
//    public void addNewOwner(Owner owner){
//
//    }
}
