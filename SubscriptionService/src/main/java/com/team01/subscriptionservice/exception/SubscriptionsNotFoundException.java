package com.team01.subscriptionservice.exception;

public class SubscriptionsNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public SubscriptionsNotFoundException(String message) {
        super(message);
    }

}