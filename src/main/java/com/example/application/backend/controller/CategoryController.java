package com.example.application.backend.controller;

import com.example.application.backend.model.Category;
import com.example.application.backend.service.CategoryService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Get all categories
     * @return List of categories from database
     */
    @GetMapping("/categories")
    @ApiOperation("Get all categories")
    public ResponseEntity<List<Category>> findAll(
            @ApiParam("Category name for search (optional): String") @QueryParam("name") String name,
            @ApiParam("Pagination: page from which the records start to be displayed (optional): Integer") @QueryParam("page") String page,
            @ApiParam("Pagination: number of records displayed per page (optional): Integer") @QueryParam("limit") String limit
    ) {

        Map<String, String> map1 = new HashMap<>();
        map1.put("name", name);
        map1.put("page", page);
        map1.put("limit", limit);

        List<Category> result = categoryService.findAll(map1);

        if (result.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        if (result.get(0).getId()== -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().body(result);
    }

    /**
     * Get category by ID
     * @param id Primary key of Category: Long
     * @return Category from database
     */
    @GetMapping("/categories/{id}")
    @ApiOperation("Get category by Id")
    public ResponseEntity<Category> findOne(@ApiParam("Primary key of category: Long") @PathVariable Long id){

        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Category> resultOpt = categoryService.findOne(id);

        if(!resultOpt.isPresent())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        if (resultOpt.get().getId() == -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().body(resultOpt.get());
    }

    /**
     * Create a new category in database
     * @param category Category to create
     * @return category Category created
     * @throws URISyntaxException
     */
    @PostMapping("/categories")
    @ApiOperation("Create a new user in DB")
    public ResponseEntity<Category> createCategory(@ApiParam("Category that you want to create: Category") @RequestBody Category category) throws URISyntaxException {

        if(category.getId() != null || ObjectUtils.isEmpty(category))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Category result = categoryService.createOne(category);

        if (result.getId() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (result.getId() == -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity
                .created(new URI("/api/categories/" + result.getId()))
                .body(result);
    }

    /**
     * It update a category of database
     * @param category to update
     * @return category updated in database
     */
    @PutMapping("/categories/{id}")
    @ApiOperation("Update an existing category in DB")
    public ResponseEntity<Category> updateCategory(
            @ApiParam("id of Category that you want to update: Long") @PathVariable Long id,
            @ApiParam("Category that you want to update: Category") @RequestBody Category category
    ){

        if(id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Category result = categoryService.updateOne(id, category);

        if (result.getId() == -404L)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (result.getId() == -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().body(result);
    }

    /**
     * Delete category of database by ID
     * @param id category primary key that you want to delete
     * @return void
     */
    @DeleteMapping("/categories/{id}")
    @ApiOperation("Delete category of DB by Id")
    public ResponseEntity<Void> deleteOne(@ApiParam("Primary key of category: Long") @PathVariable Long id){

        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Boolean> result = categoryService.deleteOne(id);

        if (Objects.equals(result, Optional.of(false)))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (!result.isPresent())
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.noContent().build();
    }

}
