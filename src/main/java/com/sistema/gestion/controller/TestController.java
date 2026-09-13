package com.sistema.gestion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private MongoTemplate mongoTemplate;

    // GET /api/test-db - Verificar conexión a MongoDB
    @GetMapping("/test-db")
    public ResponseEntity<?> testDatabase() {
        try {
            // Ejecutar un comando simple para verificar la conexión
            mongoTemplate.getDb().runCommand(new org.bson.Document("ping", 1));

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "¡Conexión a MongoDB exitosa!");
            response.put("database", mongoTemplate.getDb().getName());
            response.put("server_time", LocalDateTime.now().toString());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error conectando a MongoDB: " + e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Fallo la conexión a la base de datos");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/health - Health check
    @GetMapping("/health")
    public ResponseEntity<?> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("service", "sistema-gestion-moviles");
        return ResponseEntity.ok(response);
    }
}
