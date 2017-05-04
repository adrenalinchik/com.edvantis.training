package com.edvantis.training.parking.services.impl;

import com.edvantis.training.parking.models.*;
import com.edvantis.training.parking.repository.*;
import com.edvantis.training.parking.services.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by taras.fihurnyak on 2/11/2017.
 */

@Service
public class ParkingServiceImp implements ParkingService {
    @Autowired
    private OwnerRepository ownerRepo;
    @Autowired
    private VehicleRepository vehicleRepo;
    @Autowired
    private GarageRepository garageRepo;
    @Autowired
    private ParkingRepository parkingRepo;
    @Autowired
    private ReservationRepository reservationRepo;

    @Autowired
    public ParkingServiceImp(OwnerRepository ownerRepo, VehicleRepository vehicleRepo, GarageRepository garageRepo, ParkingRepository parkingRepo, ReservationRepository reservationRepo) {
        this.ownerRepo = ownerRepo;
        this.vehicleRepo = vehicleRepo;
        this.garageRepo = garageRepo;
        this.parkingRepo = parkingRepo;
        this.reservationRepo = reservationRepo;
    }

    @Override
    public Owner getOwner(long id) {
        return ownerRepo.getById(id);
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
    public Owner updateOwner(long id, Owner owner) {
        ownerRepo.update(id, owner);
        return ownerRepo.getById(id);
    }

    @Override
    public List<Garage> getAllParkingGarages(long parkingId) {
        return new ArrayList<>(garageRepo.getGaragesByParking(parkingId));
    }

    @Override
    public List<Owner> getAllOwners() {
        return new ArrayList<>(ownerRepo.getAll());
    }

    @Override
    public List<Garage> getAllGarages() {
        return new ArrayList<>(garageRepo.getAll());
    }


    @Override
    public List<Vehicle> getOwnerVehicles(long ownerId) {
        return new ArrayList<>(vehicleRepo.getOwnerVehicles(ownerId));
    }

    @Override
    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservationRepo.getAllReservations());
    }

    @Override
    public Owner getOwnerByLastName(String ownerLastName) {

        return ownerRepo.getByLastName(ownerLastName);
    }

    @Override
    public Reservation makeReservation(Reservation reserv, GarageType type) {
        List<Garage> availableGarages = null;
        if (reserv.getBegin() != null && reserv.getEnd() != null) {
            availableGarages = getAvailableGaragesByType(reserv.getBegin(), reserv.getEnd(), type);
            if (availableGarages.size() > 0) {
                Garage garage = availableGarages.stream().findFirst().get();
                reserv.setGarageId(garage.getId());
                reserv.setParkingId(garage.getParking().getId());
                reservationRepo.insert(reserv);
            }
        }
        return reservationRepo.getById(reserv.getId());
    }

    @Override
    public Reservation makeReservation(Reservation reserv) {
        List<Garage> availableGarages = null;
        if (reserv.getBegin() != null && reserv.getEnd() != null) {
            if (reserv.getParkingId() != 0) {
                availableGarages = getAvailableGaragesByParking(reserv.getBegin(), reserv.getEnd(), reserv.getParkingId());
            } else availableGarages = getAvailableGarages(reserv.getBegin(), reserv.getEnd());
        }
        if (availableGarages.size() > 0) {
            Garage garage = availableGarages.stream().findFirst().get();
            reserv.setGarageId(garage.getId());
            reserv.setParkingId(garage.getParking().getId());
            reservationRepo.insert(reserv);
        }
        return reservationRepo.getById(reserv.getId());
    }

    @Override
    public List<Garage> getAvailableGarages(Date from, Date to) {
        List<Garage> garagesSet = new ArrayList<>();
        HashMap<Reservation, Boolean> reservations =
                filterReservations(reservationRepo.getAllReservations(), from, to);
        Set<Long> garagesId = getFinalAvailableGarageId(deleteFalseReservation(reservations));
        garagesId.forEach((i) -> garagesSet.add(garageRepo.getById(i)));
        garagesSet.addAll(reservationRepo.getAllGarages());
        return garagesSet;
    }

    @Override
    public List<Garage> getAvailableGaragesByType(Date from, Date to, GarageType garageType) {
        ArrayList<Garage> garagesSet = new ArrayList<>();
        HashMap<Reservation, Boolean> reservations =
                filterReservations(reservationRepo.getAllReservationsByGarageType(garageType), from, to);
        Set<Long> garagesId = getFinalAvailableGarageId(deleteFalseReservation(reservations));
        garagesId.forEach((i) -> garagesSet.add(garageRepo.getById(i)));
        garagesSet.addAll(reservationRepo.getGaragesByType(garageType));
        return garagesSet;
    }

    @Override
    public List<Garage> getAvailableGaragesByParking(Date from, Date to, long parkingId) {
        List<Garage> garagesList = new ArrayList<>();
        HashMap<Reservation, Boolean> reservations =
                filterReservations(reservationRepo.getAllReservationsByParking(parkingId), from, to);
        Set<Long> garagesId = getFinalAvailableGarageId(deleteFalseReservation(reservations));
        garagesId.forEach((i) -> garagesList.add(garageRepo.getById(i)));
        garagesList.addAll(reservationRepo.getGaragesByParkingId(parkingId));
        return garagesList;
    }

    private HashMap<Reservation, Boolean> filterReservations(Set<Reservation> reservationsList, Date from, Date
            to) {
        HashMap<Reservation, Boolean> reservations = new HashMap<>();
        for (Reservation i : reservationsList) {
            Date reservBegine = i.getBegin();
            Date reservEnd = i.getEnd();
            if (from.before(reservBegine) && (to.before(reservBegine) || to.equals(reservBegine))) {
                reservations.put(i, true);
            } else if (to.after(reservEnd) && (from.after(reservEnd) || from.equals(reservEnd))) {
                reservations.put(i, true);
            } else reservations.put(i, false);
        }
        return reservations;
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
