package it.esedra.corso.journal;

public class JournalBuilder {
	
	private int id;
	private String name;
	private Author author;
	
	public JournalBuilder() {
		
	}
	
	public JournalBuilder(int id, String name, Author author) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
	}
	
	public Journal build() {
		return new Journal(id, name, author);
	}
	
	

	public Author getAuthor() {
		return author;
	}
	
	public JournalBuilder setAuthor(Author author) {
		this.author = author;
		return this;
	}

	public int getId() {
		return id;
	}

	public JournalBuilder setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public JournalBuilder setName(String name) {
		this.name = name;
		return this;
	}

}


