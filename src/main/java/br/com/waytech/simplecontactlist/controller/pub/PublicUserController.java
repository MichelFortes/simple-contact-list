package br.com.waytech.simplecontactlist.controller.pub;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.waytech.simplecontactlist.dto.user.UserDto;
import br.com.waytech.simplecontactlist.dto.user.UserFormDto;
import br.com.waytech.simplecontactlist.model.User;
import br.com.waytech.simplecontactlist.service.UserService;

@RestController
@RequestMapping(path = PublicUserController.PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PublicUserController {

	public final static String PATH = "/public/user";

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<UserDto> create(
			@RequestBody @Valid UserFormDto form,
			@RequestParam(name = "projection", defaultValue = "FULL") UserDto.Projection projection) {

		User user = userService.create(form);
		return ResponseEntity.status(HttpStatus.CREATED).body(UserDto.from(user, projection));
	}

}
