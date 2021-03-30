package com.team01.userservice.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.team01.userservice.model.User;

@Repository
public interface UserAutheticationRepository extends JpaRepository<User, String> {
    
    User findByUserIdAndUserPassword(String userId, String userPassword);

	User findByUserEmailAndUserPassword(String userEmail, String password);
}