package com.edvantis.training.parking.services.impl;

import com.edvantis.training.parking.models.*;
import com.edvantis.training.parking.repository.GarageRepository;
import com.edvantis.training.parking.repository.OwnerRepository;
import com.edvantis.training.parking.repository.ParkingRepository;
import com.edvantis.training.parking.repository.VehicleRepository;
import com.edvantis.training.parking.services.ParkingService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/11/2017.
 */
public class ParkingServiceImp implements ParkingService {

    private OwnerRepository ownerRepo;
    private VehicleRepository vehicleRepo;
    private GarageRepository garageRepo;
    private ParkingRepository parkingRepo;

    public ParkingServiceImp(OwnerRepository ownerRepo, VehicleRepository vehicleRepo, GarageRepository garageRepo, ParkingRepository parkingRepo) {
        this.ownerRepo = ownerRepo;
        this.vehicleRepo = vehicleRepo;
        this.garageRepo = garageRepo;
        this.parkingRepo = parkingRepo;
    }

    private final Logger logger = Logger.getLogger(ParkingServiceImp.class);

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
               parkingRepo.insert((Parking)obj);
            }
        }

    }

    @Override
    public void addNewOwner(Owner owner) {
        ownerRepo.insert(owner);
    }

    @Override
    public Set<Owner> getAllOwners() {
        return ownerRepo.getAllOwnersFromDb();

    }

    @Override
    public Owner getOwnerByLastName(String ownerLastName) {
        return ownerRepo.getOwnerByLastName(ownerLastName);

    }

    @Override
    public Vehicle getVehicleByNumber(String vehicleNumber) {

        return vehicleRepo.getVehicleByNumber(vehicleNumber);
    }

    @Override
    public Owner getOwnerByVehicleNumber(String vehicleNumber) {
        return ownerRepo.getOwnerByVehicleNumber(vehicleNumber);

    }

    @Override
    public Set<Vehicle> getAllVehicleByType(VehicleType vehicleType) {
        return vehicleRepo.getAllVehiclesByType(vehicleType);

    }

    @Override
    public void addVehicleToOwner(Owner owner, Vehicle vehicle) {
        int ownerId = ownerRepo.getOwnerIdByLastName(owner.getLastName());
        int vehicleId = vehicleRepo.getVehicleIdByNumber(vehicle.getNumber());
        vehicleRepo.addOwnerIdToVehicle(ownerId, vehicleId);
        owner.addVehicleToOwner(vehicle);

        logger.info("Vechicle " + vehicle.getNumber() + " is added to " + owner.getFirstName() + " " + owner.getLastName() + " owner successfully.");
    }
}
