package com.scaler.userservicemwfeve.services;

import com.scaler.userservicemwfeve.exceptions.TokenNotFoundOrExpiredException;
import com.scaler.userservicemwfeve.exceptions.UserNotFoundException;
import com.scaler.userservicemwfeve.exceptions.WrongPasswordException;
import com.scaler.userservicemwfeve.models.Token;
import com.scaler.userservicemwfeve.models.User;

public interface UserService {

    public User signUp(String name, String email, String hashPassword);
    public Token login(String email, String password) throws UserNotFoundException, WrongPasswordException;
    public void logout(String token) throws TokenNotFoundOrExpiredException;

    public User validateToken(String token);
}
