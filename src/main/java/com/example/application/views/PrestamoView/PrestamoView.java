package com.example.application.views.PrestamoView;

import com.example.application.backend.model.BankAccount;
import com.example.application.backend.repository.BankAccountRepository;
import com.example.application.backend.service.TransactionService;
import com.example.application.views.PrestamoView.AsyncPush.AsyncPush;
import com.example.application.views.PrestamoView.form.PrestamoForm;
import com.flowingcode.vaadin.addons.simpletimer.SimpleTimer;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.example.application.views.main.MainView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;


@Route(value = "prestamo", layout = MainView.class)
@PageTitle("Prestamo")
public class PrestamoView extends Div implements HasUrlParameter<String>, RouterLayout {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int NOTIFICATION_DEFAULT_DURATION = 1000;

    private TransactionService transactionService;
    private BankAccountRepository bankAccountRepository;

    private static BankAccount bankAccount;

    private TextField cuentaIngreso = new TextField("Cuenta de Ingreso");
    private TextField cuentaCobro = new TextField("Cuenta de Cobro");
    private TextField tipoDeInteres = new TextField("Tipo de interes");
    private TextField cantidad = new TextField("Cantidad");
    private Select<String> duracionSelect = new Select<>();


    private Button cancel = new Button("Cancel");
    private Button calcular = new Button("Calcular");


    public PrestamoView(BankAccountRepository bankAccountRepository, TransactionService transactionService) {
        super();
        this.bankAccountRepository = bankAccountRepository;
        this.transactionService = transactionService;

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

    private void getBankaccount(Long bankaccountId) {

        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(bankaccountId);

        try {
            if (!optionalBankAccount.isEmpty()) {
                this.bankAccount = optionalBankAccount.get();

                //llamada a crear vista
                createViewLayout2();
            }
        } catch (Exception ex) {
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

        tipoDeInteres.setValue("10");
        tipoDeInteres.setReadOnly(true);

        cuentaIngreso.setValue(this.bankAccount.getNumAccount());
        cuentaIngreso.setReadOnly(true);

        cuentaCobro.setValue(bankAccountRepository.findById(7L).get().getNumAccount());
        cuentaCobro.setReadOnly(true);


        formLayout.add(cuentaIngreso, cuentaCobro, tipoDeInteres, duracionSelect, cantidad);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        calcular.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(calculateButton());
        buttonLayout.add(cancel);
        return buttonLayout;
    }


    private void createViewLayout() {
        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        calcular.addClickListener(e ->
        {

            PrestamoForm prestamoForm = new PrestamoForm(bankAccount, cantidad, tipoDeInteres, duracionSelect);


            // open form dialog view
            prestamoForm.open();



        });


        cantidad.addKeyPressListener(new ComponentEventListener<KeyPressEvent>() {
            @Override
            public void onComponentEvent(KeyPressEvent event) {
                if (event.getKey().matches("Enter"))
                    calcular.click();
            }
        });
    }






























































    private void createViewLayout2() {
        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        calcular.addClickListener(e ->
        {

            PrestamoForm prestamoForm = new PrestamoForm(bankAccount, cantidad, tipoDeInteres, duracionSelect);


            // open form dialog view
            prestamoForm.open();

        });

    }

    private Button calculateButton() {
        Button button = new Button("Previsualizar Prestamo", clickEvent -> {
            // define form dialog
            PrestamoForm prestamoForm = new PrestamoForm(bankAccount, cantidad, tipoDeInteres, duracionSelect);


            // define form dialog view callback
            prestamoForm.addOpenedChangeListener(event -> {
                if(!event.isOpened()) {
                    if (prestamoForm.getDialogResult() == PrestamoForm.DIALOG_RESULT.CONFIRM)
                        try {

                            cantidad.setValue("1000");
                            tipoDeInteres.setValue("10");
                            duracionSelect.setValue("5");

                            AsyncPush asyncPush = new AsyncPush(bankAccount, cantidad.getValue(), duracionSelect.getValue(), tipoDeInteres.getValue(), transactionService);

                        } catch (Exception ex) {
                            logger.error(ex.getMessage());

                            Notification.show(ex.getMessage(), NOTIFICATION_DEFAULT_DURATION, Notification.Position.TOP_END);

                        }
                }
            });

            // open form dialog view
            prestamoForm.open();
        });

        return button;
    }


    private void cobrarDeudas() throws InterruptedException {
/*
        UI.getCurrent().navigate("push");
*/



    }


}
