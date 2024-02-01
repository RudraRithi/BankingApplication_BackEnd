package com.Banking._Application.model;

import lombok.Data;

@Data
public class Login {
	
	int id;
	String password;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Login(int id, String password) {
		super();
		this.id = id;
		this.password = password;
	}
	
	
}
