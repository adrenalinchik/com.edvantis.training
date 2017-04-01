package com.edvantis.training.parking.test;

import com.edvantis.training.parking.config.ApplicationConfig;
import com.edvantis.training.parking.jdbc.DataBaseJdbcUtil;
import com.edvantis.training.parking.models.*;
import com.edvantis.training.parking.repository.*;
import com.edvantis.training.parking.services.ParkingService;
import com.edvantis.training.parking.util.TestsHelper;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;


/**
 * Created by taras.fihurnyak on 2/24/2017.
 */
public class ApplicationTest {
    private static long id = 1;

    private static ApplicationConfig factory = new ApplicationConfig();
    private static OwnerRepository ownerRepo = factory.getOwnerRepository();
    private static VehicleRepository vehicleRepo = factory.getVehicleRepository();
    private static ParkingRepository parkingRepo = factory.getParkingRepository();
    private static GarageRepository garageRepo = factory.getGarageRepository();
    private static ReservationRepository reservationRepo = factory.getReservationRepository();
    private static ParkingService parkingService = factory.getParkingService(ownerRepo, vehicleRepo, garageRepo, parkingRepo, reservationRepo);

    @BeforeClass
    public static void createPopulateDb() {
        DataBaseJdbcUtil.createDb();
        parkingService.populateWithMockObjects(TestsHelper.generateObjects());
        Assert.assertNotNull(ownerRepo.getById(1));
        Assert.assertNotNull(vehicleRepo.getById(1));
        Assert.assertNotNull(parkingRepo.getById(1));
        Assert.assertNotNull(garageRepo.getById(1));
        Assert.assertNotNull(reservationRepo.getById((long) 1));
    }

    @Test
    public void testGetAllObjects() {
        Set<Garage> garages = garageRepo.getAll();
        Assert.assertNotNull(garages);
        Assert.assertEquals(25, garages.size());
        Set<Owner> owners = ownerRepo.getAll();
        Assert.assertNotNull(owners);
        Assert.assertEquals(10, owners.size());
        Set<Parking> parkings = parkingRepo.getAll();
        Assert.assertNotNull(parkings);
        Assert.assertEquals(11, parkings.size());
        Set<Vehicle> vehicles = vehicleRepo.getAll();
        Assert.assertNotNull(vehicles);
        Assert.assertEquals(10, vehicles.size());
        Set<Reservation> reservations = reservationRepo.getAll();
        Assert.assertNotNull(reservations);
        Assert.assertEquals(10, reservations.size());
    }

    @Test
    public void testUpdateGarage() {
        long id = 1;
        Garage garage = garageRepo.getById(id);
        GarageType garageType = garage.getGarageType();
        garage.setGarageType(GarageType.MEDIUM);
        garageRepo.update(garage);
        Assert.assertNotEquals(garageType, garageRepo.getById(id).getGarageType());
    }

    @Test
    public void testUpdateParking() {
        long parkingId = 10;
        Garage garage = garageRepo.getById(id);
        Parking parking = parkingRepo.getById(parkingId);
        Set<Garage> parkingGarages = parking.getGarages();
        Assert.assertFalse(parkingGarages.contains(garage));
        parking.setGarage(garage);
        parkingRepo.update(parking);
        Assert.assertTrue(parkingGarages.contains(garage));
    }

    @Test
    public void testUpdateVehicle() {
        Vehicle vehicle = vehicleRepo.getById(id);
        VehicleType vehicleType = vehicle.getCarType();
        vehicle.setCarType(VehicleType.DIESEL);
        vehicleRepo.update(vehicle);
        Assert.assertNotEquals(vehicleType, vehicleRepo.getById(1).getCarType());
    }

    @Test
    public void testUpdateOwner() {
        Owner owner = ownerRepo.getById(id);
        String firstName = owner.getFirstName();
        owner.setFirstName("New_test_name");
        ownerRepo.update(owner);
        Assert.assertNotEquals(firstName, ownerRepo.getById(1).getFirstName());
    }

    @Test
    public void testUpdateReservation() {
        Reservation reservation = reservationRepo.getById(id);
        long ownerId = reservation.getOwnerId();
        reservation.setOwnerId(5);
        reservationRepo.update(reservation);
        Assert.assertNotEquals(ownerId, reservationRepo.getById(id).getOwnerId());
    }

    @Test
    public void testGetOwnerByLastName() {
        String ownerLastName = "ownerLastName_9";
        Owner owner = parkingService.getOwnerByLastName(ownerLastName);
        Assert.assertEquals(ownerLastName, owner.getLastName());
    }

    @Test
    public void testGetOwnerByVehicleNumber() {
        String vehicleNumber = "123452";
        Owner owner = parkingService.getOwnerByVehicleNumber(vehicleNumber);
        Assert.assertEquals(vehicleNumber, owner.getVehicleByNumber(vehicleNumber).getNumber());
    }


    @AfterClass
    public static void dropDb() {
//        Set<Garage> garages = garageRepo.getAll();
//        garages.forEach(i -> garageRepo.delete(i.getId()));
//        Assert.assertNull(garageRepo.getById(id));
//        Set<Vehicle> vehicles = vehicleRepo.getAll();
//        vehicles.forEach(i -> vehicleRepo.delete(i.getId()));
//        Assert.assertNull(vehicleRepo.getById(id));
//        Set<Owner> owners = ownerRepo.getAll();
//        owners.forEach(i -> ownerRepo.delete(i.getId()));
//        Assert.assertNull(ownerRepo.getById(id));
//        Set<Parking> parkings = parkingRepo.getAll();
//        parkings.forEach(i -> parkingRepo.delete(i.getId()));
//        Assert.assertNull(parkingRepo.getById(id));
//        Set<Reservation> reservations = reservationRepo.getAll();
//        reservations.forEach(i -> reservationRepo.delete(i.getId()));
//        Assert.assertNull(reservationRepo.getById(id));

        DataBaseJdbcUtil.clearDb(TestsHelper.tablesList());
        DataBaseJdbcUtil.dropAllObjects();
        DataBaseJdbcUtil.dropDb();

    }
}