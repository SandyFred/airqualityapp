package com.team01.favouriteservice.service;

import com.team01.favouriteservice.exception.LocationAlreadyExistsException;
import com.team01.favouriteservice.model.Favourite;
import com.team01.favouriteservice.model.Location;
import com.team01.favouriteservice.repository.FavouriteRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FavouriteServiceTest {

    @Mock
    private Favourite fav;

    @MockBean
    private Location location;

    @Mock
    private FavouriteRepository favouriteRepository;

    @InjectMocks
    private FavouriteServiceImpl favouriteServiceImpl;

    private List<Location> locationList;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        fav = new Favourite();
        location = new Location();
        location.setCountry("India");
        location.setState("Kerala");
        location.setCity("Thiruvananthapuram");

        fav.setUsername("sandy");
        locationList = new ArrayList<>();
        locationList.add(location);
        fav.setLocations(locationList);
    }

    @Test
    public void addFavouriteSuccess() throws LocationAlreadyExistsException {
        when(favouriteRepository.save((Favourite) any())).thenReturn(fav);
        Location created = favouriteServiceImpl.addFavourite("sandy",location);
        Assert.assertEquals(location, created);
        verify(favouriteRepository, times(1)).save((Favourite) any());
    }

    @Test(expected = LocationAlreadyExistsException.class)
    public void addFavouriteFailure() throws LocationAlreadyExistsException {
        when(favouriteRepository.findByUsername(eq("sandy"))).thenReturn(fav);
        Location created = favouriteServiceImpl.addFavourite("sandy",location);
        Assert.assertEquals(location, created);
        verify(favouriteRepository, times(1)).save((Favourite) any());
    }
}
