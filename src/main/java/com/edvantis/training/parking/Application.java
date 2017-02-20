package com.edvantis.training.parking;

import com.edvantis.training.parking.factory.ApplicationConfigJdbcFactory;
import com.edvantis.training.parking.jdbc.DataBaseJdbcUtil;
import com.edvantis.training.parking.models.*;
import com.edvantis.training.parking.repository.GarageJdbcRepository;
import com.edvantis.training.parking.repository.OwnerJdbcRepository;
import com.edvantis.training.parking.repository.ParkingJdbcRepository;
import com.edvantis.training.parking.repository.VehicleJdbcRepository;
import com.edvantis.training.parking.services.ParkingService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/9/2017.
 */
public class Application {
    public static void main(String[] args) {
        ApplicationConfigJdbcFactory factory = new ApplicationConfigJdbcFactory();

//        String dbName = "test";
//        String login = "root";
//        String password = "root";
//
//        OwnerJdbcRepository ownerRepo = factory.getOwnerRepository(dbName, login, password);
//        VehicleJdbcRepository vehicleRepo = factory.getVehicleRepository(dbName, login, password);
//        ParkingJdbcRepository parkingJdbcRepository = factory.getParkingRepository(dbName, login, password);
//        GarageJdbcRepository garageJdbcRepository = factory.getGarageRepository(dbName, login, password);
//        ParkingService parkingService = factory.getParkingService(ownerRepo, vehicleRepo, garageJdbcRepository, parkingJdbcRepository);
//
//
//        DataBaseJdbcUtil.createDb(dbName, login, password);
//        parkingService.populateWithMockObjects(generateObjects());
//        DataBaseJdbcUtil.clearDb(dbName, login, password, tableList());
//        DataBaseJdbcUtil.dropDb(dbName,login,password);
//
//        //add new owner to db
//        Owner owner = new Owner();
//        owner.setFirstName("ownerFirstName_NEW");
//        owner.setLastName("ownerLastName_NEW");
//        owner.setDOB(LocalDate.now());
//        owner.setGender(Gender.FEMALE);


        if (args.length > 0) {
            String dbName = args[0];
            String login = args[1];
            String password = args[2];
            OwnerJdbcRepository ownerRepo = factory.getOwnerRepository(dbName, login, password);
            VehicleJdbcRepository vehicleRepo = factory.getVehicleRepository(dbName, login, password);
            ParkingJdbcRepository parkingJdbcRepository = factory.getParkingRepository(dbName, login, password);
            GarageJdbcRepository garageJdbcRepository = factory.getGarageRepository(dbName, login, password);
            ParkingService parkingService = factory.getParkingService(ownerRepo, vehicleRepo, garageJdbcRepository, parkingJdbcRepository);


            DataBaseJdbcUtil.createDb(dbName, login, password);
            parkingService.populateWithMockObjects(generateObjects());
            DataBaseJdbcUtil.clearDb(dbName, login, password, tablesList());
            DataBaseJdbcUtil.dropDb(dbName, login, password);
        }


    }

    private static ArrayList<Object> generateObjects() {

        Set<Garage> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            Garage garage = new Garage();
            garage.setGarageType(GarageType.BIG);
            garage.setSquare(1 + i);
            set.add(garage);
        }

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
            arrayList.add(vehicle);
            Parking parking = new Parking();
            parking.setFreeGarages(1 + i);
            parking.setAddress("Lviv, Main str " + i);
            parking.setGarages(set);
            arrayList.add(parking);

        }
        for (Garage g : set) {
            arrayList.add(g);
        }

        return arrayList;
    }

    private static String [] tablesList(){
        return new String[]{"owner","vehicle","garage","parking"};
    }


}
