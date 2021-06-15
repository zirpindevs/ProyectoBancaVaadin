package com.example.application.views.movimientos;

import com.example.application.backend.model.*;
import com.example.application.backend.model.transaction.operations.TransactionsBankAccountResponse;
import com.example.application.backend.model.transaction.operations.TransactionsUserResponse;
import com.example.application.backend.repository.BankAccountRepository;
import com.example.application.backend.security.service.UserDetailsServiceImpl;
import com.example.application.backend.service.BankAccountService;
import com.example.application.backend.service.TransactionService;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Route(value = "movimientosCustom", layout = MainView.class)
@PageTitle("movimientosCustom")
public class CustomMovimientosView extends VerticalLayout implements HasUrlParameter<String>, RouterLayout {

    private static User userLogged;

    TransactionsUserResponse allUserTransactions;

    TransactionsBankAccountResponse transactionsBankAccountResponse;

    List<TransactionGrid> transactions;

    private ListDataProvider<TransactionGrid> transactionGridProvider;

    private Grid<TransactionGrid> gridTransactions = new Grid<>(TransactionGrid.class);

    Logger logger = LoggerFactory.getLogger(this.getClass());

    // Services
    private TransactionService transactionService;

    private BankAccountService bankAccountService;

    private BankAccountRepository bankAccountRepository;

    private UserDetailsServiceImpl userDetailsService;

    BankAccount bankAccount;
    Long bankAccountId;



    public CustomMovimientosView(TransactionService transactionService, BankAccountService bankAccountService, UserDetailsServiceImpl userDetailsService, BankAccountRepository bankAccountRepository) {
        super();
        this.transactionService = transactionService;
        this.bankAccountService = bankAccountService;
        this.userDetailsService = userDetailsService;
        this.bankAccountRepository = bankAccountRepository;


        addClassName("transactions-view");

        this.setWidthFull();
        this.setHeightFull();
        this.setAlignSelf(Alignment.CENTER);
        this.setPadding(true);

        // User logged data
        this.userLogged = userDetailsService.getUserLogged();

        // load data from service
/*
        loadDataAllTransactions();
*/

        // fill grid with data
 /*       configureGrid();

        // create view layput
        createViewLayout();*/
    }


    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        Location location = event.getLocation();
        QueryParameters queryParameters = location.getQueryParameters();

        Map<String, List<String>> parametersMap = queryParameters.getParameters();

        if (parametersMap.get("bankaccountId") != null) {
            Long bankaccountId = Long.parseLong(parametersMap.get("bankaccountId").get(0));

            this.bankAccountId = bankaccountId;
            getBankaccount(bankaccountId);
        }
    }


    private void getBankaccount(Long bankaccountId) {

        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(bankaccountId);

        try {
            if (optionalBankAccount.isPresent()) {
                this.bankAccount = optionalBankAccount.get();
                loadDataAllTransactions();
                configureGrid();
                createViewLayout();
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();

            logger.debug(ex.getLocalizedMessage());
        }

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
            this.transactionsBankAccountResponse = transactionService.findAllTransactionsByDateRangeByIdBankAccount(this.bankAccountId, map1);
            this.transactions = this.transactionsBankAccountResponse.getTransactions();

/*            final int[] i = {0};
            this.transactions.forEach(
                    item -> {
                        if (item.getNumCreditCard() == null){
                            this.transactions.get(i[0]).setNumBankAccount(maskNumber(item.getNumBankAccount()));
                        }else{
                            this.transactions.get(i[0]).setNumBankAccount(maskNumber(item.getNumCreditCard()));
                        }
                        i[0]++;
                    }

            );*/

        }
        catch(Exception e) {
            logger.error(e.getMessage());
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

/*
        gridTransactions.addComponentColumn(item ->new Text(item.getNumBankAccount())).setFlexGrow(0).setWidth("12%").setTextAlign(ColumnTextAlign.START).setHeader("Cuenta / Tarjeta");
*/

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
        if(transaction.getTipoMovimiento().name().equals("PAGO") || transaction.getTipoMovimiento().name().equals("RECIBO") || transaction.getTipoMovimiento().name().equals("TRANSFERENCIA_EMITIDA")){
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
