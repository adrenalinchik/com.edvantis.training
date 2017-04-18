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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Set;


/**
 * Created by taras.fihurnyak on 2/24/2017.
 */
public class ApplicationTest {
    private static long id = 1;

    private static AnnotationConfigApplicationContext ctx;
    private static OwnerRepository ownerRepo;
    private static VehicleRepository vehicleRepo;
    private static ParkingRepository parkingRepo;
    private static GarageRepository garageRepo;
    private static ReservationRepository reservationRepo;
    private static ParkingService parkingService;

    @BeforeClass
    public static void createPopulateDb() {
        DataBaseJdbcUtil.createDb();
        ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        ownerRepo = ctx.getBean(OwnerRepository.class);
        vehicleRepo = ctx.getBean(VehicleRepository.class);
        parkingRepo = ctx.getBean(ParkingRepository.class);
        garageRepo = ctx.getBean(GarageRepository.class);
        reservationRepo = ctx.getBean(ReservationRepository.class);
        parkingService = ctx.getBean(ParkingService.class);
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
        garageRepo.update(garage.getId(), garage);
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
        parkingRepo.update(parkingId, parking);
        Assert.assertTrue(parkingGarages.contains(garage));
    }

    @Test
    public void testUpdateVehicle() {
        Vehicle vehicle = vehicleRepo.getById(id);
        VehicleType vehicleType = vehicle.getCarType();
        vehicle.setCarType(VehicleType.DIESEL);
        vehicleRepo.update(vehicle.getId(), vehicle);
        Assert.assertNotEquals(vehicleType, vehicleRepo.getById(1).getCarType());
    }

    @Test
    public void testUpdateOwner() {
        Owner owner = ownerRepo.getById(id);
        String firstName = owner.getFirstName();
        owner.setFirstName("New_test_name");
        ownerRepo.update(owner.getId(), owner);
        Assert.assertNotEquals(firstName, ownerRepo.getById(1).getFirstName());
    }

    @Test
    public void testUpdateReservation() {
        Reservation reservation = reservationRepo.getById(id);
        long ownerId = reservation.getOwnerId();
        reservation.setOwnerId(5);
        reservationRepo.update(reservation.getId(), reservation);
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
        // DataBaseJdbcUtil.clearDb(TestsHelper.tablesList());
      // DataBaseJdbcUtil.dropDB();
    }
}