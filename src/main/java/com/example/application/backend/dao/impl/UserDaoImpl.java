package com.example.application.backend.dao.impl;

import com.example.application.backend.dao.UserDao;

import com.example.application.backend.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager manager;

    private final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    /**
     * Get all users with optionals filters options (String name, String pag, String limit)
     * Dao Method
     * @param map1 map with all filters options (String name, String pag, String limit)
     * @return List of users from database
     */
    @Override
    public List<User> findAllByFilters(Map<String, String> map1) {

        try {

            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> root = criteria.from(User.class);

            List<Predicate> predicates = buildPredicatesFilter(map1, builder, root);

            criteria.distinct(true).select(root).where(builder.and(predicates.toArray(new Predicate[0])));

            TypedQuery<User> usersQuery = manager.createQuery(criteria);

            if(map1.get("page")!=null && map1.get("limit")!=null){
                usersQuery.setFirstResult(Integer.parseInt(map1.get("page")));
                usersQuery.setMaxResults(Integer.parseInt(map1.get("limit")));
            }

            return usersQuery.getResultList();

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
     * Get all id of the bank accounts that a user has
     * @param idUser
     * @return List with all id of the bank accounts that a user has
     */
    @Override
    public List findAllBankAccountsByUser(Long idUser, Map<String, String> map1) {

        try {
            if (idUser != null) {

                Query queryNative = manager.createNativeQuery(
                        "SELECT bank_account_id FROM users_bank_accounts WHERE  user_id = " + idUser);
                List result = queryNative.getResultList();

                return result;
            }
            return new ArrayList<>();

        }catch (Exception e){
            log.error(e.getMessage());
            return new ArrayList<>();
        }

    }

    /**
     * Build the predicates needed to create the filter search sql query
     * @param map1 Filters of
     * @param builder
     * @param root
     * @return predicates to sql search
     */
    private List<Predicate> buildPredicatesFilter(Map<String, String> map1,CriteriaBuilder builder, Root<User> root){

        List<Predicate> predicates = new ArrayList<>();

        if (map1.get("name") != null)
            predicates.add(builder.like(root.get("name"), map1.get("name") + "%"));


        return predicates;
    }


    /**
     * Delete all register relationated with Users and BankAccounts
     * @param idUser primary key of User need to delete of the relation
     * @return Boolean
     */
    @Override
    @Transactional
    public Optional<Boolean> deleteUsersBankAccountsRelation(Long idUser){

        try {
            if (idUser != null) {

                Query queryNative = manager.createNativeQuery("delete from users_bank_accounts where user_id = "+ idUser);
                queryNative.executeUpdate();

                return Optional.of(true);
            }
            return Optional.of(false);

        }catch (Exception e){
            log.error(e.getMessage());
            return Optional.empty();
        }
    }
}
