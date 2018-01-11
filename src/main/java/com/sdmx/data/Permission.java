package com.sdmx.data;

import com.sdmx.support.app.AppContext;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
@Entity
@Table(name = "permission")
public class Permission implements java.io.Serializable {

	@Transient
	private String[] fillable = {"name", "description"};

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;

	private String description;

	@ManyToMany(mappedBy = "permissions", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Role> roles;

	public Permission() {}

	public Permission(String name) {
		this.name = name;
		this.description = name.substring(name.lastIndexOf(".") + 1);
	}

	public Permission(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
