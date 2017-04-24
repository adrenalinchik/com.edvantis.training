package com.edvantis.training.parking.util;

import com.edvantis.training.parking.models.*;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by taras.fihurnyak on 3/27/2017.
 */
public class TestsHelper {

    public static ArrayList<Object> generateObjects() {
        Date from = parseDate("2017-03-15 19:16:59");
        Date to = parseDate("2017-03-25 19:16:59");
        ArrayList<Object> arrayList = new ArrayList<>();
        Parking parking = new Parking();
        parking.setFreeGaragesNumber(15);
        parking.setAddress("Lviv, Main str " + 15);
        arrayList.add(parking);
        for (int i = 0; i < 5; i++) {
            Garage garage1 = new Garage();
            garage1.setGarageType(GarageType.SMALL);
            garage1.setSquare(1 + i);
            garage1.setParking(parking);
            arrayList.add(garage1);
        }
        for (int i = 0; i < 5; i++) {
            Garage garage1 = new Garage();
            garage1.setGarageType(GarageType.BIG);
            garage1.setSquare(1 + i);
            garage1.setParking(parking);
            arrayList.add(garage1);
        }
        for (int i = 0; i < 5; i++) {
            Garage garage1 = new Garage();
            garage1.setGarageType(GarageType.MEDIUM);
            garage1.setSquare(1 + i);
            garage1.setParking(parking);
            arrayList.add(garage1);
        }
        for (int i = 0; i < 10; i++) {
            Owner owner = new Owner();
            owner.setFirstName("ownerFirstName_" + i);
            owner.setLastName("ownerLastName_" + i);
            owner.setDOB(LocalDate.now());
            owner.setGender(Gender.MALE);
            arrayList.add(owner);
            Vehicle vehicle = new Vehicle();
            vehicle.setModel("model_" + i);
            vehicle.setNumber("12345" + i);
            vehicle.setCarType(VehicleType.ELECTRO);
            vehicle.setOwner(owner);
            arrayList.add(vehicle);
            Parking parking1 = new Parking();
            parking1.setFreeGaragesNumber(1 + i);
            parking1.setAddress("Lviv, Main str " + i);
            arrayList.add(parking1);
            Garage garage = new Garage();
            garage.setGarageType(GarageType.BIG);
            garage.setSquare(1 + i);
            garage.setParking(parking1);
            arrayList.add(garage);
            Reservation reservation = new Reservation();
            reservation.setBegin(from);
            reservation.setEnd(to);
            reservation.setOwnerId(1);
            reservation.setParkingId(1);
            reservation.setGarageId(i + 1);
            arrayList.add(reservation);
        }

        return arrayList;
    }

    public static Date parseDate(String dateString) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String[] tablesList() {
        return new String[]{"VEHICLE", "OWNER", "PARKING", "GARAGE", "RESERVATION"};
    }

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );

}
