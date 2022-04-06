package br.com.waytech.simplecontactlist.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.waytech.simplecontactlist.config.error.custom.UnauthorizedException;
import br.com.waytech.simplecontactlist.config.security.service.JWTService;
import br.com.waytech.simplecontactlist.model.User;
import br.com.waytech.simplecontactlist.persistence.repository.UserRepository;

public class JWTSecurityFilter extends OncePerRequestFilter {

	private UserRepository userRepository;
	private JWTService jwtService;

	public JWTSecurityFilter(UserRepository userRepository, JWTService jwtService) {
		this.userRepository = userRepository;
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		try {
			
			String jwtToken = jwtService.extractToken(request);
			DecodedJWT decodedJWT = jwtService.validateToken(jwtToken);
			User user = userRepository.findById(Long.valueOf(decodedJWT.getSubject())) .orElseThrow(() -> new UnauthorizedException("user not found"));
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			
		} catch (Exception ignore) { }

		filterChain.doFilter(request, response);
	}

}
