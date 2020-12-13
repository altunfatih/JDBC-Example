package com.proje.service.impl;

import java.util.List;

import com.proje.model.User;
import com.proje.repository.UserRepository;
import com.proje.repository.impl.UserRepositoryImpl;
import com.proje.service.UserService;

public class UserServiceImpl implements UserService{
	
	private UserRepository userRepository = new UserRepositoryImpl();

	@Override
	public User saveUser(User user) {
		return userRepository.saveUser(user);
	}

	@Override
	public boolean saveUserProduct(int userId, int productId) {
		return userRepository.saveUserProduct(userId, productId);
	}

	@Override
	public User updateUser(User user) {
		return userRepository.updateUser(user);
	}

	@Override
	public boolean removeUser(int id) {
		return userRepository.removeUser(id);
	}

	@Override
	public User findUserById(int id) {
		return userRepository.findUserById(id);
	}

	@Override
	public User findUserProductById(int id) {
		return userRepository.findUserProductById(id);
	}

	@Override
	public List<User> findUsers() {
		return userRepository.findUsers();
	}
	
}
