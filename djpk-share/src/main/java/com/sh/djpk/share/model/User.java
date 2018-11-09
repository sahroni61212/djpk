package com.sh.djpk.share.model;

public class User {

	private String user_name,email,status,role,nameDesc,nameShort;

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getNameDesc() {
		return nameDesc;
	}

	public void setNameDesc(String nameDesc) {
		this.nameDesc = nameDesc;
	}

	public String getNameShort() {
		return nameShort;
	}

	public void setNameShort(String nameShort) {
		this.nameShort = nameShort;
	}
	
	

	@Override
	public String toString() {
		return "User [username=" + user_name + ", email=" + email + ", status="
				+ status + ", role=" + role + ", nameDesc=" + nameDesc
				+ ", nameShort=" + nameShort + "]";
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

		
	
}
