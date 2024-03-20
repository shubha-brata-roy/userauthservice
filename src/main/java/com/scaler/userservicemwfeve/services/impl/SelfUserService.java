package com.scaler.userservicemwfeve.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.userservicemwfeve.dtos.SendEmailEventDto;
import com.scaler.userservicemwfeve.dtos.UserDto;
import com.scaler.userservicemwfeve.exceptions.TokenNotFoundOrExpiredException;
import com.scaler.userservicemwfeve.exceptions.UserNotFoundException;
import com.scaler.userservicemwfeve.exceptions.WrongPasswordException;
import com.scaler.userservicemwfeve.models.Token;
import com.scaler.userservicemwfeve.models.User;
import com.scaler.userservicemwfeve.repositories.TokenRepository;
import com.scaler.userservicemwfeve.repositories.UserRepository;
import com.scaler.userservicemwfeve.services.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class SelfUserService implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public SelfUserService(UserRepository userRepository,
                           TokenRepository tokenRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           KafkaTemplate<String,String> kafkaTemplate,
                           ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public User signUp(String name, String email, String password) {
        User user = new User();

        user.setEmail(email);
        user.setName(name);
        user.setHashPassword(bCryptPasswordEncoder.encode(password));

        user = userRepository.save(user);

        SendEmailEventDto sendEmailEvent = new SendEmailEventDto();

        sendEmailEvent.setTo(email);
        sendEmailEvent.setFrom("info@scaler.com");
        sendEmailEvent.setSubject("Welcome to Scaler");
        sendEmailEvent.setBody("Thank you signing up with Scaler, "+ user.getName() +
                ". We are glad to have you on board and we wish you all the success in your career.");

        try {
            kafkaTemplate.send(
                    "sendEmail",
                    objectMapper.writeValueAsString(sendEmailEvent)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @Override
    public Token login(String email, String password) throws UserNotFoundException, WrongPasswordException {
        Optional<User> userOptional = userRepository.findUserByEmail(email);

        if(userOptional.isEmpty()) {
            throw new UserNotFoundException("User with email: "+email+" not found.");
        }

        User user = userOptional.get();

        if(!bCryptPasswordEncoder.matches(password, user.getHashPassword())) {
            throw new WrongPasswordException("Incorrect Password. Try again.");
        }

        // create token
        Token token = new Token();
        token.setUser(user);

        LocalDate today = LocalDate.now();
        LocalDate dateAfter30Days = today.plusDays(30);
        Date date = Date.from(dateAfter30Days.atStartOfDay(ZoneId.systemDefault()).toInstant());

        token.setExpiryAt(date);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        return tokenRepository.save(token);
    }

    @Override
    public void logout(String token) throws TokenNotFoundOrExpiredException {
        Optional<Token> tokenOptional = tokenRepository
                .findTokenByValueAndDeletedAndExpiryAtGreaterThanEqual(
                        token,
                        false,
                        new Date());

        if(tokenOptional.isEmpty()) {
            throw new TokenNotFoundOrExpiredException(
                            "Token is Invalid or Expired. Please login again.");
        }

        Token savedToken = tokenOptional.get();

        savedToken.setDeleted(true);

        tokenRepository.save(savedToken);
    }

    @Override
    public User validateToken(String token) {
        Optional<Token> tokenOptional = tokenRepository
                .findTokenByValueAndDeletedAndExpiryAtGreaterThanEqual(
                        token,
                        false,
                        new Date());

        return tokenOptional.map(Token::getUser).orElse(null);

    }
}
