package com.example.application.views.tarjetas.form;

import com.example.application.backend.model.CreditCard;
import com.example.application.backend.service.CreditCardService;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "credit-card", layout = MainView.class)
@PageTitle("Credit Card")
public class CreditCardForm extends Dialog {

    private CreditCard creditCard;

    private TextField cardNumber = new TextField("Numbero de Tarjeta");
    private TextField cardholderName = new TextField("Name de la tarjeta");
    private TextField creditCardType = new TextField("Tipo de tarjeta");
    private TextField card_provider = new TextField("Proovedor de Tarjeta");

    private Checkbox enabled = new Checkbox();

    private TextField dateField = new TextField("Fecha de Expiracion");

    private PasswordField pin = new PasswordField("PIN");
    private PasswordField cvv = new PasswordField("CVV");

    private Button Salir = new Button("Salir");

    public CreditCardForm(CreditCard creditCard) {

        super();
        this.creditCard = creditCard;


        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout(creditCard));

        Salir.addClickListener(e -> {
            close();

        });
    }

    private Component createTitle() {
        return new H3("Detalles de la Tarjeta de Credito");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(cardNumber, cardholderName, dateField, creditCardType, card_provider, pin, cvv);
        return formLayout;
    }

    private Component createButtonLayout(CreditCard creditCard) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        cardNumber.setPlaceholder(creditCard.getNumCreditCard().toString());
        cardholderName.setPlaceholder(creditCard.getPlaceholder().toString());
        creditCardType.setPlaceholder(creditCard.getType().toString());
        card_provider.setPlaceholder(creditCard.getCardProvider().toString());
        pin.setPlaceholder(creditCard.getPin().toString());
        cvv.setPlaceholder(creditCard.getCvv().toString());

        dateField.setPlaceholder(creditCard.getExpirationDate().toString());
        Salir.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        buttonLayout.add(Salir);
        return buttonLayout;
    }


}
