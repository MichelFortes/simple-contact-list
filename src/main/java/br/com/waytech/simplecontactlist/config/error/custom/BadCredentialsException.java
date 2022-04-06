package br.com.waytech.simplecontactlist.config.error.custom;

public class BadCredentialsException extends RuntimeException {

	private static final long serialVersionUID = 6379388925884035702L;

	public BadCredentialsException(String message) {
		super(message);
	}
	
}
