package com.sikshagrih.aaAuth2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.sikshagrih.aaAuth2.filter.JwtAuthenticationFilter;
import com.sikshagrih.aaAuth2.util.JwtTokenUtil;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
	

	@Bean
	public UserDetailsService userDetails() {
		return new CustomUserDetailsService();
	}
	
//	@Bean
//	public JwtAuthenticationFilter createAuthenticationFilter(JwtTokenUtil tokenUtil, AuthenticationManager authManager,
//			UserDetailsService userDetailService) {
//		return new JwtAuthenticationFilter(userDetailService, authManager, tokenUtil);
//	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter authenticationFilter) throws Exception {
		http.csrf().disable().cors().and().authorizeHttpRequests().requestMatchers(new RequestMatcher() {
			@Override
			public boolean matches(HttpServletRequest request) {
				if ((request.getMethod().equals(HttpMethod.POST.name())
						&& request.getRequestURI().contains("/api/v1/user/login"))
						|| (request.getMethod().equals(HttpMethod.POST.name())
								&& request.getRequestURI().contains("/error"))
						|| (request.getMethod().equals(HttpMethod.GET.name())
								&& request.getRequestURI().contains("/api/v1/basic/welcome"))) {
					return true;
				}
				return false;
			}
		}).permitAll().anyRequest().authenticated().and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(getAuthenticationProvider())
        .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

	@Bean
	public AuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetails());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	@Bean
	public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}