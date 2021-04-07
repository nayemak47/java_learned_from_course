package it.esedra.corso.journal;

public class UserBuilder {

	private int id;
	private String name;
	private String surname;
	private String email;
	private String password;
	private String registration;

	public UserBuilder() {

	}

	public UserBuilder(int id, String name, String surname, String email, String password, String registration) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.registration = registration;

	}

	public User build() {
		return new User(id, name, surname, email, password, registration);
	}

	public int getId() {
		return id;
	}

	public UserBuilder setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public UserBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public String getSurname() {
		return surname;
	}

	public UserBuilder setSurname(String surname) {
		this.surname = surname;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserBuilder setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public UserBuilder setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getRegistration() {
		return registration;
	}

	public UserBuilder setRegistration(String registration) {
		this.registration = registration;
		return this;
	}

}
