package com.edvantis.training.parking.repository;

import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.GarageType;
import com.edvantis.training.parking.models.Reservation;

import java.util.Set;

/**
 * Created by taras.fihurnyak on 3/7/2017.
 */
public interface ReservationRepository {

    Reservation getById(Integer id);

    void insert(Reservation garage);

    void update(int reservationId, Reservation garage);

    void delete(int garageId);

    Set<Reservation> getAllReservationsByParking(long parkingId);

    Set<Garage> getGaragesByParkingId(long parkingId);

    Set<Reservation> getAllReservationsByGarageType(GarageType garageType);

    Set<Garage> getGaragesByType(GarageType garageType);

    Reservation getLastReservation();

}
