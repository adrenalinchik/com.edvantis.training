package com.edvantis.training.parking.test;

import com.edvantis.training.parking.config.ApplicationTestConfig;
import com.edvantis.training.parking.jdbc.DataBaseJdbcUtil;
import com.edvantis.training.parking.models.*;
import com.edvantis.training.parking.models.enums.GarageType;
import com.edvantis.training.parking.models.enums.VehicleType;
import com.edvantis.training.parking.repository.*;
import com.edvantis.training.parking.repository.jdbc.impl.GarageJdbcRepositoryImp;
import com.edvantis.training.parking.services.HelpService;
import com.edvantis.training.parking.services.OwnerService;
import com.edvantis.training.parking.util.TestsHelper;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Set;


/**
 * Created by taras.fihurnyak on 2/24/2017.
 */
public class ApplicationTest {

    private final Logger logger = LoggerFactory.getLogger(ApplicationTest.class);

    private static long id = 1;

    private static AnnotationConfigApplicationContext ctx;
    private static OwnerRepository ownerRepo;
    private static VehicleRepository vehicleRepo;
    private static ParkingRepository parkingRepo;
    private static GarageRepository garageRepo;
    private static ReservationRepository reservationRepo;
    private static HelpService helpService;
    private static OwnerService ownerService;

    @BeforeClass
    public static void createPopulateDb() {
        DataBaseJdbcUtil.createDb();
        ctx = new AnnotationConfigApplicationContext(ApplicationTestConfig.class);
        ownerRepo = ctx.getBean(OwnerRepository.class);
        vehicleRepo = ctx.getBean(VehicleRepository.class);
        parkingRepo = ctx.getBean(ParkingRepository.class);
        garageRepo = ctx.getBean(GarageRepository.class);
        reservationRepo = ctx.getBean(ReservationRepository.class);
        helpService = ctx.getBean(HelpService.class);
        ownerService = ctx.getBean(OwnerService.class);
        helpService.populateWithMockObjects(TestsHelper.generateObjects());
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
        Owner owner = ownerService.getOwnerByLastName(ownerLastName);
        Assert.assertEquals(ownerLastName, owner.getLastName());
    }

    @Test
    public void testGetOwnerByVehicleNumber() {
        String vehicleNumber = "123452";
        Owner owner = ownerService.getOwnerByVehicleNumber(vehicleNumber);
        Assert.assertEquals(vehicleNumber, owner.getVehicleByNumber(vehicleNumber).getNumber());
    }

    @Test
    public void testGetOwnerVehicles() {
        Set<Vehicle> set = vehicleRepo.getOwnerVehicles(1);
        Assert.assertNotNull(set);
    }

    @AfterClass
    public static void dropDb() {
        DataBaseJdbcUtil.clearDb(TestsHelper.tablesList());
        DataBaseJdbcUtil.dropDB();
    }
}