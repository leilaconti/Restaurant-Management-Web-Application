package team14.arms.ui.page;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import team14.arms.backend.data.entity.MenuItem;
import team14.arms.backend.repositories.MenuItemRepository;
import team14.arms.backend.repositories.OrderRepository;
import team14.arms.backend.data.model.Basket;
import team14.arms.ui.MainLayout;
import team14.arms.ui.components.MenuItemCard;

/*
 * TODO: Use a different layout for menu.
 *       Customers should not be presented by the regular website layout.
 *       Menu should not allow customers to navigate away from the menu page.
 */
@Route(value = "waiteredit", layout = MainLayout.class)
public class WaiterEdit extends VerticalLayout {

    private static final long serialVersionUID = 3L;

    // Database access.
    private MenuItemRepository menuItemRepository;
    private OrderRepository orderRepository;

    // Basket.
    private Basket basket = new Basket();

    // Start components.
    private HorizontalLayout visual = new HorizontalLayout();

    private Div buttons = new Div();
   
    
    
    private TextField itemrmv = new TextField();

    
    // End components.

    public WaiterEdit(MenuItemRepository menuItemRepository, OrderRepository orderRepository) {
        
        super();
        
        itemrmv.setLabel("Remove Menu Item ");
        itemrmv.setPlaceholder("Item ID");
        
        buttons.add(itemrmv);

        this.menuItemRepository = menuItemRepository;
        this.orderRepository = orderRepository;

        // TODO: add menuItems columns and design

        populateMenu();
    }

    private void populateMenu() {
      for (MenuItem menuItem : menuItemRepository.findAll()) {
        visual.add(new MenuItemCard(menuItem, basket, menuItemRepository));
      }
      add(visual);
    }
}
