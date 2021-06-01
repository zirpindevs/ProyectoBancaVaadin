package com.example.application.views.login;

import com.example.application.backend.payload.request.LoginRequest;
import com.example.application.backend.payload.response.JwtResponse;

import com.example.application.backend.security.service.AuthService;
import com.example.application.backend.security.service.UserDetailsServiceImpl;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.example.application.backend.model.User;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;

import org.springframework.beans.factory.annotation.Autowired;



@Route(value = "login")
@PageTitle("Login")
@UIScope
public class LoginView extends HorizontalLayout {

    private UserDetailsServiceImpl userDetailsService;

    public static final String NAME = "login";
    private Binder<User> userBinder = new Binder<>();
    private User user = new User("", "");
    private TextField userNameTextField = new TextField("NIF");
    private PasswordField passwordTextField = new PasswordField("Password");
    private Button signInButton = new Button("Sign in", e ->  signIn(user));
   // private Label newUserLabel = new Label("<span style='cursor: pointer; color:blue'>new user?</span>");


    @Autowired
    AuthService authService;

    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService){

        this.userDetailsService = userDetailsService;
    }

    public LoginView(){

        // Initialize and arrange layout components
       // HorizontalLayout signUpLayout = new HorizontalLayout(newUserLabel);
      //  FormLayout logInFormLayout = new FormLayout(userNameTextField, passwordTextField,
      //                                              signUpLayout, signInButton);
        FormLayout logInFormLayout = new FormLayout(userNameTextField, passwordTextField, signInButton);
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

        LoginRequest userLogin = new LoginRequest();
        userLogin.setNif(userRequest.getNif());
        userLogin.setPassword(userRequest.getPassword());

        try{
            JwtResponse token = authService.authenticateUser(userLogin);
            UI.getCurrent().navigate("inicio");
       //     SecurityConfiguration.getUserDetails().getUsername()

        }catch (Exception e){
            e.printStackTrace();
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
