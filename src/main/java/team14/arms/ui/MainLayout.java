package team14.arms.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.VaadinService;

import team14.arms.ui.page.Kitchen;
import team14.arms.ui.page.Menu;
import team14.arms.ui.page.Waiter;


@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=no")
public class MainLayout extends VerticalLayout implements RouterLayout {

    private static final long serialVersionUID = 4L;

    public MainLayout() {
        super();
        

        // TODO: replace H2 with a banner with navigation menu
        HorizontalLayout header = new HorizontalLayout();
        Div panel = new Div();
        H1 title = new H1("OAXACA");
        panel.add(title);
        header.add(panel);
        header.setSizeFull();
        header.setAlignItems(Alignment.CENTER);
        add(header);
        

        // TODO: add other global components
    }
}
