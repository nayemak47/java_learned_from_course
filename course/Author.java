package it.esedra.corso.journal;

import javax.json.Json;
import javax.json.JsonObject;

import it.esedra.corso.collections.interfaces.DataObjectInterface;

/**
 * 
 * @author Lynda, Samuel API http://localhost/journal/author/11
 */
public class Author implements DataObjectInterface  {
	private final Integer id;
	private final String name;
	private final String email;
	

	public Author(Integer id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;

	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public JsonObject toJson() {
		return Json.createObjectBuilder().add("id", this.id).add("name", this.name).add("email", this.email).build();
	}

}