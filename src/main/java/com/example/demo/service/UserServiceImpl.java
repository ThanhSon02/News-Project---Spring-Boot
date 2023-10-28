package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository UserRepository;
	
	
	@Override
	public void save(UserModel userModel) {
		UserRepository.save(userModel);
	}
	
}
