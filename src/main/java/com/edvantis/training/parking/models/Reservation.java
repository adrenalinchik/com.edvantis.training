package com.edvantis.training.parking.models;

import com.edvantis.training.parking.models.enums.ModelState;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "BEGIN")
    private Date begin;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "END")
    private Date end;

    @Column(name = "PARKING_ID")
    private long parkingId;

    @Column(name = "VEHICLE_NUMBER")
    private String vehicleNumber;

    @Column(name = "OWNER_ID")
    private long ownerId;

    @Column(name = "GARAGE_ID")
    private long garageId;

    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    private ModelState state = ModelState.ACTIVE;

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

    public long getDaysAmount() {
        return end != null && begin!=null? TimeUnit.DAYS.convert(end.getTime() - begin.getTime(), TimeUnit.MILLISECONDS): 0;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public long getGarageId() {
        return garageId;
    }

    public void setGarageId(long garageId) {
        this.garageId = garageId;
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

        Reservation that = (Reservation) o;
        if (parkingId != that.parkingId) return false;
        if (ownerId != that.ownerId) return false;
        if (garageId != that.garageId) return false;
        if (!begin.equals(that.begin)) return false;
        if (!vehicleNumber.equals(that.vehicleNumber)) return false;
        return end.equals(that.end);
    }

    @Override
    public int hashCode() {
        int result = 31 * begin.hashCode();
        result = 31 * result + end.hashCode();
//        result = 31 * result + vehicleNumber.hashCode();
        result = 31 * result + (int) (parkingId ^ (parkingId >>> 32));
        result = 31 * result + (int) (ownerId ^ (ownerId >>> 32));
        result = 31 * result + (int) (garageId ^ (garageId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", begin=" + begin +
                ", end=" + end +
                ", parkingId=" + parkingId +
                ", ownerId=" + ownerId +
                ", garageId=" + garageId +
                '}';
    }
}
