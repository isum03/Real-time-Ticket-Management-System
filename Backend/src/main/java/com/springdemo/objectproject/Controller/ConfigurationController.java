package com.springdemo.objectproject.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/api/configuration")
public class ConfigurationController {

    private Map<String, Object> currentConfig = new HashMap<>(); // Store current configuration

    @PostMapping("/update")
    public ResponseEntity<String> updateConfiguration(@RequestBody Map<String, Object> newConfig) {
        // Update the configuration in memory
        currentConfig.putAll(newConfig);

        // Save to a file when the configuration is updated
        saveConfigurationToFile(newConfig);

        return ResponseEntity.ok("Configuration updated successfully.");
    }

    private void saveConfigurationToFile(Map<String, Object> config) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Save the updated configuration to a file (configuration.json)
            objectMapper.writeValue(new File("configuration.json"), config);
            System.out.println("Configuration saved to configuration.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/current")
    public ResponseEntity<Map<String, Object>> getCurrentConfiguration() {
        return ResponseEntity.ok(currentConfig); // Return the current configuration
    }

}
