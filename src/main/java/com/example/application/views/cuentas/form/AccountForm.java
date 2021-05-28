package com.example.application.views.cuentas.form;

import com.example.application.backend.model.*;
import com.example.application.backend.service.TransactionService;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Route(value = "account", layout = MainView.class)
@PageTitle("Account")
public class AccountForm extends Dialog {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private BankAccount bankAccount;
    private Transaction transaction;
    private TransactionService transactionService;
    private MovimientoType movimientoTransferencia = MovimientoType.TRANSFERENCIA;

    private TextField  numberBankAccount = new TextField("Numbero Cuenta Origen");
    private TextField importe = new TextField("Importe");
    private TextField concepto = new TextField("Concepto");
    private TextField tipoMovimiento = new TextField("Tipo Movimiento");

    private Button enviar = new Button("Enviar");

    private Button salir = new Button("Salir");

    public AccountForm(BankAccount bankAccount, TransactionService transactionService) {

        super();
        this.bankAccount = bankAccount;
        this.transaction = transaction;
        this.transactionService = transactionService;


        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout(bankAccount));


        enviar.addClickListener(e -> {

            if(createTransaction()) {
                Notification.show("Transferencia realizada");
                close();
            }

            Notification.show("Error en la transferencia");
            close();


        });

        salir.addClickListener(e -> {
            close();

        });
    }


    private Component createTitle() {
        return new H3("Realizar Transferencia");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(numberBankAccount, tipoMovimiento, importe, concepto);
        return formLayout;
    }

    private Component createButtonLayout(BankAccount bankAccount) {

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("transferencia-layout");


        numberBankAccount.setValue(bankAccount.getNumAccount().toString());
        numberBankAccount.setReadOnly(true);

        tipoMovimiento.setValue(movimientoTransferencia.toString());
        tipoMovimiento.setReadOnly(true);

        importe.setPlaceholder("Importe");
        importe.setRequired(true);

        concepto.setPlaceholder("concepto");

        enviar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        buttonLayout.add(enviar);
        buttonLayout.add(salir);
        return buttonLayout;
    }

    private Boolean createTransaction() {
        TransactionDTO nuevaTransaction = new TransactionDTO();
        try {
            nuevaTransaction.setConcepto(concepto.getValue());
            nuevaTransaction.setImporte(Double.valueOf(importe.getValue()));
            nuevaTransaction.setTipoMovimiento(movimientoTransferencia);
            nuevaTransaction.setIdBankAccount(bankAccount.getId());

            transactionService.createTransaction(nuevaTransaction);

        } catch (Exception e) {
            logger.error(e.getMessage());
            nuevaTransaction.setId(-500L);
            return false;
        }
        return true;
    }

}
