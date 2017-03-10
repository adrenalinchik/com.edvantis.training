package com.edvantis.training.parking.repository;

import com.edvantis.training.parking.models.Reservation;

/**
 * Created by taras.fihurnyak on 3/7/2017.
 */
public interface ReservationRepository {

    Reservation getById(int id);

    void insert(Reservation garage);

    void update(int reservationId, Reservation garage);

    void delete(int garageId);
}
