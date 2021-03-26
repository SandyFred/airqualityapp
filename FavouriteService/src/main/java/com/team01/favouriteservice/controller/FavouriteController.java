package com.team01.favouriteservice.controller;

import com.team01.favouriteservice.model.Location;
import com.team01.favouriteservice.service.FavouriteService;
import com.team01.favouriteservice.service.FavouriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FavouriteController {

    @Autowired
    private FavouriteServiceImpl favouriteService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getFavourites(@PathVariable("username") String username) {

        return ResponseEntity.ok().body(favouriteService.getFavourites(username));
    }

    @DeleteMapping("/{username}/{city}")
    public ResponseEntity<?> deleteFavourite(@PathVariable("username") String username,
                                             @PathVariable("city") String city) {
        favouriteService.deleteFavourite(username, city);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> getFavourites(@PathVariable("username") String username,
                                           @RequestBody Location location) {

        return ResponseEntity.ok().body(favouriteService.addFavourite(username,location));
    }
}
