package com.team01.subscriptionservice.service;

import java.util.List;

import com.team01.subscriptionservice.exception.SubscriptionsNotFoundException;
import com.team01.subscriptionservice.model.Subscriber;

public interface SubService {
    //add service methods for adding,removing and updating subscriber
	boolean createSubcriber(Subscriber subcriber);
	Subscriber getSubscriberById(String Id)throws SubscriptionsNotFoundException;
	List<Subscriber> getAllSubscriber();
	boolean removeSubscriber(String Id)throws SubscriptionsNotFoundException;
	Subscriber updateSubscriber(Subscriber subcriber) throws SubscriptionsNotFoundException;
	
}
