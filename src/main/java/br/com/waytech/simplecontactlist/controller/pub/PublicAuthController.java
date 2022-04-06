package br.com.waytech.simplecontactlist.controller.pub;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.waytech.simplecontactlist.config.error.custom.BadRequestException;
import br.com.waytech.simplecontactlist.config.security.dto.AuthForm;
import br.com.waytech.simplecontactlist.config.security.dto.AuthResponseDto;
import br.com.waytech.simplecontactlist.config.security.service.JWTService;
import br.com.waytech.simplecontactlist.model.User;

@RestController
@RequestMapping(path = PublicAuthController.PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PublicAuthController {

	public final static String PATH = "/public/auth";

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JWTService jwtService;

	@PostMapping
	public ResponseEntity<AuthResponseDto> auth(@RequestBody @Valid AuthForm form) {

		try {
			
			UsernamePasswordAuthenticationToken authData = form.generateAuthToken();
			Authentication authentication = authManager.authenticate(authData);
			User user = (User) authentication.getPrincipal();
			String token = jwtService.generate(user);
			
			AuthResponseDto dto = new AuthResponseDto();
			dto.setName(user.getName());
			dto.setEmail(user.getEmail());
			dto.setTokenType("bearer");
			dto.setToken(token);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(dto);
			
		} catch (AuthenticationException e) {
			throw new BadRequestException(e.getLocalizedMessage());
		}
	}

}
