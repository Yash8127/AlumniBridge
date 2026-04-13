package com.yaswanth.Alumni_Bridge.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.yaswanth.Alumni_Bridge.entity.Profile;
import com.yaswanth.Alumni_Bridge.entity.User;
import com.yaswanth.Alumni_Bridge.service.ProfileService;
import com.yaswanth.Alumni_Bridge.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	@Autowired
	private UserService userService;

	// 🔹 VIEW PROFILE
	@GetMapping("/profile")
	public String viewProfile(Model model, HttpSession session) {

		User user = (User) session.getAttribute("loggedUser");

		if (user == null) {
			return "redirect:/login";
		}

		Profile profile = profileService.getProfile(user);

		model.addAttribute("profile", profile);
		model.addAttribute("user", user); // 🔥 needed for image display

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

	// 🔹 SAVE PROFILE + IMAGE UPLOAD
	@PostMapping("/save-profile")
	public String saveProfile(@ModelAttribute Profile profile, @RequestParam("image") MultipartFile file,
			HttpSession session) throws Exception {

		User user = (User) session.getAttribute("loggedUser");

		if (user == null) {
			return "redirect:/login";
		}

		// 🔥 HANDLE IMAGE UPLOAD
		if (!file.isEmpty()) {

			String uploadDir = System.getProperty("user.dir") + "/uploads/";

			File uploadPath = new File(uploadDir);

			if (!uploadPath.exists()) {
				uploadPath.mkdirs();
			}

			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

			// optional: remove spaces
			fileName = fileName.replaceAll(" ", "_");

			file.transferTo(new File(uploadDir + fileName));

			user.setProfileImage(fileName);
			userService.registerUser(user);
		}

		// 🔥 CHECK EXISTING PROFILE
		Profile existingProfile = profileService.getProfile(user);

		if (existingProfile != null) {

			// UPDATE
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