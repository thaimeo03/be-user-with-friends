package com.tht.be_user_with_friends.user.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tht.be_user_with_friends.common.exception.UserExistedException;
import com.tht.be_user_with_friends.user.entity.User;
import com.tht.be_user_with_friends.user.repository.UserRepository;
import com.tht.be_user_with_friends.user.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void register(String email, String password) {
    // Check user existed
    Optional<User> existingUser = userRepository.findByEmail(email);
    if (existingUser.isPresent()) {
      throw new UserExistedException();
    }

    // Create new user with hashed password
    String hashedPassword = passwordEncoder.encode(password);
    User newUser = User.builder()
        .email(email)
        .password(hashedPassword)
        .build();

    userRepository.save(newUser);
  }
}
