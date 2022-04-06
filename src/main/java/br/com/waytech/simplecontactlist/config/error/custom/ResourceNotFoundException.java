package br.com.waytech.simplecontactlist.config.error.custom;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2041799449653653108L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
}
