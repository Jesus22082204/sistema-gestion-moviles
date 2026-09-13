package com.sistema.gestion.seed;

import com.sistema.gestion.model.Product;
import com.sistema.gestion.model.Quote;
import com.sistema.gestion.model.User;
import com.sistema.gestion.repository.ProductRepository;
import com.sistema.gestion.repository.QuoteRepository;
import com.sistema.gestion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private QuoteRepository quoteRepository;

    @Override
    public void run(String... args) {
        // Solo insertar datos si la base de datos está vacía
        if (productRepository.count() > 0) {
            System.out.println("[Seeder] La base de datos ya tiene datos. Omitiendo seed.");
            return;
        }

        System.out.println("[Seeder] Insertando datos iniciales...");

        // === Usuario Admin ===
        User admin = new User();
        admin.setEmail("admin@sistema.com");
        admin.setPassword("hashed_password_placeholder");
        admin.setRole("admin");
        admin.setCreatedAt(LocalDateTime.now());
        admin.setUpdatedAt(LocalDateTime.now());
        User savedAdmin = userRepository.save(admin);
        System.out.println("[Seeder] Usuario admin creado: " + savedAdmin.getEmail());

        // === Productos ===
        Product p1 = new Product();
        p1.setName("Camara 3k");
        p1.setDescription("Camara 3k con wifi");
        p1.setPrice(249990.0);
        p1.setStock(50);
        p1.setCategory("Camaras");
        p1.setCreatedAt(LocalDateTime.now());
        p1.setUpdatedAt(LocalDateTime.now());

        Product p2 = new Product();
        p2.setName("Alarma con GPS");
        p2.setDescription("Alarma con GPS y sirena");
        p2.setPrice(120000.0);
        p2.setStock(15);
        p2.setCategory("Alarmas");
        p2.setCreatedAt(LocalDateTime.now());
        p2.setUpdatedAt(LocalDateTime.now());

        Product p3 = new Product();
        p3.setName("Sensor infrarrojo");
        p3.setDescription("Sensor infrarrojo con sirena");
        p3.setPrice(35000.0);
        p3.setStock(100);
        p3.setCategory("Sensores");
        p3.setCreatedAt(LocalDateTime.now());
        p3.setUpdatedAt(LocalDateTime.now());

        Product p4 = new Product();
        p4.setName("Cerradura inteligente");
        p4.setDescription("Cerradura inteligente con wifi");
        p4.setPrice(150000.0);
        p4.setStock(20);
        p4.setCategory("Cerraduras");
        p4.setCreatedAt(LocalDateTime.now());
        p4.setUpdatedAt(LocalDateTime.now());

        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4));
        System.out.println("[Seeder] 4 productos insertados");

        // === Cotización de ejemplo ===
        Quote quote = new Quote();
        quote.setUserId(savedAdmin.getId());
        quote.setTotal(249990.0);
        quote.setStatus("demo");
        quote.setCreatedAt(LocalDateTime.now());
        quote.setUpdatedAt(LocalDateTime.now());
        quoteRepository.save(quote);
        System.out.println("[Seeder] 1 cotización de ejemplo insertada");

        System.out.println("[Seeder] ¡Datos iniciales insertados exitosamente!");
    }
}
