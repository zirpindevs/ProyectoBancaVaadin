package com.example.application.views.inicio;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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

import java.util.List;

@Route(value = "inicio", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Inicio")
public class InicioView extends Div {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int NOTIFICATION_DEFAULT_DURATION = 5000;

/*    private WarehouseService warehouseService;
    private ProductService productService;

    private List<Product> products;
    private ListDataProvider<Product> productProvider;

    private HorizontalLayout toolBarLayout;
    private Button refreshProducts;
    private Grid<Product> gridProduct = new Grid<>(Product.class);*/


    public InicioView() {
        addClassName("inicio-view");

        // load data from service
/*
        loadData();
*/

        // fill grid with data
/*
        configureGrid();
*/

        // create view layput
        createViewLayout();;
/*
        add(configureGrid());
*/
    }

    private void loadGrid() {
/*        productProvider =  DataProvider.ofCollection(this.products);
        productProvider.setSortOrder(Product::getName, SortDirection.ASCENDING);

        gridProduct.setDataProvider(productProvider);*/
    }

    private void createViewLayout() {
        /*toolBarLayout = new HorizontalLayout();
        toolBarLayout.setPadding(true);
        toolBarLayout.setWidthFull();

        addProduct = new Button("Add Product", clickEvent -> createProductButton(clickEvent));
        addProduct.getElement().getStyle().set("margin-right", "auto");
        addProduct.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        refreshProducts = new Button("Refresh Products", clickEvent -> refreshProducts(clickEvent));

        toolBarLayout.add(addProduct, refreshProducts);

        gridProduct.setSizeFull();

        add(toolBarLayout);
        add(gridProduct);*/
    }

    private void configureGrid() {
/*        loadGrid();

        gridProduct.setSizeFull();
        gridProduct.setColumns("warehouse.name", "name", "description", "family", "price");
        gridProduct.getColumnByKey("warehouse.name").setFlexGrow(0).setWidth("200px").setHeader("Warehouse").setFooter("Total: " + this.products.size() + " products");
        gridProduct.getColumnByKey("name").setFlexGrow(0).setWidth("200px").setHeader("Name");
        gridProduct.getColumnByKey("description").setFlexGrow(1).setHeader("Description");
        gridProduct.getColumnByKey("family").setFlexGrow(0).setWidth("150px").setHeader("Family");
        gridProduct.getColumnByKey("price").setFlexGrow(0).setWidth("100px").setHeader("Price");
        gridProduct.addColumn(
                new ComponentRenderer<>(
                        product -> {
                            Checkbox checkbox = new Checkbox();
                            checkbox.setReadOnly(true);
                            checkbox.setValue( product.isActive());

                            return checkbox;
                        }
                )
        ).setHeader("Active").setKey("active").setFlexGrow(0).setWidth("80px").setHeader("Active");

        if (SecurityConfiguration.isAdmin()) {
            gridProduct.addComponentColumn(item -> updateProductButton(gridProduct, item)).setFlexGrow(0).setWidth("120px").setHeader("");
            gridProduct.addComponentColumn(item -> removeRemoveButton(gridProduct, item)).setFlexGrow(0).setWidth("120px").setHeader("");
        }

        gridProduct.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS,
                GridVariant.LUMO_ROW_STRIPES);*/
    }

    private void refreshProducts(ClickEvent e) {
        try {
            // load data from service
/*
            loadData();
*/

            // fill grid with data
            loadGrid();
        } catch (Exception ex) {
            logger.error(ex.getMessage());

            Notification.show(ex.getMessage());
        }
    }

}
