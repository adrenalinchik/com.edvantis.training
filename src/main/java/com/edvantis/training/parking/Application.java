package com.edvantis.training.parking;

import com.edvantis.training.parking.factory.ApplicationConfigJdbcFactory;
import com.edvantis.training.parking.jdbc.DataBaseJdbcUtil;
import com.edvantis.training.parking.models.*;
import com.edvantis.training.parking.repository.GarageJdbcRepository;
import com.edvantis.training.parking.repository.OwnerJdbcRepository;
import com.edvantis.training.parking.repository.ParkingJdbcRepository;
import com.edvantis.training.parking.repository.VehicleJdbcRepository;
import com.edvantis.training.parking.repository.jpa.GarageJpaRepositoryEntityManagerExample;
import com.edvantis.training.parking.repository.jpa.GarageJpaRepositorySessionExample;
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
//
//        String dbName = "test";
//        String login = "root";
//        String password = "root";
//
//        OwnerJdbcRepository ownerRepo = factory.getOwnerRepository(dbName, login, password);
//        VehicleJdbcRepository vehicleRepo = factory.getVehicleRepository(dbName, login, password);
//        ParkingJdbcRepository parkingJdbcRepository = factory.getParkingRepository(dbName, login, password);
//        GarageJdbcRepository garageJdbcRepository = factory.getGarageRepository(dbName, login, password);
//        ParkingService parkingService = factory.getParkingService(ownerRepo, vehicleRepo, garageJdbcRepository, parkingJdbcRepository);

        //DataBaseJdbcUtil.createDb(dbName, login, password);
        //parkingService.populateWithMockObjects(generateObjects());
//
//        GarageJpaRepositoryEntityManagerExample entityManagerExample = new GarageJpaRepositoryEntityManagerExample();
//        Garage garage = new Garage();
//        garage.setSquare(8888);
//        garage.setGarageType(GarageType.MEDIUM);
//        garage.setParking_id(1);

        // entityManagerExample.insert(garage);

//        Garage garage1 = entityManagerExample.getById(2);
//        System.out.println(garage1.getSquare() + " " + garage1.getGarageType() + " " + garage1.getId() + " " + garage1.getParking_id());

//        garage1.setGarageType(GarageType.MEDIUM);
//        garage1.setSquare(777997);
//        garage1.setParking_id(5);

        //entityManagerExample.update(garage1);
        //System.out.println(garage1.getSquare() + " " + garage1.getGarageType() + " " + garage1.getId() + " " + garage1.getParking_id());

        // entityManagerExample.delete(1);


        //   Set<Garage> setEntity = entityManagerExample.getAllGaragesByType(GarageType.BIG);
//
//        for (Garage g : setEntity) {
//            System.out.println(g.getSquare() + " " + g.getGarageType() + " " + g.getId() + " " + g.getParking_id());
//        }
//
//        GarageJpaRepositorySessionExample jpaRepositorySessionExample = new GarageJpaRepositorySessionExample();
//        Garage garage = garageRepo.getById(10);
//        garage.setGarageType(GarageType.MEDIUM);
//        garage.setSquare(7777);
//        garage.setParking_id(5);
//        //garageRepo.insert(garage);
//        garageRepo.update(garage);
        //garageRepo.delete(garage);
//        Garage garageUpdated = garageRepo.getById(11);
//        System.out.println(garageUpdated.getSquare()+" "+garageUpdated.getGarageType()+" "+garageUpdated.getId()+" "+garageUpdated.getParking_id());

//        Set<Garage> setSession = jpaRepositorySessionExample.getAllGaragesByType(GarageType.BIG);
//
//        for (Garage g : setSession) {
//            System.out.println(g.getSquare() + " " + g.getGarageType() + " " + g.getId() + " " + g.getParking_id());
//        }

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

            GarageJpaRepositoryEntityManagerExample entityManagerExample = new GarageJpaRepositoryEntityManagerExample();
//            Garage garage = new Garage();
//            garage.setSquare(8888);
//            garage.setGarageType(GarageType.MEDIUM);
//            garage.setParking_id(1);
//
//            entityManagerExample.insert(garage);
//
//            Garage garage1 = entityManagerExample.getById(2);
//            System.out.println(garage1.getSquare() + " " + garage1.getGarageType() + " " + garage1.getId() + " " + garage1.getParking_id());

//        garage1.setGarageType(GarageType.MEDIUM);
//        garage1.setSquare(777997);
//        garage1.setParking_id(5);

            //entityManagerExample.update(garage1);
            //System.out.println(garage1.getSquare() + " " + garage1.getGarageType() + " " + garage1.getId() + " " + garage1.getParking_id());

            // entityManagerExample.delete(1);


            Set<Garage> setEntity = entityManagerExample.getAllGaragesByType(GarageType.BIG);

            for (Garage g : setEntity) {
                System.out.println(g.getSquare() + " " + g.getGarageType() + " " + g.getId() + " " + g.getParking_id());
            }

            GarageJpaRepositorySessionExample jpaRepositorySessionExample = new GarageJpaRepositorySessionExample();
//        Garage garage = garageRepo.getById(10);
//        garage.setGarageType(GarageType.MEDIUM);
//        garage.setSquare(7777);
//        garage.setParking_id(5);
//        //garageRepo.insert(garage);
//        garageRepo.update(garage);
            //garageRepo.delete(garage);
//        Garage garageUpdated = garageRepo.getById(11);
//        System.out.println(garageUpdated.getSquare()+" "+garageUpdated.getGarageType()+" "+garageUpdated.getId()+" "+garageUpdated.getParking_id());

            Set<Garage> setSession = jpaRepositorySessionExample.getAllGaragesByType(GarageType.BIG);

            for (Garage g : setSession) {
                System.out.println(g.getSquare() + " " + g.getGarageType() + " " + g.getId() + " " + g.getParking_id());
            }


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

    private static String[] tablesList() {
        return new String[]{"owner", "vehicle", "garage", "parking"};
    }


}
