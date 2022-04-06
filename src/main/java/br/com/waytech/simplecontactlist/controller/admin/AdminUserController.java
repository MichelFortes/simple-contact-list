package br.com.waytech.simplecontactlist.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.waytech.simplecontactlist.dto.user.UserDto;
import br.com.waytech.simplecontactlist.dto.user.UserFormDto;
import br.com.waytech.simplecontactlist.model.User;
import br.com.waytech.simplecontactlist.service.UserService;

@RestController
@RequestMapping(path = AdminUserController.PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserController {

	public final static String PATH = AdminController.PATH + "/users";

	@Autowired
	private UserService userService;

	@GetMapping("{id}")
	public ResponseEntity<UserDto> get(
			@PathVariable("id") Long id,
			@RequestParam(name = "projection", defaultValue = "FULL") UserDto.Projection projection) {
		
		User user = userService.get(id);
		return ResponseEntity.status(HttpStatus.OK).body(UserDto.from(user, projection));
	}

	@GetMapping
	public ResponseEntity<Page<UserDto>> get(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "projection", defaultValue = "SIMPLE") UserDto.Projection projection,
			@PageableDefault(page = 0, size = 20, sort = "name", direction = Direction.ASC) Pageable pageable) {

		Page<User> user = userService.getAll(name, pageable);
		return ResponseEntity.status(HttpStatus.OK).body(UserDto.from(user, pageable, projection));
	}

	@PatchMapping
	public ResponseEntity<UserDto> update(
			@PathVariable("id") Long id,
			@RequestBody UserFormDto form,
			@RequestParam(name = "projection", defaultValue = "FULL") UserDto.Projection projection) {

		User user = userService.update(id, form);
		return ResponseEntity.status(HttpStatus.OK).body(UserDto.from(user, projection));
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Long id) {

		userService.delete(id);
	}

}
