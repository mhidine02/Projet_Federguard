package com.federguard.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "client_updates")
public class ClientUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String clientId;

    @Column(columnDefinition = "TEXT")
    private String updateData;

    @Column(nullable = false)
    private boolean isAnomaly;

    private String anomalyType;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileStatus;

    @Column(nullable = false)
    private double anomalyPercentage;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ClientUpdate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public String getUpdateData() {
        return updateData;
    }

    public void setUpdateData(String updateData) {
        this.updateData = updateData;
    }

    public boolean isAnomaly() {
        return isAnomaly;
    }

    public void setAnomaly(boolean anomaly) {
        isAnomaly = anomaly;
    }

    public String getAnomalyType() {
        return anomalyType;
    }

    public void setAnomalyType(String anomalyType) {
        this.anomalyType = anomalyType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    public String getFileName() {
        return fileName;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public double getAnomalyPercentage() {
        return anomalyPercentage;
    }

    public void setAnomalyPercentage(double anomalyPercentage) {
        this.anomalyPercentage = anomalyPercentage;
    }
}

