package com.edvantis.training.parking.services;

import com.edvantis.training.parking.models.*;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by taras.fihurnyak on 2/11/2017.
 */
public interface ParkingService {

    void populateWithMockObjects(ArrayList<Object> arrayList);

    void addNewOwner(Owner owner);

    ArrayList<Owner> getAllOwners();

    Owner getOwner(long id);

    Owner getOwnerByLastName(String ownerLastName);

    Owner getOwnerByVehicleNumber(String vehicleNumber);

    ArrayList<Vehicle> getOwnerVehicles(long ownerId);

    Reservation makeReservation(Date from, Date to, GarageType type, long ownerId);

    Reservation makeReservation(Date from, Date to, long ownerId);

    ArrayList<Garage> getAvailableGaragesByType(Date from, Date to, GarageType garageType);

    ArrayList<Garage> getAvailableGaragesByParking(Date from, Date to, long parkingId);

    ArrayList<Garage> getAvailableGarages(Date from, Date to);

    Owner updateOwner(long id, Owner owner);

    ArrayList<Garage> getAllParkingGarages(long parkingId);

    ArrayList<Garage> getAllGarages();
}
