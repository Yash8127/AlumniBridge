package com.yaswanth.Alumni_Bridge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.yaswanth.Alumni_Bridge.entity.Profile;
import com.yaswanth.Alumni_Bridge.entity.User;
import com.yaswanth.Alumni_Bridge.service.ProfileService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	// 🔹 VIEW PROFILE
	@GetMapping("/profile")
	public String viewProfile(Model model, HttpSession session) {

		User user = (User) session.getAttribute("loggedUser");

		if (user == null) {
			return "redirect:/login";
		}

		Profile profile = profileService.getProfile(user);

		model.addAttribute("profile", profile);

		return "profile";
	}

	// 🔹 EDIT PROFILE PAGE
	@GetMapping("/edit-profile")
	public String editProfile(Model model, HttpSession session) {

		User user = (User) session.getAttribute("loggedUser");

		if (user == null) {
			return "redirect:/login";
		}

		Profile profile = profileService.getProfile(user);

		if (profile == null) {
			profile = new Profile();
		}

		model.addAttribute("profile", profile);

		return "edit-profile";
	}

	// 🔹 SAVE PROFILE
	@PostMapping("/save-profile")
	public String saveProfile(@ModelAttribute Profile profile, HttpSession session) {

		User user = (User) session.getAttribute("loggedUser");

		if (user == null) {
			return "redirect:/login";
		}

		// 🔥 CHECK EXISTING PROFILE
		Profile existingProfile = profileService.getProfile(user);

		
		if (existingProfile != null) {
			// ✅ UPDATE (set ID to avoid duplicate insert)
			existingProfile.setSkills(profile.getSkills());
			existingProfile.setCompany(profile.getCompany());
			existingProfile.setExperience(profile.getExperience());
			existingProfile.setBio(profile.getBio());

			profileService.saveProfile(existingProfile);
		} else {
			profile.setUser(user);
			profileService.saveProfile(profile);
		}

		return "redirect:/profile";
	}
}