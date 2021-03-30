package com.team01.userservice.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.team01.userservice.exception.UserAlreadyExistsException;
import com.team01.userservice.exception.UserNotFoundException;
import com.team01.userservice.model.User;
import com.team01.userservice.repository.UserAutheticationRepository;


@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

	@Autowired
	UserAutheticationRepository userAuthRepository;
 	public UserAuthenticationServiceImpl(UserAutheticationRepository userAuthRepository) {
		this.userAuthRepository = userAuthRepository;
	}
	
	@Override
	public User findByUserIdAndPassword(String userId, String password) throws UserNotFoundException {
		User user=userAuthRepository.findByUserIdAndUserPassword(userId, password);
		if(user ==null) {
			throw new UserNotFoundException("User is not found");
		}
		return user;
	}
	public User findByUserEmailAndPassword(String userEmail, String password) throws UserNotFoundException{
		User user=userAuthRepository.findByUserEmailAndUserPassword(userEmail, password);
		if(user ==null) {
			throw new UserNotFoundException("User is not found");
		}
		return user;
	}


	@Override
	public boolean saveUser(User user) throws UserAlreadyExistsException {
		Optional<User> optional=userAuthRepository.findById(user.getUserId());
		if(optional.isPresent()){
			throw new UserAlreadyExistsException("user already exist");
		}
		userAuthRepository.save(user);
		return true;
	}
	
}