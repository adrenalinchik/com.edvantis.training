package com.edvantis.training.parking.api;

import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.services.ParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.*;

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
    public List<Vehicle> getAllOwnerVehicles(@PathVariable("ownerId") long ownerId) {
        return parkingService.getOwnerVehicles(ownerId);
    }

    @RequestMapping(value = "/owners", method = GET)
    public List<Owner> getAllOwners() {
        return parkingService.getAllOwners();
    }

    @RequestMapping(value = "/owners/active", method = GET)
    public List<Owner> getAllActiveOwners() {
        return parkingService.getAllActiveOwners();
    }

    @RequestMapping(value = "/owners/inactive", method = GET)
    public List<Owner> getAllInactiveOwners() {
        return parkingService.getAllInactiveOwners();
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "/owner/createOwner", method = POST)
    public Owner addNewOwner(@RequestBody Owner owner) {
        parkingService.addNewOwner(owner);
        return parkingService.getOwnerByLastName(owner.getLastName());
    }

    @RequestMapping(value = "/owner/updateOwner", method = PUT)
    public Owner updateOwner(@RequestBody Owner owner) {
        return parkingService.updateOwner(owner.getId(), owner);
    }

    @RequestMapping(value = "/owner/delete/{ownerId}", method = DELETE)
    public void deleteOwner(@PathVariable("ownerId") long ownerId) {
        parkingService.deleteOwner(ownerId);
    }
}
