package team14.arms.backend.data.model;

import java.util.Locale;

import com.vaadin.flow.shared.util.SharedUtil;


/**
 * Represents the meal type of a {@link MenuItem}.
 * <p>
 * The meal type is the main focus of a menu item.
 */
public enum MealType {
  /**
   * <h2>Chicken</h2>
   * <p>
   * A main dish which contains chicken as its main meat or key ingredient.
   */
  CHICKEN,

  /**
   * <h2>Beef</h2>
   * <p>
   * A main dish which contains beef as its main meat or key ingredient.
   */
  BEEF,

  /**
   * <h2>Vegetarian</h2>
   * <p>
   * A dish which contains no meats inside and instead has vegetables as its main ingredient.
   */
  VEGETARIAN,

  /**
   * <h2>Pasta</h2>
   * <p>
   * A dish primarily based around pasta, this may contain meats however the focus is on pasta.
   * (e.g. carbonara)
   */
  PASTA,

  /**
   * <h2>Seafood</h2>
   * <p>
   * A dish which contains any type of fish as its main meat or key ingredient.
   */
  SEAFOOD;
  
  /**
   * Gets a version of the enum identifier in a more readable format.
   *
   * @return a more readable version of the identifier
   */
  public String getDisplayName() {
      return SharedUtil.capitalize(name().toLowerCase(Locale.ENGLISH));
  }
}
