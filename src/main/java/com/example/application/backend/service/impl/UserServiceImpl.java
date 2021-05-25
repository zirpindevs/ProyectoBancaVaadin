package com.example.application.backend.service.impl;

import com.example.application.backend.dao.UserDao;
import com.example.application.backend.model.User;
import com.example.application.backend.model.UserStatus;
import com.example.application.backend.repository.UserRepository;
import com.example.application.backend.service.UserService;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private Session session;

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository repository;

    private final UserDao userDao;

    public UserServiceImpl(UserRepository repository, UserDao userDao) {
        this.repository = repository;
        this.userDao = userDao;
    }

    /**
     * Get all users with optionals filters options (String name, String pag, String limit)
     * Service Method
     * @param map1 map with all filters options (String name, String pag, String limit)
     * @return List of users from database
     */
    @Override
    public List<User> findAll(Map<String, String> map1) {

        try {

            return this.userDao.findAllByFilters(map1);

        }catch (Exception e){

            log.error(e.getMessage());
            List<User> usersError = new ArrayList<>();
            User userError = new User();
            userError.setId(-500L);
            usersError.add(userError);

            return usersError;
        }
    }

    /**
     * Get user by ID - Service
     * @param id Primary key of User: Long
     * @return Optional<User> from database
     */
    @Override
    public Optional<User> findOne(Long id) {
        try {

            if(id == null)
                return Optional.empty();

            Optional<User> userDb = this.repository.findById(id);

            return userDb;

        }catch (Exception e){

            log.error(e.getMessage());
            User userError = new User();
            userError.setId(-500L);

            return Optional.of(userError);
        }
    }

    /**
     * Create a new user in database - Service
     * @param user User to create
     * @return user created in database
     */
    @Override
    public User createUser(User user) {

        try {

            User userValidate = createValidateFields(user);
            User userVacio = new User();
            if (ObjectUtils.isEmpty(userValidate.getNif()))
                return userValidate;

            return repository.save(userValidate);

        }catch (Exception e){

            log.error(e.getMessage());
            e.printStackTrace();
            User userError = new User();
            userError.setId(-500L);

            return userError;
        }
    }

    /**
     * It update a user of database - Service
     * @param user to update
     * @return user updated in database
     */
    @Override
    public User updateUser(Long id, User user) {

        try {

            Optional<User> beforeUser = repository.findById(id);

            if (!beforeUser.isPresent()){
                User userError = new User();
                userError.setId(-404L);
                return userError;
            }

            if(user.getEnabled()==null)
                user.setEnabled(beforeUser.get().getEnabled());

            if(user.getStatus()==null)
                user.setStatus(beforeUser.get().getStatus());

            user.setId(id);
            user.setCreatedAt(beforeUser.get().getCreatedAt());
            user.setUpdatedAt(LocalDateTime.now());
            return repository.save(user);

        }catch (Exception e){

            log.error(e.getMessage());
            User userError = new User();
            userError.setId(-500L);
            return userError;

        }
    }

    /**
     * Delete user of database by ID - Service
     * @param id user primary key that you want to delete
     * @return Optional<Boolean>
     */
    @Override
    public Optional<Boolean> deleteOne(Long id){

        try {
            if (id != null && repository.existsById(id)) {

                // Delete users - bank accounts relations
                Optional<Boolean> result = userDao.deleteUsersBankAccountsRelation(id);

                if (Objects.equals(result, Optional.of(true))){
                    repository.deleteById(id);
                }

                return Optional.of(true);
            }
            return Optional.of(false);
        }catch (Exception e){
            log.error(e.getMessage());
            return Optional.empty();
        }

    }

    /**
     * Validate a user before to save in db
     * @param user
     * @return User
     */
    private User createValidateFields (User user){

        if ( user.getNif() == null || user.getEmail() == null || user.getPassword() == null || user.getName() == null || user.getLastName() == null){

            user = new User();
            // user.setId(-404L);

        }else {

            Optional<User> userExist = repository.findOneByNif(user.getNif());
            if (!ObjectUtils.isEmpty(userExist))
                return new User();

            Boolean userEmailExist = repository.existsByEmail(user.getEmail());
            if (userEmailExist)
                return new User();

            Boolean numberPhoneExist = repository.existsByNumberPhone(user.getNumberPhone());
            if (numberPhoneExist)
                return new User();


            if ( user.getEnabled() == null)
                user.setEnabled(true);

            user.setCreatedAt(LocalDateTime.now());
            user.setStatus(UserStatus.pendiente);
        }

        return user;
    }
}
