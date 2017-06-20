package com.edvantis.training.parking.services.impl;

import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.repository.*;
import com.edvantis.training.parking.services.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taras.fihurnyak on 6/14/2017.
 */

@Service
public class GarageServiceImp implements GarageService {

    @Autowired
    private GarageRepository garageRepo;

    @Autowired
    public GarageServiceImp(GarageRepository garageRepo) {
        this.garageRepo = garageRepo;
    }

    @Override
    public List<Garage> getAllParkingGarages(long parkingId) {
        return new ArrayList<>(garageRepo.getGaragesByParking(parkingId));
    }

    @Override
    public List<Garage> getAllGarages() {
        return new ArrayList<>(garageRepo.getAll());
    }
}
