package it.esedra.corso.journal;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonObject;

import it.esedra.corso.collections.interfaces.DataObjectInterface;

/**
 * Questa classe rappresenta una immagine presente nel capitolo
 * 
 * @author Nayem
 *
 */
public class Image extends MultimediaResource implements DataObjectInterface  {
	
	private int idParagraph;

	public Image(int id, String src, String name,  int idParagraph) {
		super(id, src, name);
		this.idParagraph = idParagraph;

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
				.add("name", super.getName()).build();
	    }

    }
