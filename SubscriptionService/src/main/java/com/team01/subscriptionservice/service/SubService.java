package com.team01.subscriptionservice.service;

import com.team01.subscriptionservice.repository.SubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubService {

    @Autowired
    private SubRepository subRepository;

    //add service methods for adding,removing and updating subscriber
}
