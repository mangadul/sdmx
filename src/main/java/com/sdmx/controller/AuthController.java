package com.sdmx.controller;

import javax.validation.Valid;

import com.sdmx.data.repository.AccountRepository;
import com.sdmx.form.SignupForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sdmx.data.*;
import com.sdmx.support.web.*;

@Controller
class AuthController {

	private static final String SIGNUP_VIEW_NAME = "auth/signup";

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRepository accountRepository;

	@GetMapping("autho")
	public String autho() {
		accountService.signin(accountRepository.findOneByEmail("admin@app.com"));

		return "redirect:/";
	}

	@GetMapping("signin")
	public String signin() {
		return "auth/signin";
	}

	@GetMapping("signup")
	String signup(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
		model.addAttribute(new SignupForm());

		if (Ajax.isAjaxRequest(requestedWith)) {
			return SIGNUP_VIEW_NAME.concat(" :: signupForm");
		}

		return SIGNUP_VIEW_NAME;
	}

	@PostMapping("signup")
	String signup(@Valid @ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return SIGNUP_VIEW_NAME;
		}

		Account account = accountService.save(signupForm.createAccount());
		accountService.signin(account);

        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
//        MessageHelper.addSuccessAttribute(ra, "signup.success");

		return "redirect:/";
	}
}
