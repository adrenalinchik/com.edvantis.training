package com.edvantis.training.parking.services;

import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.models.VehicleType;
import com.edvantis.training.parking.repository.OwnerServiceJdbcRepository;
import com.edvantis.training.parking.repository.VehicleServiceJdbcRepository;
import org.apache.log4j.Logger;

import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/11/2017.
 */
public class ParkingServiceImp implements ParkingService {

    private OwnerServiceJdbcRepository ownerRepo;
    private VehicleServiceJdbcRepository vehicleRepo;

    public ParkingServiceImp(OwnerServiceJdbcRepository ownerRepo, VehicleServiceJdbcRepository vehicleRepo) {
        this.ownerRepo = ownerRepo;
        this.vehicleRepo = vehicleRepo;

    }

    private final Logger logger = Logger.getLogger(ParkingServiceImp.class);

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
        return ownerRepo.getOwnerByVehicleNymber(vehicleNumber);

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
