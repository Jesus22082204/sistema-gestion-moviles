package com.sistema.gestion.controller;

import com.sistema.gestion.model.Product;
import com.sistema.gestion.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // GET /api/products - Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/products/{id} - Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                return ResponseEntity.ok(product.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Producto no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener el producto: " + e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al obtener el producto");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST /api/products - Crear un nuevo producto
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) {
        try {
            Product savedProduct = productRepository.save(product);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Producto creado");
            response.put("id", savedProduct.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.err.println("Error al crear el producto: " + e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al crear el producto");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PUT /api/products/{id} - Actualizar un producto
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @Valid @RequestBody Product productDetails) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            if (!optionalProduct.isPresent()) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Producto no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            Product product = optionalProduct.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setStock(productDetails.getStock());
            product.setCategory(productDetails.getCategory());
            product.setImageUrl(productDetails.getImageUrl());

            Product updatedProduct = productRepository.save(product);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Producto actualizado");
            response.put("product", updatedProduct);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error al actualizar el producto: " + e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al actualizar el producto");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE /api/products/{id} - Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (!product.isPresent()) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Producto no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            productRepository.deleteById(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Producto eliminado");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error al eliminar el producto: " + e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al eliminar el producto");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET /api/products/category/{category} - Buscar por categoría
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable String category) {
        try {
            List<Product> products = productRepository.findByCategory(category);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            System.err.println("Error al buscar por categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/products/search?name=xxx - Buscar por nombre
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchByName(@RequestParam String name) {
        try {
            List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            System.err.println("Error en la búsqueda: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
