package com.sdmx.controller.usermanager;

import com.sdmx.data.Role;
import com.sdmx.data.repository.RoleRepository;
import com.sdmx.support.app.ModelEntity;
import com.sdmx.support.app.ModelEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("admin/role")
@PreAuthorize("hasAuthority('role.view')")
public class RoleController {

	@Autowired
	@Qualifier("roleRepository")
	private RoleRepository repo;

	@Autowired
	private Pageable pageable;

	private ModelEntity me;

	public RoleController(ModelEntityFactory modelEntityFactory) {
		me = modelEntityFactory.create(Role.class);
	}

	@GetMapping
	public String list(Model model) {
		model.addAttribute("data", repo.findAll(pageable));
		return "admin/role/list";
	}

	@GetMapping("create")
	@PreAuthorize("hasAuthority('role.create')")
	public String create(Model model) {
		model.addAttribute("title", "Create New Role");
		model.addAttribute("model", new Role());

		return "admin/role/form";
	}

	@PostMapping("create")
	@PreAuthorize("hasAuthority('role.create')")
	public String store(@RequestParam Map<String, Object> param) {
		me.save(param);
		return "redirect:/admin/role";
	}

	@GetMapping("edit/{id}")
	@PreAuthorize("hasAuthority('role.update')")
	public String edit(Model model, @PathVariable("id") long id) {
		model.addAttribute("title", "Edit Role");
		model.addAttribute("model", repo.findOne(id));

		return "admin/role/form";
	}

	@PostMapping("edit/{id}")
	@PreAuthorize("hasAuthority('role.update')")
	public String update(@RequestParam Map<String, Object> param, @PathVariable("id") long id) {
		me.updateOrFail(id, param);
		return "redirect:/admin/role";
	}

	@PostMapping("delete/{id}")
	@PreAuthorize("hasAuthority('role.delete')")
	public String delete(@PathVariable("id") long id) {
		me.deleteOrFail(id);
		return "redirect:/admin/role";
	}
}
