package com.cetc.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class User extends BaseEntity {

	/**
	 * 用户名
	 */
	@Column(unique = true)
	private String username;
	
	/**
	 * 密码
	 */
	private String password;

	/**
	 * 角色
	 */
	@ManyToMany(mappedBy = "users")
	private List<Role> roles = new ArrayList<Role>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

 }
