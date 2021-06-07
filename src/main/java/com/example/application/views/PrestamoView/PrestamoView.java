package com.example.application.views.PrestamoView;

import com.example.application.backend.model.BankAccount;
import com.example.application.backend.model.User;
import com.example.application.backend.model.bankaccount.operations.BankAccountUserResponse;
import com.example.application.backend.repository.BankAccountRepository;
import com.example.application.backend.security.service.UserDetailsServiceImpl;
import com.example.application.backend.service.BankAccountService;
import com.example.application.views.PrestamoView.form.PrestamoForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.KeyPressEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.example.application.views.main.MainView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;


@Route(value = "prestamo", layout = MainView.class)
@PageTitle("Prestamo")
public class PrestamoView extends Div implements HasUrlParameter<String> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserDetailsServiceImpl userDetailsService;
    private static User userLogged;

    private BankAccountRepository bankAccountRepository;

    private BankAccountService bankAccountService;

    // Cuenta en la que se abona el préstamo
    private static BankAccount bankAccountIncome;

    private List<BankAccount> bankAccountsUser;
    BankAccountUserResponse bankAccountUserResponse;

    private TextField cuentaIngreso = new TextField("Cuenta de Ingreso");
    private Component cuentaCobro = new ComboBox<String>();
    private TextField tipoDeInteres = new TextField("Tipo de interes");
    private TextField cantidad = new TextField("Cantidad");
    private Select<String> duracionSelect = new Select<>();



    private Button cancel = new Button("Cancel");
    private Button calcular = new Button("Calcular");


    public PrestamoView(BankAccountRepository bankAccountRepository, BankAccountService bankAccountService, UserDetailsServiceImpl userDetailsService) {
        super();
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountService = bankAccountService;
        this.userLogged = userDetailsService.getUserLogged();



    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        Location location = event.getLocation();
        QueryParameters queryParameters = location.getQueryParameters();

        Map<String, List<String>> parametersMap = queryParameters.getParameters();

        if (parametersMap.get("bankaccountId") != null) {
            Long bankaccountId = Long.parseLong(parametersMap.get("bankaccountId").get(0));

            getBankaccount(bankaccountId);
        }
    }


    @PostConstruct
    public void init() {

        this.cuentaCobro = comboBankAccount();



    }

    private void getBankaccount(Long bankaccountId) {

        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(bankaccountId);

        try {
            if (optionalBankAccount.isPresent()) {
                this.bankAccountIncome = optionalBankAccount.get();
                createViewLayout();
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();

            logger.debug(ex.getLocalizedMessage());
        }

    }

    private Component createTitle() {
        return new H3("Alta de Préstamo");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();

        duracionSelect.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
        duracionSelect.setLabel("Meses de Duracion");

        tipoDeInteres.setValue("10%");
        tipoDeInteres.setReadOnly(true);

        cuentaIngreso.setValue(this.bankAccountIncome.getNumAccount());
        cuentaIngreso.setReadOnly(true);

        // cuentaCobro.setValue(bankAccountRepository.findById(7L).get().getNumAccount());
        // cuentaCobro.setReadOnly(true);



        formLayout.add(cuentaIngreso, cuentaCobro, tipoDeInteres, duracionSelect, cantidad);
        // formLayout.add(cuentaIngreso, comboBankAccount(), tipoDeInteres, duracionSelect, cantidad);

        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        calcular.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(calcular);
        buttonLayout.add(cancel);
        return buttonLayout;
    }


    private void createViewLayout() {
        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        calcular.addClickListener(e ->
        {

            PrestamoForm prestamoForm = new PrestamoForm(bankAccountIncome, cantidad, tipoDeInteres, duracionSelect);


            // open form dialog view
            prestamoForm.open();
        });


        cantidad.addKeyPressListener(new ComponentEventListener<KeyPressEvent>() {
            @Override
            public void onComponentEvent(KeyPressEvent event) {
                if(event.getKey().matches("Enter"))
                    calcular.click();
            }
        });
    }

    private Component comboBankAccount(){

        final String[] numBankAccountSelected = {""};

        List<BankAccount>bankAccountsUser = loadBankAccountsUser();


        Collection<String> bankAccountsCombo= new ArrayList<>();
        //  bankAccountsCombo.add("00025145487541254");
        //  bankAccountsCombo.add("00025145487541254");

        for(int x = 0; x < bankAccountsUser.size();x++) {
            bankAccountsCombo.add(bankAccountsUser.get(x).getNumAccount());
        }

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setLabel("Cuenta de cobro");
        comboBox.setItems(bankAccountsCombo);
        comboBox.setValue(bankAccountsUser.get(0).getNumAccount());


        comboBox.setClearButtonVisible(true);
        comboBox.setAllowCustomValue(false);

        Div value = new Div();
        value.setText("Select a value");
        comboBox.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                value.setText("No option selected");
            } else {

                value.setText(event.getValue());
                numBankAccountSelected[0] = value.getText();
            }

        });

        return comboBox;
        // add(comboBox, value);
    }

    /**
     * Load all bank accounts of a user
     * @return
     */
    private List<BankAccount> loadBankAccountsUser() {

        try {

            //  this.userLogged = userDetailsService.getUserLogged();
            this.bankAccountUserResponse = bankAccountService.findAllBankAccountsByIdUser(userLogged.getId());
            this.bankAccountsUser = this.bankAccountUserResponse.getBankAccounts();

        }
        catch(Exception e) {
            logger.error(e.getMessage());
        }
        return this.bankAccountsUser;
    }


}
