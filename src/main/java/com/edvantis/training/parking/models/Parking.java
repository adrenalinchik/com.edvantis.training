package com.edvantis.training.parking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/14/2017.
 */
@Entity
@Table(name = "PARKING")
public class Parking {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "ID", unique = true, nullable = false)
    private int id;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "FREE_GARAGES")
    private int freeGarages;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parking", fetch = FetchType.EAGER)
    private Set<Garage> garages = new HashSet<>();


    public Parking() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFreeGaragesNumber() {
        return freeGarages;
    }

    public void setFreeGaragesNumber(int freeGarages) {
        this.freeGarages = freeGarages;
    }

    public Set<Garage> getGarage(int garage) {
        return garages;
    }

    public void setGarages(Set<Garage> garages) {
        this.garages = garages;
    }
}