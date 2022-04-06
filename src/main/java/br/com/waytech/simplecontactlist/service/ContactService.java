package br.com.waytech.simplecontactlist.service;

import java.io.IOException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import br.com.waytech.simplecontactlist.dto.Base64File;
import br.com.waytech.simplecontactlist.dto.contact.ContactFormDto;
import br.com.waytech.simplecontactlist.model.Contact;
import br.com.waytech.simplecontactlist.model.User;
import br.com.waytech.simplecontactlist.persistence.repository.ContactRepository;

@Service
public class ContactService {

	@Autowired
	private ContactRepository contactRepository;


	public Contact create(User user, ContactFormDto form, MultipartFile file) throws IOException {
		
		Contact model = form.toModel();
		model.setUser(user);
		
		model.setPicture(new Base64File(file.getContentType(), Base64Utils.encodeToString(file.getBytes())));
		return contactRepository.save(model);
	}

	public Contact getByIdAndUser(Long id, User user) {
		return contactRepository.findByIdAndUser(id, user).orElseThrow(() -> new EntityNotFoundException("contact not found"));
	}

	public Page<Contact> getAllByUserAndName(User user, String name, Pageable pageable) {
		return contactRepository.findByNameContainingAndUser(name, user, pageable);
	}

	public Contact updateByIdAndUser(Long id, User user, ContactFormDto dto) {
		Contact model = contactRepository.findByIdAndUser(id, user).orElseThrow(() -> new EntityNotFoundException("contact not found"));
		dto.update(model);
		return model;
	}

	public Contact updatePictureByIdAndUser(Long id, User user, MultipartFile file) throws IOException {
		Contact model = contactRepository.findByIdAndUser(id, user).orElseThrow(() -> new EntityNotFoundException("contact not found"));
		model.setPicture(new Base64File(file.getContentType(), Base64Utils.encodeToString(file.getBytes())));
		return model;
	}

	public void deleteByIdAndUser(Long id, User user) {
		Contact model = contactRepository.findByIdAndUser(id, user).orElseThrow(() -> new EntityNotFoundException("contact not found"));
		contactRepository.delete(model);
	}
	
	public void writeImageByIdAndUser(Long id, User user, HttpServletResponse response) throws IOException {
		Contact contact = contactRepository.findByIdAndUser(id, user).orElseThrow(() -> new EntityNotFoundException("contact not found"));
		Base64File picture = contact.getPicture();
		if(picture == null) throw new EntityNotFoundException();
		byte[] bytes = picture.toBytes();
		response.setContentType(picture.getMediaType());
		response.setContentLength(bytes.length);
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(bytes);
		outputStream.close();
	}

}
