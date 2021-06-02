package com.example.application.views.movimientos;

import com.example.application.backend.model.*;
import com.example.application.backend.model.transaction.operations.TransactionsUserResponse;
import com.example.application.backend.security.service.UserDetailsServiceImpl;
import com.example.application.backend.service.BankAccountService;
import com.example.application.backend.service.TransactionService;
import com.example.application.views.main.MainView;
import com.github.appreciated.card.label.PrimaryLabel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Route(value = "transactions", layout = MainView.class)
@PageTitle("Movimientos")
public class MovimientosView extends VerticalLayout {

    private static User userLogged;

    TransactionsUserResponse allUserTransactions;

    List<TransactionGrid> transactions;

    private ListDataProvider<TransactionGrid> transactionGridProvider;

    private Grid<TransactionGrid> gridTransactions = new Grid<>(TransactionGrid.class);

    // Services
    private TransactionService transactionService;

    private BankAccountService bankAccountService;

    private UserDetailsServiceImpl userDetailsService;



    public MovimientosView(TransactionService transactionService, BankAccountService bankAccountService, UserDetailsServiceImpl userDetailsService) {

        this.transactionService = transactionService;
        this.bankAccountService = bankAccountService;
        this.userDetailsService = userDetailsService;

        addClassName("transactions-view");

        this.setWidthFull();
        this.setHeightFull();
        this.setAlignSelf(Alignment.CENTER);
        this.setPadding(true);

        // User logged data
        this.userLogged = userDetailsService.getUserLogged();

        // load data from service
        loadDataAllTransactions();

        // fill grid with data
        configureGrid();

        // create view layput
        createViewLayout();
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
            this.allUserTransactions = transactionService.findAllTransactionsByDateRangeByIdUser(userLogged.getId(), map1);
            this.transactions = this.allUserTransactions.getTransactions();

            final int[] i = {0};
            this.transactions.forEach(
                    item -> {
                        if (item.getNumCreditCard() == null){
                            this.transactions.get(i[0]).setNumBankAccount(maskNumber(item.getNumBankAccount()));
                        }else{
                            this.transactions.get(i[0]).setNumBankAccount(maskNumber(item.getNumCreditCard()));
                        }
                        i[0]++;
                    }

            );

        }
        catch(Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * Print Grid with transactions data
     */
    private void configureGrid() {
        loadGrid();

        gridTransactions.setWidth("100%");
        gridTransactions.setColumns();

        gridTransactions.addComponentColumn(
                item -> printTransactionIcon(gridTransactions, item)
        ).setFlexGrow(0).setWidth("200px").setTextAlign(ColumnTextAlign.START).setHeader("");

        gridTransactions.addComponentColumn(item ->new Text(item.getNumBankAccount())).setFlexGrow(0).setWidth("12%").setTextAlign(ColumnTextAlign.START).setHeader("Cuenta / Tarjeta");

        gridTransactions.addComponentColumn(item ->new Label(item.getImporte().toString() + " €")).setFlexGrow(0).setWidth("15%").setTextAlign(ColumnTextAlign.END).setHeader("Importe");

        gridTransactions.addComponentColumn(item ->new Text(item.getConcepto())).setFlexGrow(1).setHeader("Concepto");

        gridTransactions.addComponentColumn(item ->new Label(
                item.getCreatedDate().toString().substring(8,10) + "/" +
                    item.getCreatedDate().toString().substring(6,7) + "/" +
                        item.getCreatedDate().toString().substring(0,4)
                )
        ).setFlexGrow(0).setWidth("10%").setTextAlign(ColumnTextAlign.END).setHeader("Fecha");

        gridTransactions.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS,
                GridVariant.LUMO_ROW_STRIPES);
    }

    private Image printTransactionIcon(Grid<TransactionGrid> gridTransactions, TransactionGrid transaction){
        Image icon = new Image();
        if(transaction.getTipoMovimiento().name().equals("PAGO") || transaction.getTipoMovimiento().name().equals("RECIBO")){
            icon.setSrc("/images/icon-rojo.png");
        }else{
            icon.setSrc("/images/icon-verde.png");
        }

        return icon;
    }


    private String maskNumber(String number){
        return "**** " + number.substring(number.length() - 4);
    }

    private void loadGrid() {
        transactionGridProvider =  DataProvider.ofCollection(this.transactions);

        gridTransactions.setDataProvider(transactionGridProvider);
    }

    private void createViewLayout() {

        gridTransactions.setWidth("100%");
        gridTransactions.setHeightFull();

        add(gridTransactions);
    }

}
