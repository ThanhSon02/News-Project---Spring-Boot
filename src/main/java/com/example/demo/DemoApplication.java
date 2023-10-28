package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserServiceImpl;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{
	
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);		
	}

	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
//		UserModel userModel = new UserModel();
//		userModel.setUsername("admin");
//		userModel.setPassword(passwordEncoder.encode("admin"));
//		userServiceImpl.save(userModel);
	}
}
