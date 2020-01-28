package com.be.booker.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class OccupiedRoomException extends RuntimeException {

    public OccupiedRoomException(String s) {
        super(s);
    }
}
