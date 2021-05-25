package com.example.application.backend.repository;

import com.example.application.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findOneByName(String name);

    boolean existsById(Long id);

    Boolean existsByName(String name);

    Optional<Category> findOneById(Long id);

}
