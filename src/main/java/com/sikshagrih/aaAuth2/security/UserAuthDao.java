package com.sikshagrih.aaAuth2.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sikshagrih.aaAuth2.dao.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDao implements UserDetails {

	private String username;
	private String password;
	private List<String> authority;

	public UserAuthDao(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.authority = user.getRoles();
	}

	private List<GrantedAuthority> getAuthorities(List<String> roles) {
		List<GrantedAuthority> authority = roles.stream().map(role -> new GrantedAuthority() {
			@Override
			public String getAuthority() {
				// TODO Auto-generated method stub
				return role;
			}
		}).collect(Collectors.toList());
		return authority;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return this.getAuthorities(authority);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
