package team14.arms.backend.data.entity;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyClass;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import team14.arms.backend.data.model.*;

@Entity(name = "OrderInfo") // "Order" is a reserved word
public class Order extends AbstractEntity implements OrderSummary {
  
  // Actual price * 100 as an Integer (to avoid rounding errors)
  @Min(value = 0, message = "{arms.price.limits}")
  @Max(value = 100000, message = "{arms.price.limits}")
  
  private Integer price;
  private User waiter;
  private boolean archive;
  private LocalTime orderTime;
  private LocalDate orderDate;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "table_number", referencedColumnName = "number", nullable = true)
  private TableNumber table;
  
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "order_info_items")
  @MapKeyClass(value = MenuItem.class)
  @MapKeyJoinColumn(name = "item_id")
  private Map<MenuItem, Integer> items;
  
  /*
  @ElementCollection
  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
  private List<MenuItem> items;
  */
  
  @Enumerated(EnumType.STRING)
  private OrderStatus status;
  
  public Order() {
  }

  public Order(Basket items, TableNumber table, boolean archive) {
      this.items = items.getItemMap();
      this.price = items.getTotalPrice();
      this.orderTime = LocalTime.now();
      this.orderDate = LocalDate.now();
      this.table = table;
      this.status = OrderStatus.NEW;
      this.waiter = null;
      this.archive = archive;
  }
  
  public Order(Basket items, boolean archive) {
    this.items = items.getItemMap();
    this.price = items.getTotalPrice();
    this.orderTime = LocalTime.now();
    this.orderDate = LocalDate.now();
    this.table = null;
    this.status = OrderStatus.NEW;
    this.waiter = null;
    this.archive = archive;
  }

  /**
   * Returns all the menu items ordered by the customer.
   *
   * @return The menu items.
   *
  public List<MenuItem> getItems() {
      return items;
  }*/

  public Map<MenuItem, Integer> getItems() {
    return items;
}
  
  /**
   * Returns the time the order was placed.
   *
   * @return The time of order.
   */
  @Override
  public LocalTime getDueTime() {
      return orderTime;
  }
  
  /**
   * Returns the date the order was placed.
   * 
   * @return Date of order.
   */
  @Override
  public LocalDate getDueDate() {
    return orderDate;
  }

  /**
   * Returns the total price of the items ordered.
   *
   * @return The price.
   */
  @Override
  public Integer getTotalPrice() {
      return price;
  }

  /**
   * Returns the table number of the customer.
   *
   * @return The table number.
   */
  @Override
  public TableNumber getTableNumber() {
      return table;
  }

  /**
   * Gets the status of the order.
   *
   * @return The order status.
   */
  public OrderStatus getStatus() {
      return status;
  }

  /**
   * Sets the status of the order.
   *
   * @param status the new status.
   */
  public void setStatus(OrderStatus status) {
      this.status = status;
  }

  /**
   * Sets the waiter assigned to the order.
   *
   * @param waiter the waiter to assign to the order.
   */
  public void setWaiter(User waiter) {
      this.waiter = waiter;
  }

  /**
   * Sets whether the order should be archived
   * 
   * @param boolean showing whether item is archived.
   */
  public void setArchive(boolean archive) {
    this.archive = archive;
  }
  
  /**
   * Returns the orders archive status.
   * 
   * @return boolean of archive.
   */
  public boolean getArchive() {
    return this.archive;
  }
  
  @Override
  public String toString() {
      return String.format("Order[items=%s, orderTime=%s, price=%.2bd, table=%d, status=%s, waiter=%s]", items, orderTime, getTotalPrice(), table, status, waiter);
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
      if (!super.equals(o)) {
          return false;
      }
      Order that = (Order) o;
      return Objects.equals(items, that.items) &&
              Objects.equals(table, that.table);
  }

  @Override
  public int hashCode() {
      return Objects.hash(super.hashCode(), items, price);
  }
}
