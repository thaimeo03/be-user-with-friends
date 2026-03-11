package com.tht.be_user_with_friends;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BeUserWithFriendsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeUserWithFriendsApplication.class, args);
	}

}
