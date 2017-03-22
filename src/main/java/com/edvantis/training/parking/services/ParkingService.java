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

    Owner getOwnerByLastName(String ownerLastName);

    Vehicle getVehicleByNumber(String vehicleNumber);

    Owner getOwnerByVehicleNumber(String vehicleNumber);

    Set<Vehicle> getAllVehicleByType(VehicleType vehicleType);

    void makeReservation(Date from, Date to, GarageType type, long ownerId);

    Set<Garage> getAvailableGaragesByGarageType(Date from, Date to, GarageType garageType);

    Set<Garage> getAvailableGaragesByParking(Date from, Date to, long parkingId);


}
