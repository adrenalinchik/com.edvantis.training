package com.edvantis.training.parking.services.impl;

import com.edvantis.training.parking.jdbc.AppProperty;
import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.models.Reservation;
import com.edvantis.training.parking.models.enums.ModelState;
import com.edvantis.training.parking.repository.GarageRepository;
import com.edvantis.training.parking.repository.OwnerRepository;
import com.edvantis.training.parking.repository.ReservationRepository;
import com.edvantis.training.parking.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by taras.fihurnyak on 6/14/2017.
 */
@Service
public class OwnerServiceImp implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepo;

    @Autowired
    private ReservationRepository reservationRepo;

    @Autowired
    public OwnerServiceImp(OwnerRepository ownerRepo, ReservationRepository reservationRepo) {

        this.ownerRepo = ownerRepo;

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
    public void addNewOwner(Owner owner) {
        ownerRepo.insert(owner);
    }

    @Override
    public Owner updateOwner(long id, Owner owner) {
        ownerRepo.update(id, owner);
        return ownerRepo.getById(id);
    }

    @Override
    public void deleteOwner(long id) {
        ownerRepo.delete(id);
    }

    @Override
    public List<Owner> getAllOwners() {
        return new ArrayList<>(ownerRepo.getAll());
    }

    @Override
    public List<Owner> getAllActiveOwners() {
        return new ArrayList<>(ownerRepo.getActiveOrInactive(ModelState.ACTIVE));
    }

    @Override
    public List<Owner> getAllInactiveOwners() {
        return new ArrayList<>(ownerRepo.getActiveOrInactive(ModelState.INACTIVE));
    }

    @Override
    public Owner getOwnerByLastName(String ownerLastName) {

        return ownerRepo.getByLastName(ownerLastName);
    }

    @Override
    public Map<Owner, Long> getProfitForAllOwners(Date from, Date to) throws ExecutionException, InterruptedException {
        List<Owner> owners = getAllOwners();
        Map<Owner, Long> ownerProfit = new HashMap<>();
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(owners.size());
            for (Owner i : owners) {
                Future<Long> result = service.submit(() -> countProfitFromOwner(from, to, i.getId()));
                ownerProfit.put(i, result.get());
            }
        } finally {
            if (service != null) service.shutdown();
        }
        return ownerProfit;
    }

    @Override
    public long countProfitFromOwner(Date from, Date to, long ownerId) {
        Set<Reservation> reservationsList = reservationRepo.getAllReservationsByOwner(ownerId);
        long diffHours = 0;
        for (Reservation i : reservationsList) {
            Date reservBegine = i.getBegin();
            Date reservEnd = i.getEnd();
            if (from.before(reservEnd) && reservBegine.before(to)) {
                Date maxStart = from.after(reservBegine) ? from : reservBegine;
                Date minEnd = to.before(reservEnd) ? to : reservEnd;
                long diff = minEnd.getTime() - maxStart.getTime();
                diffHours += TimeUnit.MILLISECONDS.toHours(diff);
            }
        }
        return diffHours * Long.parseLong(new AppProperty().getApplicationProperties().getProperty("parkingRate"));
    }

}
