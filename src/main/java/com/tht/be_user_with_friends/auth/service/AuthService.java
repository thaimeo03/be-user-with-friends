package com.tht.be_user_with_friends.auth.service;

import com.tht.be_user_with_friends.auth.dto.request.LoginRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
  public void login(LoginRequest request, HttpServletRequest httpRequest) throws Exception;

  public void logout(HttpServletRequest httpRequest);
}
