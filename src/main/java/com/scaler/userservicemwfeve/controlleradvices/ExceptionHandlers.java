package com.scaler.userservicemwfeve.controlleradvices;

import com.scaler.userservicemwfeve.dtos.MessageDto;
import com.scaler.userservicemwfeve.exceptions.TokenNotFoundOrExpiredException;
import com.scaler.userservicemwfeve.exceptions.UserNotFoundException;
import com.scaler.userservicemwfeve.exceptions.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<MessageDto> handleUserNotFoundException(
                                    Exception exception) {
        MessageDto message = new MessageDto();
        message.setMessage(exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<MessageDto> handleWrongPasswordException(
                                    Exception exception) {
        MessageDto message = new MessageDto();
        message.setMessage(exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenNotFoundOrExpiredException.class)
    public ResponseEntity<MessageDto> TokenNotFoundOrExpiredException(
                                    Exception exception) {
        MessageDto message = new MessageDto();
        message.setMessage(exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }
}
