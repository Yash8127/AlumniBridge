package com.yaswanth.Alumni_Bridge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.yaswanth.Alumni_Bridge.entity.User;
import com.yaswanth.Alumni_Bridge.service.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;

    @Autowired
    private EventService eventService;

    // 🔐 ADMIN CHECK
    private boolean isAdmin(User user) {
        return user != null && user.getRole().equals("ADMIN");
    }

    // DASHBOARD
    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (!isAdmin(user)) return "redirect:/dashboard";
        return "admin-dashboard";
    }

    // USERS
    @GetMapping("/users")
    public String users(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (!isAdmin(user)) return "redirect:/dashboard";

        model.addAttribute("users", userService.getAllUsers());
        return "admin-users";
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (!isAdmin(user)) return "redirect:/dashboard";

        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/toggle-block/{id}")
    public String blockUser(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (!isAdmin(user)) return "redirect:/dashboard";

        userService.toggleBlockUser(id);
        return "redirect:/admin/users";
    }

    // JOBS
    @GetMapping("/jobs")
    public String jobs(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (!isAdmin(user)) return "redirect:/dashboard";

        model.addAttribute("jobs", jobService.getAllJobs());
        return "admin-jobs";
    }

    @GetMapping("/delete-job/{id}")
    public String deleteJob(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (!isAdmin(user)) return "redirect:/dashboard";

        jobService.deleteJob(id);
        return "redirect:/admin/jobs";
    }

    // EVENTS
    @GetMapping("/events")
    public String events(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (!isAdmin(user)) return "redirect:/dashboard";

        model.addAttribute("events", eventService.getAllEvents());
        return "admin-events";
    }

    @GetMapping("/delete-event/{id}")
    public String deleteEvent(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (!isAdmin(user)) return "redirect:/dashboard";

        eventService.deleteEvent(id);
        return "redirect:/admin/events";
    }
}