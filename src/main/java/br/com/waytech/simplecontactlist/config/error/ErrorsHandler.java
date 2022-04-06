package br.com.waytech.simplecontactlist.config.error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.waytech.simplecontactlist.config.error.custom.BadCredentialsException;
import br.com.waytech.simplecontactlist.config.error.custom.BadRequestException;
import br.com.waytech.simplecontactlist.config.error.custom.ForbiddenException;
import br.com.waytech.simplecontactlist.config.error.custom.ResourceAlreadyExistsException;
import br.com.waytech.simplecontactlist.config.error.custom.ResourceNotFoundException;
import br.com.waytech.simplecontactlist.config.error.custom.UnauthorizedException;

@RestControllerAdvice
public class ErrorsHandler {

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponseDto handle(MethodArgumentNotValidException ex) {

		ErrorResponseDto dto = new ErrorResponseDto(ErrorTemplateType.FIELD_VALIDATION);
		HashMap<String, List<String>> content = new HashMap<String, List<String>>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			if (content.containsKey(error.getField()))
				content.get(error.getField()).add(error.getDefaultMessage());
			else {
				List<String> list = new ArrayList<String>();
				list.add(error.getDefaultMessage());
				content.put(error.getField(), list);
			}
		});
		dto.setContent(content);
		return dto;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ErrorResponseDto handle(MethodArgumentTypeMismatchException ex) {
		ErrorResponseDto dto = new ErrorResponseDto(ErrorTemplateType.GENERIC);
		dto.setContent(ex.getLocalizedMessage());
		return dto;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public ErrorResponseDto handle(ConstraintViolationException ex) {
		ErrorResponseDto dto = new ErrorResponseDto(ErrorTemplateType.GENERIC);
		dto.setContent(ex.getLocalizedMessage());
		return dto;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ErrorResponseDto handle(DataIntegrityViolationException ex) {
		ErrorResponseDto dto = new ErrorResponseDto(ErrorTemplateType.GENERIC);
		dto.setContent(ex.getRootCause().getLocalizedMessage());
		return dto;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	public ErrorResponseDto handle(BadRequestException ex) {
		ErrorResponseDto dto = new ErrorResponseDto(ErrorTemplateType.GENERIC);
		dto.setContent(ex.getLocalizedMessage());
		return dto;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ErrorResponseDto handle(HttpMediaTypeNotSupportedException ex) {
		ErrorResponseDto dto = new ErrorResponseDto(ErrorTemplateType.GENERIC);
		dto.setContent(ex.getLocalizedMessage());
		return dto;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ErrorResponseDto handle(HttpMessageNotReadableException ex) {
		ErrorResponseDto dto = new ErrorResponseDto(ErrorTemplateType.GENERIC);
		dto.setContent(ex.getLocalizedMessage());
		return dto;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestPartException.class)
	public ErrorResponseDto handle(MissingServletRequestPartException ex) {
		ErrorResponseDto dto = new ErrorResponseDto(ErrorTemplateType.GENERIC);
		dto.setContent(ex.getLocalizedMessage());
		return dto;
	}
	
	@ResponseStatus(code = HttpStatus.CONFLICT)
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ErrorResponseDto handle(ResourceAlreadyExistsException ex) {
		ErrorResponseDto dto = new ErrorResponseDto(ErrorTemplateType.GENERIC);
		dto.setContent(ex.getLocalizedMessage());
		return dto;
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public ErrorResponseDto handle(ResourceNotFoundException ex) {
		ErrorResponseDto dto = new ErrorResponseDto(ErrorTemplateType.GENERIC);
		dto.setContent(ex.getLocalizedMessage());
		return dto;
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntityNotFoundException.class)
	public ErrorResponseDto handle(EntityNotFoundException ex) {
		ErrorResponseDto dto = new ErrorResponseDto(ErrorTemplateType.GENERIC);
		dto.setContent(ex.getLocalizedMessage());
		return dto;
	}

	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
	public ErrorResponseDto handle(UnauthorizedException ex) {
		ErrorResponseDto dto = new ErrorResponseDto(ErrorTemplateType.GENERIC);
		dto.setContent(ex.getLocalizedMessage());
		return dto;
	}

	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(JWTVerificationException.class)
	public ErrorResponseDto handle(JWTVerificationException ex) {
		ErrorResponseDto dto = new ErrorResponseDto(ErrorTemplateType.GENERIC);
		dto.setContent(ex.getLocalizedMessage());
		return dto;
	}

	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	@ExceptionHandler(ForbiddenException.class)
	public ErrorResponseDto handle(ForbiddenException ex) {
		ErrorResponseDto dto = new ErrorResponseDto(ErrorTemplateType.GENERIC);
		dto.setContent(ex.getLocalizedMessage());
		return dto;
	}

	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	@ExceptionHandler(BadCredentialsException.class)
	public ErrorResponseDto handle(BadCredentialsException ex) {
		ErrorResponseDto dto = new ErrorResponseDto(ErrorTemplateType.GENERIC);
		dto.setContent(ex.getLocalizedMessage());
		return dto;
	}
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponseDto handle(Exception ex) {
		
		ex.printStackTrace();
		
		ErrorResponseDto dto = new ErrorResponseDto(ErrorTemplateType.GENERIC);
		dto.setContent(ex.getLocalizedMessage());
		return dto;
	}

}
