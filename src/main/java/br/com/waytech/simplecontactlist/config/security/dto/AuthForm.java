package br.com.waytech.simplecontactlist.config.security.dto;

import javax.validation.constraints.NotEmpty;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AuthForm {

	@NotEmpty
	private String email;
	@NotEmpty
	private String pass;

	public AuthForm() {
	}
	
	public AuthForm(String email, String pass) {
		this.email = email;
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public UsernamePasswordAuthenticationToken generateAuthToken() {
		return new UsernamePasswordAuthenticationToken(email, pass);
	}

}
