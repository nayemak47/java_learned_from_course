package it.esedra.corso.journal;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonObject;

import it.esedra.corso.collections.interfaces.DataObjectInterface;

/**
 * Questa classe è un video presente nel capitolo
 * 
 *
 */
public class Video extends MultimediaResource implements DataObjectInterface  {

	private final String title;
	private int idParagraph;

	public Video(int id, String src, String name, String title, int idParagraph) {
		super(id, src, name);
		this.title = title;
		this.idParagraph = idParagraph;
	}

	public String getTitle() {
		return title;
	}
	
	public int getIdParagraph() {
		return idParagraph;
	}

	public static Config loadProperties() throws IOException {

		Properties prop = new Properties();
		prop.load(new FileInputStream("config.properties"));

		Config config = new Config();
		config.setDbpath(prop.getProperty("dbpath"));

		return config;

	}
	
	public JsonObject toJson() {
		
		return Json.createObjectBuilder()
				.add("id", super.getId())
				.add("src", super.getSrc())
				.add("name", super.getName())
				.add("title", this.title).build();
	}

}
