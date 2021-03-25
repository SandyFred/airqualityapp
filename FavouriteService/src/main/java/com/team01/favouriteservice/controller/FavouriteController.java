package com.team01.favouriteservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FavouriteController {

    @GetMapping("/")
    public ResponseEntity<?> getFavourites() {
        return ResponseEntity.ok().body("Welcome");
    }
}
