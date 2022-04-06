package br.com.waytech.simplecontactlist.controller.user;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.waytech.simplecontactlist.dto.contact.ContactDto;
import br.com.waytech.simplecontactlist.dto.contact.ContactFormDto;
import br.com.waytech.simplecontactlist.model.Contact;
import br.com.waytech.simplecontactlist.model.User;
import br.com.waytech.simplecontactlist.service.ContactService;

@RestController
@RequestMapping(path = UserContactController.PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserContactController {

	public final static String PATH = UserController.PATH + "/contacts";

	@Autowired
	private ContactService contactService;

	
	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ContactDto> create(
			@RequestPart(name = "form") ContactFormDto form,
			@RequestPart(name = "file") MultipartFile file,
			Authentication authentication) throws IOException {

		Contact contact = contactService.create((User) authentication.getPrincipal(), form, file);
		return ResponseEntity.status(HttpStatus.CREATED).body(ContactDto.from(contact, ContactDto.Projection.FULL));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ContactDto> get(
			@PathVariable(name = "id") Long id,
			@RequestParam(name = "projection", defaultValue = "FULL") ContactDto.Projection projection,
			Authentication authentication) {

		Contact contact = contactService.getByIdAndUser(id, (User) authentication.getPrincipal());
		return ResponseEntity.status(HttpStatus.OK).body(ContactDto.from(contact, projection));
	}

	@GetMapping
	public ResponseEntity<Page<ContactDto>> get(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "projection", defaultValue = "SIMPLE") ContactDto.Projection projection,
			@PageableDefault(page = 0, size = 20, sort = "name", direction = Direction.ASC) Pageable pageable,
			Authentication authentication) {

		Page<Contact> contact = contactService.getAllByUserAndName((User) authentication.getPrincipal(), name, pageable);
		return ResponseEntity.status(HttpStatus.OK).body(ContactDto.from(contact, pageable, projection));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ContactDto> update(
			@PathVariable(name = "id") Long id, @RequestBody ContactFormDto form,
			@RequestParam(name = "projection", defaultValue = "FULL") ContactDto.Projection projection,
			Authentication authentication) {

		Contact contact = contactService.updateByIdAndUser(id, (User) authentication.getPrincipal(), form);
		return ResponseEntity.status(HttpStatus.OK).body(ContactDto.from(contact, projection));
	}

	@GetMapping(path = "/{id}/picture", consumes = { MediaType.ALL_VALUE })
	public void getPicture(
			@PathVariable(name = "id") Long id,
			HttpServletResponse response,
			Authentication authentication) throws IOException {

		contactService.writeImageByIdAndUser(id, (User) authentication.getPrincipal(), response);
	}
	
	@DeleteMapping(path = "/{id}/picture")
	@Transactional
	public void deletePicture(
			@PathVariable(name = "id") Long id,
			Authentication authentication) throws IOException {

		Contact contact = contactService.getByIdAndUser(id, (User) authentication.getPrincipal());
		contact.setPicture(null);
	}
	
	@PostMapping(path = "/{id}/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Transactional
	public ResponseEntity<?> updatePicture(
			@PathVariable(name = "id") Long id,
			@RequestPart(name = "file") MultipartFile file,
			Authentication authentication) throws IOException {

		Contact contact = contactService.updatePictureByIdAndUser(id, (User) authentication.getPrincipal(), file);
		return ResponseEntity.status(HttpStatus.OK).body(ContactDto.from(contact, ContactDto.Projection.FULL));
	}

	@DeleteMapping("/{id}")
	public void delete(
			@PathVariable(name = "id") Long id,
			Authentication authentication) {
		
		contactService.deleteByIdAndUser(id, (User) authentication.getPrincipal());
	}

}
