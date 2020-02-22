package com.omega.server.saberapp.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="users")
/**
 * 
 * @author Ramon
 *Clase que mapea la tabla de usuarios de base de datos
 */
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6180295660677673098L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	@NotEmpty(message = "Name must not be empty")
	String name;
	
	@NotEmpty(message = "Cognom must not be empty")
	String cognom;
	
	@NotEmpty(message = "{email.notempty}")
    @Email(message = "{email.notempty}")
	String email;
	
	@NotEmpty(message = "{email.notempty}")
	String nickname;
	
	@NotEmpty(message = "Password must not be empty")
	String password;
	
	@NotEmpty(message = "Center must not be empty")
	String center;
	
	@NotNull
	Character rol;

	public User() {
	}

	public User(String name, String cognom, String email, String nickname, String password, String center,
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
		return "User [id=" + id + ", name=" + name +", cognom=" + cognom + ", email=" + email + ", nickname=" + nickname+
				", password=" + password + ", center=" + center+", rol=" + rol  +"]";
	}

}