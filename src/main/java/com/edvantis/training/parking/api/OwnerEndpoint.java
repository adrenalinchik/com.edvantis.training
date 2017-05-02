package com.edvantis.training.parking.api;

import com.edvantis.training.parking.models.*;
import com.edvantis.training.parking.services.ParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/parking/api")
public class OwnerEndpoint {

    private final static Logger logger = LoggerFactory.getLogger(OwnerEndpoint.class);

    private final ParkingService parkingService;

    @Autowired
    public OwnerEndpoint(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @RequestMapping(value = "/owner/{ownerId}", method = GET)
    public Owner getOwner(@PathVariable("ownerId") long ownerId) {
        return parkingService.getOwner(ownerId);
    }

    @RequestMapping(value = "/owner", method = GET)
    public Owner getOwnerByLastName(@RequestParam(value = "name") String name) {
        return parkingService.getOwnerByLastName(name);
    }

    @RequestMapping(value = "/owner/{ownerId}/vehicles", method = GET)
    public ArrayList<Vehicle> getAllOwnerVehicles(@PathVariable("ownerId") long ownerId) {
        return parkingService.getOwnerVehicles(ownerId);
    }

    @RequestMapping(value = "/garages/parking/{parkingId}", method = GET)
    public ArrayList<Garage> getGarages(@PathVariable("parkingId") long parkingId) {
        return parkingService.getAllParkingGarages(parkingId);
    }
    @RequestMapping(value = "/owners", method = GET)
    public ArrayList<Owner> getAllOwners() {
        return parkingService.getAllOwners();
    }

    @RequestMapping(value = "/garages", method = GET)
    public ArrayList<Garage> getAllGarages() {
        return parkingService.getAllGarages();
    }

    @RequestMapping(value = "/reservation/owner/{ownerId}", method = GET)
    public Reservation makeReservation(@PathVariable("ownerId") long ownerId,
                                       @RequestParam(value = "from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                       @RequestParam(value = "to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
                                       @RequestParam(value = "garageType", required = false) GarageType type) {
        logger.info("Reservation for {}", ownerId);
        return type == null ? parkingService.makeReservation(from, to, ownerId) : parkingService.makeReservation(from, to, type, ownerId);
    }

    @RequestMapping(value = "/reservation/availableGarages/parking/{parkingId}", method = GET)
    public ArrayList<Garage> getAvailableGaragesByParking(@PathVariable("parkingId") long parkingId,
                                                 @RequestParam(value = "from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                                 @RequestParam(value = "to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
        logger.info("Available garages are gotten");
        return parkingService.getAvailableGaragesByParking(from, to, parkingId);
    }

    @RequestMapping(value = "/reservation/availableGarages", method = GET)
    public ArrayList<Garage> getAvailableGarages(@RequestParam(value = "from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                                 @RequestParam(value = "to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
                                                 @RequestParam(value = "type", required = false) GarageType type) {
        logger.info("Available garages are got");
        return type == null ? parkingService.getAvailableGarages(from, to) : parkingService.getAvailableGaragesByType(from, to, type);
    }

    @RequestMapping(value = "/createOwner", method = POST)
    public Owner addNewOwner(@RequestBody Owner owner) {
        parkingService.addNewOwner(owner);
        return parkingService.getOwnerByLastName(owner.getLastName());
    }

    @RequestMapping(value = "/updateOwner", method = POST)
    public Owner updateOwner(@RequestBody Owner owner) {
        return parkingService.updateOwner(owner.getId(), owner);
    }
}
