package com.edvantis.training.parking.models;

import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/14/2017.
 */
public class Parking {

    private String address;
    private int freeGarages;
    private Set<Garage> garages;


    public Parking() {
    }

    public Set<Garage> getGarage(int garage) {
        return garages;
    }

    public void setGarages(Set<Garage> garages) {
        this.garages = garages;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFreeGarages() {
        return freeGarages;
    }

    public void setFreeGarages(int freeGarages) {
        this.freeGarages = freeGarages;
    }
}
