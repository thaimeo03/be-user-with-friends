package com.tht.be_user_with_friends.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class BannedUserException extends BaseException {
  private static final String MESSAGE = "User is banned";

  public BannedUserException() {
    super(MESSAGE, HttpStatus.FORBIDDEN);
  }
}