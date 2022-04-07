package br.com.waytech.simplecontactlist.dto.contact;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.waytech.simplecontactlist.model.Contact;

public class ContactFormDto {

	@NotEmpty
	@Size(min=3)
	private String name;
	@Email
	private String email;
	private String address;
	private Set<String> phones = new HashSet<String>();

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

	public Set<String> getPhones() {
		return phones;
	}

	public void setPhones(Set<String> phones) {
		this.phones = phones;
	}

	public Contact toModel() {
		Contact model = new Contact();
		model.setName(this.name);
		model.setEmail(this.email);
		model.setAddress(this.getAddress());
		model.setPhones(this.phones);
		return model;
	}

	public void update(final Contact model) {
		if (model == null) return;
		model.setName(this.name);
		model.setName(this.email);
		model.setAddress(this.address);
		model.setPhones(this.phones);
	}

}
