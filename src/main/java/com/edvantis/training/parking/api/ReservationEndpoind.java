package com.edvantis.training.parking.api;

import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.enums.GarageType;
import com.edvantis.training.parking.models.Reservation;
import com.edvantis.training.parking.services.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by taras.fihurnyak on 5/3/2017.
 */

@RestController
@RequestMapping("/parking/api")
public class ReservationEndpoind {
    private final static Logger logger = LoggerFactory.getLogger(ReservationEndpoind.class);

    private final ReservationService reservationService;

    @Autowired
    public ReservationEndpoind(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @RequestMapping(value = "/reservations/active", method = GET)
    public List<Reservation> getAllActiveReservations() {
        reservationService.checkIfReservationExpired();
        return reservationService.getAllActiveReservations();
    }

    @RequestMapping(value = "/reservations/inactive", method = GET)
    public List<Reservation> getAllInactiveReservations() {
        return reservationService.getAllInactiveReservations();
    }

    //Get All Reservations
    @RequestMapping(value = "/reservations", method = GET)
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @RequestMapping(value = "/reservationsByDate", method = GET)
    public List<Reservation> getAllReservationsByStartDate(@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date) {
        return reservationService.getAllReservationsByStartDate(date);
    }

    //Get All Garages available on particular parking for specific dates
    @RequestMapping(value = "/reservation/availableGarages/parking/{parkingId}", method = GET)
    public List<Garage> getAvailableGaragesByParking(@PathVariable("parkingId") long parkingId,
                                                     @RequestParam(value = "from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date from,
                                                     @RequestParam(value = "to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date to) {

        return reservationService.getAvailableGaragesByParking(from, to, parkingId);
    }

    //Get All Garages (or only specified type) available on any parking for specific dates
    @RequestMapping("/reservation/availableGarages")
    public List<Garage> getAvailableGarages(@RequestParam(value = "from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date from,
                                            @RequestParam(value = "to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date to,
                                            @RequestParam(value = "type", required = false) GarageType type) {
        return type == null ? reservationService.getAvailableGarages(from, to) : reservationService.getAvailableGaragesByType(from, to, type);
    }

    //Create Reservation on any or specific parking with any garage or specified type for specific dates
    @ResponseStatus(CREATED)
    @RequestMapping(value = "/reservation/addReservation", method = POST)
    public Reservation makeReservation(@RequestBody Reservation reser,
                                       @RequestParam(value = "type", required = false) GarageType type) {
        return type == null ? reservationService.makeReservation(reser) : reservationService.makeReservation(reser, type);
    }

    @RequestMapping(value = "/reservation/updateReservation", method = PUT)
    public Reservation updateReservation(@RequestBody Reservation reser) {
        return reservationService.updateReservation(reser.getId(), reser);
    }

    @RequestMapping(value = "/reservation/delete/{reservId}", method = DELETE)
    public void deleteReservation(@PathVariable("reservId") long reservId) {
        reservationService.deleteReservation(reservId);
    }
}
