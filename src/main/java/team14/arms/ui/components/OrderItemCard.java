package team14.arms.ui.components;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Set;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinService;

import team14.arms.backend.data.entity.*;
import team14.arms.backend.data.model.OrderStatus;
import team14.arms.backend.repositories.OrderRepository;

/**
 * Creates order item cards for kitchen staff and waiters to view.
 */
public class OrderItemCard extends VerticalLayout {

    private Order order;
    private String url = (VaadinService.getCurrentRequest().getPathInfo());
    private OrderRepository orderRepo;
    private Temporal orderTime;
    private Set<MenuItem> items;
    private TableNumber table;
    private Icon newItem = new Icon(VaadinIcon.EXCLAMATION_CIRCLE);
    private Icon orderReady = new Icon(VaadinIcon.CHECK_CIRCLE);
    private H5 status;

    private VerticalLayout layout = new VerticalLayout();

    /**
     * Sets up order item card to use on the page.
     * 
     * @param order placed within this card
     * @param orderRepo the repository of which the order is being accessed
     */
    public OrderItemCard(Order order, OrderRepository orderRepo) {
        super();
        
        layout.getStyle().set("border", "15px solid #80aaff");
        
        layout.setPadding(true);
        layout.setMargin(true);
        layout.setSpacing(true);

        this.order = order;
        this.orderRepo = orderRepo;
        this.items = order.getItems().keySet();
        this.table = order.getTableNumber();
        
        newItem.setColor("red");
        newItem.setSize("20px");
        newItem.setVisible(false);
        orderReady.setColor("green");
        orderReady.setSize("20px");
        orderReady.setVisible(false);
        
        orderTime = order.getDueTime();
        
        addInner();
        addFooter();
        add(layout);
    }

    private void addInner() {
        Div inner = new Div();
        H3 time = new H3(order.getDueTime().toString());
        
        VerticalLayout orderItems = new VerticalLayout();
        orderItems.add(time);
        
        if(table != null)
          orderItems.add(new H3("Table: " + table.getNumber()));

        for(MenuItem mi : items) {
          orderItems.add(new Label(mi.getName() + ": " + this.order.getItems().get(mi)));
        }
        
        
        HorizontalLayout statusInfo = new HorizontalLayout();
        status = new H5("Status: " + order.getStatus().toString());
        
        //Puts red exclamation mark if order is at least 10 minutes old
        if(order.getStatus() == OrderStatus.NEW)
          newItem.setVisible(true);

        statusInfo.add(status, newItem);
        inner.add(orderItems, statusInfo);
        layout.add(inner);
    }

    private void addFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        
        /**
         * Allows waiter to confirm order and updates database with new order status
         * also refreshes the page in order to update buttons and so on.
         */
        Button waiterConfirm = new Button("Confirm Order", e -> {
            
            order.setStatus(OrderStatus.CONFIRMED);
            orderRepo.save(order);
            status.setText(order.getStatus().toString());
            
            UI.getCurrent().getPage().reload();
        });
        waiterConfirm.setVisible(false);

        /**
         * Allows kitchen staff to confirm order
         */
        Button kitchenConfirm = new Button("Confirm Order", e -> {
          
          if(order.getStatus() == OrderStatus.CONFIRMED)
            order.setStatus(OrderStatus.PREPARING);
            orderRepo.save(order);
            status.setText(order.getStatus().toString());
            
          UI.getCurrent().getPage().reload();
        });
        kitchenConfirm.setVisible(false);

        /**
         * Allows kitchen staff to notify waiters that order is now ready to deliver, sets 
         * green check icon to visible
         */
        Button kitchenOrderReady = new Button("Ready", e -> {
            
          if(order.getStatus() == OrderStatus.PREPARING)
            order.setStatus(OrderStatus.READY);
            status.setText(order.getStatus().toString());
            orderRepo.save(order);
            orderReady.setVisible(true);
            
          UI.getCurrent().getPage().reload();
        });
        kitchenOrderReady.setVisible(false);

        /**
         * Waiter can mark items as delivered
         * 
         * TODO: Decide what happens with order item when marked as delivered
         */
        Button delivered = new Button("Delivered", e -> {
          
          if(order.getStatus() == OrderStatus.READY)
            order.setStatus(OrderStatus.DELIVERED);
            status.setText(order.getStatus().toString());
            order.setArchive(true);
            orderRepo.save(order);
            
          UI.getCurrent().getPage().reload();
        });
        delivered.setVisible(false);
        
        /**
         * Cancels order placed by customer.
         */
        Button cancelOrder = new Button("Delete", e -> {
          orderRepo.delete(order);
          
          UI.getCurrent().getPage().reload();
        });
        
        if(order.getStatus() == OrderStatus.READY)
          orderReady.setVisible(true);
        
        if(order.getStatus() == OrderStatus.DELIVERED)
          cancelOrder.setVisible(false);
        
        if(url.equals("/waiter")) {
          if(order.getStatus() == OrderStatus.NEW)
            waiterConfirm.setVisible(true);
          
          if(order.getStatus() == OrderStatus.READY)
            delivered.setVisible(true);
        }
        
        if(url.equals("/kitchen")) {
          cancelOrder.setVisible(false);
          
          if(order.getStatus() == OrderStatus.CONFIRMED)
            kitchenConfirm.setVisible(true);
          
          if(order.getStatus() == OrderStatus.PREPARING)
            kitchenOrderReady.setVisible(true);
        }
        
        footer.add(waiterConfirm, kitchenConfirm, kitchenOrderReady, delivered, cancelOrder, orderReady);
        layout.add(footer);
    }

}

