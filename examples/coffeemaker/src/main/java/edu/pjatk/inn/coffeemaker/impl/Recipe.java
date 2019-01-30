package edu.pjatk.inn.coffeemaker.impl;

import sorcer.core.context.ServiceContext;
import sorcer.service.Context;
import sorcer.service.ContextException;

import java.io.Serializable;

/**
 * @author Andrii Zinchenko s17563 Yana Lytvynenko i Denys Pogurskiy
 */
public class Recipe implements Serializable {
    private String name;
    private int price;
    private int amtCoffee;
    private int amtMilk;
    private int amtSugar;
    private int amtChocolate;

    /**
     * Recipe constructor. Set initial values
     */
    public Recipe() {
        this.name = "";
        this.price = 0;
        this.amtCoffee = 0;
        this.amtMilk = 0;
        this.amtSugar = 0;
        this.amtChocolate = 0;
    }

    /**
     * Get chokolate amount in the recipe
     * @return Returns the amtChocolate.
     */
    public int getAmtChocolate() {
        return amtChocolate;
    }

    /**
     * Set amount of the chokolate. Value must be not less than 0
     * @param amtChocolate The amtChocolate to setValue.
     */
    public void setAmtChocolate(int amtChocolate) {
        if (amtChocolate >= 0) {
            this.amtChocolate = amtChocolate;
        }
    }

    /**
     * Get coffe amount in the recipe
     * @return Returns the amtCoffee.
     */
    public int getAmtCoffee() {
        return amtCoffee;
    }

    /**
     * Set amount of the coffee. Value must be not less than 0
     * @param amtCoffee The amtCoffee to setValue.
     */
    public void setAmtCoffee(int amtCoffee) {
        if (amtCoffee >= 0) {
            this.amtCoffee = amtCoffee;
        }
    }

    /**
     * Get milk amount in the recipe
     * @return Returns the amtMilk.
     */
    public int getAmtMilk() {
        return amtMilk;
    }

    /**
     * Set amount of the milk. Value must be not less than 0
     * @param amtMilk The amtMilk to setValue.
     */
    public void setAmtMilk(int amtMilk) {
        if (amtMilk >= 0) {
            this.amtMilk = amtMilk;
        }
    }

    /**
     * Get sugar amount in the recipe
     * @return Returns the amtSugar.
     */
    public int getAmtSugar() {
        return amtSugar;
    }

    /**
     * Set amount of the sugar. Value must be not less than 0
     * @param amtSugar The amtSugar to setValue.
     */
    public void setAmtSugar(int amtSugar) {
        if (amtSugar >= 0) {
            this.amtSugar = amtSugar;
        }
    }

    /**
     * Get recipe name
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set recipe name. It must not be null value
     * @param name The name to setValue.
     */
    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    /**
     * Get recipe price
     * @return Returns the price.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Set price. Value must be not less than 0
     * @param price The price to setValue.
     */
    public void setPrice(int price) {
        if (price >= 0) {
            this.price = price;
        }
    }

    /**
     * Check if recipe names are equal
     * @param r Recipe to compare
     * @return boolean value that indicate if name is equal
     */
    public boolean equals(Recipe r) {
        if ((this.name).equals(r.getName())) {
            return true;
        }
        return false;
    }

    /**
     * Return string (recipe name) that represent recipe
     * @return recipe name
     */
    public String toString() {
        return name;
    }

    /**
     * Generate recipe using context and return it
     * @param context Recipe context
     * @return recipe object
     * @throws ContextException
     */
    static public Recipe getRecipe(Context context) throws ContextException {
        Recipe r = new Recipe();
        r.name = (String) context.getValue("name");
        r.price = (int) context.getValue("price");
        r.amtCoffee = (int) context.getValue("amtCoffee");
        r.amtMilk = (int) context.getValue("amtMilk");
        r.amtSugar = (int) context.getValue("amtSugar");
        r.amtChocolate = (int) context.getValue("amtChocolate");
        return r;
    }

    /**
     * Generate recipe context from recipe object
     * @param recipe recipe object
     * @return recipe context
     * @throws ContextException
     */
    static public Context getContext(Recipe recipe) throws ContextException {
        Context cxt = new ServiceContext();
        cxt.putValue("name", recipe.getName());
        cxt.putValue("price", recipe.getPrice());
        cxt.putValue("amtCoffee", recipe.getAmtCoffee());
        cxt.putValue("amtMilk", recipe.getAmtMilk());
        cxt.putValue("amtSugar", recipe.getAmtSugar());
        cxt.putValue("amtChocolate", recipe.getAmtChocolate());
        return cxt;
    }
}
