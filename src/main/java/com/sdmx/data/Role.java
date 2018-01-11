package com.sdmx.data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
@Entity
@Table(name = "role")
public class Role implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;

	@ManyToMany(targetEntity=Permission.class, cascade={CascadeType.MERGE}, fetch=FetchType.EAGER)
	@JoinTable(
		name="role_permission",
		joinColumns={@JoinColumn(name="role_id")},
		inverseJoinColumns={@JoinColumn(name="permission_id")}
	)
	private Set<Permission> permissions = new HashSet<>();

	@ManyToMany(mappedBy = "roles")
	private Set<Account> accounts;

	@Transient
	private String[] fillable = {"name"};

	public Role() {}
	
	public Role(String name) {
		this.name = name;
	}

	public Role(Long id) {
		this.id = id;
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

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void addPermission(String name) {
		Permission permission = new Permission(name);
		permissions.add(permission);
	}

	public void addPermission(Permission permission) {
		permissions.add(permission);
	}

	public void emptyPermission() {
		permissions = new HashSet<>();
	}

	public boolean hasPermission(Permission permission) {
		String permissionName = permission.getName();

		for (Permission item : permissions) {
			if (item.getName().equals(permissionName)) {
				return true;
			}
		}

		return false;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}
}
