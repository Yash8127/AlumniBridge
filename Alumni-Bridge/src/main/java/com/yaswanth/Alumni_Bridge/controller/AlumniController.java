package com.yaswanth.Alumni_Bridge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.yaswanth.Alumni_Bridge.entity.User;
import com.yaswanth.Alumni_Bridge.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AlumniController {

	@Autowired
	private UserService userService;

	@GetMapping("/alumni")
	public String viewAlumni(@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String role, Model model, HttpSession session) {

		// 🔒 protect route
		if (session.getAttribute("loggedUser") == null) {
			return "redirect:/login";
		}

		List<User> users = userService.searchUsers(keyword, role);

		model.addAttribute("users", users);
		model.addAttribute("keyword", keyword);
		model.addAttribute("role", role);

		return "alumni-list";
	}

	@GetMapping("/view-user/{id}")
	public String viewUser(@PathVariable Long id, Model model, HttpSession session) {

		if (session.getAttribute("loggedUser") == null) {
			return "redirect:/login";
		}

		User user = userService.getUserById(id);
		model.addAttribute("user", user);

		return "view-user";
	}
}