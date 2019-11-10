package team14.arms.backend.data.model;

import team14.arms.backend.data.entity.MenuItem;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents a basket of menu items.
 * <p>
 * Wraps a map of {@link MenuItem} to {@link Integer}, handling operations and null safety.
 */
public class Basket {

    private Map<MenuItem, Integer> items = new TreeMap<>();

    /**
     * Checks if the basket contains the specified menu item.
     *
     * @param item the menu item to check for.
     *
     * @return {@code true} if the basket contains the menu item.
     *
     * @throws NullPointerException If the specified menu item is {@code null}.
     */
    public boolean contains(MenuItem item) {
        return items.containsKey(item);
    }

    /**
     * Removes the menu item from the basket, if present.
     * <p>
     * Returns the number of menu items removed.
     *
     * @param item the menu item to remove.
     *
     * @return The number of menu items removed from the basket.
     *
     * @throws NullPointerException If the specified menu item is {@code null}.
     */
    public int remove(MenuItem item) {
        return getOrZero(items.remove(item));
    }

    /**
     * Adds {@code count} of the specified menu item to the basket.
     *
     * @param item  the menu item to add.
     * @param count the number of items to add.
     *
     * @throws NullPointerException If the specified menu item is {@code null}.
     */
    public void add(MenuItem item, int count) {
        // Fetch current number of item and increment by count.
        items.compute(item, (i, n) -> getOrZero(n) + count);
    }
    

    /**
     * Removes the menu item from the basket once, if present.
     * <p>
     *
     * @param item the menu item to remove.
     */
    public void decrease(MenuItem item) {
        items.compute(item, (i, n) -> getOrZero(n) - 1);
    }
    
    /**
     * Adds the menu item from the basket once.
     * <p>
     *
     * @param item the menu item to add.
     */
    public void increase(MenuItem item) {
        items.compute(item, (i, n) -> getOrZero(n) + 1);
    }

    /**
     * Gets the number of the specified menu item in the basket.
     *
     * @param item the menu item to count.
     *
     * @return The number of menu items in the basket.
     *
     * @throws NullPointerException If the specified menu item is {@code null}.
     */
    public int get(MenuItem item) {
        return items.get(item);
    }

    /**
     * Removes all items from the basket.
     */
    public void clear() {
        items.clear();
    }

    /**
     * Gets the price for the total number of the specified menu item in the basket.
     *
     * @param item the menu item.
     *
     * @return The number of menu items in the basket.
     *
     * @throws NullPointerException If the specified menu item is {@code null}.
     */
    public Integer getPrice(MenuItem item) {
        return item.getPrice()*get(item);
    }

    /**
     * Gets the total price for all the menu items in the basket.
     *
     * @return The total price of the basket.
     */
    public Integer getTotalPrice() {
        return items.entrySet().parallelStream()
            .map(e -> e.getKey().getPrice()*(e.getValue()))
            .reduce(0, Integer::sum);
    }

    /**
     * Gets a map of menu items in the basket.
     *
     * @return An unmodifiable map of menu items in the basket.
     */
    public Map<MenuItem, Integer> getItemMap() {
        return Collections.unmodifiableMap(items);
    }
    
    /**
     * Gets the total number of items in the basket.
     * 
     * @return Total number of items.
     */
    public int getTotalItems() {
      return items.size();
    }
    
    /**
     * Checks if basket contains any items
     * 
     * @return True if basket is empty.
     */
    public boolean isEmpty() {
      return items.isEmpty();
    }

    /**
     * Utility method for unboxing a nullable Integer.
     */
    private static int getOrZero(Integer n) {
        return n != null ? n : 0;
    }
}

