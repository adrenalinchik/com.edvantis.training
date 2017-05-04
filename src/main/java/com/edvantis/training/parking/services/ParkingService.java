package com.edvantis.training.parking.services;

import com.edvantis.training.parking.models.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by taras.fihurnyak on 2/11/2017.
 */
public interface ParkingService {

    void populateWithMockObjects(ArrayList<Object> arrayList);

    void addNewOwner(Owner owner);

    List<Owner> getAllOwners();

    Owner getOwner(long id);

    Owner getOwnerByLastName(String ownerLastName);

    Owner getOwnerByVehicleNumber(String vehicleNumber);

    List<Vehicle> getOwnerVehicles(long ownerId);

    Reservation makeReservation(Reservation reserv, GarageType type);

    Reservation makeReservation(Reservation reserv);

    List<Garage> getAvailableGaragesByType(Date from, Date to, GarageType garageType);

    List<Garage> getAvailableGaragesByParking(Date from, Date to, long parkingId);

    List<Garage> getAvailableGarages(Date from, Date to);

    Owner updateOwner(long id, Owner owner);

    List<Garage> getAllParkingGarages(long parkingId);

    List<Garage> getAllGarages();

    List<Reservation> getAllReservations();
}
