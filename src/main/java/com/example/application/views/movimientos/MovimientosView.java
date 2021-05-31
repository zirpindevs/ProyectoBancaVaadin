package com.example.application.views.movimientos;

import com.example.application.backend.model.BankAccount;
import com.example.application.backend.model.Transaction;
import com.example.application.backend.model.TransactionDTO;
import com.example.application.backend.model.TransactionGrid;
import com.example.application.backend.model.transaction.operations.TransactionsUserResponse;
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

    private TransactionService transactionService;

    private BankAccountService bankAccountService;

    TransactionsUserResponse allUserTransactions;

    List<TransactionGrid> transactions;

    private ListDataProvider<TransactionGrid> transactionGridProvider;

    private Grid<TransactionGrid> gridTransactions = new Grid<>(TransactionGrid.class);

    private HorizontalLayout toolBarLayout;



    public MovimientosView(TransactionService transactionService, BankAccountService bankAccountService) {

        this.transactionService = transactionService;
        this.bankAccountService = bankAccountService;

        addClassName("transactions-view");

        this.setWidthFull();
        this.setHeightFull();
        this.setAlignSelf(Alignment.CENTER);
        this.setPadding(true);

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
            this.allUserTransactions = transactionService.findAllTransactionsByDateRangeByIdUser(3L, map1);
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
        ).setFlexGrow(0).setWidth("10%").setSortable(true).setTextAlign(ColumnTextAlign.END).setHeader("Fecha");

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
        //warehouseProvider.setSortOrder(Warehouse::getName, SortDirection.ASCENDING);

        gridTransactions.setDataProvider(transactionGridProvider);
    }

    private void createViewLayout() {

        Component toolbarLayout = createToolBarLayout();

        gridTransactions.setWidth("100%");
        gridTransactions.setHeightFull();

        add(toolbarLayout, gridTransactions);
    }

    private Component createToolBarLayout() {

        Button buscarButton = new Button("Hola");

        toolBarLayout = new HorizontalLayout();
        toolBarLayout.setPadding(false);
        toolBarLayout.setWidthFull();
        //toolBarLayout.setHeight("60px");

       //**********************************************************
       /* Map<String, String> map1 = new HashMap<>();
        map1.put("startDate", "2020-01-01 00:00:00.000000");
        map1.put("endDate", "2022-05-31 23:59:59.999999");
        map1.put("page", "0");
        map1.put("limit", "50");

        Div message = new Div();

        ComboBox<TransactionGrid> comboBox = new ComboBox<>("Project");
        ListDataProvider<List<TransactionGrid>> dataProvider = DataProvider
                .ofItems(this.transactions.stream());
               *//* .fromFilteringCallbacks(this::this.transactionService.findAllTransactionsByDateRangeByIdUser(3L, map1),
                        15);*//*
        comboBox.setDataProvider(dataProvider);
        comboBox.setItemLabelGenerator(TransactionGrid::getNumCreditCard);

        comboBox.addValueChangeListener(valueChangeEvent -> {
            if (valueChangeEvent.getValue() == null) {
                message.setText("No project selected");
            } else {
                message.setText(
                        "Selected value: " + valueChangeEvent.getValue());
            }
        });

        comboBox.addCustomValueSetListener(event -> {
            TransactionGrid transaction = projectData.addProject(event.getDetail());
            comboBox.setValue(transaction);
        });
        add(comboBox, message);*/
        //********************************************************
        add(buscarButton);

        return toolBarLayout;
    }



}
