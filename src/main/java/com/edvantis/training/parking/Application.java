package com.edvantis.training.parking;

import com.edvantis.training.parking.factory.ApplicationConfigFactory;
import com.edvantis.training.parking.models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/9/2017.
 */
public class Application {
    public static void main(String[] args) {

//        ApplicationConfigFactory factory = new ApplicationConfigFactory("test", "root", "root");
//        factory.getParkingJdbcService().createDb();
//        factory.getParkingJdbcService().populateDb(generateObjects());
//        factory.getParkingJdbcService().clearDb("owner");
//        factory.getParkingJdbcService().dropDb("test");

        //add new owner to db
//        Owner owner = new Owner();
//        owner.setFirstName("ownerFirstName_NEW");
//        owner.setLastName("ownerLastName_NEW");
//        owner.setDOB(LocalDate.now());
//        owner.setGender(Gender.FEMALE);
//        factory.getParkingService().addNewOwner(owner);


        if (args.length > 0) {
            ApplicationConfigFactory factory1 = new ApplicationConfigFactory(args[0],args[1],args[2]);
            factory1.getParkingJdbcService().createDb();
            factory1.getParkingJdbcService().populateDb(generateObjects());

        }
    }

    private static ArrayList<Object> generateObjects(){
        Set<Garage> set = new HashSet<>();
        for(int i=0;i<10;i++){
            Garage garage = new Garage();
            garage.setGarageType(GarageType.BIG);
            garage.setSquare(1 + i);
            set.add(garage);
        }

        ArrayList<Object> arrayList = new ArrayList<>();
        for(int i=0;i<10;i++){
            Owner owner = new Owner();
            owner.setFirstName("ownerFirstName_"+i);
            owner.setLastName("ownerLastName_" + i);
            owner.setDOB(LocalDate.now());
            owner.setGender(Gender.MALE);
            arrayList.add(owner);
            Vehicle vehicle = new Vehicle();
            vehicle.setModel("model_"+i);
            vehicle.setNumber("12345"+i);
            vehicle.setCarType(VehicleType.ELECTRO);
            arrayList.add(vehicle);
            Parking parking = new Parking();
            parking.setFreeGarages(1+i);
            parking.setAddress("Lviv, Main str "+i);
            parking.setGarages(set);

        }
        for (Garage g: set) {
            arrayList.add(g);
        }

        return arrayList;
    }

}
