package com.tht.be_user_with_friends.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends BaseException {
  private static final String MESSAGE = "Bad request exception";

  public BadRequestException(String message) {
    super(message, HttpStatus.BAD_REQUEST);
  }

  public BadRequestException() {
    super(MESSAGE, HttpStatus.BAD_REQUEST);
  }
}
