package com.be.booker.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class AlreadyExistsException extends RuntimeException {

  public AlreadyExistsException(String s) {
    super(s);
  }
}
