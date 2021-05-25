package com.example.application.backend.dao;

import com.example.application.backend.model.Category;

import java.util.List;
import java.util.Map;

public interface CategoryDao {

    List<Category> findAllByFilters(Map<String, String> map1);

}
