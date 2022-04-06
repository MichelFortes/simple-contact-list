package br.com.waytech.simplecontactlist.config.error.custom;

public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = -812691643185971947L;

	public UnauthorizedException(String message) {
		super(message);
	}
	
}
