package com.yaswanth.Alumni_Bridge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yaswanth.Alumni_Bridge.entity.Message;
import com.yaswanth.Alumni_Bridge.entity.User;
import com.yaswanth.Alumni_Bridge.service.MessageService;
import com.yaswanth.Alumni_Bridge.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MessageController {

    @Autowired
    private MessageService msgService;

    @Autowired
    private UserService userService;

    // Inbox
    @GetMapping("/messages")
    public String inbox(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) return "redirect:/login";

        model.addAttribute("messages", msgService.getInbox(user));

        return "messages";
    }

    // Chat page
    @GetMapping("/chat/{id}")
    public String chat(@PathVariable Long id, Model model, HttpSession session) {

        User current = (User) session.getAttribute("loggedUser");
        User other = userService.getUserById(id);

        // 🔥 mark seen
        msgService.markAsSeen(current, other);

        List<Message> messages = msgService.getConversation(current, other);

        model.addAttribute("messages", messages);
        model.addAttribute("receiver", other);

        return "chat";
    }

    // Send message
    @PostMapping("/send-message")
    public String sendMessage(@RequestParam Long receiverId,
                              @RequestParam String content,
                              HttpSession session) {

        User sender = (User) session.getAttribute("loggedUser");
        User receiver = userService.getUserById(receiverId);

        msgService.sendMessage(sender, receiver, content);

        return "redirect:/chat/" + receiverId;
    }
}