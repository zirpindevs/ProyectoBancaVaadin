package com.example.application.views.PrestamoView.form;

import com.example.application.backend.model.BankAccount;
import com.example.application.backend.model.MovimientoType;
import com.example.application.backend.model.TransactionDTO;
import com.example.application.backend.service.TransactionService;
import com.example.application.views.main.MainView;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;


@Route(value = "calculateLoan", layout = MainView.class)
@PageTitle("Calculo Prestamo")
public class PrestamoForm extends Dialog {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static enum DIALOG_RESULT {CONFIRM, CANCEL};

    @Autowired
    private TransactionService transactionService;



    private DIALOG_RESULT dialogResult;

        private TextField bankAccountIncomeForm = new TextField();
        private TextField bankAccountCobroForm = new TextField();
        private TextField importePrestamoForm = new TextField();
        private TextField interesPrestamoForm = new TextField();
        private TextField numCuotasPrestamoForm = new TextField();
        private TextField cuotaMensualPrestamoForm = new TextField();
        private Button enviar = new Button("Enviar");
        private BankAccount bankAccountIncome;


    public PrestamoForm(BankAccount bankAccountIncome, BankAccount bankAccountCobro, String importePrestamo, String tipoDeInteres, String numCuotas) {
        super();

        this.transactionService = transactionService;
        this.bankAccountIncome = bankAccountIncome;

        this.bankAccountIncomeForm.setLabel("Cuenta de Ingreso");
        this.bankAccountIncomeForm.setValue(bankAccountIncome.getNumAccount());
        this.bankAccountIncomeForm.setReadOnly(true);

        this.bankAccountCobroForm.setLabel("Cuenta de Cobro de recibos");
        this.bankAccountCobroForm.setValue(bankAccountCobro.getNumAccount());
        this.bankAccountCobroForm.setReadOnly(true);

        this.importePrestamoForm.setLabel("Importe Préstamo");
        this.importePrestamoForm.setValue(importePrestamo);
        this.importePrestamoForm.setReadOnly(true);

        this.interesPrestamoForm.setLabel("Tipo de interés");
        this.interesPrestamoForm.setValue(tipoDeInteres);
        this.interesPrestamoForm.setReadOnly(true);

        this.numCuotasPrestamoForm.setLabel("Número de cuotas");
        this.numCuotasPrestamoForm.setValue(numCuotas);
        this.numCuotasPrestamoForm.setReadOnly(true);

        this.cuotaMensualPrestamoForm.setLabel("Cuota mensual");
        this.cuotaMensualPrestamoForm.setValue(calculateMonthlyPayment().toString());
        this.cuotaMensualPrestamoForm.setReadOnly(true);


       // add(bankAccountIncomeForm, bankAccountCobroForm, importePrestamoForm, interesPrestamoForm, numCuotasPrestamoForm, cuotaMensualPrestamoForm, createToolbarLayout());

        FormLayout formConfirmLayout = new FormLayout(bankAccountIncomeForm, bankAccountCobroForm, importePrestamoForm, interesPrestamoForm, numCuotasPrestamoForm, cuotaMensualPrestamoForm);
        add(formConfirmLayout, createToolbarLayout());

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

        Double loanedMoney = Double.valueOf(importePrestamoForm.getValue());
        Double loanRate = 10D;
        Integer numCuotas = Integer.valueOf(numCuotasPrestamoForm.getValue());

        Double monthlyQuota = (loanedMoney + ( loanedMoney / loanRate)) / numCuotas;

        return monthlyQuota;
    }






}
