package com.edvantis.training.parking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by taras.fihurnyak on 2/13/2017.
 */
@Entity(name="GARAGE")
@Table(name = "GARAGE", catalog = "test")
public class Garage {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "ID", unique = true, nullable = false)
    private int id;

    @Column(name = "PARKING_ID")
    private int parking_id;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private GarageType garageType;

    @Column(name = "SQUARE")
    private float square;

    public Garage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public void setSquare(float square) {
        this.square = square;
    }

    public int getParking_id() {
        return parking_id;
    }

    public void setParking_id(int parking_id) {
        this.parking_id = parking_id;
    }

}
