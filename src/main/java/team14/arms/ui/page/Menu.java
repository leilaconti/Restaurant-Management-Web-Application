package team14.arms.ui.page;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import team14.arms.backend.data.entity.MenuItem;
import team14.arms.backend.data.entity.Order;
import team14.arms.backend.data.entity.TableNumber;
import team14.arms.backend.repositories.MenuItemRepository;
import team14.arms.backend.repositories.OrderRepository;
import team14.arms.backend.repositories.TableNumberRepository;
import team14.arms.backend.service.WaiterNotification;
import team14.arms.backend.data.model.Basket;
import team14.arms.ui.MainLayout;
import team14.arms.ui.components.MenuItemCard;
import team14.arms.ui.components.SearchBar;

@Tag("menu-view")
//@JavaScript("src/main/resources/static/backend/src/javascript/Checkout.js")
@StyleSheet("src/views/menu/menu-view.css")
@Route(value = "menu", layout = MainLayout.class)

public class Menu extends VerticalLayout{

    private static final long serialVersionUID = 3L;

    // Database access.
    private MenuItemRepository menuItemRepository;
    private OrderRepository orderRepository;
    private TableNumberRepository tableNumberRepo;
    private MenuItem [] items;

    // Basket.
    private Basket basket = new Basket();
    
    //Table Number
    private TableNumber table = new TableNumber(generateTableNumber());

    // Start components.
    private HorizontalLayout buttons = new HorizontalLayout();
    
    // TODO: use submenus to allow for viewing items and categories etc.
    private Label basketList = new Label("");

    private H3 total = new H3("Total: ");
    private final Button basketButton = new Button();
    private final Button closeNotification = new Button("Close");

    private Label content = new Label("Please review your order.");
    
    private final Button orderButton = new Button("Order", event -> {
      Order order = new Order(this.basket, table, false);
      
      tableNumberRepo.save(table);
      orderRepository.save(order);

      // Clear basket after order.
      basket.clear();
      content.setText("Your order has been placed!");
    });
    
    private Notification notification;
    
    private Button getWaiter = new Button("Get Waiter");
    // End components.

    public Menu(MenuItemRepository menuItemRepository, OrderRepository orderRepository, TableNumberRepository tableNumberRepo) {
        
        super();
        
        this.getStyle().set("background-image", "src/images/menu-background.jpg");
        
        H3 tablenum = new H3("Table Number:  " + table.getNumber());
        add(tablenum);
        
//        visual.getStyle().set("background", "gray");

        this.menuItemRepository = menuItemRepository;
        this.orderRepository = orderRepository;
        this.tableNumberRepo = tableNumberRepo;
        
        Notification call = new Notification(
        		"A waiter will be with you shortly", 3000);
        getWaiter.addClickListener(event -> {
        		call.open();
        		WaiterNotification send = new WaiterNotification();
        		send.setVisable();
        });
        
        VerticalLayout order = new VerticalLayout();
        
        this.notification = new Notification();
        basketButton.setIcon(VaadinIcon.CART_O.create());
        
        basketButton.addClickListener(event -> {
          if(!this.basket.isEmpty()) {
            this.items = null;
            this.items = basket.getItemMap().keySet().toArray(new MenuItem[basket.getTotalItems()]);
            total.setText("Total: £" + this.basket.getTotalPrice().toString());
          }
            
          basketList.setText("");
          String bList = "";
          
          for(int i = 0; i < this.basket.getTotalItems(); i++) {
              bList = bList + "<br>" + items[i].getName() + ": " + this.basket.get(items[i]) + "</br>";
          }
            
          basketList.setText(bList);
          notification.open();
        });
        
        closeNotification.addClickListener(event -> {
          notification.close();
        });
          
        buttons.add(orderButton, closeNotification);
        
        notification.setPosition(Position.MIDDLE);
        order.add(basketList);
        
        notification.add(content, order, total, buttons);
        // TODO: add menuItems columns and design

        populateMenu();
    }

    private void populateMenu() {
      int counter = 0;
      HorizontalLayout bottomBar = new HorizontalLayout();
      HorizontalLayout visual = new HorizontalLayout();
      VerticalLayout menuList = new VerticalLayout();
      
        for (MenuItem menuItem : menuItemRepository.findAll()) {
            if(menuItem.getStock()) {
              visual.add(new MenuItemCard(menuItem, basket, menuItemRepository));
              counter++;
            }
            
            if(counter == 4) {
              menuList.add(visual);
              counter = 0;
              visual = new HorizontalLayout();
            }
        }
        visual.setAlignItems(Alignment.CENTER);
        add(menuList);
        bottomBar.add(basketButton, getWaiter);
        add(bottomBar, notification);
    }
    
    private String generateTableNumber() {
      return String.valueOf(Math.floor(Math.random()*19 + 1));
    }
    
    
}
