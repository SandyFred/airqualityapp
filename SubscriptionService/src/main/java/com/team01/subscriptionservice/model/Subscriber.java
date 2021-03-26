package com.team01.subscriptionservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Subscriber {

    @Id
    private String id;
    private String username;
    private String subscriptionPlan;
    private String subscriptionDate;
    private String subscriptionValidity;


}
