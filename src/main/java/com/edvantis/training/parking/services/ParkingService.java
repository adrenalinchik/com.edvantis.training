package com.edvantis.training.parking.services;

import com.edvantis.training.parking.models.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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

    void deleteOwner(long id);

    Map<Owner, Long> getProfitForAllOwners(Date from, Date to) throws ExecutionException, InterruptedException;

    long countProfitFromOwner(Date from, Date to, long ownerId);

    List<Owner> getAllActiveOwners();

    List<Owner> getAllInactiveOwners();

    void deleteVehicle(long id);

    List<Vehicle> getAllVehicles();

    List<Vehicle> getAllActiveVehicles();

    List<Vehicle> getAllInactiveVehicles();

    Vehicle getVehicle(long id);

    Vehicle updateVehicle(long id, Vehicle vehicle);

    void addNewVehicle(Vehicle vehicle);

    Vehicle getVehicleByNumber(String number);

    List<Reservation> getAllInactiveReservations();

    List<Reservation> getAllActiveReservations();

    Reservation updateReservation(long id, Reservation reservation);

}
