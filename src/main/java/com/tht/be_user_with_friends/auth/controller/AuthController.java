package com.tht.be_user_with_friends.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tht.be_user_with_friends.auth.dto.request.LoginRequest;
import com.tht.be_user_with_friends.auth.dto.request.RegisterRequest;
import com.tht.be_user_with_friends.auth.service.AuthService;
import com.tht.be_user_with_friends.common.dto.response.BaseResponse;
import com.tht.be_user_with_friends.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final UserService userService;
  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    userService.register(request.getEmail(), request.getPassword());

    return ResponseEntity.ok(
        BaseResponse.success("User registered successfully"));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) throws Exception {
    authService.login(request, httpRequest);

    return ResponseEntity.ok(BaseResponse.success("Login successful"));
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest httpRequest) {
    authService.logout(httpRequest);

    return ResponseEntity.ok(BaseResponse.success("Logout successful"));
  }

}
