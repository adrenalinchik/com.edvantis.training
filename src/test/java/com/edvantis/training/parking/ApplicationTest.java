package com.edvantis.training.parking;

import com.edvantis.training.parking.factory.ApplicationConfigJpaFactory;
import com.edvantis.training.parking.jdbc.DataBaseJdbcUtil;
import com.edvantis.training.parking.models.*;
import com.edvantis.training.parking.repository.GarageRepository;
import com.edvantis.training.parking.repository.OwnerRepository;
import com.edvantis.training.parking.repository.ParkingRepository;
import com.edvantis.training.parking.repository.VehicleRepository;
import com.edvantis.training.parking.services.ParkingService;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private ParkingRepository parkingJdbcRepository = factory.getParkingRepository();
    private GarageRepository garageJdbcRepository = factory.getGarageRepository();
    private ParkingService parkingService = factory.getParkingService(ownerRepo, vehicleRepo, garageJdbcRepository, parkingJdbcRepository);

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

        }

        return arrayList;
    }

    private String[] tablesList() {
        return new String[]{"vehicle", "owner", "parking", "garage"};
    }

}