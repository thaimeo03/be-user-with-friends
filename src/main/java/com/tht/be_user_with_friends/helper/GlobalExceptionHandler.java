package com.tht.be_user_with_friends.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import com.tht.be_user_with_friends.common.dto.response.BaseResponse;
import com.tht.be_user_with_friends.common.exception.BaseException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  @ExceptionHandler(Unauthorized.class)
  public ResponseEntity<BaseResponse> handleUnauthorizedException(Unauthorized ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(BaseResponse.error(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()));
  }

  @ExceptionHandler(AuthorizationDeniedException.class)
  public ResponseEntity<BaseResponse> handleAccessDeniedException(AuthorizationDeniedException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(BaseResponse.error(HttpStatus.FORBIDDEN.value(), ex.getMessage()));
  }

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<BaseResponse> handleBaseException(BaseException ex) {
    log.error(ex.getStatus().getReasonPhrase(),
        ex.getMessage(), ex);

    return ResponseEntity.status(ex.getStatus())
        .body(BaseResponse.error(ex.getStatus().value(), ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<BaseResponse> handleInternalException(Exception ex) {
    log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
        ex.getMessage(), ex);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
  }
}
