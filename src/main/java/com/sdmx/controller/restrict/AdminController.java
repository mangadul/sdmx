package com.sdmx.controller.restrict;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
class AdminController {

	@Secured("ROLE_ADMIN")
	@GetMapping({"", "/"})
	public String dashboard() {
		return "admin/dashboard";
	}
}
