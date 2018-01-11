package com.sdmx.error;

import com.sdmx.error.exception.FormValidationException;
import com.sdmx.error.exception.HttpException;
import com.sdmx.support.web.Message;
import com.sdmx.support.web.MessageCollection;
import com.sdmx.support.web.MessageHelper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Throwables;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * General error handler for the application.
 */
@ControllerAdvice
class ExceptionHandler {

	/**
	 * Handle exceptions thrown by handlers.
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(value = HttpException.class)
	public String http(Model model, HttpException e) {
		model.addAttribute("title", HttpStatus.valueOf(e.getStatusCode()).getReasonPhrase());
		model.addAttribute("status_code", e.getStatusCode());

		// todo get message from lang properties file if defined
		model.addAttribute("message", e.getMessage());

		return "error/general";
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(FormValidationException.class)
	public String formValidation(FormValidationException e, HttpServletRequest request, RedirectAttributes ra) {
		MessageCollection msgCollection = new MessageCollection();

		for (String message : e.getMessages()) {
			msgCollection.error(message);
		}

		ra.addFlashAttribute("messages", msgCollection);

		return "redirect:"+ request.getHeader("Referer");
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handle(Model model, Exception e, HttpServletRequest request) {
		if (e instanceof AccessDeniedException) {
			setModel(model, 403, "Access Denied");
		}
		else {
			Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
			setModel(model, statusCode);
			model.addAttribute("message", e.toString()); // #dev
		}

		e.printStackTrace();

		return "error/general";
	}

	protected void setModel(Model model, Integer statusCode) {
		if (statusCode == null) {
			statusCode = 500;
		}

		setModel(model, statusCode, HttpStatus.valueOf(statusCode).getReasonPhrase());
	}

	protected void setModel(Model model, Integer statusCode, String title) {
		if (statusCode == null) {
			statusCode = 500;
		}

		model.addAttribute("status_code", statusCode);
		model.addAttribute("title", title);
	}
}