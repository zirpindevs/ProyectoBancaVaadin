package com.example.application.views.tarjetas;

import com.example.application.backend.model.CreditCard;
import com.example.application.backend.model.Transaction;
import com.example.application.backend.model.User;
import com.example.application.backend.service.CreditCardService;
import com.example.application.backend.service.UserService;
import com.example.application.views.main.MainView;
import com.github.appreciated.card.Card;
import com.github.appreciated.card.ClickableCard;
import com.github.appreciated.card.RippleClickableCard;
import com.github.appreciated.card.action.ActionButton;
import com.github.appreciated.card.action.Actions;
import com.github.appreciated.card.content.IconItem;
import com.github.appreciated.card.content.Item;
import com.github.appreciated.card.label.PrimaryLabel;
import com.github.appreciated.card.label.SecondaryLabel;
import com.github.appreciated.card.label.TitleLabel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Route(value = "tarjetas", layout = MainView.class)
@PageTitle("Tarjetas")
public class TarjetasView extends HorizontalLayout {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    UserService userService;
    CreditCardService creditCardService;
    private List<CreditCard> creditCards;
    private List<Transaction> transactions;


    User miUser = new User();


    public TarjetasView(UserService userService, CreditCardService creditCardService) {
  /*      this.setSizeFull();
        this.setPadding(true);*/

        this.userService = userService;
        this.creditCardService = creditCardService;

        loadData();


        //pinta cada creditcard que tenga el usuario
        creditCards.forEach(creditCard -> add(cardGenerator(creditCard.getNumCreditCard(), creditCard.getCardProvider())));

    }

    private void loadData() {
        try {

            miUser = userService.findOne(1L).get();

            this.creditCards = creditCardService.findbyUser(miUser.getId());

            if(!creditCards.isEmpty()){
                System.out.println(creditCards);
                creditCards.forEach(creditCard -> {
                    List<Transaction> miTransactions;
/*
                    miTransactions.add(creditCardService.findTransactionByCreditCardId(creditCard.getId()));
*/
                });
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();

            logger.debug(ex.getLocalizedMessage());
        }
    }


    public Component cardGenerator(String numCreditCard, String cardProvider) {

            String maskedNumbers = maskCardNumber(numCreditCard);
            HorizontalLayout cardLayout = new HorizontalLayout();

            Image logoBanco = new Image();

            logoBanco = new Image("images/unicaja-logo.png","");
            logoBanco.setHeight("75px");
            logoBanco.setWidth("115px");

            Card card = new Card(
                    // if you don't want the title to wrap you can set the whitespace = nowrap
                    logoBanco,
                    new PrimaryLabel("Gasto 5000€"),
                    new SecondaryLabel(maskedNumbers),
                    new IconItem(getIcon(cardProvider), ""),
                    new Actions(
                            new ActionButton("Ver detalles", event -> {/* Handle Action*/})
                    )
            );
            cardLayout.add(card);
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
