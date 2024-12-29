package com.federguard.controller;

import com.federguard.model.User;
import com.federguard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class SettingsController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/settings")
    public String showSettings(Model model, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        model.addAttribute("user", user);

        if (user.getRoles().contains("ROLE_ADMIN")) {
            return "admin/settings";
        } else {
            return "client/settings";
        }
    }

    @PostMapping("/settings")
    public String updateSettings(@RequestParam String username,
                                 @RequestParam(required = false) String newPassword,
                                 Authentication authentication,
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 RedirectAttributes redirectAttributes) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        boolean changed = false;

        if (!user.getUsername().equals(username) && userRepository.findByUsername(username).isEmpty()) {
            user.setUsername(username);
            changed = true;
        }

        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
            changed = true;
        }

        if (changed) {
            userRepository.save(user);
            // Logout the user
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            SecurityContextHolder.clearContext();
            redirectAttributes.addFlashAttribute("message", "Settings updated successfully. Please log in with your new credentials.");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("message", "No changes were made to your settings.");
            return "redirect:/settings";
        }
    }
}

