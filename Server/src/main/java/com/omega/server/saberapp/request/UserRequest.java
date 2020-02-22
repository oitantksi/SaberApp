package com.omega.server.saberapp.request;

public class UserRequest {

	String name;
	String cognom;
	String email;
	String nickname;
	String password;
	String center;
	Character rol;

	public UserRequest() {
	}

	public UserRequest(String name, String cognom, String email, String nickname, String password, String center,
			Character rol) {
		super();
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
		return "User [name=" + name +", cognom=" + cognom + ", email=" + email + ", nickname=" + nickname+
				", password=" + password + ", center=" + center+", rol=" + rol  +"]";
	}

}

