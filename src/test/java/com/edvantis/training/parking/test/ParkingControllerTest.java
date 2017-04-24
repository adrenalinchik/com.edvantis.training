package com.edvantis.training.parking.test;

import com.edvantis.training.parking.config.TestControllerContext;
import com.edvantis.training.parking.controllers.ParkingController;
import com.edvantis.training.parking.models.Gender;
import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.models.VehicleType;
import com.edvantis.training.parking.services.ParkingService;
import com.edvantis.training.parking.util.TestsHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.time.Clock;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by taras.fihurnyak on 4/21/2017.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestControllerContext.class})
@WebAppConfiguration
public class ParkingControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ParkingService parkingServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ParkingController(parkingServiceMock))
                .setViewResolvers(viewResolver())
                .build();
    }

    @Test
    public void findAllOwners() throws Exception {
        Owner owner1 = new Owner();
        owner1.setDOB(LocalDate.now());
        owner1.setFirstName("FirstName_User1");
        owner1.setLastName("LastName_User1");
        owner1.setGender(Gender.MALE);
        owner1.setId(1L);

        Owner owner2 = new Owner();
        owner2.setDOB(LocalDate.now(Clock.systemDefaultZone()));
        owner2.setFirstName("FirstName_User2");
        owner2.setLastName("LastName_User2");
        owner2.setGender(Gender.FEMALE);
        owner2.setId(2L);
        Set ownerSet = new HashSet<Owner>();
        ownerSet.add(owner1);
        ownerSet.add(owner2);

        when(parkingServiceMock.getAllOwners()).thenReturn(ownerSet);

        mockMvc.perform(get("/parking/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("allOwners"))
                //.andExpect(forwardedUrl("/WEB-INF/jsp/allOwners.jsp"))
                .andExpect(model().attribute("owners", hasSize(2)))
                .andExpect(model().attribute("owners", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("firstName", is("FirstName_User1")),
                                hasProperty("lastName", is("LastName_User1")),
                                hasProperty("gender", is(Gender.MALE)),
                                hasProperty("DOB", is(LocalDate.now()))
                        )
                )))
                .andExpect(model().attribute("owners", hasItem(
                        allOf(
                                hasProperty("id", is(2L)),
                                hasProperty("firstName", is("FirstName_User2")),
                                hasProperty("lastName", is("LastName_User2")),
                                hasProperty("gender", is(Gender.FEMALE)),
                                hasProperty("DOB", is(LocalDate.now(Clock.systemDefaultZone())))
                        )
                )));
        verify(parkingServiceMock, times(1)).getAllOwners();
    }

    @Test
    public void findOwnerVehicles() throws Exception {
        Vehicle v1 = new Vehicle();
        v1.setId(11L);
        v1.setCarType(VehicleType.DIESEL);
        v1.setModel("BMW");
        v1.setNumber("123456");
        Vehicle v2 = new Vehicle();
        v2.setId(22L);
        v2.setCarType(VehicleType.HIBRID);
        v2.setModel("Audi");
        v2.setNumber("654321");
        Set vehicSet = new HashSet<Owner>();
        vehicSet.add(v1);
        vehicSet.add(v2);

        when(parkingServiceMock.getOwnerVehicles(1)).thenReturn(vehicSet);

        mockMvc.perform(get("/parking/owner/1/vehicles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestsHelper.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(22)))
                .andExpect(jsonPath("$[0].model", is("Audi")))
                .andExpect(jsonPath("$[0].number", is("654321")))
                .andExpect(jsonPath("$[0].carType", is(VehicleType.HIBRID.toString())))
                .andExpect(jsonPath("$[1].id", is(11)))
                .andExpect(jsonPath("$[1].model", is("BMW")))
                .andExpect(jsonPath("$[1].number", is("123456")))
                .andExpect(jsonPath("$[1].carType", is(VehicleType.DIESEL.toString())));

        verify(parkingServiceMock, times(1)).getOwnerVehicles(1);
    }

    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/web/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }
}
