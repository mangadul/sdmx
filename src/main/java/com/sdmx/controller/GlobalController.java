package com.sdmx.controller;

import com.sdmx.controller.menu.AdminMenu;
import com.sdmx.controller.menu.CategoryMenu;
import com.sdmx.controller.menu.MenuContainer;
import com.sdmx.controller.menu.UserMenu;
import com.sdmx.data.Account;
import com.sdmx.data.repository.AccountRepository;
import com.sdmx.support.app.menu.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
class GlobalController {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MenuContainer menuContainer;

	@ModelAttribute("menu")
	public Collection menu(HttpServletRequest req) {
		if (req.getMethod().equals("GET")) {
			Map<Class, String[]> menus = new HashMap<>();
			menus.put(CategoryMenu.class, new String[]{"^news", "^publication", "^stats", "^search"});
			menus.put(AdminMenu.class, new String[]{"^admin"});
			menus.put(UserMenu.class, new String[]{"^user"});

			String url = req.getServletPath().substring(1);

			for (Map.Entry<Class, String[]> menu : menus.entrySet()) {
				for (String pattern : menu.getValue()) {
					Matcher m = Pattern.compile(pattern).matcher(url);

					if (m.find()) {
						return menuContainer.get(menu.getKey());
					}
				}
			}
		}

		return null;
	}

	@ModelAttribute
	public void auth(HttpServletRequest req, Model model, Principal principal) {
		if (principal == null || !req.getMethod().equals("GET")) {
			return;
		}

		// Logged Account
		Account loggedAccount = accountRepository.findOneByEmail(principal.getName());
		model.addAttribute("loggedAccount", loggedAccount);

		/*// Dashboard URL
		String dashboardUrl;

		if (req.isUserInRole("ROLE_ADMIN")) {
			dashboardUrl = "/admin";
		}
		else dashboardUrl = "/user";

		model.addAttribute("dashboardUrl", dashboardUrl);*/
	}
}
