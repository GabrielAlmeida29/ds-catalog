package com.devsuperior.dscalatog.dto;

import javax.validation.constraints.Positive;

public class UserInsertDTO extends UserDTO {
	private static final long serialVersionUID = 1L;

	@Positive(message = "Preço deve ser um valor ")
	private String password;

	UserInsertDTO() {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
