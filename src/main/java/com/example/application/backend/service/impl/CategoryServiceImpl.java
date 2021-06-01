package com.example.application.backend.service.impl;

import com.example.application.backend.dao.CategoryDao;
import com.example.application.backend.model.Category;
import com.example.application.backend.repository.CategoryRepository;
import com.example.application.backend.service.CategoryService;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private Session session;

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final CategoryRepository repository;

    private final CategoryDao categoryDao;


    public CategoryServiceImpl(CategoryRepository repository, CategoryDao categoryDao) {
        this.repository = repository;
        this.categoryDao = categoryDao;
    }


    /**
     * Get all categories with optionals filters options (String name, String pag, String limit)
     * Service Method
     *
     * @param map1 map with all filters options (String name, String pag, String limit)
     * @return List of categories from database
     */
    @Override
    public List<Category> findAll(Map<String, String> map1) {

        try {

            return this.categoryDao.findAllByFilters(map1);

        } catch (Exception e) {

            log.error(e.getMessage());
            e.printStackTrace();
            List<Category> categoriesError = new ArrayList<>();
            Category categoryError = new Category();
            categoryError.setId(-500L);
            categoriesError.add(categoryError);

            return categoriesError;
        }
    }

    /**
     * Get category by ID - Service
     *
     * @param id Primary key of Category: Long
     * @return Optional<Category> from database
     */
    @Override
    public Optional<Category> findOne(Long id) {
        try {

            if (id == null)
                return Optional.empty();

            Optional<Category> result = this.repository.findById(id);

            return result;

        } catch (Exception e) {

            log.error(e.getMessage());
            Category categoryError = new Category();
            categoryError.setId(-500L);

            return Optional.of(categoryError);
        }
    }

    /**
     * Create a new category in database - Service
     *
     * @param category Category to create
     * @return category created in database
     */
    @Override
    public Category createOne(Category category) {

        try {

            Boolean exist = repository.existsByName(category.getName());

            if (exist)
                return new Category();

            return repository.save(category);

        } catch (Exception e) {

            log.error(e.getMessage());
            Category categoryError = new Category();
            categoryError.setId(-500L);

            return categoryError;
        }
    }

    /**
     * It update a category of database - Service
     *
     * @param category to update
     * @return category updated in database
     */
    @Override
    public Category updateOne(Long id, Category category) {

        try {

            boolean exist = repository.existsById(id);

            if (!exist) {
                Category categoryError = new Category();
                categoryError.setId(-404L);
                return categoryError;
            }

            return repository.save(category);

        } catch (Exception e) {

            log.error(e.getMessage());
            Category categoryError = new Category();
            categoryError.setId(-500L);
            return categoryError;

        }
    }

    /**
     * Delete category of database by ID - Service
     *
     * @param id category primary key that you want to delete
     * @return Optional<Boolean>
     */
    @Override
    public Optional<Boolean> deleteOne(Long id) {

        try {
            if (id != null && repository.existsById(id)) {

                // TODO - ELIMINAR LA RELACIÓN CON CUENTAS BANCARIAS
                // Optional<Boolean> result = userDao.deleteUsersBankAccountsRelation(id);

           /*     if (Objects.equals(result, Optional.of(true))){
                    repository.deleteById(id);
                }*/

                repository.deleteById(id);

                return Optional.of(true);
            }
            return Optional.of(false);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }

    }

    @Override
    public Long getTotalCount() {
        return null;
    }


    /**
     * Find all category by Name - Service
     *
     * @return List<String> from total categories for paint a chart
     */
    @Override
    public List<String> findChartCategoriesAllByName() {

        try {
            List<String> listResult = this.categoryDao.findChartCategoriesAllByName();

            return listResult;

        } catch (Exception e) {

            log.error(e.getMessage());
            List<String> listsError = new ArrayList<>();
            listsError.add("-500");

            return listsError;
        }

    }
}
