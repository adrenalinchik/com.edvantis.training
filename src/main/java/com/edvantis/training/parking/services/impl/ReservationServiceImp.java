package com.edvantis.training.parking.services.impl;

import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.Reservation;
import com.edvantis.training.parking.models.enums.GarageType;
import com.edvantis.training.parking.models.enums.ModelState;
import com.edvantis.training.parking.repository.*;
import com.edvantis.training.parking.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by taras.fihurnyak on 6/14/2017.
 */
@Service
public class ReservationServiceImp implements ReservationService{


    @Autowired
    private GarageRepository garageRepo;

    @Autowired
    private ReservationRepository reservationRepo;

    @Autowired
    public ReservationServiceImp(GarageRepository garageRepo, ReservationRepository reservationRepo) {

        this.garageRepo = garageRepo;

        this.reservationRepo = reservationRepo;
    }

    @Override
    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservationRepo.getAllReservations());
    }

    @Override
    public List<Reservation> getAllActiveReservations() {
        return new ArrayList<>(reservationRepo.getActiveOrInactive(ModelState.ACTIVE));
    }

    @Override
    public List<Reservation> getAllInactiveReservations() {
        return new ArrayList<>(reservationRepo.getActiveOrInactive(ModelState.INACTIVE));
    }

    @Override
    public List<Reservation> getAllReservationsByStartDate(Date startDate) {
        List<Reservation> allReservations = getAllReservations();
        List<Reservation> reservationsByDate = new ArrayList<>();
        Calendar reservCal = Calendar.getInstance();
        reservCal.setTime(startDate);
        int year = reservCal.get(Calendar.YEAR);
        int month = reservCal.get(Calendar.MONTH);
        int day = reservCal.get(Calendar.DAY_OF_MONTH);
        for (Reservation i : allReservations) {
            reservCal.setTime(i.getBegin());
            int reservYear = reservCal.get(Calendar.YEAR);
            int reservMonth = reservCal.get(Calendar.MONTH);
            int reservDay = reservCal.get(Calendar.DAY_OF_MONTH);
            if (reservYear == year && reservMonth == month && reservDay == day) {
                reservationsByDate.add(i);
            }
        }
        return reservationsByDate;
    }

    @Override
    public Reservation updateReservation(long id, Reservation reservation) {
        reservationRepo.update(id, reservation);
        return reservationRepo.getById(id);
    }

    @Override
    public void deleteReservation(long id) {
        reservationRepo.delete(id);
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

    @Override
    public void checkIfReservationExpired() {
        List<Reservation> resrList = getAllActiveReservations();
        resrList.forEach(i -> {
            if (i.getEnd().before(new Date())) {
                i.setState(ModelState.INACTIVE);
                updateReservation(i.getId(), i);
            }
        });
    }

    private HashMap<Reservation, Boolean> filterReservations(Set<Reservation> reservationsList, Date from, Date to) {
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
            if (v) garagesId.add(k.getGarageId());
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
