package com.sdmx.controller.restrict;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
class UserController {

	@GetMapping({"", "/"})
	public String dashboard() {
		return "user/dashboard";
	}
}
