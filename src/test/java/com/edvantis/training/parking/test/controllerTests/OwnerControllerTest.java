package com.edvantis.training.parking.test.controllerTests;

import com.edvantis.training.parking.api.OwnerEndpoint;
import com.edvantis.training.parking.config.TestControllerContext;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by taras.fihurnyak on 4/21/2017.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestControllerContext.class})
@WebAppConfiguration
public class OwnerControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private ParkingService parkingServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new OwnerEndpoint(parkingServiceMock))
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
        owner2.setDOB(LocalDate.now());
        owner2.setFirstName("FirstName_User2");
        owner2.setLastName("LastName_User2");
        owner2.setGender(Gender.FEMALE);
        owner2.setId(2L);
        ArrayList<Owner> ownerList = new ArrayList<>();
        ownerList.add(owner1);
        ownerList.add(owner2);

        when(parkingServiceMock.getAllOwners()).thenReturn(ownerList);

        mockMvc.perform(get("/parking/api/owners"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestsHelper.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("FirstName_User1")))
                .andExpect(jsonPath("$[0].lastName", is("LastName_User1")))
                .andExpect(jsonPath("$[0].gender", is(Gender.MALE.toString())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("FirstName_User2")))
                .andExpect(jsonPath("$[1].lastName", is("LastName_User2")))
                .andExpect(jsonPath("$[1].gender", is(Gender.FEMALE.toString())));

        verify(parkingServiceMock, times(2)).getAllOwners();
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
        ArrayList<Vehicle> vehicList = new ArrayList<>();
        vehicList.add(v1);
        vehicList.add(v2);

        when(parkingServiceMock.getOwnerVehicles(1)).thenReturn(vehicList);

        mockMvc.perform(get("/parking/api/owner/1/vehicles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestsHelper.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(11)))
                .andExpect(jsonPath("$[0].model", is("BMW")))
                .andExpect(jsonPath("$[0].number", is("123456")))
                .andExpect(jsonPath("$[0].carType", is(VehicleType.DIESEL.toString())))
                .andExpect(jsonPath("$[1].id", is(22)))
                .andExpect(jsonPath("$[1].model", is("Audi")))
                .andExpect(jsonPath("$[1].number", is("654321")))
                .andExpect(jsonPath("$[1].carType", is(VehicleType.HIBRID.toString())));

        verify(parkingServiceMock, times(1)).getOwnerVehicles(1);
    }

    @Test
    public void createOwner() throws Exception {
        Owner owner = new Owner();
        owner.setDOB(LocalDate.now());
        owner.setFirstName("FirstName_User1");
        owner.setLastName("LastName_User1");
        owner.setGender(Gender.MALE);
        owner.setId(1L);

        doNothing().when(parkingServiceMock).addNewOwner(owner);

        mockMvc.perform(
                post("/parking/api/owners/createOwner")
                        .contentType(TestsHelper.APPLICATION_JSON_UTF8)
                        .content(createOwnerInJson(owner)))
                .andExpect(status().isCreated());

        verify(parkingServiceMock, times(1)).addNewOwner(owner);
    }

    private String createOwnerInJson(Owner owner) {
        DateTimeFormatter formatter_1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dob = owner.getDOB().format(formatter_1);
        return "{ \"id\": \"" + owner.getId() + "\", " +
                "\"firstName\":\"" + owner.getFirstName() + "\"," +
                "\"lastName\":\"" + owner.getLastName() + "\"," +
                "\"gender\":\"" + owner.getGender().toString() + "\"," +
                "\"dob\":\"" + dob + "\"}";

    }

}
