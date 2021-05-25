package com.example.application.backend.repository;

import com.example.application.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneById(Long id);

    Optional<User> findOneByNif(String nif);

    boolean existsById(Long id);

    Boolean existsByNif(String nif);

    Boolean existsByEmail(String email);

    Boolean existsByNumberPhone(String email);

}
