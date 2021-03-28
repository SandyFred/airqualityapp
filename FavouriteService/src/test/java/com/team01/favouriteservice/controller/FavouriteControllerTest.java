package com.team01.favouriteservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team01.favouriteservice.dto.Components;
import com.team01.favouriteservice.dto.Response;
import com.team01.favouriteservice.exception.LocationAlreadyExistsException;
import com.team01.favouriteservice.exception.UserNotFoundException;
import com.team01.favouriteservice.model.Location;
import com.team01.favouriteservice.service.FavouriteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringRunner.class)
@WebMvcTest
public class FavouriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Response response;

    @MockBean
    private Components pollutants;

    @MockBean
    private Location location;

    @MockBean
    private FavouriteService favouriteService;

    @InjectMocks
    private FavouriteController favouriteController;

    private List<Response> responseList;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(favouriteController).build();

        pollutants = new Components("540.73","9.94","32.54", "8.82", "30.23", "34.8");
        response = new Response("India",
                "Kerala",
                "Thiruvananthapuram",
                60,
                pollutants);
        responseList = new ArrayList<>();
        responseList.add(response);

        location = new Location();
        location.setCountry("India");
        location.setState("Kerala");
        location.setCity("Thiruvananthapuram");

    }

    @Test
    public void getFavouritesSuccess() throws Exception {
        when(favouriteService.getFavourites(any())).thenReturn(responseList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/favourites/sandy")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getFavouritesFailure() throws Exception {
        when(favouriteService.getFavourites(any())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/favourites/sandy")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void addFavouritesSuccess() throws Exception {
        when(favouriteService.addFavourite(eq("sandy"),any())).thenReturn(location);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/favourites/sandy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(location)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void addFavouritesFailure() throws Exception {
        when(favouriteService.addFavourite(eq("sandy"),any())).thenThrow(LocationAlreadyExistsException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/favourites/sandy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(location)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
