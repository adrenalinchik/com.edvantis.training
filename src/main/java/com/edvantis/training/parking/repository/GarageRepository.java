package com.edvantis.training.parking.repository;

import com.edvantis.training.parking.models.Garage;

import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/13/2017.
 */
public interface GarageRepository{

    Garage getById(long id);

    Set<Garage> getAll();

    void insert(Garage garage);

    void update(int Id, Garage garage);

    void update(Garage garage);

    void delete(long id);
}
