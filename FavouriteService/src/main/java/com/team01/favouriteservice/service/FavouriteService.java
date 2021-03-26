package com.team01.favouriteservice.service;

import com.team01.favouriteservice.dto.AirVisual;
import com.team01.favouriteservice.model.Location;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FavouriteService {

    Location addFavourite(String username, Location location);

    Location deleteFavourite(String username, String city);

    List<AirVisual> getFavourites(String username) throws ExecutionException, InterruptedException;

}
