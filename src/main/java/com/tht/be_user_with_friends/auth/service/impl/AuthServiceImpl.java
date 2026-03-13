package com.tht.be_user_with_friends.auth.service.impl;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import com.tht.be_user_with_friends.auth.dto.request.LoginRequest;
import com.tht.be_user_with_friends.auth.service.AuthService;
import com.tht.be_user_with_friends.common.enums.UserRole;
import com.tht.be_user_with_friends.common.exception.BadRequestException;
import com.tht.be_user_with_friends.common.exception.BannedUserException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final AuthenticationManager authenticationManager;

  @Override
  public void login(LoginRequest request, HttpServletRequest httpRequest) throws Exception {
    try {
      // Create authentication
      AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(),
          request.getPassword());
      Authentication authentication = authenticationManager.authenticate(authenticationToken);

      // Check if user is banned
      UserRole userRole = authentication.getAuthorities().stream()
          .filter(auth -> auth.getAuthority().startsWith("ROLE_"))
          .map(auth -> UserRole.valueOf(auth.getAuthority().substring(5)))
          .findFirst()
          .orElseThrow(() -> new BadRequestException());
      if (userRole == UserRole.BANNED) {
        throw new BannedUserException();
      }

      // Set authentication to security context
      SecurityContext context = SecurityContextHolder.createEmptyContext();
      context.setAuthentication(authentication);

      // Create session and set security context
      HttpSession session = httpRequest.getSession(true);
      session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
    } catch (Exception e) {
      if (e instanceof BannedUserException) {
        throw e;
      }

      throw new BadRequestException("Invalid email or password");
    }
  }

  @Override
  public void logout(HttpServletRequest httpRequest) {
    HttpSession session = httpRequest.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    SecurityContextHolder.clearContext();
  }
}
