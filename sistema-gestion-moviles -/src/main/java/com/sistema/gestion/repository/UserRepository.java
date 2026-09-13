package com.sistema.gestion.repository;

import com.sistema.gestion.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // Buscar usuario por email
    Optional<User> findByEmail(String email);

    // Verificar si existe un email registrado
    boolean existsByEmail(String email);
}
