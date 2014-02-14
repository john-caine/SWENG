import java.io.Serializable;

public class Recipe implements Serializable {
	private static final long serialVersionUID = 1L;
	String id, title, chef, description;
	// String for now, should be int?
	String people, time;
		
	public Recipe() {
	}

	public String getID() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getPeople() {
		return people;
	}
	
	public String getTime() {
		return time;
	}
	
	public String getChef() {
		return chef;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setID(String id) {
		this.id = id;		
	}
	
	public void setTitle(String title) {
		this.title = title;		
	}

	public void setPeople(String people) {
		this.people = people;		
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public void setChef(String chef) {
			this.chef = chef;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}