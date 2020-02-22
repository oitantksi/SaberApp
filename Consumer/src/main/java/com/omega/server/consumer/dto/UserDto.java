package com.omega.server.consumer.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDto {


	Long id;
	
	@NotEmpty(message = "Name must not be empty")
	String name;
	
	@NotEmpty(message = "Cognom must not be empty")
	String cognom;
	
	@NotEmpty(message = "{email.notempty}")
    @Email(message = "{email.notvalid}")
	String email;
	
	@NotEmpty(message = "{email.notempty}")
	String nickname;
	
	@NotEmpty(message = "Password must not be empty")
	String password;
	
	@NotEmpty(message = "Center must not be empty")
	String center;
	
//	@NotNull(message = "Rol must not be empty")
	Character rol;

	public UserDto() {
		
	}

	public UserDto(Long id, String name, String cognom, String email, String nickname, String password, String center,
			Character rol) {
		super();
		this.id = id;
		this.name = name;
		this.cognom = cognom;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.center = center;
		this.rol = rol;
	}

	public String getCognom() {
		return cognom;
	}

	public void setCognom(String cognom) {
		this.cognom = cognom;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public Character getRol() {
		return rol;
	}

	public void setRol(Character rol) {
		this.rol = rol;
	}

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

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name +", cognom=" + cognom + ", email=" + email + ", nickname=" + nickname+
				", password=" + password + ", center=" + center+", rol=" + rol  +"]";
	}

}