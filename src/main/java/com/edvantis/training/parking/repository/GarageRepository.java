package com.edvantis.training.parking.repository;

import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.enums.GarageType;

import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/13/2017.
 */
public interface GarageRepository{

    Garage getById(long id);

    Set<Garage> getAll();

    void insert(Garage garage);

    void update(long Id, Garage garage);

    void delete(long id);

    Set<Garage> getAllGaragesByType(GarageType garageType);

    Set<Garage> getGaragesByParking(long parkingId);
}
