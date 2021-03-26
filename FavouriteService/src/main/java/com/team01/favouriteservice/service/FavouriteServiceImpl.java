package com.team01.favouriteservice.service;

import com.team01.favouriteservice.dto.AirVisual;
import com.team01.favouriteservice.model.Favourite;
import com.team01.favouriteservice.model.Location;
import com.team01.favouriteservice.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.*;

@Service
public class FavouriteServiceImpl implements FavouriteService{

    @Autowired
    private FavouriteRepository favouriteRepository;


    private final RestTemplate restTemplate = new RestTemplate();

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
    public List<AirVisual> getFavourites(String username) throws ExecutionException, InterruptedException {
        Favourite fav = favouriteRepository.findByUsername(username);
        return getAirVisualData(fav.getLocations());
    }

    private List<AirVisual> getAirVisualData(List<Location> locations) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Callable<AirVisual>> callableTasks = new ArrayList<>();

        for(Location location: locations) {
            Callable<AirVisual> callableTask = () -> {
                return getSingleRequest(location.getCountry(), location.getState(), location.getCity());
            };
            callableTasks.add(callableTask);
        }

        List<Future<AirVisual>> futures = executor.invokeAll(callableTasks);
        List<AirVisual> listOfFavourites = new ArrayList<>();
        for(Future<AirVisual> fav : futures) {
            listOfFavourites.add(fav.get());
        }
        executor.shutdown();
        return listOfFavourites;
    }

    private AirVisual getSingleRequest(String country,String state,String city) {
        String url = uriBuilder(country,state,city);
         return restTemplate.getForObject(url,AirVisual.class);
    }

    private String uriBuilder(String country,String state,String city) {
        //http://api.airvisual.com/v2/city?city=Thiruvananthapuram&state=Kerala&country=India&key=46222c20-ed3d-4478-86ca-c13f2896ed48
        String apiKey = "46222c20-ed3d-4478-86ca-c13f2896ed48";
        String baseUri = "http://api.airvisual.com/v2/city";
        return  baseUri + "?" + "city=" + city + "&" +
                                    "state=" + state + "&" +
                                    "country=" + country + "&" +
                                    "key=" + apiKey;
    }


}
