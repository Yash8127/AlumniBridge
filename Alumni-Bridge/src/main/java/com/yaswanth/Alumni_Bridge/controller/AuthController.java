package com.yaswanth.Alumni_Bridge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.yaswanth.Alumni_Bridge.entity.User;
import com.yaswanth.Alumni_Bridge.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

	@Autowired
	private UserService service;

	// 🔹 REGISTER PAGE
	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	// 🔹 REGISTER SAVE
	@PostMapping("/register")
	public String register(@ModelAttribute User user) {
		service.registerUser(user);
		return "redirect:/login";
	}

	// 🔹 LOGIN PAGE
	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}

	// 🔹 LOGIN PROCESS
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, HttpSession session,
			Model model) {

		User user = service.loginUser(username, password);

		if (user != null) {
			session.setAttribute("loggedUser", user);
			return "redirect:/dashboard";
		} else {
			model.addAttribute("error", "Invalid Email or Password");
			return "login";
		}
	}

	// 🔹 LOGOUT
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}