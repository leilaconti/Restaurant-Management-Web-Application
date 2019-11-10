package team14.arms.ui.components;

import java.util.Set;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout.Orientation;
import com.vaadin.flow.server.VaadinService;

import team14.arms.backend.data.entity.*;
import team14.arms.backend.data.model.*;
import team14.arms.backend.repositories.MenuItemRepository;

public class MenuItemCard extends VerticalLayout {

    private MenuItem menuItem;
    private MenuItemRepository menuItemRepository;
    private String url = (VaadinService.getCurrentRequest().getPathInfo());
    private Basket basket;
    private Set<Allergen> allergyList;
    private String allergenNames = "";
    private Label calories =  new Label();
    private Label allergens = new Label();
    private Label description = new Label();

    private NumberField count = new NumberField(0);
    private VerticalLayout layout = new VerticalLayout();


    public MenuItemCard(MenuItem menuItem, Basket basket, MenuItemRepository menuItemRepository) {
        super();

      	layout.getStyle().set("border", "15px solid #80aaff");
      	
      	layout.setPadding(true);
      	layout.setMargin(true);
      	layout.setSpacing(true);

        this.menuItem = menuItem;
        this.basket = basket;
        this.menuItemRepository = menuItemRepository;
        
        this.allergyList = menuItem.getAllergens();
        this.count.setWidth("50px");
        this.calories.setText("Calories: " + menuItem.getCalories().toString());
        this.allergens.setText(" - Allergens: None");
        this.description.setText(menuItem.getDescription());

        addInner();
        addFooter();
        addToMenu();
        add(layout);
    }

    private void addInner() {
        Div inner = new Div();

        Div image = new Div();
        Image img = new Image("/001pozole.jpg", "Missing image");
        image.add(img);
        
        Icon itemInfo = new Icon(VaadinIcon.INFO_CIRCLE_O);
        Button infoButton = new Button(itemInfo);
        Button close = new Button("close");
        
        SplitLayout innerLayout = new SplitLayout(); 
        innerLayout.setOrientation(Orientation.VERTICAL);
        innerLayout.addToPrimary(description);
        Div innerNotif = new Div();
        innerNotif.add(calories, allergens);
        innerLayout.addToSecondary(innerNotif);
        Notification infoPopUp = new Notification(innerLayout, close);
        close.addClickListener(event -> {
          infoPopUp.close();
        });
        infoButton.addClickListener(event -> {
          if(menuItem.getAllergens() != null)
            for(Allergen a : allergyList) {
              allergenNames += a.getDisplayName() + ", ";
            }
          
            this.allergens.setText(" - Allergens: " + allergenNames);
          infoPopUp.open();
        });
        
        infoPopUp.setPosition(Position.MIDDLE);

        VerticalLayout details = new VerticalLayout();
        HorizontalLayout info = new HorizontalLayout();
        
        H6 name = new H6(menuItem.getName());
        Label price = new Label(String.format("£%s", menuItem.getPrice().toString()));
        
        info.add(name, infoButton, infoPopUp);
        details.add(info, price);
        inner.add(image, details);
        layout.add(inner);
    }

    private void addFooter() {
        HorizontalLayout footer = new HorizontalLayout();

        Button add = new Button("+", evt -> {
            int value = count.getNumValue() + 1;
            count.setNumValue(value >= 0 ? value : Integer.MAX_VALUE);
            
            basket.increase(menuItem);
        });
        Button sub = new Button("-", evt -> {
            int value = count.getNumValue() - 1;
            count.setNumValue(value >= 0 ? value : 0);
            
            basket.decrease(menuItem);
        });
        
        footer.add(add, count, sub);
        layout.add(footer);
    }

    private void addToMenu() {
      HorizontalLayout basketLayout = new HorizontalLayout();
      
      Div remover = new Div();

      Notification notification = new Notification();
      HorizontalLayout notifButtons = new HorizontalLayout();
      Label context = new Label();
      Label yesNo = new Label();
      
      Button decide = new Button(yesNo, e -> {
        notification.open();
      });
      
      notification.setPosition(Position.MIDDLE);
      
      if(menuItem.getStock()) {
        yesNo.setText("Remove");
        removeItem(notification, context, notifButtons);
      } else {
        yesNo.setText("Add");
        addItem(notification, context, notifButtons);
      }
      
      remover.add(decide);
      remover.setVisible(false);
      
      if(url.equals("/waiteredit"))
        remover.setVisible(true);

      basketLayout.add(remover);
      add(basketLayout);
  }
  
    public void removeItem(Notification notification, Label context, HorizontalLayout notifButtons) {
      context.setText("Are you sure you want to remove this item?");
      Button close = new Button("No", e ->{        
        notification.close();
      });
      Button yes = new Button("Yes", e ->{
        if(this.menuItem.getStock())
          this.menuItem.setStock(false);
          
        menuItemRepository.save(this.menuItem);
        notification.close();
      });
      notifButtons.add(yes, close);
      
      notification.add(context, notifButtons);
    }
    
    public void addItem(Notification notification, Label context, HorizontalLayout notifButtons) {
      context.setText("Are you sure you want to add this item?");
      Button close = new Button("No", e ->{
        notification.close();
      });
      Button yes = new Button("Yes", e ->{
        if(!this.menuItem.getStock())
          this.menuItem.setStock(true);
          
        menuItemRepository.save(this.menuItem);
        notification.close();
      });
      notifButtons.add(yes, close);
      
      notification.add(context, notifButtons);
  }


}