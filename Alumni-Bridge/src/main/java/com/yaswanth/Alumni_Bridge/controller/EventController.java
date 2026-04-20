package com.yaswanth.Alumni_Bridge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yaswanth.Alumni_Bridge.entity.Event;
import com.yaswanth.Alumni_Bridge.entity.EventRegistration;
import com.yaswanth.Alumni_Bridge.entity.User;
import com.yaswanth.Alumni_Bridge.repository.EventRegistrationRepository;
import com.yaswanth.Alumni_Bridge.service.EventService;
import com.yaswanth.Alumni_Bridge.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EventController {

	@Autowired
	private EventService eventService;

	@Autowired
	private UserService userService;
	@Autowired
	private EventRegistrationRepository regRepo;

	// VIEW EVENTS
	@GetMapping("/events")
	public String viewEvents(Model model, HttpSession session) {

		model.addAttribute("events", eventService.getAllEvents());
		model.addAttribute("eventService", eventService); // 🔥 important

		return "events";
	}

	// CREATE EVENT PAGE
	@GetMapping("/create-event")
	public String createEventPage(HttpSession session) {

		User user = (User) session.getAttribute("loggedUser");

		if (user == null || (!user.getRole().equals("ALUMNI") && !user.getRole().equals("ADMIN"))) {
			return "redirect:/events";
		}

		return "create-event";
	}

	// SAVE EVENT
	@PostMapping("/save-event")
	public String saveEvent(@ModelAttribute Event event, HttpSession session) {

		User user = (User) session.getAttribute("loggedUser");
		event.setCreatedBy(user);

		eventService.saveEvent(event);

		return "redirect:/events";
	}

	// REGISTER
	@GetMapping("/register-event/{id}")
	public String register(@PathVariable Long id, HttpSession session) {

		User user = (User) session.getAttribute("loggedUser");
		Event event = eventService.getAllEvents().stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);

		eventService.register(user, event);

		return "redirect:/events";
	}

	@GetMapping("/my-events")
	public String myEvents(Model model, HttpSession session) {

		User user = (User) session.getAttribute("loggedUser");

		List<EventRegistration> regs = regRepo.findByUser(user);

		model.addAttribute("registrations", regs);

		return "my-events";
	}
}
