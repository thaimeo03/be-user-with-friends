package com.tht.be_user_with_friends.auth.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tht.be_user_with_friends.common.enums.UserRole;
import com.tht.be_user_with_friends.common.exception.BannedUserException;
import com.tht.be_user_with_friends.user.entity.User;
import com.tht.be_user_with_friends.user.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ValidUserFilter extends OncePerRequestFilter {
  private final UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // Get authentication from security context
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // Check if authentication is valid
    if (authentication != null && authentication.isAuthenticated()) {
      String email = authentication.getName();
      Optional<User> user = userRepository.findByEmail(email);

      // If user is found and role is BANNED, throw exception
      if (user.isPresent() && user.get().getRole() == UserRole.BANNED) {
        throw new BannedUserException();
      }
    }

    filterChain.doFilter(request, response);
  }

}
