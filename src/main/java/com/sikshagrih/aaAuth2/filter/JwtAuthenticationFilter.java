package com.sikshagrih.aaAuth2.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sikshagrih.aaAuth2.security.UserAuthDao;
import com.sikshagrih.aaAuth2.util.JwtTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private JwtTokenUtil tokenUtil;

	private UserDetailsService userDetailsService;

	private ObjectMapper mapper = new ObjectMapper();

	public JwtAuthenticationFilter(UserDetailsService userDetailService, JwtTokenUtil tokenUtil) {
		this.tokenUtil = tokenUtil;
		this.userDetailsService = userDetailService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader("Authorization");
		if (token != null) {
			String userName = tokenUtil.getUsernameFromToken(token.substring(7));
			UserDetails userDetails = (UserAuthDao) userDetailsService.loadUserByUsername(userName);
			if (tokenUtil.validateToken(token.substring(7), userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
				doFilter(request, response, filterChain);
			} else {
				Exception exception = new Exception("Invalid Exception");
				response.setStatus(401);
				response.setContentType("application/json");
				response.getWriter().write(mapper.writeValueAsString(exception));
			}
		} else {
			doFilter(request, response, filterChain);
		}

	}

}
