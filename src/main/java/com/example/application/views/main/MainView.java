package com.example.application.views.main;

import java.util.Optional;

import com.example.application.views.chart.ChartView;
import com.example.application.views.login.LoginView;
import com.example.application.views.movimientos.MovimientosView;
import com.example.application.views.tarjetas.TarjetasView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.cuentas.CuentasView;
import com.example.application.views.inicio.InicioView;

/**
 * The main view is a top-level placeholder for other views.
 */
@Route(value = "main")
@PWA(name = "IngeniaBank", shortName = "IngeniaBank", enableInstallPrompt = false)
@Theme(themeFolder = "proyectobanca")
public class MainView extends AppLayout {

    private final Tabs menu;
    private H1 viewTitle;

    public MainView() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        menu = createMenu();
        addToDrawer(createDrawerContent(menu));
    }

    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(new DrawerToggle());
        viewTitle = new H1();
        layout.add(viewTitle);
        layout.add(createAvatarMenu());
        return layout;
    }

    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new Image("images/logo.png", ""));

        layout.add(logoLayout, menu);
        return layout;
    }

    private Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(createMenuItems());
        return tabs;
    }

    private Component[] createMenuItems() {
        return new Tab[]{
                createTab("Inicio", InicioView.class),
                createTab("Cuentas", CuentasView.class),
                createTab("Tarjetas", TarjetasView.class),
                createTab("Movimientos", MovimientosView.class),
                createTab("Graficas", ChartView.class)};
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();

        if(text.equals("Inicio"))
            tab.add(new Icon(VaadinIcon.HOME));
        if(text.equals("Cuentas"))
            tab.add(new Image("/images/menu/group.png",""));
        if(text.equals("Tarjetas"))
            tab.add(new Image("/images/menu/tarjetas.png",""));
        if(text.equals("Movimientos"))
            tab.add(new Image("/images/menu/transfer.png",""));
        if(text.equals("Graficas"))
            tab.add(new Image("/images/menu/balance.png",""));

        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    private Component createAvatarMenu() {
        // get security context
        Avatar avatar = new Avatar();
/*
        avatar.setName(SecurityConfiguration.getUserDetails().getUsername());
*/
        avatar.setName("usuario");


        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setOpenOnClick(true);
        contextMenu.setTarget(avatar);

        contextMenu.addItem("Logout", e -> {
            contextMenu.getUI().ifPresent(ui -> ui.getPage().setLocation("/logout"));
        });

        return avatar;
    }


    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
        viewTitle.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
