package com.edvantis.training.parking.services.impl;

import com.edvantis.training.parking.config.ApplicationConfig;
import com.edvantis.training.parking.models.*;
import com.edvantis.training.parking.repository.*;
import com.edvantis.training.parking.services.ParkingService;

import java.util.*;

/**
 * Created by taras.fihurnyak on 2/11/2017.
 */
public class ParkingServiceImp implements ParkingService {

    private OwnerRepository ownerRepo;
    private VehicleRepository vehicleRepo;
    private GarageRepository garageRepo;
    private ParkingRepository parkingRepo;
    private ReservationRepository reservationRepo;

    public ParkingServiceImp(OwnerRepository ownerRepo, VehicleRepository vehicleRepo, GarageRepository garageRepo, ParkingRepository parkingRepo, ReservationRepository reservationRepo) {
        this.ownerRepo = ownerRepo;
        this.vehicleRepo = vehicleRepo;
        this.garageRepo = garageRepo;
        this.parkingRepo = parkingRepo;
        this.reservationRepo = reservationRepo;
    }

    @Override
    public Owner getOwnerByVehicleNumber(String vehicleNumber) {
        return ownerRepo.getByVehicleNumber(vehicleNumber);
    }

    @Override
    public void populateWithMockObjects(ArrayList<Object> arrayList) {
        for (Object obj : arrayList) {
            if (obj instanceof Owner) {
                ownerRepo.insert((Owner) obj);
            } else if (obj instanceof Vehicle) {
                vehicleRepo.insert((Vehicle) obj);
            } else if (obj instanceof Garage) {
                garageRepo.insert((Garage) obj);
            } else if (obj instanceof Parking) {
                parkingRepo.insert((Parking) obj);
            } else if (obj instanceof Reservation) {
                reservationRepo.insert((Reservation) obj);
            }
        }
    }

    @Override
    public void addNewOwner(Owner owner) {
        ownerRepo.insert(owner);
    }

    @Override
    public Set<Owner> getAllOwners() {
        return ownerRepo.getAll();
    }

    @Override
    public Owner getOwnerByLastName(String ownerLastName) {

        return ownerRepo.getByLastName(ownerLastName);
    }

    @Override
    public Reservation makeReservation(Date from, Date to, GarageType type, long ownerId) {
        Set<Garage> availableGarages = getAvailableGaragesByType(from, to, type);
        Reservation reserv = null;
        if (availableGarages.size() > 0) {
            Garage garage = availableGarages.stream().findFirst().get();
            reserv = new Reservation();
            reserv.setBegin(from);
            reserv.setEnd(to);
            reserv.setGarageId(garage.getId());
            reserv.setParkingId(garage.getParking().getId());
            reserv.setOwnerId(ownerId);
            reservationRepo.insert(reserv);
            reserv = reservationRepo.getById(reserv.getId());
        }
        return reserv;
    }

    @Override
    public Reservation makeReservation(Date from, Date to, long ownerId) {
        Set<Garage> availableGarages = getAvailableGarages(from, to);
        Reservation reserv = null;
        if (availableGarages.size() > 0) {
            Garage garage = availableGarages.stream().findFirst().get();
            reserv = new Reservation();
            reserv.setBegin(from);
            reserv.setEnd(to);
            reserv.setGarageId(garage.getId());
            reserv.setParkingId(garage.getParking().getId());
            reserv.setOwnerId(ownerId);
            reservationRepo.insert(reserv);
            reserv = reservationRepo.getById(reserv.getId());
        }
        return reserv;
    }

    @Override
    public Set<Garage> getAvailableGarages(Date from, Date to) {
        Set<Garage> garagesSet = new HashSet<>();
        HashMap<Reservation, Boolean> reservations = new HashMap<>();
        Set<Reservation> reservationsList = reservationRepo.getAllReservations();
        for (Reservation i : reservationsList) {
            Date reservBegine = i.getBegin();
            Date reservEnd = i.getEnd();
            if (from.before(reservBegine) && (to.before(reservBegine) || to.equals(reservBegine))) {
                reservations.put(i, true);
            } else if (to.after(reservEnd) && (from.after(reservEnd) || from.equals(reservEnd))) {
                reservations.put(i, true);
            } else reservations.put(i, false);
        }
        reservations = deleteFalseReservation(reservations);
        Set<Long> garagesId = getFinalAvailableGarageId(reservations);
        garagesId.forEach((i) -> garagesSet.add(new ApplicationConfig().getGarageRepository().getById(i)));
        garagesSet.addAll(reservationRepo.getAllGarages());
        return garagesSet;
    }

    @Override
    public Set<Garage> getAvailableGaragesByType(Date from, Date to, GarageType garageType) {
        Set<Garage> garagesSet = new HashSet<>();
        HashMap<Reservation, Boolean> reservations = new HashMap<>();
        Set<Reservation> reservationsList = reservationRepo.getAllReservationsByGarageType(garageType);
        for (Reservation i : reservationsList) {
            Date reservBegine = i.getBegin();
            Date reservEnd = i.getEnd();
            if (from.before(reservBegine) && (to.before(reservBegine) || to.equals(reservBegine))) {
                reservations.put(i, true);
            } else if (to.after(reservEnd) && (from.after(reservEnd) || from.equals(reservEnd))) {
                reservations.put(i, true);
            } else reservations.put(i, false);
        }
        reservations = deleteFalseReservation(reservations);
        Set<Long> garagesId = getFinalAvailableGarageId(reservations);
        garagesId.forEach((i)-> garagesSet.add(new ApplicationConfig().getGarageRepository().getById(i)));
        garagesSet.addAll(reservationRepo.getGaragesByType(garageType));

        return garagesSet;
    }

    @Override
    public Set<Garage> getAvailableGaragesByParking(Date from, Date to, long parkingId) {
        Set<Garage> garagesSet = new HashSet<>();
        HashMap<Reservation, Boolean> reservations = new HashMap<>();
        Set<Reservation> reservationsList = reservationRepo.getAllReservationsByParking(parkingId);
        for (Reservation i : reservationsList) {
            Date reservBegine = i.getBegin();
            Date reservEnd = i.getEnd();
            if (from.before(reservBegine) && (to.before(reservBegine) || to.equals(reservBegine))) {
                reservations.put(i, true);
            } else if (to.after(reservEnd) && (from.after(reservEnd) || from.equals(reservEnd))) {
                reservations.put(i, true);
            } else reservations.put(i, false);
        }
        reservations = deleteFalseReservation(reservations);
        Set<Long> garagesId = getFinalAvailableGarageId(reservations);
        garagesId.forEach((i)-> garagesSet.add(new ApplicationConfig().getGarageRepository().getById(i)));
        garagesSet.addAll(reservationRepo.getGaragesByParkingId(parkingId));
        return garagesSet;
    }

    private Set<Long> getFinalAvailableGarageId(HashMap<Reservation, Boolean> mapReservations) {
        Set<Long> garagesId = new HashSet<>();
        mapReservations.forEach((k, v) -> {
            if (v)
                garagesId.add(k.getGarageId());
        });
        return garagesId;
    }

    private HashMap<Reservation, Boolean> deleteFalseReservation(HashMap<Reservation, Boolean> mapReservations) {
        Map<Reservation, Boolean> reservationsFalseValue = new HashMap<>();
        mapReservations.forEach((k, v) -> {
            if (!v) reservationsFalseValue.put(k, v);
        });
        reservationsFalseValue.forEach((k, v) -> {
            long garageId = k.getGarageId();
            for (Iterator<Map.Entry<Reservation, Boolean>> it = mapReservations.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<Reservation, Boolean> entry = it.next();
                if (entry.getKey().getGarageId() == garageId && entry.getValue()) {
                    it.remove();
                }
            }
        });
        return mapReservations;
    }
}
