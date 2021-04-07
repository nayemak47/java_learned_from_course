package it.esedra.corso.journal;

import it.esedra.corso.collections.interfaces.DataObjectInterface;

/**
 * Questa classe rappresenta una immagine presente nel capitolo
 * 
 * @author bauhausk
 *
 */
abstract class MultimediaResource  {
	private final String name; // nome dell'immagine
	private final String src; // source path assoluto
	private final int id; // source path assoluto

	public MultimediaResource(int id, String src, String name) {
		this.id = id;
		this.name = name;
		this.src = src;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSrc() {
		return src;
	}

}
