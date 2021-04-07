package it.esedra.corso.journal;

/**
 * Contiene le proprietà della application Journal
 * 
 * dbpath = percorso relativo del database sqlite "journal"
 * 
 * @author bauhausk
 *
 */
public class Config {

	private String dbpath;

	public String getDbpath() {
		return dbpath;
	}

	public void setDbpath(String dbpath) {
		this.dbpath = dbpath;
	}

}
