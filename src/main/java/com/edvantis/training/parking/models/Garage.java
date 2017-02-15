package com.edvantis.training.parking.models;

/**
 * Created by taras.fihurnyak on 2/13/2017.
 */
public class Garage {

    private GarageType garageType;
    private float square;

    public Garage() {
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
}
