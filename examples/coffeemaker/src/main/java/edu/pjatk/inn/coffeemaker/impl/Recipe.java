package edu.pjatk.inn.coffeemaker.impl;

import sorcer.core.context.ServiceContext;
import sorcer.service.Context;
import sorcer.service.ContextException;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Class that represents model of recipe.
 * Sets or returns amount of each requested ingredient that is contained in recipe.
 * @author   Sarah & Mike
 */
public class Recipe implements Serializable {

    private String name;
    private int price;
    private int amtCoffee;
    private int amtMilk;
    private int amtSugar;
    private int amtChocolate;
    
    public Recipe() {
    	this.name = "";
    	this.price = 0;
    	this.amtCoffee = 0;
    	this.amtMilk = 0;
    	this.amtSugar = 0;
    	this.amtChocolate = 0;
    }
    
    /**
	 * Returns amount of chocolate for this recipe.
	 * @return   Returns the amtChocolate.
	 */
    public int getAmtChocolate() {
		return amtChocolate;
	}
    /**
	 * Sets amount of chocolate for this recipe.
	 * @param amtChocolate   The amtChocolate to setValue.
	 */
    public void setAmtChocolate(int amtChocolate) {
		if (amtChocolate >= 0) {
			this.amtChocolate = amtChocolate;
		} 
	}
    /**
	 * Returns amount of coffee for this recipe.
	 * @return   Returns the amtCoffee.
	 */
    public int getAmtCoffee() {
		return amtCoffee;
	}
    /**
	 * Sets amount of coffee for this recipe.
	 * @param amtCoffee   The amtCoffee to setValue.
	 */
    public void setAmtCoffee(int amtCoffee) {
		if (amtCoffee >= 0) {
			this.amtCoffee = amtCoffee;
		} 
	}
    /**
	 * Returns amount of milk for this recipe.
	 * @return   Returns the amtMilk.
	 */
    public int getAmtMilk() {
		return amtMilk;
	}
    /**
	 * Sets amount of milk for this recipe.
	 * @param amtMilk   The amtMilk to setValue.
	 */
    public void setAmtMilk(int amtMilk) {
		if (amtMilk >= 0) {
			this.amtMilk = amtMilk;
		} 
	}
    /**
	 * Returns amount of sugar for this recipe.
	 * @return   Returns the amtSugar.
	 */
    public int getAmtSugar() {
		return amtSugar;
	}
    /**
	 * Sets amount of sugar for this recipe.
	 * @param amtSugar   The amtSugar to setValue.
	 */
    public void setAmtSugar(int amtSugar) {
		if (amtSugar >= 0) {
			this.amtSugar = amtSugar;
		} 
	}
    /**
	 * Returns name of this recipe.
	 * @return   Returns the key.
	 */
    public String getName() {
		return name;
	}
    /**
	 * Sets name for this recipe.
	 * @param name   The key to setValue.
	 */
    public void setName(String name) {
    	if(name != null) {
    		this.name = name;
    	}
	}
    /**
	 * Returns price of this recipe.
	 * @return   Returns the price.
	 */
    public int getPrice() {
		return price;
	}
    /**
	 * Sets price for this recipe.
	 * @param price   The price to setValue.
	 */
    public void setPrice(int price) {
		if (price >= 0) {
			this.price = price;
		} 
	}

	/**
	 * Checks if this recipe is equals to selected recipe.
	 * @return  Equivalence.
	 */
    public boolean equals(Recipe r) {
        if((this.name).equals(r.getName())) {
            return true;
        }
        return false;
    }

	/**
	 * Overrides method toString() as name of this recipe .
	 * @return  Name of this recipe.
	 */
    @Override
    public String toString() {
    	return name;
    }
	/**
	 * Returns all values of this recipe or throws exception.
	 * @return  Values of Recipe.
	 */
	public static Recipe getRecipe(Context context) throws ContextException {
		Recipe r = new Recipe();
		try {
			r.name = (String)context.getValue("key");
			r.price = (int)context.getValue("price");
			r.amtCoffee = (int)context.getValue("amtCoffee");
			r.amtMilk = (int)context.getValue("amtMilk");
			r.amtSugar = (int)context.getValue("amtSugar");
			r.amtChocolate = (int)context.getValue("amtChocolate");
		} catch (RemoteException e) {
			throw new ContextException(e);
		}
		return r;
	}
	/**
	 * Returns context of all values of this recipe or throws exception.
	 * @return  Values' context of Recipe.
	 */
	public static Context getContext(Recipe recipe) throws ContextException {
		Context cxt = new ServiceContext();
		cxt.putValue("key", recipe.getName());
		cxt.putValue("price", recipe.getPrice());
		cxt.putValue("amtCoffee", recipe.getAmtCoffee());
		cxt.putValue("amtMilk", recipe.getAmtMilk());
		cxt.putValue("amtSugar", recipe.getAmtSugar());
		cxt.putValue("amtChocolate", recipe.getAmtChocolate());
		return cxt;
	}
}
