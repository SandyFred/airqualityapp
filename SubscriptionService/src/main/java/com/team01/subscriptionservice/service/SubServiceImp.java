package com.team01.subscriptionservice.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.team01.subscriptionservice.exception.SubscriptionsNotFoundException;
import com.team01.subscriptionservice.model.Subscriber;
import com.team01.subscriptionservice.repository.SubRepository;

@Service
public class SubServiceImp implements SubService 
{
	
		 @Autowired
		 private SubRepository subRepository;
		 
		 Subscriber subscriber = null;
		 List<Subscriber> subscriptionList = null;
		 SubServiceImp(SubRepository subRepository){
			 this.subRepository = subRepository;
		 }

		 //Create new Subscription
		 public boolean createSubcriber(Subscriber subscriber) {
			 
			 if(subRepository.existsById(subscriber.getId())) {
				 return false;
			 }
			 else {
				 
				 subscriber.setSubscriptionDate(new Date());
				 if(subRepository.save(subscriber) != null) {
					 return true;
				 }
				 else {
					 return false;
				 }
			 }
		 }
		//getAllSubscriber
		 
		 public List<Subscriber> getAllSubscriber(){
			 
			 subscriptionList = subRepository.findAll();
			 return subscriptionList;
		 }
		//getSubscriberDataBySubscriberId
		 public Subscriber getSubscriberById(String Id) throws SubscriptionsNotFoundException{
			try {
				
					Subscriber subscriber =  subRepository.findById(Id).get();
				return subscriber;
			}
			catch(NoSuchElementException e) {
				throw new SubscriptionsNotFoundException("Subscription not found");
			}
		 }
		//Delete Subscription
		 public boolean removeSubscriber(String Id) throws SubscriptionsNotFoundException{
			 try {
				 Subscriber subscriber = subRepository.findById(Id).get();
				 subRepository.delete(subscriber);
				return true;
			 }
			 catch(NoSuchElementException e) {
				 throw new SubscriptionsNotFoundException("SubsriptionDoesNotexist");
			 }
		 }
		 //Update Subscription
		 public Subscriber updateSubscriber(Subscriber subscriber) throws SubscriptionsNotFoundException{
			 try {
				 Subscriber fetchSubscriber = subRepository.findById(subscriber.getId()).get();
				 fetchSubscriber.setUsername(subscriber.getUsername());
				 fetchSubscriber.setSubscriptionDate(new Date());
				 fetchSubscriber.setSubscriptionPlan(subscriber.getSubscriptionPlan());
				 
				 subRepository.save(fetchSubscriber);
				 return fetchSubscriber;
			 }
			 catch(NoSuchElementException e) {
				 throw new SubscriptionsNotFoundException("Subscription does not exist");
			 }
		 }

}
