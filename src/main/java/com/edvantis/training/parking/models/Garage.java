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


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Garage garage = (Garage) o;

        if (Float.compare(garage.square, square) != 0) return false;
        return garageType == garage.garageType;

    }

    @Override
    public int hashCode() {
        int result = garageType != null ? garageType.hashCode() : 0;
        result = 31 * result + (square != +0.0f ? Float.floatToIntBits(square) : 0);
        return result;
    }
}
