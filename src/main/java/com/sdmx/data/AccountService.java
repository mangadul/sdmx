package com.sdmx.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sdmx.data.repository.AccountRepository;
import com.sdmx.data.repository.PermissionRepository;
import com.sdmx.data.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import javax.annotation.PostConstruct;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AccountService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Environment env;

//	@PostConstruct // #dev
	protected void initialize() {
		/** Permission */
//		Set<Permission> credentials = new HashSet<>();
//		String[] list = {
//			"role.add", "role.edit", "role.delete", "role.view",
//			"user.add", "user.edit", "user.delete", "user.view",
//		};
//
//		for (String name : list) {
//			credentials.add(new Permission(name));
//		}
//
//		List<Permission> permissions = permissionRepository.save(credentials);
//
//		/** Roles */
//		Role role_guest = new Role("ROLE_GUEST");
//		role_guest.addPermission(permissions.get(0));
//		role_guest.addPermission(permissions.get(1));
//		role_guest.addPermission(permissions.get(2));
//		role_guest.addPermission(permissions.get(3));
//
//		Role role_admin = new Role("ROLE_ADMIN");
//		role_admin.addPermission(permissions.get(4));
//		role_admin.addPermission(permissions.get(5));
//		role_admin.addPermission(permissions.get(6));
//		role_admin.addPermission(permissions.get(7));
//
//		roleRepository.save(role_guest);
//		roleRepository.save(role_admin);
//
//		/** Accounts */
//		Set<Role> roles = new HashSet<>();
//		roles.add(role_guest);
//		save(new Account("Demo User", "user", "demo", roles));

//		roles.add(role_admin);
//		save(new Account("Administrator", "admin", "admin", roles));
		save(new Account("Administrator", "admin@app.com", "admindev"));
	}

	@Transactional
	public Account save(Account account) {
		// todo replace with hook model
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		accountRepository.save(account);
		return account;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findOneByEmail(username);

		if (account == null) {
			throw new UsernameNotFoundException("User not found");
		}

		return createUser(account);
	}
	
	public void signin(Account account) {
		SecurityContextHolder.getContext().setAuthentication(authenticate(account));
	}
	
	private Authentication authenticate(Account account) {
		return new UsernamePasswordAuthenticationToken(createUser(account), null, createAuthority(account));
	}
	
	private User createUser(Account account) {
		return new User(account.getEmail(), account.getPassword(), createAuthority(account));
	}

	private Set<GrantedAuthority> createAuthority(Account account) {
		Set<GrantedAuthority> authorities = new HashSet<>();

//		if (env.getProperty("permission.enable") != "false") {e
			for (Role role : account.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(role.getName()));

				for (Permission permission : role.getPermissions()) {
					authorities.add(new SimpleGrantedAuthority(permission.getName()));
				}
			}
//		}

		return authorities;
	}

}
