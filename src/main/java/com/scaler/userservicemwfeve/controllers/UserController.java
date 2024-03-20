package com.scaler.userservicemwfeve.controllers;

import com.scaler.userservicemwfeve.dtos.*;
import com.scaler.userservicemwfeve.exceptions.TokenNotFoundOrExpiredException;
import com.scaler.userservicemwfeve.exceptions.UserNotFoundException;
import com.scaler.userservicemwfeve.exceptions.WrongPasswordException;
import com.scaler.userservicemwfeve.models.Token;
import com.scaler.userservicemwfeve.models.User;
import com.scaler.userservicemwfeve.services.UserService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto request)
                                throws UserNotFoundException, WrongPasswordException {
        return userService.login(request.getEmail(), request.getPassword());
    }

    @PostMapping("/signup")
    public User signUp(@RequestBody SignUpRequestDto request) {
        return userService.signUp(request.getName(),
                                    request.getEmail(),
                                    request.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageDto> logout(
                @RequestHeader("AuthenticationToken") @NonNull String token)
                                throws TokenNotFoundOrExpiredException {
        userService.logout(token);
        MessageDto message = new MessageDto();
        message.setMessage("Logged out successfully.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/validate/{token}")
    public UserDto validateToken(@PathVariable("token") @NonNull String token) {
        return UserDto.from(userService.validateToken(token));
    }
}
