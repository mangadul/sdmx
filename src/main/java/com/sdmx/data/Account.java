package com.sdmx.data;

import javax.persistence.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sdmx.data.repository.AccountRepository;
import com.sdmx.data.repository.RoleRepository;
import com.sdmx.support.app.AppContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@SuppressWarnings("serial")
@Entity
@Table(name = "account")
public class Account implements java.io.Serializable {

	@Transient
	private RoleRepository roleRepository;

	@Transient
	private String[] fillable = {"name", "email", "password"};

	public Map<String, String> rules() {
		Map<String, String> rules = new HashMap<>();
		rules.put("name", "required|max:255");
		rules.put("email", "required|email|unique");
		rules.put("password", "required");

		return rules;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	@JsonIgnore
	private String password;

	private Instant created;

	@ManyToMany(targetEntity = Role.class, cascade={CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinTable(
		name="role_account",
		joinColumns={@JoinColumn(name="account_id")},
		inverseJoinColumns={@JoinColumn(name="role_id")}
	)
	@OrderBy("name")
	private Set<Role> roles = new HashSet<>();

	public Account() {
		this.created = Instant.now();
	}

	public Account(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.created = Instant.now();
	}

	public Account(String name, String email, String password, String... roles) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.created = Instant.now();

		for (String role : roles) {
			addRole(role);
		}
	}

	public Account(String name, String email, String password, Set<Role> roles) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.created = Instant.now();
		this.roles = roles;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Instant getCreated() {
		return created;
	}

	public Set<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void addRole(String name) {
		if (roleRepository == null) {
			roleRepository = (RoleRepository) AppContext.getContext().getBean("roleRepository");
		}

		roles.add(roleRepository.findByName(name));
	}

	public void addRole(Role role) {
		roles.add(role);
	}
}
