package com.edvantis.training.parking.services;

import com.edvantis.training.parking.models.Garage;

import java.util.List;

/**
 * Created by taras.fihurnyak on 6/14/2017.
 */
public interface GarageService {

    List<Garage> getAllParkingGarages(long parkingId);

    List<Garage> getAllGarages();
}
