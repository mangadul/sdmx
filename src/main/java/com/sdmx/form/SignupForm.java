package com.sdmx.form;

import org.hibernate.validator.constraints.*;

import com.sdmx.data.Account;

public class SignupForm {

	private static final String NOT_BLANK_MESSAGE = "{validation.required}";
	private static final String EMAIL_MESSAGE = "{validation.email}";
	private static final String EMAIL_EXISTS_MESSAGE = "{validation.email.exists}";

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String name;

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Email(message = SignupForm.EMAIL_MESSAGE)
	@EmailExists(message = SignupForm.EMAIL_EXISTS_MESSAGE)
	private String email;

    @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String password;

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Account createAccount() {
        return new Account(getName(), getEmail(), getPassword(), "ROLE_GUEST");
	}
}
