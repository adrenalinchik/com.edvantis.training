package com.edvantis.training.parking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by taras.fihurnyak on 2/2/2017.
 */


@Entity
@Table(name = "VEHICLE")
public class Vehicle {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "ID", unique = true, nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name="OWNER_ID")
    private Owner owner;

    @Column(name = "MODEL")
    private String model;


    @Column(name = "NUMBER")
    private String number;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private VehicleType carType;

    public Vehicle() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public VehicleType getCarType() {
        return carType;
    }

    public void setCarType(VehicleType carType) {
        this.carType = carType;
    }
}
