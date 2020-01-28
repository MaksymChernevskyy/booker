package com.be.booker.business.exceptions;

import java.util.Date;

public class ExceptionDetails {

    private Date timestamp;
    private String message;

    public ExceptionDetails(Date timestamp, String message) {
        super();
        this.timestamp = timestamp;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

}
