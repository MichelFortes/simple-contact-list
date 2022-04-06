package br.com.waytech.simplecontactlist.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.waytech.simplecontactlist.dto.user.UserDto;
import br.com.waytech.simplecontactlist.dto.user.UserFormDto;
import br.com.waytech.simplecontactlist.model.User;
import br.com.waytech.simplecontactlist.service.UserService;

@RestController
@RequestMapping(path = UserController.PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	public final static String PATH = "/user";

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<UserDto> get(
			@RequestParam(name = "projection", defaultValue = "FULL") UserDto.Projection projection,
			Authentication authentication) {
		
		User user = userService.get(((User) authentication.getPrincipal()).getId());
		return ResponseEntity.status(HttpStatus.OK).body(UserDto.from(user, projection));
	}

	@PatchMapping
	public ResponseEntity<UserDto> update(
			@RequestBody UserFormDto form,
			@RequestParam(name = "projection", defaultValue = "FULL") UserDto.Projection projection,
			Authentication authentication) {

		User user = userService.update(((User) authentication.getPrincipal()).getId(), form);
		return ResponseEntity.status(HttpStatus.OK).body(UserDto.from(user, projection));
	}

	@DeleteMapping
	public void delete(Authentication authentication) {

		userService.delete(((User) authentication.getPrincipal()).getId());
	}

}
