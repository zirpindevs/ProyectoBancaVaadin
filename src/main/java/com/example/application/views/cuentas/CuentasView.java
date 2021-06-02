package com.example.application.views.cuentas;

import com.example.application.backend.model.*;
import com.example.application.backend.model.bankaccount.operations.BankAccountUserResponse;
import com.example.application.backend.model.transaction.operations.TransactionsCreditcardResponse;

import com.example.application.backend.security.service.UserDetailsServiceImpl;
import com.example.application.backend.service.BankAccountService;

import com.example.application.backend.service.TransactionService;
import com.example.application.backend.service.UserService;
import com.example.application.views.cuentas.form.AccountForm;

import com.github.appreciated.card.Card;
import com.github.appreciated.card.action.ActionButton;
import com.github.appreciated.card.action.Actions;

import com.github.appreciated.card.label.PrimaryLabel;
import com.github.appreciated.card.label.SecondaryLabel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;

import com.vaadin.flow.component.html.Image;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route(value = "cuentas", layout = MainView.class)
@PageTitle("Cuentas")
public class CuentasView extends HorizontalLayout {

    private UserDetailsServiceImpl userDetailsService;

    private static User userLogged;

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int NOTIFICATION_DEFAULT_DURATION = 5000;

    private UserService userService;
    private BankAccountService bankAccountService;
    private TransactionService transactionService;
    private TransactionsCreditcardResponse transactionsCreditcardResponse;

    Map<String, String> map1 = new HashMap<>();

    private List<BankAccount> bankAccounts;
    BankAccountUserResponse bankAccountUserResponse;


    public CuentasView(UserService userService, BankAccountService bankaccountService, TransactionService transactionService, UserDetailsServiceImpl userDetailsService) {

        this.userService = userService;
        this.bankAccountService = bankaccountService;
        this.transactionService = transactionService;
        this.userDetailsService = userDetailsService;

        this.userLogged = userDetailsService.getUserLogged();


        loadDataAllTransactions();


        //pinta cada bankaccount que tenga el usuario
        bankAccounts.forEach(bankAccount -> add(cardGenerator(bankAccount)));

    }

    /**
     * Load Data with all Transactions of all bank accounts of a user
     */
    private void loadDataAllTransactions() {

        Map<String, String> map1 = new HashMap<>();
        map1.put("startDate", "2020-01-01 00:00:00.000000");
        map1.put("endDate", "2022-05-31 23:59:59.999999");
        map1.put("page", "0");
        map1.put("limit", "50");

        try {

            this.bankAccountUserResponse = bankAccountService.findAllBankAccountsByIdUser(this.userLogged.getId());
            this.bankAccounts = this.bankAccountUserResponse.getBankAccounts();

        }
        catch(Exception e) {
            logger.error(e.getMessage());
        }
    }


    public Component cardGenerator(BankAccount bankAccount) {

        HorizontalLayout cardLayout = new HorizontalLayout();

        Image logoBanco = new Image();

        logoBanco = new Image("images/unicaja-logo.png","");
        logoBanco.setHeight("75px");
        logoBanco.setWidth("115px");

        map1.put("startDate", "2020-01-01 00:00:00.000000");
        map1.put("endDate", "2022-05-31 23:59:59.999999");
        map1.put("page", "0");
        map1.put("limit", "50");


        Card card = new Card(
                // if you don't want the title to wrap you can set the whitespace = nowrap
                logoBanco,
                new PrimaryLabel(bankAccount.getBalance().toString()+" €"),
                new SecondaryLabel(bankAccount.getNumAccount()),
                new Actions(
                        new ActionButton("Transferencias", event -> {

                            AccountForm accountForm = new AccountForm(bankAccount, transactionService, userLogged.getId());


                            // open form dialog view
                            accountForm.open();
                            })

                ),
                new Actions(
                        new ActionButton("Consultar Movimientos", event -> {
                            UI.getCurrent().navigate("transactions");

                        })

        )

        );
        cardLayout.add(card);
        cardLayout.setAlignItems(Alignment.CENTER);
        return cardLayout;
    }

    private Image getIcon(String cardProvider) {
        Image img = new Image();

        if(cardProvider.equals("Visa"))
            img = new Image("images/visa.png","");
        if(cardProvider.equals("MasterCard"))
            img = new Image("images/mastercard.png","");

        return img;
    }

    private String maskCardNumber(String cardNumber){
        return "**** " + cardNumber.substring(cardNumber.length() - 4);
    }

}
