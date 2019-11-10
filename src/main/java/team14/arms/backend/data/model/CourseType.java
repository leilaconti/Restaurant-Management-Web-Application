package team14.arms.backend.data.model;

import java.util.Locale;

import com.vaadin.flow.shared.util.SharedUtil;


/**
 * Represents the course type of a {@link MenuItem}.
 */
public enum CourseType {
    /**
     * <h2>Appetiser</h2>
     * <p>
     * All entree dishes which are eaten as the beginning of the meal.
     */
    APPETISER,

    /**
     * <h2>Main</h2>
     * <p>
     * Main course dishes served at the heart of the meal.
     */
    MAIN,

    /**
     * <h2>Desert</h2>
     * <p>
     * Sweet dishes served at the end of the meal.
     */
    DESSERT,

    /**
     * <h2>Side</h2>
     * <p>
     * Side dishes which complement other dishes. Can be served at any time.
     */
    SIDE,

    /**
     * <h2>Drink</h2>
     * <p>
     * A drink to go with a meal.
     */
    DRINK;
    
    /**
     * Gets a version of the enum identifier in a more readable format.
     *
     * @return a more readable version of the identifier
     */
    public String getDisplayName() {
        return SharedUtil.capitalize(name().toLowerCase(Locale.ENGLISH));
    }
}
