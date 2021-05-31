package com.example.application.backend.service.impl;

import com.example.application.backend.model.User;
import com.example.application.backend.repository.exceptions.EmptyFieldException;
import com.example.application.backend.repository.exceptions.NonUniqueUserNameException;
import com.example.application.backend.service.UserAuthenticationDAO;

import com.vaadin.flow.server.VaadinSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserAuthenticationDAOSQL implements UserAuthenticationDAO {

    // stores the user name during the session; the string serves as the key for the value
    public static final String AUTHENTICATED_USER_NAME = "authenticatedUserName";

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAuthenticationDAOSQL(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder){

        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkAuthentication(User userRequest){

        boolean result = false;

        List<User> users = jdbcTemplate.query(

                "SELECT * FROM users WHERE nif = ?",
                new String[]{userRequest.getNif()},
                (rs, rowNum) -> new User(rs.getString("nif"),
                                         rs.getString("password"))
        );

        if(users.size() != 0){

            User user = users.get(0);

            if(passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {

                result = true;
                VaadinSession.getCurrent().setAttribute(AUTHENTICATED_USER_NAME, user.getName());
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewUser(User userRequest) throws Exception {

        String nif = userRequest.getNif();
        String email = userRequest.getEmail();
        String password = userRequest.getPassword();

        // All form fields are required; if any of them is empty, throw an exception.
        if(nif.equals("")|| email.equals("") || password.equals("")){

            throw new EmptyFieldException();
        }

        try{
            // If the specified user_name (PK) is available for a new user,
            // insert the user object with encoded password; otherwise, rethrow an exception.
            userRequest.setPassword(passwordEncoder.encode(password));
            jdbcTemplate.update(

                "INSERT INTO users(nif, email, password) VALUES(?, ?, ?)",
                nif, email, userRequest.getPassword()
            );
        } catch(DuplicateKeyException e){

            throw new NonUniqueUserNameException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signOut(){

        VaadinSession.getCurrent().close();
      //  Page.getCurrent().reload();
    }
}
