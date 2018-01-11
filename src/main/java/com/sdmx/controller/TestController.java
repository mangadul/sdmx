package com.sdmx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("test")
class TestController {

	@GetMapping
	public String index() {
		return "syncfusion/pivot-grid";
	}

	@GetMapping("{component}")
	public String component(@PathVariable String component) {
		return "syncfusion/"+component;
	}
}
