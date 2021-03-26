package com.team01.subscriptionservice.repository;

import com.team01.subscriptionservice.model.Subscriber;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubRepository extends MongoRepository<Subscriber, String> {
}
