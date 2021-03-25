package com.team01.favouriteservice.service;

import com.team01.favouriteservice.model.Location;

import java.util.List;

public interface FavouriteService {

    Location addFavourite(String username, Location location);

    Location deleteFavourite(String username, String city);

    List<Location> getFavourites(String username);

}
