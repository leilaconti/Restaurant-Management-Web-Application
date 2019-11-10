package team14.arms.backend.data.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import team14.arms.backend.data.model.*;

/**
 * An item on the menu that can be ordered.
 */
@Entity
public class MenuItem extends AbstractEntity {

    @NotBlank(message = "{arms.name.required}")
    @Size(max = 255)
    @Column(unique = true)
    private String name;

    // Actual price * 100 as an Integer (to avoid rounding errors)
    @Min(value = 0, message = "{arms.price.limits}")
    @Max(value = 100000, message = "{arms.price.limits}")

    private Integer price;
    private String description;
    private Integer calories;
    private boolean stock;
    
    @ElementCollection(targetClass = Allergen.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Allergen> allergens;
    
    @ElementCollection(targetClass = CourseType.class)
    @Enumerated(EnumType.STRING)
    private Set<CourseType> courseType;
    
    /**
     * Gets the name of the menu item.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the menu item
     * 
     * @param name of menu item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the price of the menu item.
     *
     * @return The price in pence.
     */
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * Gets the description of the menu item.
     *
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the calorie content of the menu item.
     *
     * @return The calorie content.
     */
    public Integer getCalories() {
        return calories;
    }
    
    /**
     * Gets the allergen content of the menu item.
     *
     * @return The allergen content.
     */
    public Set<Allergen> getAllergens() {
        return allergens;
    }
    
    /**
     * Sets the status of the stock of the item.
     * 
     * @param stock of item.
     */
    public void setStock(boolean stock) {
      this.stock = stock;
    }
    
    /**
     * Gets the status of the stock of the item.
     * 
     * @return stock of item.
     */
    public boolean getStock() {
      return this.stock;
    }

    @Override
    public String toString() {
        return name;
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
        MenuItem that = (MenuItem) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, price);
    }

}