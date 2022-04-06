package br.com.waytech.simplecontactlist.config.error.custom;

public class ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 80613771186707936L;

	public ForbiddenException(String message) {
		super(message);
	}
	
}
