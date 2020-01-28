package com.be.booker.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OccupiedRoomException.class)
    public final ResponseEntity<ExceptionDetails> handleInvalidIDNumber(OccupiedRoomException ex, WebRequest request) {
        ExceptionDetails errorDetails = new ExceptionDetails(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
