package com.edvantis.training.parking.services;

import com.edvantis.training.parking.models.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/11/2017.
 */
public interface ParkingService {

    void populateWithMockObjects(ArrayList<Object> arrayList);

    void addNewOwner(Owner owner);

    Set<Owner> getAllOwners();

    Owner getOwner(long id);

    Owner getOwnerByLastName(String ownerLastName);

    Owner getOwnerByVehicleNumber(String vehicleNumber);

    Reservation makeReservation(Date from, Date to, GarageType type, long ownerId);

    Reservation makeReservation(Date from, Date to, long ownerId);

    Set<Garage> getAvailableGaragesByType(Date from, Date to, GarageType garageType);

    Set<Garage> getAvailableGaragesByParking(Date from, Date to, long parkingId);

    Set<Garage> getAvailableGarages(Date from, Date to);

    Owner updateOwner(long id, Owner owner);
}
