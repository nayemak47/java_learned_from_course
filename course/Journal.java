package it.esedra.corso.journal;

import javax.json.JsonObject;
import javax.management.RuntimeErrorException;

import it.esedra.corso.collections.interfaces.DataObjectInterface;
/**
 * Classe base dell'applicazione
 * 
 * @author bauhausk
 *
 */
public class Journal implements DataObjectInterface, Cloneable {

	private int id;
	private String name;
	private Author author;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Journal(int id, String name,Author author) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
	}

	
	public Author getAuthor() {
		return author;
	}

	public JsonObject toJson() {
		return null;
	}
	
	@Override
	public Journal clone() {
		try {
			Journal j = (Journal) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		return null;
	}
	

}
