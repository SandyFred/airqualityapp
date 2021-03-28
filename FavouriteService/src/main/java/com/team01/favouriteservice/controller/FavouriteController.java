package com.team01.favouriteservice.controller;

import com.team01.favouriteservice.exception.LocationAlreadyExistsException;
import com.team01.favouriteservice.exception.UserNotFoundException;
import com.team01.favouriteservice.model.Location;
import com.team01.favouriteservice.service.FavouriteService;
import com.team01.favouriteservice.service.FavouriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/favourites")
@CrossOrigin(origins = "http://localhost:4200")
public class FavouriteController {

    @Autowired
    private FavouriteService favouriteService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getFavourites(@PathVariable("username") String username) throws ExecutionException, InterruptedException {
        try {
            return ResponseEntity.ok().body(favouriteService.getFavourites(username));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    //add failure mapping for city not found
    @DeleteMapping("/{username}/{city}")
    public ResponseEntity<?> deleteFavourite(@PathVariable("username") String username,
                                             @PathVariable("city") String city) {
        favouriteService.deleteFavourite(username, city);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> addFavourites(@PathVariable("username") String username,
                                           @RequestBody Location location) {
        try {
            return ResponseEntity.ok().body(favouriteService.addFavourite(username, location));
        } catch (LocationAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }
}
