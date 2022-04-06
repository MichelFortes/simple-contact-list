package br.com.waytech.simplecontactlist.dto.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.waytech.simplecontactlist.model.User;

public class UserDto {

	private Long id;
	private String name;
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public static UserDto from(User model, Projection projection) {
		if(model == null) return null;
		
		UserDto dto = new UserDto();
		switch(projection) {
			case FULL:
				dto.setEmail(model.getEmail());
			case SIMPLE:
				dto.setId(model.getId());
				dto.setName(model.getName());
				break;
		}
		return dto;
	}
	
	public static Page<UserDto> from(Page<User> page, Pageable pageable, Projection projection) {
		
		List<UserDto> dtos = new ArrayList<UserDto>(page.getNumberOfElements());
		page.forEach( (user) -> dtos.add(UserDto.from(user, projection)));
		return new PageImpl<UserDto>(dtos, pageable, page.getTotalElements());		
	}
	
	public static enum Projection {
		SIMPLE, FULL
	}

}
