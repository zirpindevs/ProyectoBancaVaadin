package com.example.application.views.PrestamoView;

import com.example.application.backend.model.BankAccount;
import com.example.application.backend.model.TransactionDTO;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.awt.*;
import java.io.IOException;

@Route(value = "calculateLoan", layout = MainView.class)
@PageTitle("Calculo Prestamo")
public class PrestamoForm extends Dialog {

 /*   private Label cuentaIngreso = new Label("Cuenta de Ingreso");
    private Label cuentaCobro = new Label("Cuenta de Cobro");
    private Label tipoDeInteres = new Label("Tipo de interes");
    private Label cantidad = new Label("Cantidad");*/

    public PrestamoForm(BankAccount bankAccount, TextField cantidad, TextField tipoDeInteres, Select duracionSelect) {
        super();

        add(tipoDeInteres, cantidad);
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