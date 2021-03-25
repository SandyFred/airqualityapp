package com.team01.favouriteservice.repository;

import com.team01.favouriteservice.model.Favourite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteRepository extends MongoRepository<Favourite, String> {
    Favourite findByUsername(String username);
}
