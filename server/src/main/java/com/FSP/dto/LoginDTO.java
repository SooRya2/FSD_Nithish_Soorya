package com.FSP.dto;

public class LoginDTO {
    private String id;
    private String name;
    private String role;
    private String token;
	public LoginDTO() {
	}

	public LoginDTO(String id, String username, String password, String name, String role, String token) {
		super();
		this.id = id;
		this.name = name;
		this.role = role;
		this.token = token;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}