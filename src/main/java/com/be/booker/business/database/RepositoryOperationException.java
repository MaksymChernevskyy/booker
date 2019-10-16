package com.be.booker.business.database;

public class RepositoryOperationException extends RuntimeException{

  public RepositoryOperationException() {
  }

  public RepositoryOperationException(String message) {
    super(message);
  }

  public RepositoryOperationException(Throwable cause) {
    super(cause);
  }

  public RepositoryOperationException(String message, Throwable cause) {
    super(message, cause);
  }
}
