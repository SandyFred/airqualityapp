package com.team01.userservice.service;
import com.team01.userservice.exception.UserAlreadyExistsException;
import com.team01.userservice.exception.UserNotFoundException;
import com.team01.userservice.model.User;

public interface UserAuthenticationService {
    
    public User findByUserIdAndPassword(String userId, String password) throws UserNotFoundException;
    public User findByUserEmailAndPassword(String userEmail, String password) throws UserNotFoundException;
    boolean saveUser(User user) throws UserAlreadyExistsException;
}
