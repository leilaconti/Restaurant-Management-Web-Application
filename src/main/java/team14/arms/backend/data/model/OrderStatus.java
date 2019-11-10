package team14.arms.backend.data.model;

import java.util.Locale;

import com.vaadin.flow.shared.util.SharedUtil;

/**
 * Represents the status of an {@link Order}
 */
public enum OrderStatus {

    /**
     * <h2>New</h2>
     * <p>New order submitted.</p>
     */
    NEW,

    /**
     * <h2>Confirmed</h2>
     * <p>Order has been confirmed.</p>
     */
    CONFIRMED,

    /**
     * <h2>Preparing</h2>
     * <p>Order is being prepared.</p>
     */
    PREPARING,

    /**
     * <h2>Ready</h2>
     * <p>Order is ready to be delivered.</p>
     */
    READY,

    /**
     * <h2>Delivered</h2>
     * <p>Order has been delivered.</p>
     */
    DELIVERED,

    /**
     * <h2>Problem</h2>
     * <p>There is a problem with the order.</p>
     */
    PROBLEM,

    /**
     * <h2>Cancelled</h2>
     * <p>Order has been cancelled.</p>
     */
    CANCELLED;

    /**
     * Gets a version of the enum identifier in a more readable format.
     *
     * @return a more readable version of the identifier
     */
    public String getDisplayName() {
        return SharedUtil.capitalize(name().toLowerCase(Locale.ENGLISH));
    }

}
