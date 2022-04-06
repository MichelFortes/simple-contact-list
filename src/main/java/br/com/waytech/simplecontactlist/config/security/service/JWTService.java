package br.com.waytech.simplecontactlist.config.security.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import br.com.waytech.simplecontactlist.config.error.custom.UnauthorizedException;
import br.com.waytech.simplecontactlist.model.User;

@Service
public class JWTService {
	
	private final static String CLAIM_ISSUER = "WebService";

	@Value("${security.jwt.ttl}")
	private String tokenTTL;

	@Value("${security.jwt.secret}")
	private String tokenSecret;

	public String generate(User user) {
		Algorithm algorithm = Algorithm.HMAC512(tokenSecret);
		return JWT.create()
				.withIssuer(CLAIM_ISSUER)
				.withSubject(user.getId().toString())
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + Long.parseUnsignedLong(tokenTTL.trim())))
				.sign(algorithm);
	}

	public DecodedJWT validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC512(tokenSecret);
			JWTVerifier verifier = JWT.require(algorithm).withIssuer(CLAIM_ISSUER).build(); // Reusable verifier instance
			return verifier.verify(token);
		} catch (Exception e) {
			throw new UnauthorizedException(e.getLocalizedMessage());
		}
	}

	public String extractToken(HttpServletRequest request) {

		try {
			String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			return authHeader.substring(7);
		} catch (Exception ex) {
			throw new UnauthorizedException("bad token");
		}
	}
	
}
