package br.com.waytech.simplecontactlist.dto.user;

import javax.validation.constraints.NotEmpty;

import br.com.waytech.simplecontactlist.model.User;

public class UserFormDto {

	@NotEmpty
	private String name;
	@NotEmpty
	private String email;
	@NotEmpty
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
