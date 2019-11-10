package team14.arms.backend.data.model;

import java.util.Locale;

import com.vaadin.flow.shared.util.SharedUtil;

/**
 * Allergens recognised by the <a href="https://food.gov.uk/">Food Standards Agency (FSA)</a>.
 */
public enum Allergen {
  /**
   * <h2>Celery</h2>
   * <p>
   * This includes celery stalks, leaves, seeds and the root called celeriac. You can find celery
   * in celery salt, salads, some meat products, soups and stock cubes.
   */
  CELERY,
  /**
   * <h2>Cereal Containing Gluten</h2>
   * <p>
   * Wheat (such as spelt and Khorasan wheat/Kamut), rye, barley, and oats is often found in foods
   * containing flour, such as some types of baking powder, batter, breadcrumbs, bread, cakes,
   * couscous, meat products, pasta, pastry, sauces, soups and fried foods which are dusted with
   * flour.
   */
  GLUTEN,
  /**
   * <h2>Crustaceans</h2>
   * <p>
   * Crabs, lobster, prawns and scampi are crustaceans. Shrimp paste, often used in Thai and
   * south-east Asian curries or salads, is an ingredient to look out for.
   */
  CRUSTACEANS,
  /**
   * <h2>Eggs</h2>
   * <p>
   * Eggs are often found in cakes, some meat products, mayonnaise, mousses, pasta, quiche, sauces
   * and pastries or foods brushed or glazed with egg.
   */
  EGGS,
  /**
   * <h2>Fish</h2>
   * <p>
   * You will find this in some fish sauces, pizzas, relishes, salad dressings, stock cubes and
   * Worcestershire sauce.
   */
  FISH,
  /**
   * <h2>Lupin</h2>
   * <p>
   * Yes, lupin is a flower, but it’s also found in flour! Lupin flour and seeds can be used in
   * some types of bread, pastries and even in pasta.
   */
  LUPIN,
  /**
   * <h2>Milk</h2>
   * <p>
   * Milk is a common ingredient in butter, cheese, cream, milk powders and yoghurt. It can also
   * be found in foods brushed or glazed with milk, and in powdered soups and sauces.
   */
  MILK,
  /**
   * <h2>Molluscs</h2>
   * <p>
   * These include mussels, land snails, squid and whelks, but can also be commonly found in
   * oyster sauce or as an ingredient in fish stews.
   */
  MOLLUSCS,
  /**
   * <h2>Mustard</h2>
   * <p>
   * Liquid mustard, mustard powder and mustard seeds fall into this category. This ingredient can
   * also be found in breads, curries, marinades, meat products, salad dressings, sauces and
   * soups.
   */
  MUSTARD,
  /**
   * <h2>Nuts</h2>
   * <p>
   * Not to be mistaken with peanuts (which are actually a legume and grow underground), this
   * ingredient refers to nuts which grow on trees, like cashew nuts, almonds and hazelnuts. You
   * can find nuts in breads, biscuits, crackers, desserts, nut powders (often used in Asian
   * curries), stir-fried dishes, ice cream, marzipan (almond paste), nut oils and sauces.
   */
  NUTS,
  /**
   * <h2>Peanuts</h2>
   * <p>
   * Peanuts are actually a legume and grow underground, which is why it’s sometimes called a
   * groundnut. Peanuts are often used as an ingredient in biscuits, cakes, curries, desserts,
   * sauces (such as satay sauce), as well as in groundnut oil and peanut flour.
   */
  PEANUTS,
  /**
   * <h2>Sesame Seeds</h2>
   * <p>
   * These seeds can often be found in bread (sprinkled on hamburger buns for example),
   * breadsticks, houmous, sesame oil and tahini. They are sometimes toasted and used in salads.
   */
  SESAME_SEEDS,
  /**
   * <h2>Soya</h2>
   * <p>
   * Often found in bean curd, edamame beans, miso paste, textured soya protein, soya flour or
   * tofu, soya is a staple ingredient in oriental food. It can also be found in desserts, ice
   * cream, meat products, sauces and vegetarian products.
   */
  SOYA,
  /**
   * <h2>Sulphur Dioxide (Sulphites)</h2>
   * <p>
   * This is an ingredient often used in dried fruit such as raisins, dried apricots and prunes.
   * You might also find it in meat products, soft drinks, vegetables as well as in wine and beer.
   * If you have asthma, you have a higher risk of developing a reaction to sulphur dioxide.
   */
  SULPHUR_DIOXIDE;
  
  /**
   * Gets a version of the enum identifier in a more readable format.
   *
   * @return a more readable version of the identifier
   */
  public String getDisplayName() {
      return SharedUtil.capitalize(name().toLowerCase(Locale.ENGLISH));
  }
}
