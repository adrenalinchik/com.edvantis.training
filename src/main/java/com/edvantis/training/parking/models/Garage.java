package com.edvantis.training.parking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by taras.fihurnyak on 2/13/2017.
 */
@Entity
@Table(name = "GARAGE")
public class Garage {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "ID", unique = true, nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "PARKING_ID")
    private Parking parking;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private GarageType garageType;

    @Column(name = "SQUARE")
    private float square;

    public Garage() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GarageType getGarageType() {
        return garageType;
    }

    public void setGarageType(GarageType garageType) {
        this.garageType = garageType;
    }

    public float getSquare() {
        return square;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public void setSquare(float square) {
        this.square = square;
    }


}
