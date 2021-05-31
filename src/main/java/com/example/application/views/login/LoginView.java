package com.example.application.views.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.example.application.backend.model.User;
import com.example.application.backend.service.UserAuthenticationDAO;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;

import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "login")
//@Route(value = LoginView.NAME)
@PageTitle("Login")
@UIScope
public class LoginView extends HorizontalLayout {

    private UserAuthenticationDAO userAuthenticationDAO;

    public static final String NAME = "login";
    private Binder<User> userBinder = new Binder<>();
    private User user = new User("", "");
    private TextField userNameTextField = new TextField("NIF");
    private PasswordField passwordTextField = new PasswordField("Password");
    private Button signInButton = new Button("Sign in", e ->  signIn(user));
    private Label newUserLabel = new Label("<span style='cursor: pointer; color:blue'>new user?</span>");



    @Autowired
    public void setUserAuthenticationDAO(UserAuthenticationDAO userAuthenticationDAO){

        this.userAuthenticationDAO = userAuthenticationDAO;
    }

    public LoginView(){

        // Initialize and arrange layout components
        HorizontalLayout signUpLayout = new HorizontalLayout(newUserLabel);
        FormLayout logInFormLayout = new FormLayout(userNameTextField, passwordTextField,
                                                    signUpLayout, signInButton);
        VerticalLayout logInPageLayout = new VerticalLayout(logInFormLayout);
        logInFormLayout.setSizeUndefined();
        logInPageLayout.setSizeFull();

        logInPageLayout.setAlignItems(Alignment.CENTER);
       // logInPageLayout.setComponentAlignment(logInFormLayout, Alignment.TOP_CENTER);

        // Bind the user object to text fields for reading in form inputs
        userBinder.bind(userNameTextField, User::getNif, User::setNif);
        userBinder.bind(passwordTextField, User::getPassword, User::setPassword);
        userBinder.setBean(user);

        // Set up button and link
       // signInButton.set.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        //signUpLayout.addLayoutClickListener(e -> navigator.navigateTo(org.si.simple_login.vaadin.views.SignUpView.NAME));

        add(logInPageLayout);
    }

    /**
     * If the user has valid credentials, show the main page; else, show the error message
     * @param userRequest User object encapsulating the input form data, namely user_name and password
     */
    private void signIn(User userRequest){

        if(userAuthenticationDAO.checkAuthentication(userRequest)){

            UI.getCurrent().navigate("cuentas");
            Notification.show("Autenticado");
        } else{

            Notification.show("Invalid user name or password");
        }
    }

   // @Override
    public void beforeLeave (BeforeLeaveEvent event){

        // Clear the text fields before redirection
        userNameTextField.setValue("");
        passwordTextField.setValue("");
        event.getUI().navigate("main");
    }

}
