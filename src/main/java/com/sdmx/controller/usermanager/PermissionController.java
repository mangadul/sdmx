package com.sdmx.controller.usermanager;

import com.sdmx.data.*;
import com.sdmx.data.repository.PermissionRepository;
import com.sdmx.data.repository.RoleRepository;
import com.sdmx.support.app.ModelEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("admin/permission")
public class PermissionController {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	private ModelEntityFactory modelEntityFactory;

	public PermissionController(ModelEntityFactory modelEntityFactory) {
		this.modelEntityFactory = modelEntityFactory;
	}

	@GetMapping
	public String config(Model model) {
		List<Role> roles = roleRepository.findAll();
		List<Permission> permissions = permissionRepository.findAll();
		Map<String, List<Permission>> data = new LinkedHashMap<>();

		for (Permission permission : permissions) {
			String permission_name = permission.getName();
			String feature = permission_name.substring(0, permission_name.indexOf("."));

			data.putIfAbsent(feature, new ArrayList<>());
			data.get(feature).add(permission);
		}

		model.addAttribute("roles", roles);
		model.addAttribute("permissions", data);

		return "admin/permission/config";
	}

	@PostMapping
	public String store(HttpServletRequest req) {
		EntityManager em = modelEntityFactory.em();
		em.getTransaction().begin();
		Enumeration<String> e = req.getParameterNames();

		while (e.hasMoreElements()) {
			String param = e.nextElement();
			Matcher matcher = Pattern.compile("^config_(\\d+)").matcher(param);

			if (matcher.find()) {
				Role role = em.getReference(Role.class, new Long(matcher.group(1)));
				role.emptyPermission();

				for (String perm_id : req.getParameterValues(param)) {
					role.addPermission(em.getReference(Permission.class, new Long(perm_id)));
				}

				em.persist(role);
			}
		}

		em.getTransaction().commit();
		em.close();

		return "redirect:/admin/permission";
	}
}
