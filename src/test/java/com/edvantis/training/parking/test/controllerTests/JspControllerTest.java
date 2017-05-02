package com.edvantis.training.parking.test.controllerTests;

import com.edvantis.training.parking.config.TestControllerContext;
import com.edvantis.training.parking.controllers.ParkingController;
import com.edvantis.training.parking.models.Gender;
import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.services.ParkingService;
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

import java.time.LocalDate;
import java.util.ArrayList;

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
public class JspControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ParkingService parkingServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ParkingController(parkingServiceMock))
                .setViewResolvers(jspViewResolver())
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

        mockMvc.perform(get("/parking/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("allOwners"))
                .andExpect(forwardedUrl("/WEB-INF/views/allOwners.jsp"))
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
                                hasProperty("DOB", is(LocalDate.now()))
                        )
                )));
        verify(parkingServiceMock, times(1)).getAllOwners();
    }

    private ViewResolver jspViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }
}
