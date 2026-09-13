package com.sistema.gestion.repository;

import com.sistema.gestion.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    // Buscar productos por categoría
    List<Product> findByCategory(String category);

    // Buscar productos por nombre (búsqueda parcial, ignora mayúsculas)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Buscar productos con stock mayor a 0
    List<Product> findByStockGreaterThan(int stock);
}
