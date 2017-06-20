package com.edvantis.training.parking.services.impl;

import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.models.enums.ModelState;
import com.edvantis.training.parking.repository.*;
import com.edvantis.training.parking.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taras.fihurnyak on 6/14/2017.
 */
@Service
public class VehicleServiceImp implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepo;

    @Autowired
    public VehicleServiceImp(VehicleRepository vehicleRepo) {
        this.vehicleRepo = vehicleRepo;
    }

    @Override
    public void addNewVehicle(Vehicle vehicle) {
        vehicleRepo.insert(vehicle);
    }

    @Override
    public Vehicle updateVehicle(long id, Vehicle vehicle) {
        vehicleRepo.update(id, vehicle);
        return vehicleRepo.getById(id);
    }

    @Override
    public void deleteVehicle(long id) {
        vehicleRepo.delete(id);
    }

    @Override
    public Vehicle getVehicle(long id) {
        return vehicleRepo.getById(id);
    }

    @Override
    public Vehicle getVehicleByNumber(String number) {
        return vehicleRepo.getVehicleByNumber(number);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicleRepo.getAll());
    }

    @Override
    public List<Vehicle> getAllActiveVehicles() {
        return new ArrayList<>(vehicleRepo.getActiveOrInactive(ModelState.ACTIVE));
    }

    @Override
    public List<Vehicle> getAllInactiveVehicles() {
        return new ArrayList<>(vehicleRepo.getActiveOrInactive(ModelState.INACTIVE));
    }

    @Override
    public List<Vehicle> getOwnerVehicles(long ownerId) {
        return new ArrayList<>(vehicleRepo.getOwnerVehicles(ownerId));
    }
}
