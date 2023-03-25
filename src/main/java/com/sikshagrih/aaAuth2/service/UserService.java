package com.sikshagrih.aaAuth2.service;

import com.sikshagrih.aaAuth2.dao.AuthRequest;
import com.sikshagrih.aaAuth2.dao.User;

public interface UserService {
	
	public User createUser(User user);

	public String login(AuthRequest authRequest);
}
