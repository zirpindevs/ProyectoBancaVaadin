package com.example.application.views.cuentas.form;

import com.example.application.backend.model.*;
import com.example.application.backend.service.TransactionService;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.KeyPressEvent;
import com.vaadin.flow.component.UI;
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
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Route(value = "account", layout = MainView.class)
@PageTitle("Account")
public class AccountForm extends Dialog {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private Long idUser;
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

    public AccountForm(BankAccount bankAccount, TransactionService transactionService, Long idUser) {

        super();
        this.bankAccount = bankAccount;
        this.transaction = transaction;
        this.transactionService = transactionService;
        this.idUser = idUser;


        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout(bankAccount));


        enviar.addClickListener(e -> {

            try {
                if(createTransaction()) {
                    Notification.show("Transferencia realizada");
                    UI.getCurrent().getPage().reload();

                }
                else {
                    Notification.show("Error en la transferencia");
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            close();


        });

        salir.addClickListener(e -> {
            close();

        });

        importe.addKeyPressListener(new ComponentEventListener<KeyPressEvent>() {
            @Override
            public void onComponentEvent(KeyPressEvent event) {
                if(event.getKey().matches("Enter"))
                    enviar.click();
            }
        });

        concepto.addKeyPressListener(new ComponentEventListener<KeyPressEvent>() {
            @Override
            public void onComponentEvent(KeyPressEvent event) {
                if(event.getKey().matches("Enter"))
                    enviar.click();
            }
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

    private Boolean createTransaction() throws IOException {

        TransactionDTO nuevaTransaction = new TransactionDTO();
        try {
            nuevaTransaction.setConcepto(concepto.getValue());
            nuevaTransaction.setImporte(Double.valueOf(importe.getValue()));
            nuevaTransaction.setTipoMovimiento(movimientoTransferencia);
            nuevaTransaction.setIdBankAccount(bankAccount.getId());

            transactionService.createTransactionVaadin(nuevaTransaction);



        } catch (Exception e) {
            logger.error(e.getMessage());
            nuevaTransaction.setId(-500L);
            return false;
        }
        return true;
    }

}
