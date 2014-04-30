package xmlparser;
/*
 * Programmers: James Sam
 * 
 * Description:
 * A class to hold data relating to a single ingredient
 * Includes getters and setters for name, units and amount
 * 
 * Created 29/04/2014
 * 
 */
public class Ingredient {
	private String name;
	private String units;
	private Float amount;
	
	public Ingredient() {
	}

	// getters
	public String getName() {
		return name;
	}

	public String getUnits() {
		return units;
	}

	public float getAmount() {
		return amount;
	}
	
	// setters
	public void setName(Object value) {
		name = (String) value;
	}

	public void setUnits(Object value) {
		units = (String) value;
	}

	public void setAmount(Object value) {
		amount =  Float.valueOf((String) value);
	}
}
