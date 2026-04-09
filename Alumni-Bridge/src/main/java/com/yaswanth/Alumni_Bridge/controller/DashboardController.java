package com.yaswanth.Alumni_Bridge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

	@GetMapping("/dashboard")
	public String dashboard(HttpSession session) {

		if (session.getAttribute("loggedUser") == null) {
			return "redirect:/login";
		}

		return "dashboard";
	}
}