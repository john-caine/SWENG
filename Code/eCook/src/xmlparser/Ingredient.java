package xmlparser;
/*
 * Programmers: James & Sam
 * 
 * Description:
 * A class to hold data relating to a single ingredient
 * Includes getters and setters for name, units and amount
 * 
 * Created 29/04/2014
 * 
 */
public class Ingredient {
	String name, units = "";
	double amount = 0;
	
	public Ingredient() {
	}

	/**
	 * Get the ingredient name
	 * @return name: The ingredient name
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the Ingredient units
	 * @return units: The ingredient units
	 * 
	 */
	public String getUnits() {
		return units;
	}

	/**
	 * Get the Ingredient amount
	 * @return: The amount of ingredient
	 * 
	 */
	public double getAmount() {
		return amount;
	}
	
	/**
	 * Set ingredient name
	 * @param value: The ingredient name
	 * 
	 */
	public void setName(Object value) {
		if (value != null) {
			name = (String) value;
		}
	}
	
	/**
	 * Set ingredient units
	 * @param value: The ingredient name
	 * 
	 */
	public void setUnits(Object value) {
		if (value != null) {
			units = (String) value;
		}
	}

	/**
	 * Set ingredient amount
	 * @param value: The amount of ingredient
	 * 
	 */
	public void setAmount(Object value) {
		if (value != null) {
			//amount =  Float.valueOf((String) value);
			amount = Float.parseFloat(value.toString());

			
		}
	}
}
