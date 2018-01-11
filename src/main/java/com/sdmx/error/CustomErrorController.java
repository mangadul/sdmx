package com.sdmx.error;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.common.base.Throwables;

@Controller
class CustomErrorController {

	/**
	 * Display an error page, as defined in web.xml <code>custom-error</code> element.
	 */
	@GetMapping("generalError")
	public String generalError(Model model, HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		String reason = HttpStatus.valueOf(statusCode).getReasonPhrase();

		model.addAttribute("title", reason);
		model.addAttribute("status_code", statusCode);

		if (throwable != null) {
			String exceptionMessage = Throwables.getRootCause(throwable).getMessage();
			model.addAttribute("message", exceptionMessage);
		}

		return "error/general";
	}
}
