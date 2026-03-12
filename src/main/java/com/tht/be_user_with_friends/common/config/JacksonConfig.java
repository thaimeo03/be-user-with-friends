package com.tht.be_user_with_friends.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfig {

  @Bean
  ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

}
