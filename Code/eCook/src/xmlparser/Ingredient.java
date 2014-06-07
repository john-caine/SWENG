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

	// getters
	public String getName() {
		return name;
	}

	public String getUnits() {
		return units;
	}

	public double getAmount() {
		return amount;
	}
	
	// setters
	public void setName(Object value) {
		if (value != null) {
			name = (String) value;
		}
	}

	public void setUnits(Object value) {
		if (value != null) {
			units = (String) value;
		}
	}

	public void setAmount(Object value) {
		if (value != null) {
			//amount =  Float.valueOf((String) value);
			amount = Float.parseFloat(value.toString());

			
		}
	}
}
