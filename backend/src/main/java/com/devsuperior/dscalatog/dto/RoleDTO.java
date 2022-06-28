package com.devsuperior.dscalatog.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.devsuperior.dscalatog.entities.Role;
import com.devsuperior.dscalatog.entities.User;

public class RoleDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String authority;
	
	public RoleDTO() {
	}

	public RoleDTO(Long id, String authority, Set<User> users) {
		super();
		this.id = id;
		this.authority = authority;
	}
	
	public RoleDTO(Role role) {
		super();
		id = role.getId();
		authority = role.getAuthority();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
}
