package it.esedra.corso.journal;

import javax.json.Json;
import javax.json.JsonObject;

import it.esedra.corso.collections.interfaces.DataObjectInterface;

/**
 * Questa classe rappresenta l'utente che fa login e avrà i campi: id, name,
 * surname, email, password, registration
 * 
 * @author Marco
 *
 */
public class User implements DataObjectInterface  {

	private int id;
	private final String name;
	private final String surname;
	private final String email;
	private final String password;
	private final String registration;

	public User(int id, String name, String surname, String email, String password, String registration) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.registration = registration;

	}
	
	// Getter di Id
	public int getId() {
		return id;
	}
	// Getter di Name
	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getRegistration() {
		return registration;
	}

	public JsonObject toJson() {
		return Json.createObjectBuilder().add("id", this.id).add("name", this.name).add("surname", this.surname)
				.add("email", this.email).add("password", this.password).add("registration", this.registration).build();
	}

}
