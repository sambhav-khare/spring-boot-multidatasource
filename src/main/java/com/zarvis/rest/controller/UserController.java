package com.zarvis.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zarvis.model.User;
import com.zarvis.repository.UserRepository;

@RestController
@RequestMapping("zarvis")
public class UserController {

	@Autowired
//	@Qualifier("userRepository")
	private UserRepository userRepository;

	@GetMapping("/user")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/user/{userId}")
	public Optional<User> getUser(@PathVariable("userId") String userId) {
		return userRepository.findById(Integer.parseInt(userId));
	}
	
	@PostMapping("/user")
	public User saveUser(@RequestBody User user) {
		userRepository.save(user);
		return user;
	}
	
	@PutMapping("/user")
	public User updateUser(@RequestBody User user) {
		return userRepository.saveAndFlush(user);
	}
	
	@DeleteMapping("/user/{userId}")
	public void deleteUser(@PathVariable("userId") int userId) {
		userRepository.deleteById(userId);
	}
}
