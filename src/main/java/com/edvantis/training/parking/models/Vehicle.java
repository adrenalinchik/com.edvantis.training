package com.edvantis.training.parking.models;

import com.edvantis.training.parking.models.enums.ModelState;
import com.edvantis.training.parking.models.enums.VehicleType;
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
    private long id;

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

    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    private ModelState state = ModelState.ACTIVE;

    public Vehicle() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public ModelState getState() {
        return state;
    }

    public void setState(ModelState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        if (id != vehicle.id) return false;
        if (owner != null ? !owner.equals(vehicle.owner) : vehicle.owner != null) return false;
        if (model != null ? !model.equals(vehicle.model) : vehicle.model != null) return false;
        if (number != null ? !number.equals(vehicle.number) : vehicle.number != null) return false;
        if (carType != vehicle.carType) return false;
        return state == vehicle.state;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (carType != null ? carType.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
