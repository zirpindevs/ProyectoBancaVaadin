package com.example.application.views.login;

import com.example.application.backend.model.User;
import com.example.application.backend.service.UserAuthenticationDAO;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

import org.springframework.beans.factory.annotation.Autowired;


@Route(value = SignUpView.NAME)
@PageTitle("Login")
@UIScope
public class SignUpView extends HorizontalLayout {

    private UserAuthenticationDAO userAuthenticationDAO;

    public static final String NAME = "sign_up";
    private Binder<User> userBinder = new Binder<>();
    private User user = new User("", "", "");
    private TextField userNameTextField = new TextField("User Name");
    private TextField emailTextField = new TextField("Email");
    private PasswordField passwordTextField = new PasswordField("Password");
    private Button signUpButton = new Button("Sign up", e -> signUp(user));

    @Autowired
    public void setUserAuthenticationDAO(UserAuthenticationDAO userAuthenticationDAO){

        this.userAuthenticationDAO = userAuthenticationDAO;
    }

    public SignUpView(){

        // Initialize and arrange layout components
        FormLayout signUpFormLayout = new FormLayout(userNameTextField, emailTextField,
                                                     passwordTextField, signUpButton);
        VerticalLayout signUpPageLayout = new VerticalLayout(signUpFormLayout);
        signUpFormLayout.setSizeUndefined();
        signUpPageLayout.setSizeFull();
       // signUpPageLayout.setComponentAlignment(signUpFormLayout, Alignment.TOP_CENTER);

        // Bind the user object to text fields for reading in form inputs
        userBinder.bind(userNameTextField, User::getNif, User::setNif);
        userBinder.bind(emailTextField, User::getEmail, User::setEmail);
        userBinder.bind(passwordTextField, User::getPassword, User::setPassword);
        userBinder.setBean(user);

        // Set up button and fields
       // signUpButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        userNameTextField.setRequiredIndicatorVisible(true);
        emailTextField.setRequiredIndicatorVisible(true);
        passwordTextField.setRequiredIndicatorVisible(true);

        add(signUpPageLayout);
    }

    /**
     * Register a new user
     * @param userRequest User object encapsulating the input form data, namely user_name, email, and password
     */
    private void signUp(User userRequest){

        try {
            userAuthenticationDAO.addNewUser(userRequest);
          //  navigator.navigateTo(LoginView.NAME);
        } catch (Exception e){

         //   Notification.show("Sign up failed: " + e.getMessage(),
        //            Notification.Type.ERROR_MESSAGE);
        }
    }

/*    @Override
    public void beforeLeave (ViewBeforeLeaveEvent event){

        // Clear the text fields before redirection
        userNameTextField.setValue("");
        emailTextField.setValue("");
        passwordTextField.setValue("");
        event.navigate();
    }*/
}
