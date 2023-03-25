package com.sikshagrih.aaAuth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sikshagrih.aaAuth2.dao.AuthRequest;
import com.sikshagrih.aaAuth2.dao.User;
import com.sikshagrih.aaAuth2.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	@Autowired
	public UserService userService;

	
	
	@PostMapping("/create")
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}
	
	@GetMapping("/{id}")
	public User getUserById(@PathVariable String id) {
		return new User();
	}
	
	@PostMapping("/login")
	public String login(@RequestBody AuthRequest authRequest) {
		return userService.login(authRequest);
	}

}
