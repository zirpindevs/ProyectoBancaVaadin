package com.example.application.views.PrestamoView.form;

import com.example.application.backend.model.BankAccount;
import com.example.application.backend.model.TransactionDTO;
import com.example.application.views.main.MainView;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Route(value = "calculateLoan", layout = MainView.class)
@PageTitle("Calculo Prestamo")
public class PrestamoForm extends Notification {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static enum DIALOG_RESULT {CONFIRM, CANCEL};


    private DIALOG_RESULT dialogResult;


    /*   private Label cuentaIngreso = new Label("Cuenta de Ingreso");
        private Label cuentaCobro = new Label("Cuenta de Cobro");
        private Label tipoDeInteres = new Label("Tipo de interes");
        private Label cantidad = new Label("Cantidad");*/
        private Button enviar = new Button("Enviar");
        private Double cantidad;


    public PrestamoForm(BankAccount bankAccount, TextField cantidad, TextField tipoDeInteres, Select duracionSelect) {
        super();

        this.cantidad = Double.valueOf(cantidad.getValue()) ;

        add(tipoDeInteres, cantidad, createToolbarLayout());

        enviar.addClickListener(e -> {

            close();

        });
    }

    public DIALOG_RESULT getDialogResult() {
        return this.dialogResult;
    }



    private Component createToolbarLayout() {
        Button saveButton = new Button("Confirm", event -> {
            // retreive the product updated from form
            this.dialogResult = DIALOG_RESULT.CONFIRM;
            close();


        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickShortcut(Key.ENTER).listenOn(this);
        saveButton.getElement().getStyle().set("margin-left", "auto");

        Button cancelButton = new Button("Cancel", event -> {
            this.dialogResult = DIALOG_RESULT.CANCEL;

            close();
        });

        HorizontalLayout formToolBar = new HorizontalLayout(saveButton, cancelButton);
        formToolBar.setWidthFull();
        formToolBar.getElement().getStyle().set("padding-top", "30px");

        return formToolBar;
    }

    private Double calculateMonthlyPayment(){
        Double monthlyAmount = 0D;

      //  Double totalAmount = this.threadCobro + ( this.threadCobro * this.threadInteres) / threadDuration;

        return monthlyAmount;
    }



/*
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
    */

}
