package com.tht.be_user_with_friends.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tht.be_user_with_friends.auth.filter.ValidUserFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  @Value("${app.max-session-per-user:1}")
  private int maxSessionPerUser;

  private final ValidUserFilter validUserFilter;

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http)
      throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/login", "/auth/register").permitAll()
            .anyRequest().authenticated())
        .addFilterAfter(validUserFilter, UsernamePasswordAuthenticationFilter.class)
        .formLogin(form -> form.disable())
        .sessionManagement(session -> session
            .sessionFixation().migrateSession()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .maximumSessions(maxSessionPerUser));

    return http.build();

  }
}
