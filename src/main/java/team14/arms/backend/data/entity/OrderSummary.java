package team14.arms.backend.data.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import team14.arms.backend.data.model.OrderStatus;

/**
 * Gives a brief and concise summary of the order.
 */
public interface OrderSummary {

    Long getId();

    OrderStatus getStatus();

    LocalDate getDueDate();

    LocalTime getDueTime();

    TableNumber getTableNumber();

    Integer getTotalPrice();

}
