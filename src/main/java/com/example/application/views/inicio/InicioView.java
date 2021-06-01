package com.example.application.views.inicio;

import com.example.application.backend.dao.CategoryDao;
import com.example.application.backend.dao.TransactionDAO;
import com.example.application.backend.dao.TransactionOperationsDao;
import com.example.application.backend.model.CreditCard;
import com.example.application.backend.model.TransactionDTO;
import com.example.application.backend.model.TransactionGrid;
import com.example.application.backend.model.User;
import com.example.application.backend.model.transaction.operations.TransactionsCreditcardResponse;
import com.example.application.backend.model.transaction.operations.TransactionsUserResponse;
import com.example.application.backend.service.BankAccountService;
import com.example.application.backend.service.CreditCardService;
import com.example.application.backend.service.TransactionService;
import com.example.application.backend.service.UserService;
import com.example.application.views.tarjetas.form.CreditCardForm;
import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.legend.HorizontalAlign;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.config.responsive.builder.OptionsBuilder;
import com.github.appreciated.apexcharts.config.stroke.Curve;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.config.xaxis.XAxisType;
import com.github.appreciated.apexcharts.helper.Series;
import com.github.appreciated.card.Card;
import com.github.appreciated.card.action.ActionButton;
import com.github.appreciated.card.action.Actions;
import com.github.appreciated.card.content.IconItem;
import com.github.appreciated.card.label.PrimaryLabel;
import com.github.appreciated.card.label.SecondaryLabel;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Route(value = "inicio", layout = MainView.class)
@RouteAlias(value = "inicio", layout = MainView.class)
@PageTitle("Inicio")
public class InicioView extends HorizontalLayout {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TransactionService transactionService;
    private final BankAccountService bankAccountService;
    private final UserService userService;
    private final CreditCardService creditCardService;
    private final TransactionDAO transactionDAO;
    private final TransactionOperationsDao transactionOperationsDao;
    private final CategoryDao categoryDao;


    Map<String, String> map1 = new HashMap<>();

    private List<CreditCard> creditCards;

    TransactionsUserResponse allUserTransactions;

    List<TransactionGrid> transactions;

    private ListDataProvider<TransactionGrid> transactionGridProvider;
    User miUser = new User();

    private Grid<TransactionGrid> gridTransactions = new Grid<>(TransactionGrid.class);



    public InicioView(UserService userService, CreditCardService creditCardService, TransactionService transactionService,
                      BankAccountService bankAccountService, TransactionDAO transactionDAO, TransactionOperationsDao transactionOperationsDao, CategoryDao categoryDao) {
        super();
        this.transactionService = transactionService;
        this.bankAccountService = bankAccountService;
        this.userService = userService;
        this.creditCardService = creditCardService;
        this.transactionDAO = transactionDAO;
        this.transactionOperationsDao = transactionOperationsDao;
        this.categoryDao = categoryDao;

        this.setSizeFull();

        addClassName("inicio-view");

        // load data from service
        miUser = userService.findOne(1L).get();
        this.creditCards = creditCardService.findbyUser(miUser.getId());


        // load data from service
        loadDataAllTransactions();

        // fill grid with data
        configureGrid();

        paintLayouts();
    }

    private void paintLayouts() {

    HorizontalLayout creditcardLayout = new HorizontalLayout();

    VerticalLayout graphicsLayout = new VerticalLayout();
    SplitLayout mainLayout = new SplitLayout();


    creditcardLayout.setHeight("55%");


    for(int x = 0; x < creditCards.size(); x++)
        creditcardLayout.add(cardGenerator(creditCards.get(x)));



        Button buttonTarjetas = new Button("Ver Tarjetas", clickEvent -> createCreditCardButton(clickEvent));

        buttonTarjetas.setThemeName("tertiary-inline");
        creditcardLayout.add(buttonTarjetas);

        creditcardLayout.setVerticalComponentAlignment(Alignment.START,
                buttonTarjetas);
        creditcardLayout.setSpacing(true);

        //CREATE LAYOUT WITH CREDITCARDS AND GRID
        SplitLayout leftLayout = new SplitLayout();
        leftLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        leftLayout.addToPrimary(creditcardLayout);
        leftLayout.addToSecondary(createViewLayout());
        leftLayout.setWidth("70%");

        //ADD GRAPHICS CHARTS
        graphicsLayout.add(new AreaBarChartExample(), new DonutChartExample());
        graphicsLayout.setWidth("40%");

        //ADD MAIN LAYOUT LEFTLAYOUT AND GRAPHICS
        mainLayout.setSizeFull();
        mainLayout.addToPrimary(leftLayout);
        mainLayout.addToSecondary(graphicsLayout);

        add(mainLayout);

    }

    private void createCreditCardButton(ClickEvent e) {

        try {
            UI.getCurrent().navigate("tarjetas");
        } catch (Exception ex) {
            logger.error(ex.getMessage());

            Notification.show(ex.getMessage());
        }
    }

    /**************************************CREDIT CARD LAYOUT***************************************************************************/


     public Component cardGenerator(CreditCard creditCard) {

     String maskedNumbers = maskCardNumber(creditCard.getNumCreditCard());
     HorizontalLayout cardLayout = new HorizontalLayout();

     Image logoBanco = new Image();

     logoBanco = new Image("images/unicaja-logo.png","");
     logoBanco.setHeight("75px");
     logoBanco.setWidth("115px");

     map1.put("startDate", "2020-01-01 00:00:00.000000");
     map1.put("endDate", "2022-05-31 23:59:59.999999");
     map1.put("page", "0");
     map1.put("limit", "50");

     TransactionsCreditcardResponse balanceTarjeta = transactionService.findAllTransactionsByDateRangeByIdCreditcard(creditCard.getId(), map1);

     List<TransactionDTO> transactionsTarjetas = balanceTarjeta.getTransactions();

     Double saldoTarjeta = 0D;

     for(int x=0; x<transactionsTarjetas.size();x++) {

     if(transactionsTarjetas.get(x).getTipoMovimiento().name().equals("PAGO") || transactionsTarjetas.get(x).getTipoMovimiento().name().equals("RECIBO"))
     saldoTarjeta = saldoTarjeta + transactionsTarjetas.get(x).getImporte();
     }


     Card card = new Card(
     // if you don't want the title to wrap you can set the whitespace = nowrap
     logoBanco,
     new PrimaryLabel(saldoTarjeta.toString()+" €"),
     new SecondaryLabel(maskedNumbers),
     new IconItem(getIcon(creditCard.getCardProvider()), "")
     );
     cardLayout.add(card);
     cardLayout.setAlignItems(FlexComponent.Alignment.CENTER);
     return cardLayout;
     }

     private Image getIcon(String cardProvider) {
     Image img = new Image();

     if(cardProvider.equals("Visa"))
     img = new Image("images/visa.png","");
     if(cardProvider.equals("MasterCard"))
     img = new Image("images/mastercard.png","");

     return img;
     }

     private String maskCardNumber(String cardNumber){
     return "**** " + cardNumber.substring(cardNumber.length() - 4);
     }




     /**************************************GRAPHICS LAYOUT***************************************************************************/



    public class AreaBarChartExample extends Div {
        public AreaBarChartExample() {

            Object transactionTest = transactionDAO.findAllBalanceAfterTransaction(1L);
            Series testserie = new Series();
            testserie.setData((Object[]) transactionTest);


            ApexCharts barChart = ApexChartsBuilder.get()
                    .withChart(ChartBuilder.get()
                            .withType(Type.bar)
                            .build())
                    .withPlotOptions(PlotOptionsBuilder.get()
                            .withBar(BarBuilder.get()
                                    .withHorizontal(true)
                                    .build())
                            .build())
                    .withDataLabels(DataLabelsBuilder.get()
                            .withEnabled(false)
                            .build())
                    .withSeries(new Series<>("balance after transaction",testserie.getData()))
                    .withXaxis(XAxisBuilder.get()
                            .withCategories()
                            .build())
                    .build();
            add(barChart);
            setWidth("100%");

        }
    }


    public class DonutChartExample extends Div {
        public DonutChartExample()
        {

            List transactionOperations = transactionOperationsDao.getAllOperationsByCategoryBankAccount(1L);
            List<String> categoriesName = categoryDao.findChartCategoriesAllByName();

            Series donutSerie = new Series();

            List<String> listaString= new ArrayList<>();
            List<Double> listaDouble = new ArrayList<>();

            for(int x = 0; x < transactionOperations.size();x++) {
                listaString.add(transactionOperations.get(x).toString());
                listaDouble.add(Double.valueOf(listaString.get(x)));
            }

            ApexCharts donutChart = ApexChartsBuilder.get()
                    .withChart(ChartBuilder.get().withType(Type.donut).build())
                    .withLegend(LegendBuilder.get()
                            .withPosition(Position.right)
                            .build())

                    .withSeries(listaDouble.get(0), listaDouble.get(1), listaDouble.get(2), listaDouble.get(3), listaDouble.get(4))
                    .withLabels(categoriesName.get(0), categoriesName.get(1), categoriesName.get(2), categoriesName.get(3), categoriesName.get(4))

                    .withResponsive(ResponsiveBuilder.get()
                            .withBreakpoint(480.0)
                            .withOptions(OptionsBuilder.get()
                                    .withLegend(LegendBuilder.get()
                                            .withPosition(Position.bottom)
                                            .build())
                                    .build())
                            .build())
                    .build();
            add(donutChart);
            setWidth("100%");
        }
    }



     /**************************************TRANSACTIONS GRID LAYOUT***************************************************************************/

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


    private Component createViewLayout() {

        Div gridViewLayout = new Div();


        gridTransactions.setWidth("100%");
        gridTransactions.setHeightFull();

        gridViewLayout.add(gridTransactions);
        gridViewLayout.setSizeFull();

        return gridViewLayout;
    }


}
