package com.be.booker.business.services;

public class ServiceOperationException extends RuntimeException {
  public ServiceOperationException() {
  }

  public ServiceOperationException(String message) {
    super(message);
  }

  public ServiceOperationException(Throwable cause) {
    super(cause);
  }

  public ServiceOperationException(String message, Throwable cause) {
    super(message, cause);
  }
}
