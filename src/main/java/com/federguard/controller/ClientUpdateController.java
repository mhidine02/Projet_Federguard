package com.federguard.controller;

import com.federguard.model.ClientUpdate;
import com.federguard.repository.ClientUpdateRepository;
import com.federguard.service.AnomalyDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/updates")
public class ClientUpdateController {

    @Autowired
    private ClientUpdateRepository clientUpdateRepository;

    @Autowired
    private AnomalyDetectionService anomalyDetectionService;

    @PostMapping
    public ResponseEntity<ClientUpdate> receiveUpdate(@RequestBody ClientUpdate update) {
        anomalyDetectionService.detectAnomaly(update);
        ClientUpdate savedUpdate = clientUpdateRepository.save(update);
        return ResponseEntity.ok(savedUpdate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientUpdate> getUpdate(@PathVariable Long id) {
        return clientUpdateRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

