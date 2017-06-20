package com.edvantis.training.parking.services;

import com.edvantis.training.parking.models.Parking;
import com.edvantis.training.parking.models.Reservation;
import com.edvantis.training.parking.models.Vehicle;

import java.util.List;

/**
 * Created by taras.fihurnyak on 6/14/2017.
 */
public interface ParkingService {

    List<Parking> getAllParking();

    List<Parking> getAllInactiveParking();

    List<Parking> getAllActiveParking();

    Parking getParking(long id);

    Parking updateParking(long id, Parking parking);

    void addNewParking(Parking parking);

    void deleteParking(long id);

    Parking getParkingByAddress(String address);
}
