package com.edvantis.training.parking.services;

import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.Reservation;
import com.edvantis.training.parking.models.enums.GarageType;

import java.util.Date;
import java.util.List;

/**
 * Created by taras.fihurnyak on 6/14/2017.
 */
public interface ReservationService {

    Reservation makeReservation(Reservation reserv, GarageType type);

    Reservation makeReservation(Reservation reserv);

    List<Garage> getAvailableGaragesByType(Date from, Date to, GarageType garageType);

    List<Garage> getAvailableGaragesByParking(Date from, Date to, long parkingId);

    List<Garage> getAvailableGarages(Date from, Date to);

    List<Reservation> getAllReservations();

    List<Reservation> getAllInactiveReservations();

    List<Reservation> getAllActiveReservations();

    Reservation updateReservation(long id, Reservation reservation);

    void deleteReservation(long id);

    void checkIfReservationExpired();

    List<Reservation> getAllReservationsByStartDate(Date startDate);
}
