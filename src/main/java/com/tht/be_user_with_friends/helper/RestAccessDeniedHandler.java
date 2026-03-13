package com.tht.be_user_with_friends.helper;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RestAccessDeniedHandler implements AccessDeniedHandler {
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    log.warn("Access denied: {}", accessDeniedException.getMessage());
    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType("application/json");
    response.getWriter()
        .write("{\"error\": \"Unauthorized\", \"message\": \"" + accessDeniedException.getMessage() + "\"}");
  }
}
