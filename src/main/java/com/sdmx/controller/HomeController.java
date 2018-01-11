package com.sdmx.controller;

import com.sdmx.controller.menu.CategoryMenu;
import com.sdmx.controller.menu.MenuContainer;
import com.sdmx.data.repository.NewsRepository;
import com.sdmx.data.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
class HomeController {

	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;

	@Autowired
	private PublicationRepository publicationRepository;

	@Autowired
	private NewsRepository newsRepository;

	@Autowired
	private MenuContainer menuContainer;

	@ModelAttribute("menuPrefixUrl")
	private String menuPrefixUrl() {
		return "/search";
	}

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("publication", publicationRepository.findTop10ByOrderByCreatedDesc());
		model.addAttribute("news", newsRepository.findTop5ByOrderByCreatedDesc());
		model.addAttribute("menu", menuContainer.get(CategoryMenu.class));

		return "home/dashboard";
	}

	@RequestMapping("/lang/{id}")
	public String lang(HttpServletRequest request, @PathVariable("id") String lang) {
		messageSource.setBasename("/WEB-INF/i18n/" + lang);

		return "redirect:"+ request.getHeader("Referer");
	}
}
