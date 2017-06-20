package com.edvantis.training.parking;

import com.edvantis.training.parking.config.ApplicationConfig;
import com.edvantis.training.parking.jdbc.DataBaseJdbcUtil;
import com.edvantis.training.parking.models.*;
import com.edvantis.training.parking.models.enums.GarageType;
import com.edvantis.training.parking.models.enums.Gender;
import com.edvantis.training.parking.models.enums.VehicleType;
import com.edvantis.training.parking.repository.ReservationRepository;
import com.edvantis.training.parking.services.HelpService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Application {
    public static Date from = parseDate("2017-03-18 19:14:59");
    public static Date to = parseDate("2017-03-23 19:16:59");

    public static void main(String[] args) throws Exception {
        DataBaseJdbcUtil.dropDb();
        DataBaseJdbcUtil.createDb();
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        HelpService parkingService = ctx.getBean(HelpService.class);
        ReservationRepository reserRepo = ctx.getBean(ReservationRepository.class);
        parkingService.populateWithMockObjects(generateObjects());

//        List<Owner> list = parkingService.getAllActiveOwners();
//        list.forEach(System.out::println);
//        parkingService.getAvailableGarages(from, to);
//        Reservation r= new Reservation();
//        r.setBegin(from);
//        r.setEnd(to);
//        r.setOwnerId(1);
//        parkingService.makeReservation(r,GarageType.BIG);
//        OwnerRepository ownerRepo = ctx.getBean(OwnerRepository.class);
//        Owner owner = ownerRepo.getById(1);
//        owner.setFirstName("Taras");
//        ownerRepo.update(owner.getId(), owner);
//        parkingService.addNewOwner(new Owner());
//        VehicleRepository vehicleRepo = ctx.getBean(VehicleRepository.class);
//        Set<Vehicle> vehicleSet = vehicleRepo.getAll();
//        Vehicle vehicle = vehicleSet.iterator().next();
//        vehicleRepo.update(vehicle.getId(), vehicle);
//        GarageRepository garageRepo = ctx.getBean(GarageRepository.class);
//        ParkingRepository parkingRepo = ctx.getBean(ParkingRepository.class);
//        garageRepo.getAllGaragesByType(GarageType.MEDIUM);
//        Garage garage = garageRepo.getById(5);
//        Parking parking = parkingRepo.getById(2);
//        garage.setParking(parking);
//        garageRepo.update(garage.getId(), garage);
//        parking.setGarage(new Garage());
//        parkingRepo.update(parking.getId(), parking);
//        ReservationRepository reserRepo = ctx.getBean(ReservationRepository.class);
//        reserRepo.getAllReservations();
//        Reservation reser = reserRepo.getLastReservation();
//        reser.setOwnerId(4);
//        reserRepo.update(reser.getId(), reser);
//        //Set JDBC environment
//        ctx.getEnvironment().setActiveProfiles("jdbc");
//        ctx.register(ApplicationConfig.class);
//        VehicleRepository vehicleJdbcRepo = ctx.getBean(VehicleRepository.class);
//        vehicleJdbcRepo.getById(1);
//        DataBaseJdbcUtil.clearDb(tablesList());
//        DataBaseJdbcUtil.dropDb();
    }


    public static ArrayList<Object> generateObjects() {
        Date from = parseDate("2017-06-15 19:16:59");
        Date to = parseDate("2017-07-25 19:16:59");
        ArrayList<Object> arrayList = new ArrayList<>();
        Parking parking = new Parking();
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
            parking1.setAddress("Lviv, Main str " + i);
            parking1.setGaragesNumber(i+25+i);
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
            reservation.setVehicleNumber(i+"2345"+i);
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


}
