package com.example.application.views.tarjetas;

import com.example.application.backend.model.CreditCard;
import com.example.application.backend.model.Transaction;
import com.example.application.backend.model.TransactionDTO;
import com.example.application.backend.model.User;
import com.example.application.backend.model.transaction.operations.TransactionsCreditcardResponse;
import com.example.application.backend.service.CreditCardService;
import com.example.application.backend.service.TransactionService;
import com.example.application.backend.service.UserService;
import com.example.application.views.main.MainView;
import com.example.application.views.tarjetas.form.CreditCardForm;
import com.github.appreciated.card.Card;
import com.github.appreciated.card.action.ActionButton;
import com.github.appreciated.card.action.Actions;
import com.github.appreciated.card.content.IconItem;
import com.github.appreciated.card.label.PrimaryLabel;
import com.github.appreciated.card.label.SecondaryLabel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route(value = "tarjetas", layout = MainView.class)
@PageTitle("Tarjetas")
public class TarjetasView extends HorizontalLayout {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int NOTIFICATION_DEFAULT_DURATION = 5000;

    private UserService userService;
    private CreditCardService creditCardService;
    private TransactionService transactionService;
    private TransactionsCreditcardResponse transactionsCreditcardResponse;
    private List<TransactionsCreditcardResponse> miLista = null;
    Map<String, String> map1 = new HashMap<>();

    private List<CreditCard> creditCards;
    private List<Transaction> transactions;



    User miUser = new User();
    private Binder<CreditCard> creditCardBinder = new BeanValidationBinder<CreditCard>(CreditCard.class);;


    public TarjetasView(UserService userService, CreditCardService creditCardService, TransactionService transactionService) {
  /*      this.setSizeFull();
        this.setPadding(true);*/

        this.userService = userService;
        this.creditCardService = creditCardService;
        this.transactionService = transactionService;

        loadData();

        //pinta cada creditcard que tenga el usuario
        creditCards.forEach(creditCard -> add(cardGenerator(creditCard)));

    }

    private void loadData() {

        try {

            miUser = userService.findOne(1L).get();

            this.creditCards = creditCardService.findbyUser(miUser.getId());

        }
        catch(Exception ex) {
            ex.printStackTrace();

            logger.debug(ex.getLocalizedMessage());
        }
    }


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
                    new IconItem(getIcon(creditCard.getCardProvider()), ""),
                    new Actions(
                            new ActionButton("Ver detalles", event -> {

                                CreditCardForm creditCardForm = new CreditCardForm(creditCard);


                                // open form dialog view
                                creditCardForm.open();
                            })
                    )
            );
            cardLayout.add(card);
            cardLayout.setAlignItems(Alignment.CENTER);
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

}
