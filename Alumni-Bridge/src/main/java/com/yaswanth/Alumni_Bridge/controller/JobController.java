package com.yaswanth.Alumni_Bridge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.yaswanth.Alumni_Bridge.entity.*;
import com.yaswanth.Alumni_Bridge.service.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class JobController {

	@Autowired
	private JobService jobService;

	@Autowired
	private ApplicationService appService;

	// VIEW JOBS
	@GetMapping("/jobs")
	public String viewJobs(Model model, HttpSession session) {

		if (session.getAttribute("loggedUser") == null) {
			return "redirect:/login";
		}

		List<Job> jobs = jobService.getAllJobs();

		model.addAttribute("jobs", jobs);

		// 🔥 ADD THIS LINE HERE
		model.addAttribute("applicationService", appService);

		return "jobs";
	}

	// POST JOB PAGE
	@GetMapping("/post-job")
	public String createJobPage(Model model, HttpSession session) {

		User user = (User) session.getAttribute("loggedUser");

		if (user == null || (!user.getRole().equals("ALUMNI") && !user.getRole().equals("ADMIN"))) {
			return "redirect:/jobs";
		}

		model.addAttribute("job", new Job()); // 🔥 REQUIRED

		return "post-job";
	}

	// SAVE JOB
	@PostMapping("/save-job")
	public String saveJob(@ModelAttribute Job job, HttpSession session) {

		User user = (User) session.getAttribute("loggedUser");

		if (user == null || (!user.getRole().equals("ALUMNI") && !user.getRole().equals("ADMIN"))) {
			return "redirect:/jobs";
		}

		job.setPostedBy(user);

		jobService.saveJob(job);

		return "redirect:/jobs";
	}

	// APPLY JOB
	@GetMapping("/apply/{jobId}")
	public String applyJob(@PathVariable Long jobId, HttpSession session) {

		User user = (User) session.getAttribute("loggedUser");

		Job job = jobService.getJobById(jobId);

		// 🔥 CHECK DUPLICATE
		if (appService.alreadyApplied(user, job)) {
			return "redirect:/jobs";
		}

		Application app = new Application();
		app.setJob(job);
		app.setUser(user);
		app.setStatus("APPLIED");

		appService.apply(app);

		return "redirect:/jobs";
	}

	@GetMapping("/my-applications")
	public String myApplications(Model model, HttpSession session) {

		User user = (User) session.getAttribute("loggedUser");

		if (user == null) {
			return "redirect:/login";
		}

		model.addAttribute("apps", appService.getUserApplications(user));

		return "my-applications";
	}
}