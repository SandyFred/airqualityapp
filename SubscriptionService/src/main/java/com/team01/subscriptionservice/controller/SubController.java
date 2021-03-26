package com.team01.subscriptionservice.controller;

import com.team01.subscriptionservice.service.SubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class SubController {

    @Autowired
    private SubService subService;

    // add controllers for adding,removing,updating and reading subscription
    @PostMapping("/{username}")
    public ResponseEntity<?> addSubscriber(@PathVariable("username") String username) {


        return null;
    }


    public ResponseEntity<?> removeSubscriber() {

    return null;
    }

    public ResponseEntity<?> isSubscribed() {

    return null;
    }

}
