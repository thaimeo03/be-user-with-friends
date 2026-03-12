package com.tht.be_user_with_friends.common.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {
  protected HttpStatus status;

  public BaseException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
