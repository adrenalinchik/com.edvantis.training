package com.edvantis.training.parking;

import com.edvantis.training.parking.factory.ApplicationConfigJpaFactory;
import com.edvantis.training.parking.jdbc.DataBaseJdbcUtil;
import com.edvantis.training.parking.models.*;
import com.edvantis.training.parking.repository.*;
import com.edvantis.training.parking.repository.jpa.ReservationJpaRepository;
import com.edvantis.training.parking.services.ParkingService;
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

    private String dbName = "test";
    private String login = "root";
    private String password = "root";

    private ApplicationConfigJpaFactory factory = new ApplicationConfigJpaFactory();

    private OwnerRepository ownerRepo = factory.getOwnerRepository();
    private VehicleRepository vehicleRepo = factory.getVehicleRepository();
    private ParkingRepository parkingRepository = factory.getParkingRepository();
    private GarageRepository garageRepository = factory.getGarageRepository();
    private ReservationRepository reservationRepository = factory.getReservationRepository();
    private ParkingService parkingService = factory.getParkingService(ownerRepo, vehicleRepo, garageRepository, parkingRepository, reservationRepository);


    private ReservationJpaRepository reservationJpaRepository = new ReservationJpaRepository();


    @Test
    public void populateDb() {

        parkingService.populateWithMockObjects(generateObjects());
    }

    @Test
    public void clearAllTables() {
        DataBaseJdbcUtil.clearDb(dbName, login, password, tablesList());
    }

    @Test
    public void dropDb() {

        DataBaseJdbcUtil.dropDb(dbName, login, password);
    }

    @Test
    public void createDb() {

        DataBaseJdbcUtil.createDb(dbName, login, password);
    }

    @Test
    public void getAllOwners() {

        Set<Owner> owners = parkingService.getAllOwners();
        for (Owner owner : owners) {
            System.out.println(owner.toString());
        }
    }

    @Test
    public void getOwnerByLastName() {
        Owner owner = parkingService.getOwnerByLastName("ownerLastName_9");
        System.out.println(owner.toString());
    }

    @Test
    public void getOwnerByVehicleNumber() {

        Owner owner = parkingService.getOwnerByVehicleNumber("123452");
        System.out.println(owner.toString());
    }


    @Test
    public void getAvailableGarages() {
        Date from = null;
        Date to = null;
        try {
            from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-03-01 19:16:59");
            to = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-03-30 19:16:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Set<Garage> list = reservationJpaRepository.getAvailableGarages(from, to, 2);

        for (Garage i : list) {
            System.out.println(i.getId());
        }

    }

    @Test
    public void getParkingGarages() {
        Set<Garage> list = reservationJpaRepository.getGaragesByParkingId(2);

        for (Garage i : list) {
            System.out.println(i.getId());
        }
    }

    public ArrayList<Object> generateObjects() {

        ArrayList<Object> arrayList = new ArrayList<>();
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
            Parking parking = new Parking();
            parking.setFreeGaragesNumber(1 + i);
            parking.setAddress("Lviv, Main str " + i);
            arrayList.add(parking);
            Garage garage = new Garage();
            garage.setGarageType(GarageType.BIG);
            garage.setSquare(1 + i);
            garage.setParking(parking);
            arrayList.add(garage);
            Reservation reservation = new Reservation();
            reservation.setBegin(new Date());
            reservation.setEnd(new Date());
            reservation.setOwnerId(1);
            reservation.setParkingId(2);
            arrayList.add(reservation);

        }

        return arrayList;
    }

    private String[] tablesList() {
        return new String[]{"vehicle", "owner", "parking", "garage", "reservation"};
    }

}