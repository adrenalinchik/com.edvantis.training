package com.edvantis.training.parking.services.impl;

import com.edvantis.training.parking.models.Parking;
import com.edvantis.training.parking.models.enums.ModelState;
import com.edvantis.training.parking.repository.ParkingRepository;
import com.edvantis.training.parking.services.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taras.fihurnyak on 6/14/2017.
 */
@Service
public class ParkingServiceImp implements ParkingService {

    @Autowired
    private ParkingRepository parkingRepo;

    @Autowired
    public ParkingServiceImp(ParkingRepository parkingRepo) {
        this.parkingRepo = parkingRepo;
    }

    @Override
    public List<Parking> getAllParking() {
        return new ArrayList<>(parkingRepo.getAll());
    }

    @Override
    public List<Parking> getAllInactiveParking() {
        return new ArrayList<>(parkingRepo.getActiveOrInactive(ModelState.INACTIVE));
    }

    @Override
    public List<Parking> getAllActiveParking() {
        return new ArrayList<>(parkingRepo.getActiveOrInactive(ModelState.ACTIVE));
    }

    @Override
    public Parking getParking(long id) {
        return parkingRepo.getById(id);
    }

    @Override
    public Parking updateParking(long id, Parking parking) {
        parkingRepo.update(id, parking);
        return parkingRepo.getById(id);
    }

    @Override
    public void addNewParking(Parking parking) {
        parkingRepo.insert(parking);
    }

    @Override
    public void deleteParking(long id) {
        parkingRepo.delete(id);
    }

    @Override
    public Parking getParkingByAddress(String address) {
        return parkingRepo.getParkingByAddress(address);
    }
}
