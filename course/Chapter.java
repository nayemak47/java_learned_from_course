package it.esedra.corso.journal;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonObject;

import it.esedra.corso.collections.interfaces.DataObjectInterface;

/**
 * Questa classe è un capitol del diario
 * 
 * 
 * 
 */
public class Chapter implements DataObjectInterface  {
	private int id;
	private  String title;
	private  String date;
	private int idJournal;

	public Chapter(int id, String title, String date, int idJournal) {
		super();
		this.id = id;
		this.title = title;
		this.date = date;
		this.idJournal = idJournal;

	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDate() {
		return date;
	}
	
	public int getIdJournal() {
		return idJournal;
	}
	public static Config loadProperties() throws IOException {

		Properties prop = new Properties();
		prop.load(new FileInputStream("config.properties"));

		Config config = new Config();
		config.setDbpath(prop.getProperty("dbpath"));

		return config;

	}
	
	public JsonObject toJson() {
		return Json.createObjectBuilder().add("id", this.id).add("title", this.title).add("date",this.date).build();
	}

}
