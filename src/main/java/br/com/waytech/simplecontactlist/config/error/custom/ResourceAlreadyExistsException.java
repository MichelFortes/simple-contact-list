package br.com.waytech.simplecontactlist.config.error.custom;

public class ResourceAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 6949871615674401562L;

	public ResourceAlreadyExistsException(String message) {
		super(message);
	}
	
}
