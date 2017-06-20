package com.edvantis.training.parking.test.controllerTests;

import com.edvantis.training.parking.api.ReservationEndpoind;
import com.edvantis.training.parking.config.TestControllerContext;
import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.enums.GarageType;
import com.edvantis.training.parking.models.Parking;
import com.edvantis.training.parking.models.Reservation;
import com.edvantis.training.parking.services.HelpService;
import com.edvantis.training.parking.services.ReservationService;
import com.edvantis.training.parking.util.TestsHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by taras.fihurnyak on 4/28/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestControllerContext.class})
@WebAppConfiguration
public class ReservationControllerTest {
    private MockMvc mockMvc;
    private Date from = TestsHelper.parseDate("2017-04-25 00:00:00");
    private Date to = TestsHelper.parseDate("2018-05-29 00:00:00");

    private ReservationService reservationServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        reservationServiceMock = Mockito.mock(ReservationService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ReservationEndpoind(reservationServiceMock))
                .build();
    }

    @Test
    public void findAvailableGarages() throws Exception {
        Parking p1 = new Parking();
        p1.setId(1L);
        p1.setAddress("Lviv, Main str 15");
        Garage g1 = new Garage();
        g1.setGarageType(GarageType.BIG);
        g1.setId(1L);
        g1.setParking(p1);
        g1.setSquare(11F);
        Garage g2 = new Garage();
        g2.setGarageType(GarageType.MEDIUM);
        g2.setId(2L);
        g2.setParking(p1);
        g2.setSquare(22F);

        ArrayList<Garage> garageList = new ArrayList<>();
        garageList.add(g1);
        garageList.add(g2);

        when(reservationServiceMock.getAvailableGaragesByParking(any(Date.class), any(Date.class), eq(1L))).thenReturn(garageList);

        mockMvc.perform(get("/parking/api/reservation/availableGarages/parking/{parkingId}", 1)
                .param("from", "2017-04-25")
                .param("to", "2018-05-29"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestsHelper.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].parking.id", is(1)))
                .andExpect(jsonPath("$[0].parking.address", is("Lviv, Main str 15")))
                .andExpect(jsonPath("$[0].square", is(11.0)))
                .andExpect(jsonPath("$[0].garageType", is(GarageType.BIG.toString())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].parking.id", is(1)))
                .andExpect(jsonPath("$[0].parking.address", is("Lviv, Main str 15")))
                .andExpect(jsonPath("$[1].square", is(22.0)))
                .andExpect(jsonPath("$[1].garageType", is(GarageType.MEDIUM.toString())));

        verify(reservationServiceMock, times(1)).getAvailableGaragesByParking(from, to, 1);
    }


    @Test
    public void makeReservation() throws Exception {
        Reservation r1 = new Reservation();
        r1.setBegin(from);
        r1.setEnd(to);
        r1.setGarageId(1);
        r1.setParkingId(1);
        r1.setOwnerId(1);
        r1.setId(1L);

        when(reservationServiceMock.makeReservation(any(Reservation.class))).thenReturn(r1);
        mockMvc.perform(
                post("/parking/api/reservations/addReservation")
                        .contentType(TestsHelper.APPLICATION_JSON_UTF8)
                        .content(createReservationInJson(r1)))
                .andExpect(status().isCreated());

        verify(reservationServiceMock, times(1)).makeReservation(any(Reservation.class));
    }

    private String createReservationInJson(Reservation reserv) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String from = df.format(reserv.getBegin());
        String to = df.format(reserv.getEnd());
        return "{ \"id\": \"" + reserv.getId() + "\", " +
                "\"begin\":\"" + from + "\"," +
                "\"end\":\"" + to + "\"," +
                "\"parkingId\":\"" + reserv.getParkingId() + "\"," +
                "\"ownerId\":\"" + reserv.getOwnerId() + "\"," +
                "\"garageId\":\"" + reserv.getGarageId() + "\"}";
    }
}
