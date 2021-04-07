package it.esedra.corso.journal;


public class AuthorBuilder {

	private Integer id;
	private String name;
	private String email;

	public AuthorBuilder() {
	}

	public AuthorBuilder(Integer id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;

	}

	public Author build() {
		return new Author(id, name, email);
	}

	public Integer getId() {
		return id;
	}

	public AuthorBuilder setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public AuthorBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public AuthorBuilder setEmail(String email) {
		this.email = email;
		return this;
	}

}
