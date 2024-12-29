package com.federguard.controller;

import com.federguard.model.ClientUpdate;
import com.federguard.model.User;
import com.federguard.repository.ClientUpdateRepository;
import com.federguard.repository.UserRepository;
import com.federguard.service.AnomalyDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientUpdateRepository clientUpdateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnomalyDetectionService anomalyDetectionService;

    @GetMapping
    public String clientDashboard(Model model, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        model.addAttribute("updates", clientUpdateRepository.findByUser(user));
        model.addAttribute("blocked", user.isBlocked());
        return "client/dashboard";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow();

        if (user.isBlocked()) {
            return "redirect:/client?error=blocked";
        }

        try {
            String content = new String(file.getBytes());
            ClientUpdate update = new ClientUpdate();
            update.setUser(user);
            update.setClientId(user.getUsername());
            update.setFileName(file.getOriginalFilename());
            update.setUpdateData(content);
            update.setTimestamp(LocalDateTime.now());

            anomalyDetectionService.detectAnomaly(update);

            update.setFileStatus(update.isAnomaly() ? "Invalid" : "Valid");
            clientUpdateRepository.save(update);

            return "redirect:/client?success";
        } catch (IOException e) {
            return "redirect:/client?error";
        }
    }
}

