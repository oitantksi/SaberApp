package com.omega.server.consumer.dto;

import javax.validation.constraints.NotEmpty;

public class PasswordDto {

	@NotEmpty(message = "new password must not be empty")
	private String newPassword;
	
	@NotEmpty(message = "Old password must not be empty")
	private String oldPassword;
	
	
	public PasswordDto(String newPassword,String oldPassword) {
		this.newPassword = newPassword;
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
}
