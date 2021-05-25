package com.example.application.views.inicio;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "inicio", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Inicio")
public class InicioView extends Div {

    public InicioView() {
        addClassName("inicio-view");
        add(new Text("Content placeholder"));
    }

}
