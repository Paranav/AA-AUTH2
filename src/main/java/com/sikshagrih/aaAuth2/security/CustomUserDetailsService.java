package com.sikshagrih.aaAuth2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sikshagrih.aaAuth2.dao.User;
import com.sikshagrih.aaAuth2.respository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		UserAuthDao userAuthDao = new UserAuthDao(user);
		return userAuthDao;
	}

}
