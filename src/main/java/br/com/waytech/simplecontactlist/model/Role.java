package br.com.waytech.simplecontactlist.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	
	ADMIN, USER;

	@Override
	public String getAuthority() {
		return this.name();
	}

}
