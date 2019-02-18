package com.cetc.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table
public class Role extends BaseEntity {

	/**角色名称*/
	private String roleName;

	/**角色类型*/
	@Column(unique = true)
	private String roleType;

	@ManyToMany
	private List<User> users = new ArrayList<>();

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public static class RoleType {
		public final static String ADMIN = "ADMIN";
		public final static String USER = "USER";
	}
}
