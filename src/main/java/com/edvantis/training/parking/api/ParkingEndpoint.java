package com.edvantis.training.parking.api;

import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.models.Parking;
import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.repository.ParkingRepository;
import com.edvantis.training.parking.services.HelpService;
import com.edvantis.training.parking.services.ParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by taras.fihurnyak on 6/1/2017.
 */
@RestController
@RequestMapping("/parking/api")
public class ParkingEndpoint {
    private final static Logger logger = LoggerFactory.getLogger(ParkingEndpoint.class);

    private final ParkingService parkingService;

    @Autowired
    public ParkingEndpoint(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @RequestMapping(value = "/parkings", method = GET)
    public List<Parking> getAllParking() {
        return parkingService.getAllParking();
    }

    @RequestMapping(value = "/parking/active", method = GET)
    public List<Parking> getAllActiveParking() {
        return parkingService.getAllActiveParking();
    }

    @RequestMapping(value = "/parking/inactive", method = GET)
    public List<Parking> getAllInactiveParking() {
        return parkingService.getAllInactiveParking();
    }

    @RequestMapping(value = "/parking/{id}", method = GET)
    public Parking getParking(@PathVariable("id") long id) {
        return parkingService.getParking(id);
    }

    @RequestMapping(value = "/parking/update", method = PUT)
    public Parking updateParking(@RequestBody Parking parking) {
        return parkingService.updateParking(parking.getId(), parking);
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "/parking/create", method = POST)
    public Parking addNewParking(@RequestBody Parking parking) {
        parkingService.addNewParking(parking);
        return parkingService.getParkingByAddress(parking.getAddress());
    }

    @RequestMapping(value = "/parking/delete/{id}", method = DELETE)
    public void deleteParking(@PathVariable("id") long id) {
        parkingService.deleteParking(id);
    }
}
