package com.edvantis.training.parking.api;

import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.GarageType;
import com.edvantis.training.parking.models.Reservation;
import com.edvantis.training.parking.services.ParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by taras.fihurnyak on 5/3/2017.
 */

@RestController
@RequestMapping("/parking/api")
public class ReservationEndpoind {
    private final static Logger logger = LoggerFactory.getLogger(ReservationEndpoind.class);

    private final ParkingService parkingService;

    @Autowired
    public ReservationEndpoind(ParkingService parkingService) {
        this.parkingService = parkingService;
    }


    //Get All Reservations
    @RequestMapping(value = "/reservations", method = GET)
    public List<Reservation> getAllReservations() {
        return parkingService.getAllReservations();
    }

    //Get All Garages available on particular parking for specific dates
    @RequestMapping(value = "/reservation/availableGarages/parking/{parkingId}", method = GET)
    public List<Garage> getAvailableGaragesByParking(@PathVariable("parkingId") long parkingId,
                                                     @RequestParam(value = "from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                                     @RequestParam(value = "to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
        return parkingService.getAvailableGaragesByParking(from, to, parkingId);
    }

    //Get All Garages (or only specified type) available on any parking for specific dates
    @RequestMapping("/reservation/availableGarages")
    public List<Garage> getAvailableGarages(@RequestParam(value = "from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                            @RequestParam(value = "to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
                                            @RequestParam(value = "type", required = false) GarageType type) {
        return type == null ? parkingService.getAvailableGarages(from, to) : parkingService.getAvailableGaragesByType(from, to, type);
    }

    //Create Reservation on any or specific parking with any garage or specified type for specific dates
    @ResponseStatus(CREATED)
    @RequestMapping(value = "/reservations/addReservation", method = POST)
    public Reservation makeReservation(@RequestBody Reservation reser,
                                       @RequestParam(value = "type", required = false) GarageType type) {
        logger.info("Reservation for {}", reser.getOwnerId());
        return type == null ? parkingService.makeReservation(reser) : parkingService.makeReservation(reser, type);
    }


}
