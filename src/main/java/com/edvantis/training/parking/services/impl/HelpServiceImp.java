package com.edvantis.training.parking.services.impl;

import com.edvantis.training.parking.models.*;
import com.edvantis.training.parking.repository.*;
import com.edvantis.training.parking.services.HelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


/**
 * Created by taras.fihurnyak on 2/11/2017.
 */

@Service
public class HelpServiceImp implements HelpService {
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
    public HelpServiceImp(OwnerRepository ownerRepo, VehicleRepository vehicleRepo, GarageRepository garageRepo, ParkingRepository parkingRepo, ReservationRepository reservationRepo) {
        this.ownerRepo = ownerRepo;
        this.vehicleRepo = vehicleRepo;
        this.garageRepo = garageRepo;
        this.parkingRepo = parkingRepo;
        this.reservationRepo = reservationRepo;
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
}
