package br.com.waytech.simplecontactlist.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.waytech.simplecontactlist.model.User;

public class UserFormDto {

	@NotEmpty
	@Size(min = 3)
	private String name;
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	@Size(min = 6)
	private String password;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User toModel() {
		User model = new User();
		model.setName(this.name);
		model.setEmail(this.email);
		model.setPassword(this.password);
		return model;
	}

	public void update(final User model) {
		if (model == null)
			return;
		if (this.name != null)
			model.setName(this.name);
		if (this.email != null)
			model.setName(this.email);
		if (this.password != null)
			model.setPassword(this.password);
	}

}
