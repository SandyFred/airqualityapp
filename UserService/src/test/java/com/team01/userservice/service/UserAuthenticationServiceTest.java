package com.team01.userservice.service;

import com.team01.userservice.exception.UserAlreadyExistsException;
import com.team01.userservice.exception.UserNotFoundException;
import com.team01.userservice.model.User;
import com.team01.userservice.repository.UserAutheticationRepository;
import com.team01.userservice.service.UserAuthenticationServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UserAuthenticationServiceTest {

    @Mock
    private UserAutheticationRepository autheticationRepository;

    private User user;
    @InjectMocks
    private UserAuthenticationServiceImpl authenticationService;

    Optional<User> optional;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setUserId("anagha");
        user.setUserPassword("password");
        optional = Optional.of(user);
    }

    @Test
    public void testSaveUserSuccess() throws UserAlreadyExistsException {

        Mockito.when(autheticationRepository.save(user)).thenReturn(user);
        boolean flag = authenticationService.saveUser(user);
        Assert.assertEquals("Cannot Register User", true, flag);

    }


    @Test(expected = UserAlreadyExistsException.class)
    public void testSaveUserFailure() throws UserAlreadyExistsException {

        Mockito.when(autheticationRepository.findById("anagha")).thenReturn(optional);
        Mockito.when(autheticationRepository.save(user)).thenReturn(user);
        boolean flag = authenticationService.saveUser(user);
        Assert.assertEquals("Cannot Register User", true, flag);

    }

    @Test
    public void testFindByUserIdAndPassword() throws UserNotFoundException {
        Mockito.when(autheticationRepository.findByUserIdAndUserPassword("anagha", "password")).thenReturn(user);
        User fetchedUser = authenticationService.findByUserIdAndPassword("anagha", "password");
        Assert.assertEquals("anagha", fetchedUser.getUserId());
    }
}
