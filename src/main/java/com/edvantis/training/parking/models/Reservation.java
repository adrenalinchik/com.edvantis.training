package com.edvantis.training.parking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by taras.fihurnyak on 3/7/2017.
 */

@Entity
@Table(name = "RESERVATION")
public class Reservation {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "ID", unique = true, nullable = false)
    private long id;

    @Column(name = "BEGIN")
    private Date begin;

    @Column(name = "END")
    private Date end;

    @Column(name = "PARKING_ID")
    private long parkingId;

    @Column(name = "OWNER_ID")
    private long ownerId;

    @Column(name = "GARAGE_ID")
    private long garageId;

    public Reservation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public long getParkingId() {
        return parkingId;
    }

    public void setParkingId(long parkingId) {
        this.parkingId = parkingId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getGarageId() {
        return garageId;
    }

    public void setGarageId(long garageId) {
        this.garageId = garageId;
    }
}
