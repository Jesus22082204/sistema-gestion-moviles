package com.sistema.gestion.repository;

import com.sistema.gestion.model.Quote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends MongoRepository<Quote, String> {

    // Buscar cotizaciones por usuario
    List<Quote> findByUserId(String userId);

    // Buscar cotizaciones por estado
    List<Quote> findByStatus(String status);
}
