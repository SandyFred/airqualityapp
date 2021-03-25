package com.team01.favouriteservice.service;

import com.team01.favouriteservice.model.Favourite;
import com.team01.favouriteservice.model.Location;
import com.team01.favouriteservice.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

@Service
public class FavouriteServiceImpl implements FavouriteService{

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Override
    public Location addFavourite(String username, Location location) {
        try {
            Favourite fav = favouriteRepository.findByUsername(username);
            List<Location> locations = fav.getLocations();
            locations.add(location);
            fav.setLocations(locations);
            favouriteRepository.save(fav);
        } catch (Exception e) {
            List<Location> locations = new ArrayList<>();
            locations.add(location);
            favouriteRepository.save(new Favourite(username,locations));
        }
        return location;
    }

    @Override
    public Location deleteFavourite(String username, String city) {
        Favourite fav = favouriteRepository.findByUsername(username);
        List<Location> locations = fav.getLocations();
        ListIterator<Location> iterator = locations.listIterator();
        while(iterator.hasNext()) {
            if(iterator.next().getCity().equals(city)){
                iterator.remove();
            }
        }
        System.out.println(locations);
        fav.setLocations(locations);
        favouriteRepository.save(fav);
        return null;
    }

    @Override
    public List<Location> getFavourites(String username) {
        Favourite fav = favouriteRepository.findByUsername(username);
        return fav.getLocations();
    }
}
