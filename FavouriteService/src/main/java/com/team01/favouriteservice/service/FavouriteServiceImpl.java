package com.team01.favouriteservice.service;

import com.team01.favouriteservice.dto.AirVisual;
import com.team01.favouriteservice.dto.OpenWeather;
import com.team01.favouriteservice.dto.Response;
import com.team01.favouriteservice.exception.LocationAlreadyExistsException;
import com.team01.favouriteservice.exception.UserNotFoundException;
import com.team01.favouriteservice.model.Favourite;
import com.team01.favouriteservice.model.Location;
import com.team01.favouriteservice.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    @Autowired
    private FavouriteRepository favouriteRepository;


    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Location addFavourite(String username, Location location) throws LocationAlreadyExistsException {
        try {
            Favourite fav = favouriteRepository.findByUsername(username);
            List<Location> locations = fav.getLocations();
            List<Location> isLocationAlreadyExists = locations.stream()
                    .filter(loc -> loc.getCity().equals(location.getCity()))
                    .collect(Collectors.toList());
            if(isLocationAlreadyExists.size() > 0) {
                throw new LocationAlreadyExistsException("Location already exists!");
            }
            locations.add(location);
            fav.setLocations(locations);
            favouriteRepository.save(fav);
        } catch (NullPointerException e) {
            List<Location> locations = new ArrayList<>();
            locations.add(location);
            favouriteRepository.save(new Favourite(username, locations));
        }
        return location;
    }

    @Override
    public Location deleteFavourite(String username, String city) {
        Favourite fav = favouriteRepository.findByUsername(username);
        List<Location> locations = fav.getLocations();
        ListIterator<Location> iterator = locations.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getCity().equals(city)) {
                iterator.remove();
            }
        }
        System.out.println(locations);
        fav.setLocations(locations);
        favouriteRepository.save(fav);
        return null;
    }

    @Override
    public List<AirVisual> getFavourites(String username) throws ExecutionException, InterruptedException, UserNotFoundException {
        try {
            Favourite fav = favouriteRepository.findByUsername(username);
            List<AirVisual> airVisualData = getAirVisualData(fav.getLocations());
//            ArrayList<ArrayList<Double>> coordinatesList = new ArrayList<>();
//            List<Response> apiResponse = new ArrayList<>();
//
//            for (AirVisual airVisual : airVisualData) {
//                coordinatesList.add(airVisual.getData().getLocation().getCoordinates());
//            }
//            List<OpenWeather> openData = getOpenApiData(coordinatesList);
//            Iterator<AirVisual> itair = airVisualData.listIterator();
//            Iterator<OpenWeather> itopen = openData.listIterator();
//            while (itair.hasNext() && itopen.hasNext()) {
//                AirVisual air = itair.next();
//                OpenWeather open = itopen.next();
//                Response response = new Response(air.getData().getCountry(),
//                        air.getData().getState(),
//                        air.getData().getCity(),
//                        air.getData().getCurrent().getPollution().getAqius(),
//                        open.getList().get(0).getComponents());
//                apiResponse.add(response);
            //}
            return airVisualData;
        } catch(NullPointerException e) {
            throw new UserNotFoundException("User doesnt have favourites");
        }

    }

    private List<AirVisual> getAirVisualData(List<Location> locations) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Callable<AirVisual>> callableTasks = new ArrayList<>();

        for (Location location : locations) {
            Callable<AirVisual> callableTask = () -> {
                return getSingleRequest(location.getCountry(), location.getState(), location.getCity());
            };
            callableTasks.add(callableTask);
        }

        List<Future<AirVisual>> futures = executor.invokeAll(callableTasks);
        List<AirVisual> listOfFavourites = new ArrayList<>();
        for (Future<AirVisual> fav : futures) {
            listOfFavourites.add(fav.get());
        }
        executor.shutdown();
        return listOfFavourites;
    }

    private AirVisual getSingleRequest(String country, String state, String city) {
        String url = uriBuilder(country, state, city);
        return restTemplate.getForObject(url, AirVisual.class);
    }

    private String uriBuilder(String country, String state, String city) {
        //http://api.airvisual.com/v2/city?city=Thiruvananthapuram&state=Kerala&country=India&key=46222c20-ed3d-4478-86ca-c13f2896ed48
        String apiKey = "46222c20-ed3d-4478-86ca-c13f2896ed48";
        String baseUri = "http://api.airvisual.com/v2/city";
        return baseUri + "?" + "city=" + city + "&" +
                "state=" + state + "&" +
                "country=" + country + "&" +
                "key=" + apiKey;
    }

    private List<OpenWeather> getOpenApiData(ArrayList<ArrayList<Double>> coordinatesList) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Callable<OpenWeather>> callableTasks = new ArrayList<>();

        for (List<Double> coordinates : coordinatesList) {
            Callable<OpenWeather> callableTask = () -> {
                return getSingleOpenRequest(coordinates);
            };
            callableTasks.add(callableTask);
        }

        List<Future<OpenWeather>> futures = executor.invokeAll(callableTasks);
        List<OpenWeather> pollutantsList = new ArrayList<>();
        for (Future<OpenWeather> pollutant : futures) {
            pollutantsList.add(pollutant.get());
        }
        return pollutantsList;
    }

    private OpenWeather getSingleOpenRequest(List<Double> coordinates) {
        String uri = uriBuilderOpen(coordinates.get(1), coordinates.get(0));
        return restTemplate.getForObject(uri, OpenWeather.class);
    }

    private String uriBuilderOpen(Double latitude, Double longitude) {
        //http://api.openweathermap.org/data/2.5/air_pollution?lat=76.9435879&lon=8.5149093&appid=bb08115ee62ead9f1188cc5419645a27

        String apiKey = "bb08115ee62ead9f1188cc5419645a27";
        String lat = latitude.toString();
        String lon = longitude.toString();
        String baseUri = "http://api.openweathermap.org/data/2.5/air_pollution";
        return baseUri + "?" + "lat=" + lat + "&" + "lon=" + lon + "&" + "appid=" + apiKey;
    }

}
