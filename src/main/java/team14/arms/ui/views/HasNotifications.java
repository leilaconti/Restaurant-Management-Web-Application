package team14.arms.ui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.Text;

import team14.arms.ui.utils.Constants;

/**
 * This interface handles notifications displayed to the end user.
 */
public interface HasNotifications extends HasElement {

    default void showNotification(String message) {
        showNotification(message, false);
    }

    default void showNotification(String message, boolean persistent) {
        if (persistent) {
            Button close = new Button("Close");
            close.getElement().setAttribute("theme", "tertiary small error");
            Notification notification = new Notification(new Text(message), close);
            notification.setPosition(Position.BOTTOM_START);
            notification.setDuration(0);
            close.addClickListener(event -> notification.close());
            notification.open();
        } else {
            Notification.show(message, Constants.NOTIFICATION_DURATION, Position.BOTTOM_STRETCH);
        }
    }
}
