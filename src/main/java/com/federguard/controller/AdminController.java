package com.federguard.controller;

import com.federguard.model.User;
import com.federguard.model.ClientUpdate;
import com.federguard.repository.ClientUpdateRepository;
import com.federguard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientUpdateRepository clientUpdateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("clients", userRepository.findByRolesContaining("ROLE_CLIENT"));
        return "admin/dashboard";
    }

    @PostMapping("/toggle-block/{userId}")
    public String toggleBlockUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        if (user.getRoles().contains("ROLE_CLIENT")) {
            user.setBlocked(!user.isBlocked());
            userRepository.save(user);
        }
        return "redirect:/admin";
    }

    @GetMapping("/user-history/{userId}")
    public String userHistory(@PathVariable Long userId, Model model) {
        User user = userRepository.findById(userId).orElseThrow();
        if (user.getRoles().contains("ROLE_CLIENT")) {
            model.addAttribute("user", user);
            model.addAttribute("updates", clientUpdateRepository.findByUser(user));
            return "admin/user-history";
        }
        return "redirect:/admin";
    }

    @GetMapping("/create-client")
    public String showCreateClientForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/create-client";
    }

    @PostMapping("/create-client")
    public String createClient(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "A client with this username already exists.");
            return "redirect:/admin/create-client";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of("ROLE_CLIENT"));
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("success", "Client created successfully.");
        return "redirect:/admin";
    }

    @GetMapping("/view-update/{updateId}")
    public String viewUpdate(@PathVariable Long updateId, Model model) {
        ClientUpdate update = clientUpdateRepository.findById(updateId).orElseThrow();
        if (update.getUser().getRoles().contains("ROLE_CLIENT")) {
            model.addAttribute("update", update);
            return "admin/view-update";
        }
        return "redirect:/admin";
    }
}

