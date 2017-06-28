package com.edvantis.training.parking.test;

import com.edvantis.training.parking.config.ApplicationTestConfig;
import com.edvantis.training.parking.jdbc.DataBaseJdbcUtil;
import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.enums.GarageType;
import com.edvantis.training.parking.models.Reservation;
import com.edvantis.training.parking.repository.*;
import com.edvantis.training.parking.services.HelpService;
import com.edvantis.training.parking.services.ReservationService;
import com.edvantis.training.parking.util.TestsHelper;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 3/25/2017.
 */
public class ReservationTest {

    private static AnnotationConfigApplicationContext ctx;
    private static OwnerRepository ownerRepo;
    private static VehicleRepository vehicleRepo;
    private static ParkingRepository parkingRepo;
    private static GarageRepository garageRepo;
    private static ReservationRepository reservationRepo;
    private static HelpService helpService;
    private static ReservationService resrvService;

    @BeforeClass
    public static void populateDb() {
        DataBaseJdbcUtil.createDb();
        ctx = new AnnotationConfigApplicationContext(ApplicationTestConfig.class);
        ownerRepo = ctx.getBean(OwnerRepository.class);
        vehicleRepo = ctx.getBean(VehicleRepository.class);
        parkingRepo = ctx.getBean(ParkingRepository.class);
        garageRepo = ctx.getBean(GarageRepository.class);
        reservationRepo = ctx.getBean(ReservationRepository.class);
        helpService = ctx.getBean(HelpService.class);
        resrvService = ctx.getBean(ReservationService.class);
        helpService.populateWithMockObjects(TestsHelper.generateObjects());
        Assert.assertNotNull(ownerRepo.getById(1));
        Assert.assertNotNull(vehicleRepo.getById(1));
        Assert.assertNotNull(parkingRepo.getById(1));
        Assert.assertNotNull(garageRepo.getById(1));
        Assert.assertNotNull(reservationRepo.getById((long) 1));
    }

    @Test
    public void testGetAvailableGaragesLowerBoundaryDate() {
        int parkingId = 1;
        Date from = TestsHelper.parseDate("2017-03-05 19:16:59");
        Date to = TestsHelper.parseDate("2017-03-15 19:16:59");
        List<Garage> list = resrvService.getAvailableGaragesByParking(from, to, parkingId);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(15, list.size());
        for (Garage i : list) {
            Assert.assertEquals(parkingId, i.getParking().getId());
        }
    }

    @Test
    public void testGetAvailableGaragesDateOverlap() {
        int parkingId = 1;
        Date from = TestsHelper.parseDate("2017-03-08 19:16:59");
        Date to = TestsHelper.parseDate("2017-03-20 19:16:59");
        List<Garage> list = resrvService.getAvailableGaragesByParking(from, to, parkingId);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(5, list.size());
        for (Garage i : list) {
            Assert.assertEquals(parkingId, i.getParking().getId());
        }
    }

    @Test
    public void testGetAvailableGaragesUpperBoundaryDate() {
        int parkingId = 1;
        Date from = TestsHelper.parseDate("2017-03-25 19:16:59");
        Date to = TestsHelper.parseDate("2017-04-05 19:16:59");
        List<Garage> list = resrvService.getAvailableGaragesByParking(from, to, parkingId);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(15, list.size());
        for (Garage i : list) {
            Assert.assertEquals(parkingId, i.getParking().getId());
        }
    }

    @Test
    public void testGetAvailableGaragesByTypeLowerBoundaryDate() {
        GarageType garageType = GarageType.SMALL;
        Date from = TestsHelper.parseDate("2017-03-05 19:16:59");
        Date to = TestsHelper.parseDate("2017-03-15 19:16:59");
        List<Garage> list = resrvService.getAvailableGaragesByType(from, to, garageType);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(5, list.size());
        for (Garage i : list) {
            Assert.assertEquals(garageType, i.getGarageType());
        }
    }

    @Test
    public void testGetAvailableGaragesByTypeDateOverlap() {
        GarageType garageType = GarageType.BIG;
        Date from = TestsHelper.parseDate("2017-03-18 19:16:59");
        Date to = TestsHelper.parseDate("2017-03-30 19:16:59");
        List<Garage> list = resrvService.getAvailableGaragesByType(from, to, garageType);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(10, list.size());
        for (Garage i : list) {
            Assert.assertEquals(garageType, i.getGarageType());
        }
    }

    @Test
    public void testGetAvailableGaragesByTypeUpperBoundaryDate() {
        GarageType garageType = GarageType.BIG;
        Date from = TestsHelper.parseDate("2017-03-25 19:16:59");
        Date to = TestsHelper.parseDate("2017-03-30 19:16:59");
        List<Garage> list = resrvService.getAvailableGaragesByType(from, to, garageType);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(15, list.size());
        for (Garage i : list) {
            Assert.assertEquals(garageType, i.getGarageType());
        }
    }

    @Test
    public void testReadAllReservationsByGarageType() {
        GarageType garageType = GarageType.BIG;
        Set<Reservation> list = reservationRepo.getAllReservationsByGarageType(garageType);
        Assert.assertFalse(list.isEmpty());
        for (Reservation i : list) {
            long garageId = i.getGarageId();
            Assert.assertEquals(garageType, garageRepo.getById(garageId).getGarageType());
        }
    }

    @Test
    public void testMakeReservationByGarageTypePositive() {
        GarageType garageType = GarageType.SMALL;
        long ownerId = 1;
        Date from = TestsHelper.parseDate("2017-04-25 19:16:59");
        Date to = TestsHelper.parseDate("2017-04-29 19:16:59");
        Reservation r = new Reservation();
        r.setBegin(from);
        r.setEnd(to);
        r.setOwnerId(1);
        Reservation reser = resrvService.makeReservation(r, garageType);
        Assert.assertNotNull(reser);
        long garageId = reser.getGarageId();
        Assert.assertEquals(garageType, garageRepo.getById(garageId).getGarageType());
        Assert.assertEquals(ownerId, reser.getOwnerId());
        Assert.assertEquals(from, reser.getBegin());
        Assert.assertEquals(to, reser.getEnd());
    }

    @Test
    public void testMakeReservation() {
        long ownerId = 1;
        Date from = TestsHelper.parseDate("2017-04-25 19:16:59");
        Date to = TestsHelper.parseDate("2017-04-29 19:16:59");
        Reservation r = new Reservation();
        r.setBegin(from);
        r.setEnd(to);
        r.setOwnerId(ownerId);
        Reservation reser = resrvService.makeReservation(r);
        Assert.assertNotNull(reser);
        Assert.assertEquals(ownerId, reser.getOwnerId());
        Assert.assertEquals(from, reser.getBegin());
        Assert.assertEquals(to, reser.getEnd());
    }

    @Test
    public void testMakeReservationByGarageTypeNegative() {
        GarageType garageType = GarageType.SMALL;
        long ownerId = 1;
        Date from = TestsHelper.parseDate("2017-03-15 19:16:59");
        Date to = TestsHelper.parseDate("2017-03-25 19:16:59");
        Reservation r = new Reservation();
        r.setBegin(from);
        r.setEnd(to);
        r.setOwnerId(1);
        Reservation reser = resrvService.makeReservation(r, garageType);
        Assert.assertNull(reser);
    }

    @AfterClass
    public static void dropDb() {
        DataBaseJdbcUtil.clearDb(TestsHelper.tablesList());
        DataBaseJdbcUtil.dropDB();
    }
}
