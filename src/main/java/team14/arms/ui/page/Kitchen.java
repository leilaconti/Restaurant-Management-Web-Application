package team14.arms.ui.page;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import team14.arms.backend.repositories.OrderRepository;
import team14.arms.backend.data.entity.Order;
import team14.arms.ui.MainLayout;
import team14.arms.ui.components.OrderItemCard;
import team14.arms.util.Constants;

@Route(value = Constants.Pages.KITCHEN, layout = MainLayout.class)
public class Kitchen extends VerticalLayout {
  private OrderRepository orderRepository;
  private HorizontalLayout visual = new HorizontalLayout();

  public Kitchen(OrderRepository orderRepository) {
      super();

      this.orderRepository = orderRepository;

      populateOrders();
  }

  private void populateOrders() {
      for (Order order : orderRepository.findAll()) {
          OrderItemCard temp = new OrderItemCard(order, orderRepository);
          visual.add(temp);
          if(order.getArchive())
            temp.setVisible(false);
      }
      add(visual);
  }
}
