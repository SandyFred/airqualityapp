package com.team01.favouriteservice.service;

import com.team01.favouriteservice.dto.AirVisual;
import com.team01.favouriteservice.dto.Response;
import com.team01.favouriteservice.exception.LocationAlreadyExistsException;
import com.team01.favouriteservice.exception.UserNotFoundException;
import com.team01.favouriteservice.model.Location;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FavouriteService {

    Location addFavourite(String username, Location location) throws LocationAlreadyExistsException;

    Location deleteFavourite(String username, String city);

    List<Response> getFavourites(String username) throws ExecutionException, InterruptedException, UserNotFoundException;

}
