package com.federguard.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping("/")
    public String dashboard(Authentication authentication) {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            return "redirect:/admin";
        } else if (roles.contains("ROLE_CLIENT")) {
            return "redirect:/client";
        }
        return "redirect:/login"; // Si l'utilisateur n'a pas de rôle valide
    }

    @GetMapping("/admin")
    public String adminDashboard() {
        return "admin/dashboard"; // Vue pour l'admin
    }

    @GetMapping("/client")
    public String clientDashboard() {
        return "client/dashboard"; // Vue pour le client
    }
}
