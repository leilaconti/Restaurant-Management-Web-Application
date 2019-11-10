package team14.arms.ui.page;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import team14.arms.backend.repositories.OrderRepository;
import team14.arms.backend.service.WaiterNotification;
import team14.arms.backend.data.entity.Order;
import team14.arms.ui.MainLayout;
import team14.arms.ui.components.OrderItemCard;
import team14.arms.util.Constants;

@Route(value = "waiter", layout = MainLayout.class)
public class Waiter extends VerticalLayout {
  
	WaiterNotification attention = new WaiterNotification();

  private HorizontalLayout visual = new HorizontalLayout();
	
    private OrderRepository orderRepository;

    public Waiter(OrderRepository orderRepository) {
        super();

        this.orderRepository = orderRepository;

        populateOrders();
    }

    private void populateOrders() {
    	if(attention.upset() == true) {
    		add(attention.getLabel());
    	}
    	
      for (Order order : orderRepository.findAll()) {
        visual.add(new OrderItemCard(order, orderRepository));
      }
      
      add(visual);
    }
}
