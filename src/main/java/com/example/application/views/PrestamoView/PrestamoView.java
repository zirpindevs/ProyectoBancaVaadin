package com.example.application.views.PrestamoView;

import com.example.application.backend.model.BankAccount;
import com.example.application.backend.model.TransactionDTO;
import com.example.application.backend.repository.BankAccountRepository;
import com.example.application.views.cuentas.form.AccountForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.KeyPressEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import com.example.application.views.main.MainView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Route(value = "prestamo", layout = MainView.class)
@PageTitle("Prestamo")
public class PrestamoView extends Div implements HasUrlParameter<String> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private BankAccountRepository bankAccountRepository;

    private static BankAccount bankAccount;

    private TextField cuentaIngreso = new TextField("Cuenta de Ingreso");
    private TextField cuentaCobro = new TextField("Cuenta de Cobro");
    private TextField tipoDeInteres = new TextField("Tipo de interes");
    private TextField cantidad = new TextField("Cantidad");
    private Select<String> duracionSelect = new Select<>();



    private Button cancel = new Button("Cancel");
    private Button calcular = new Button("Calcular");


    public PrestamoView(BankAccountRepository bankAccountRepository) {
        super();
        this. bankAccountRepository = bankAccountRepository;

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

            PrestamoForm prestamoForm = new PrestamoForm(bankAccount, cantidad, tipoDeInteres, duracionSelect);


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


}
