package br.com.waytech.simplecontactlist.config.error.custom;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -812691643185971947L;

	public BadRequestException(String message) {
		super(message);
	}

}
