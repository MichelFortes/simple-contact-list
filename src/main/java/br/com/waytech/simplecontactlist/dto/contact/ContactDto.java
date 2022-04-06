package br.com.waytech.simplecontactlist.dto.contact;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.waytech.simplecontactlist.controller.user.UserContactController;
import br.com.waytech.simplecontactlist.model.Contact;

public class ContactDto {

	private Long id;
	private String name;
	private String email;
	private String address;
	private String pictureUrl;
	private Set<String> phones = new HashSet<String>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Set<String> getPhones() {
		return phones;
	}

	public void setPhones(Set<String> phones) {
		this.phones = phones;
	}
	
	private static String generatePictureURL(Contact model) {
		if(model.getPicture() == null) return null;
		return UserContactController.PATH + "/" + model.getId() + "/picture";
	}
	
	public static ContactDto from(Contact model, Projection projection) {
		if(model == null) return null;
		
		ContactDto dto = new ContactDto();
		switch(projection) {
			case FULL:
				dto.setEmail(model.getEmail());
				dto.setAddress(model.getAddress());
				dto.setPhones(model.getPhones());
			case SIMPLE:
				dto.setId(model.getId());
				dto.setName(model.getName());
				dto.setPictureUrl(generatePictureURL(model));
				break;
		}
		return dto;
	}
	
	public static Page<ContactDto> from(Page<Contact> page, Pageable pageable, Projection projection) {
		
		List<ContactDto> dtos = new ArrayList<ContactDto>(page.getNumberOfElements());
		page.forEach( (contact) -> dtos.add(ContactDto.from(contact, projection)));
		return new PageImpl<ContactDto>(dtos, pageable, page.getTotalElements());		
	}
	
	public static enum Projection {
		SIMPLE, FULL
	}

}
