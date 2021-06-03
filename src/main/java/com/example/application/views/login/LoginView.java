package com.example.application.views.login;

import com.example.application.backend.payload.request.LoginRequest;
import com.example.application.backend.payload.response.JwtResponse;

import com.example.application.backend.security.service.AuthService;
import com.example.application.backend.security.service.UserDetailsServiceImpl;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.KeyPressEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.example.application.backend.model.User;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;

import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "login")
@PageTitle("Login")
@UIScope
@CssImport(value = "./themes/proyectobanca/views/login/login-view.css")
public class LoginView extends VerticalLayout {

    private UserDetailsServiceImpl userDetailsService;

    public static final String NAME = "login";
    private Binder<User> userBinder = new Binder<>();
    private User user = new User("", "");
    private TextField userNameTextField = new TextField("NIF");
    private PasswordField passwordTextField = new PasswordField("Password");
    private Button signInButton = new Button("Sign in", e ->  signIn(user));



    @Autowired
    AuthService authService;

    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService){

        this.userDetailsService = userDetailsService;
    }

    public LoginView(){

        this.setAlignSelf(Alignment.CENTER);
        this.setAlignItems(Alignment.CENTER);
        this.setClassName("principal-layout");

        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.add(new Image("/images/logo.png", ""));
        logoLayout.setClassName("logo-image");


        FormLayout logInFormLayout = new FormLayout(userNameTextField, passwordTextField, signInButton);
        VerticalLayout logInPageLayout = new VerticalLayout(logInFormLayout);
        logInFormLayout.setClassName("login-layout");
        logInFormLayout.setSizeUndefined();
        logInPageLayout.setWidth("20%");
        logInPageLayout.setAlignItems(Alignment.CENTER);


        logInPageLayout.setAlignItems(Alignment.CENTER);


        // Bind the user object to text fields for reading in form inputs
        userBinder.bind(userNameTextField, User::getNif, User::setNif);
        userBinder.bind(passwordTextField, User::getPassword, User::setPassword);
        userBinder.setBean(user);


        userNameTextField.addKeyPressListener(new ComponentEventListener<KeyPressEvent>() {
            @Override
            public void onComponentEvent(KeyPressEvent event) {
                if(event.getKey().matches("Enter"))
                    signInButton.click();
            }
        });

        passwordTextField.addKeyPressListener(new ComponentEventListener<KeyPressEvent>() {
            @Override
            public void onComponentEvent(KeyPressEvent event) {
                if(event.getKey().matches("Enter"))
                    signInButton.click();
            }
        });

        add(logoLayout, logInPageLayout);
    }

    /**
     * If the user has valid credentials, show the main page; else, show the error message
     * @param userRequest User object encapsulating the input form data, namely user_name and password
     */
    private void signIn(User userRequest){

        LoginRequest userLogin = new LoginRequest();
        userLogin.setNif(userRequest.getNif());
        userLogin.setPassword(userRequest.getPassword());

        try{
            JwtResponse token = authService.authenticateUser(userLogin);
            UI.getCurrent().navigate("inicio");


        }catch (Exception e){
            Notification.show("Usuario o contraseña incorrecta");
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
