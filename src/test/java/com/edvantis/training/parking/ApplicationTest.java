package com.edvantis.training.parking;

import com.edvantis.training.parking.factory.ApplicationConfig;
import com.edvantis.training.parking.jdbc.DataBaseJdbcUtil;
import com.edvantis.training.parking.models.*;
import com.edvantis.training.parking.repository.*;
import com.edvantis.training.parking.services.ParkingService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;


/**
 * Created by taras.fihurnyak on 2/24/2017.
 */
public class ApplicationTest {
    private static String dbName = "test";
    private static String login = "root";
    private static String password = "root";

    private static ApplicationConfig factory = new ApplicationConfig();
    private static OwnerRepository ownerRepo;
    private static VehicleRepository vehicleRepo;
    private static ParkingRepository parkingRepo;
    private static GarageRepository garageRepo;
    private static ReservationRepository reservationRepo;
    private static ParkingService parkingService;

    @BeforeClass
    public static void createPopulateDb() {
        DataBaseJdbcUtil.createDb(dbName, login, password);
        ownerRepo = factory.getOwnerRepository();
        vehicleRepo = factory.getVehicleRepository();
        parkingRepo = factory.getParkingRepository();
        garageRepo = factory.getGarageRepository();
        reservationRepo = factory.getReservationRepository();
        parkingService = factory.getParkingService(ownerRepo, vehicleRepo, garageRepo, parkingRepo, reservationRepo);
        Assert.assertTrue(DataBaseJdbcUtil.isDbCreated(dbName, login, password));
        parkingService.populateWithMockObjects(generateObjects());
        Assert.assertNotNull(ownerRepo.getById(1));
        Assert.assertNotNull(vehicleRepo.getById(1));
        Assert.assertNotNull(parkingRepo.getById(1));
        Assert.assertNotNull(garageRepo.getById(1));
        Assert.assertNotNull(reservationRepo.getById(1));
    }


    @Test
    public void getAllOwners() {
        Assert.assertTrue(DataBaseJdbcUtil.isTableExist(dbName, login, password, "Owner"));
        Set<Owner> owners = parkingService.getAllOwners();
        for (Owner owner : owners) {
            Assert.assertNotNull(owner.getId());
        }
    }

    @Test
    public void getOwnerByLastName() {
        Assert.assertTrue(DataBaseJdbcUtil.isTableExist(dbName, login, password, "Owner"));
        String ownerLastName = "ownerLastName_9";
        Owner owner = parkingService.getOwnerByLastName(ownerLastName);
        Assert.assertEquals(ownerLastName, owner.getLastName());
    }

    @Test
    public void getOwnerByVehicleNumber() {
        Assert.assertTrue(DataBaseJdbcUtil.isTableExist(dbName, login, password, "Owner"));
        Assert.assertTrue(DataBaseJdbcUtil.isTableExist(dbName, login, password, "Vehicle"));
        String vehicleNumber = "123452";
        Owner owner = parkingService.getOwnerByVehicleNumber(vehicleNumber);
        Assert.assertEquals(vehicleNumber, owner.getVehicleByNumber(vehicleNumber).getNumber());
    }


    @Test
    public void testAvailableGaragesLowerBoundaryDate() {
        Assert.assertTrue(DataBaseJdbcUtil.isTableExist(dbName, login, password, tablesList()));
        int parkingId = 1;
        Date from = null;
        Date to = null;
        try {
            from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-03-05 19:16:59");
            to = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-03-15 19:16:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Set<Garage> list = parkingService.getAvailableGaragesByParking(from, to, parkingId);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(15, list.size());
        for (Garage i : list) {
            Assert.assertEquals(parkingId, i.getParking().getId());
        }

    }

    @Test
    public void testAvailableGaragesDateOverlap() {
        Assert.assertTrue(DataBaseJdbcUtil.isTableExist(dbName, login, password, tablesList()));
        int parkingId = 1;
        Date from = null;
        Date to = null;
        try {
            from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-03-08 19:16:59");
            to = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-03-20 19:16:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Set<Garage> list = parkingService.getAvailableGaragesByParking(from, to, parkingId);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(5, list.size());
        for (Garage i : list) {
            Assert.assertEquals(parkingId, i.getParking().getId());
        }
    }

    @Test
    public void testAvailableGaragesUpperBoundaryDate() {
        Assert.assertTrue(DataBaseJdbcUtil.isTableExist(dbName, login, password, tablesList()));
        int parkingId = 1;
        Date from = null;
        Date to = null;
        try {
            from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-03-25 19:16:59");
            to = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-04-05 19:16:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Set<Garage> list = parkingService.getAvailableGaragesByParking(from, to, parkingId);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(15, list.size());
        for (Garage i : list) {
            Assert.assertEquals(parkingId, i.getParking().getId());

        }
    }

    @Test
    public void testReservationsByGaragesType() {
        Assert.assertTrue(DataBaseJdbcUtil.isTableExist(dbName, login, password, "Reservation"));
        Assert.assertTrue(DataBaseJdbcUtil.isTableExist(dbName, login, password, "Garage"));
        GarageType garageType = GarageType.BIG;
        Set<Reservation> list = reservationRepo.getAllReservationsByGarageType(garageType);
        Assert.assertFalse(list.isEmpty());
        for (Reservation i : list) {
            long garageId = i.getGarageId();
            Assert.assertEquals(garageType, garageRepo.getById(garageId).getGarageType());
        }
    }

    @Test
    public void testAvailableGaragesByTypeLowerBoundaryDate() {
        Assert.assertTrue(DataBaseJdbcUtil.isTableExist(dbName, login, password, tablesList()));
        GarageType garageType = GarageType.SMALL;
        Date from = null;
        Date to = null;
        try {
            from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-03-05 19:16:59");
            to = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-03-15 19:16:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Set<Garage> list = parkingService.getAvailableGaragesByGarageType(from, to, garageType);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(5, list.size());
        for (Garage i : list) {
            Assert.assertEquals(garageType, i.getGarageType());
        }
    }

    @Test
    public void testAvailableGaragesByTypeDateOverlap() {
        Assert.assertTrue(DataBaseJdbcUtil.isTableExist(dbName, login, password, tablesList()));
        GarageType garageType = GarageType.BIG;
        Date from = null;
        Date to = null;
        try {
            from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-03-18 19:16:59");
            to = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-03-30 19:16:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Set<Garage> list = parkingService.getAvailableGaragesByGarageType(from, to, garageType);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(10, list.size());
        for (Garage i : list) {
            Assert.assertEquals(garageType, i.getGarageType());
        }
    }

    @Test
    public void testAvailableGaragesByTypeUpperBoundaryDate() {
        Assert.assertTrue(DataBaseJdbcUtil.isTableExist(dbName, login, password, tablesList()));
        GarageType garageType = GarageType.BIG;
        Date from = null;
        Date to = null;
        try {
            from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-03-25 19:16:59");
            to = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-03-30 19:16:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Set<Garage> list = parkingService.getAvailableGaragesByGarageType(from, to, garageType);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(15, list.size());
        for (Garage i : list) {
            Assert.assertEquals(garageType, i.getGarageType());
        }
    }

    @Test
    public void makeReservation() {
        Assert.assertTrue(DataBaseJdbcUtil.isTableExist(dbName, login, password, "Reservation"));
        GarageType garageType = GarageType.MEDIUM;
        long ownerId = 1;
        Date from = null;
        Date to = null;
        try {
            from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-04-25 19:16:59");
            to = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-04-29 19:16:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        parkingService.makeReservation(from, to, garageType, ownerId);
        Reservation reser = reservationRepo.getLastReservation();
        long garageId = reser.getGarageId();
        Assert.assertEquals(garageType, garageRepo.getById(garageId).getGarageType());
        Assert.assertEquals(ownerId, reser.getOwnerId());
        Assert.assertEquals(from, reser.getBegin());
        Assert.assertEquals(to, reser.getEnd());

    }


    @AfterClass
    public static void dropDb() {
        Assert.assertTrue(DataBaseJdbcUtil.isDbCreated(dbName, login, password));
        Assert.assertTrue(DataBaseJdbcUtil.isTableExist(dbName, login, password, tablesList()));
        DataBaseJdbcUtil.clearDb(dbName, login, password, tablesList());
        Assert.assertNull(ownerRepo.getById(1));
        Assert.assertNull(vehicleRepo.getById(1));
        Assert.assertNull(parkingRepo.getById(1));
        Assert.assertNull(garageRepo.getById(1));
        Assert.assertNull(reservationRepo.getById(1));
        DataBaseJdbcUtil.dropDb(dbName, login, password);
        Assert.assertFalse(DataBaseJdbcUtil.isDbCreated(dbName, login, password));
    }

    private static ArrayList<Object> generateObjects() {

        Date from = null;
        Date to = null;
        try {
            from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-03-15 19:16:59");
            to = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-03-25 19:16:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    private static String[] tablesList() {
        return new String[]{"vehicle", "owner", "parking", "garage", "reservation"};
    }


}