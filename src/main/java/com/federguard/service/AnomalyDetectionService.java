package com.federguard.service;

import com.federguard.model.ClientUpdate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnomalyDetectionService {

    private static final double ANOMALY_THRESHOLD = 4.0;
    private static final double FILE_ANOMALY_PERCENTAGE_THRESHOLD = 20.0;

    public void detectAnomaly(ClientUpdate update) {
        List<double[]> updates = parseCSV(update.getUpdateData());
        int anomalyCount = 0;

        for (double[] vector : updates) {
            if (isAnomaly(vector)) {
                anomalyCount++;
            }
        }

        double anomalyPercentage = (anomalyCount * 100.0) / updates.size();
        update.setAnomaly(anomalyPercentage > FILE_ANOMALY_PERCENTAGE_THRESHOLD);
        update.setAnomalyPercentage(anomalyPercentage);
        update.setAnomalyType(update.isAnomaly() ? "High anomaly percentage" : "Normal");
    }

    private List<double[]> parseCSV(String csvContent) {
        List<double[]> updates = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(csvContent))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    if (line.toLowerCase().contains("feature")) {
                        continue; // Skip header row
                    }
                }
                String[] values = line.split(",");
                double[] vector = new double[values.length];
                boolean validLine = true;
                for (int i = 0; i < values.length; i++) {
                    try {
                        vector[i] = Double.parseDouble(values[i].trim());
                    } catch (NumberFormatException e) {
                        validLine = false;
                        break;
                    }
                }
                if (validLine) {
                    updates.add(vector);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error parsing CSV content", e);
        }
        return updates;
    }

    private boolean isAnomaly(double[] vector) {
        double sum = 0, squareSum = 0;
        for (double value : vector) {
            sum += value;
            squareSum += value * value;
        }
        double mean = sum / vector.length;
        double variance = (squareSum / vector.length) - (mean * mean);
        double stdDev = Math.sqrt(variance);
        return stdDev > ANOMALY_THRESHOLD;
    }
}

