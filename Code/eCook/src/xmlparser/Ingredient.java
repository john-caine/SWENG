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

	/*
	 * Ingredient name
	 * Return: String
	 * 
	 */
	public String getName() {
		return name;
	}

	/*
	 * Ingredient units
	 * Return: String
	 * 
	 */
	public String getUnits() {
		return units;
	}

	/*
	 * Ingredient amount
	 * Return: String
	 * 
	 */
	public double getAmount() {
		return amount;
	}
	
	/*
	 * Set ingredient name
	 * Input: Object (String)
	 * 
	 */
	public void setName(Object value) {
		if (value != null) {
			name = (String) value;
		}
	}
	
	/*
	 * Set ingredient units
	 * Input: Object (String)
	 * 
	 */
	public void setUnits(Object value) {
		if (value != null) {
			units = (String) value;
		}
	}

	/*
	 * Set ingredient amount
	 * Input: Object (String)
	 * 
	 */
	public void setAmount(Object value) {
		if (value != null) {
			//amount =  Float.valueOf((String) value);
			amount = Float.parseFloat(value.toString());

			
		}
	}
}
