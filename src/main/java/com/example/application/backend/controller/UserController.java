package com.example.application.backend.controller;

import com.example.application.backend.model.User;
import com.example.application.backend.service.UserService;
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
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get all users
     * @return List of users from database
     */
    @GetMapping("/users")
    @ApiOperation("Get all users")
    public ResponseEntity<List<User>> findAll(
            @ApiParam("User name for search (optional): String") @QueryParam("name") String name,
            @ApiParam("Pagination: page from which the records start to be displayed (optional): Integer") @QueryParam("page") String page,
            @ApiParam("Pagination: number of records displayed per page (optional): Integer") @QueryParam("limit") String limit
    ) {

        Map<String, String> map1 = new HashMap<>();
        map1.put("name", name);
        map1.put("page", page);
        map1.put("limit", limit);

        List<User> usersDB = userService.findAll(map1);

        if (usersDB.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (usersDB.get(0).getId()== -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().body(usersDB);
    }

    /**
     * Get user by ID
     * @param id Primary key of User: Long
     * @return User from database
     */
    @GetMapping("/users/{id}")
    @ApiOperation("Get user by Id")
    public ResponseEntity<User> findOne(@ApiParam("Primary key of user: Long") @PathVariable Long id){

        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<User> userOpt = userService.findOne(id);

        if(!userOpt.isPresent())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        if (userOpt.get().getId() == -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().body(userOpt.get());
    }

    /**
     * Create a new user in database
     * @param user User to create
     * @return user User created
     * @throws URISyntaxException
     */
    @PostMapping("/users")
    @ApiOperation("Create a new user in DB")
    public ResponseEntity<User> createUser(@ApiParam("User that you want to create: User") @RequestBody User user) throws URISyntaxException {

        if(user.getId() != null || ObjectUtils.isEmpty(user))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User result = userService.createUser(user);

        if (result.getId() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (result.getId() == -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity
                .created(new URI("/api/users/" + result.getId()))
                .body(result);
    }

    /**
     * It update a user of database
     * @param user to update
     * @return user updated in database
     */
    @PutMapping("/users/{id}")
    @ApiOperation("Update an existing user in DB")
    public ResponseEntity<User> updateUser(
            @ApiParam("id of User that you want to update: Long") @PathVariable Long id,
            @ApiParam("User that you want to update: User") @RequestBody User user
    ){

        if(id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User result = userService.updateUser(id, user);

        if (result.getId() == -404L)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (result.getId() == -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().body(result);
    }

    /**
     * Delete user of database by ID
     * @param id user primary key that you want to delete
     * @return void
     */
    @DeleteMapping("/users/{id}")
    @ApiOperation("Delete user of DB by Id")
    public ResponseEntity<Void> deleteUser(@ApiParam("Primary key of user: Long") @PathVariable Long id){

        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Boolean> result = userService.deleteOne(id);

        if (Objects.equals(result, Optional.of(false)))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (!result.isPresent())
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.noContent().build();
    }
}
