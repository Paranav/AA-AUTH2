package com.sikshagrih.aaAuth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sikshagrih.aaAuth2.dao.AuthRequest;
import com.sikshagrih.aaAuth2.dao.User;
import com.sikshagrih.aaAuth2.respository.UserRepository;
import com.sikshagrih.aaAuth2.security.UserAuthDao;
import com.sikshagrih.aaAuth2.util.JwtTokenUtil;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public PasswordEncoder passwordEncoder;

	
	@Autowired
	public AuthenticationManager authenticaitonManager;
	
	@Autowired
	public JwtTokenUtil jwtTokenUtil;
	
	
	@Override
	public User createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public String login(AuthRequest authRequest) {
		Authentication authentication = authenticaitonManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		return getToken(authRequest);
	}
	
	public String getToken(AuthRequest authRequest) {
		UserAuthDao authDao = new UserAuthDao();
		authDao.setUsername(authRequest.getUsername());
		authDao.setPassword(authRequest.getPassword());
		return jwtTokenUtil.generateToken(authDao);
	}

}
