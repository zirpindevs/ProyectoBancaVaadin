package com.example.application.backend.service.impl;

import com.example.application.backend.dao.CreditCardDAO;
import com.example.application.backend.model.*;
import com.example.application.backend.repository.CreditCardRepository;
import com.example.application.backend.repository.UserRepository;
import com.example.application.backend.service.CreditCardService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CreditCardServiceImpl implements CreditCardService {
    private final Logger log = LoggerFactory.getLogger(CreditCardServiceImpl.class);

    private final CreditCardRepository creditCardRepository;
    private final CreditCardDAO creditCardDAO;
    private final UserRepository userRepository;

    public CreditCardServiceImpl(CreditCardRepository creditCardRepository, CreditCardDAO creditCardDAO, UserRepository userRepository) {
        this.creditCardRepository = creditCardRepository;
        this.creditCardDAO = creditCardDAO;
        this.userRepository = userRepository;
    }

    @Override
    public List<CreditCard> findAll() {
        log.info("REST request to find all CreditCards");

        return this.creditCardRepository.findAll();
    }

    @Override
    public CreditCard findOne(Long id) {
        log.info("REST request to find one CreditCard by id");

        if(id == null)
            return null;
        return this.creditCardDAO.findById(id);
    }


    /**
     * Get user by ID - Service
     * @param id Primary key of User: Long
     * @return Optional<User> from database
     */
    @Override
    public List<CreditCard> findbyUser(Long id) {
        try {

            List<CreditCard> creditCardDb = creditCardRepository.findByUserId(id);

            return creditCardDb;

        }catch (Exception e){

            log.error(e.getMessage());
            User creditCardError = new User();
            creditCardError.setId(-500L);

            return (List<CreditCard>) creditCardError;
        }
    }

    /**
     * Create a new credit card in database - Service
     * @param creditCardDTO to update
     * @return CreditCard created in database
     */
    @Override
    public CreditCard createCreditCard(CreditCardDTO creditCardDTO) {

        try {

            CreditCard creditCardValidated = createValidateCreditCard(creditCardDTO);

            if (creditCardValidated.getNumCreditCard() == null)
                return new CreditCard();;

            return creditCardRepository.save(creditCardValidated);

        }catch (Exception e){

            log.error(e.getMessage());
            e.printStackTrace();
            CreditCard creditCardError = new CreditCard();
            creditCardError.setId(-500L);

            return creditCardError;
        }
    }

    /**
     * It update a credit card of database - Service
     * @param creditCardDTO to update
     * @return CreditCard updated in database
     */
    @Override
    public CreditCard updateCreditCard(Long id, CreditCardDTO creditCardDTO) {

        try {

            CreditCard creditCardValidated = updateValidateCreditCard(id, creditCardDTO);

            if (creditCardValidated.getNumCreditCard() == null){
                CreditCard creditCardError = new CreditCard();
                creditCardError.setId(-404L);
                return creditCardError;
            }


            return creditCardRepository.save(creditCardValidated);

        }catch (Exception e){

            log.error(e.getMessage());
            CreditCard creditCardError = new CreditCard();
            creditCardError.setId(-500L);

            return creditCardError;
        }
    }


    /**
     * Delete creditcard of database by ID - Service
     * @param id creditcard primary key that you want to delete
     * @return Optional<Boolean>
     */
    @Override
    public CreditCard markAsDeleteOne(Long id){

        CreditCard deleteCreditCard = new CreditCard();

        try {
            Optional<CreditCard> beforeCreditCard = creditCardRepository.findById(id);

            if (!beforeCreditCard.isPresent()){
                CreditCard creditCardError = new CreditCard();
                creditCardError.setId(-404L);
                return creditCardError;
            }

            deleteCreditCard = beforeCreditCard.get();

            deleteCreditCard.setDeleted(true);
            deleteCreditCard.setUpdatedAt(LocalDateTime.now());
            return creditCardRepository.save(deleteCreditCard);

        }catch (Exception e){

            log.error(e.getMessage());
            CreditCard creditCardError = new CreditCard();
            creditCardError.setId(-500L);
            return creditCardError;

        }

    }

    /**
     * Validate a credit card before to save in db
     * @param creditCardDTO
     * @return CreditCard
     */
    private CreditCard createValidateCreditCard (CreditCardDTO creditCardDTO){

        CreditCard creditCardEmpty = new CreditCard();

        if ( creditCardDTO.getNumCreditCard() == null || creditCardDTO.getPlaceholder() == null || creditCardDTO.getCvv() == null || creditCardDTO.getPin() == null
                || creditCardDTO.getExpirationDate() == null  || creditCardDTO.getCardProvider() == null){

            return creditCardEmpty;

        }else {

            if(creditCardDTO.getCvv().length() != 3 || creditCardDTO.getPin().length() != 4)
                return creditCardEmpty;

            Boolean numCreditCardExist = creditCardRepository.existsByNumCreditCard(creditCardDTO.getNumCreditCard());
            if (numCreditCardExist)
                return creditCardEmpty;

            if ( creditCardDTO.getIdUser() != null && userRepository.existsById(creditCardDTO.getIdUser()) == false)
                return creditCardEmpty;

            // If passed all validations

            if ( creditCardDTO.getEnabled() == null)
                creditCardDTO.setEnabled(true);

            if ( creditCardDTO.getType() == null)
                creditCardDTO.setType(CreditCardType.debito);

            CreditCard creditCard = new CreditCard();

            creditCard.setNumCreditCard(creditCardDTO.getNumCreditCard());
            creditCard.setPlaceholder(creditCardDTO.getPlaceholder());
            creditCard.setType(creditCardDTO.getType());
            creditCard.setCardProvider(creditCardDTO.getCardProvider());
            creditCard.setCvv(creditCardDTO.getCvv());
            creditCard.setPin(creditCardDTO.getPin());
            creditCard.setExpirationDate(creditCardDTO.getExpirationDate());
            creditCard.setEnabled(creditCardDTO.getEnabled());
            creditCard.setDeleted(false);
            creditCard.setCreatedAt(LocalDateTime.now());
            if (creditCardDTO.getIdUser() != null) {
                Optional<User> user = userRepository.findOneById(creditCardDTO.getIdUser());
                creditCard.setUser(user.get());
            }

            return creditCard;
        }


    }

    /**
     * Validate a credit card before to update in db
     * @param creditCardDTO
     * @return CreditCard
     */
    private CreditCard updateValidateCreditCard (Long id, CreditCardDTO creditCardDTO){

        if ( creditCardDTO.getNumCreditCard() == null || creditCardDTO.getPlaceholder() == null || creditCardDTO.getCvv() == null || creditCardDTO.getPin() == null
                || creditCardDTO.getExpirationDate() == null || creditCardDTO.getCardProvider() == null || creditCardDTO.getDeleted()){
            return new CreditCard();

        }else {

            // Exist ?
            Optional<CreditCard> creditCardDB = this.creditCardRepository.findById(id);
            if (ObjectUtils.isEmpty(creditCardDB))
                return new CreditCard();

            // Is not possible to modify the number of credit card
            if (!creditCardDTO.getNumCreditCard().equals(creditCardDB.get().getNumCreditCard()))
                return new CreditCard();

            // Is not possible to modify the Placeholder of credit card
            if (!creditCardDTO.getPlaceholder().equals(creditCardDB.get().getPlaceholder()))
                return new CreditCard();

            // Is not possible to modify the Type of credit card
            if (!creditCardDTO.getType().equals(creditCardDB.get().getType()))
                return new CreditCard();

            // Is not possible to modify the Card Provider of credit card
            if (!creditCardDTO.getCardProvider().equals(creditCardDB.get().getCardProvider()))
                return new CreditCard();

            // Is not possible to modify the CVV code of credit card
            if (!creditCardDTO.getCvv().equals(creditCardDB.get().getCvv()))
                return new CreditCard();

            // Is not possible to modify the Expiration Date of credit card
            if (!creditCardDTO.getExpirationDate().equals(creditCardDB.get().getExpirationDate()))
                return new CreditCard();

            // Is not possible to modify the user own of credit card
            if (!creditCardDTO.getIdUser().equals(creditCardDB.get().getUser().getId()))
                return new CreditCard();

            // Is not possible to modify the deleted status of credit card
            if (!creditCardDTO.getDeleted().equals(creditCardDB.get().getDeleted()))
                return new CreditCard();

            // Only is possible to modify in a credit card
            creditCardDB.get().setPin(creditCardDTO.getPin());
            creditCardDB.get().setEnabled(creditCardDTO.getEnabled());
            creditCardDB.get().setUpdatedAt(LocalDateTime.now());

            return creditCardDB.get();
        }


    }
}

