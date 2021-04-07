package it.esedra.corso.journal.execeptions;

public class DaoException extends Exception {

	/**
	 * Un costruttore senza parametri
	 */
	public DaoException() {

	}
	/**
	 * Un costruttore con un parametro di tipo String
	 * @param message messaggio di errore
	 */
	public DaoException(String message) {
		super(message);
	}

	/**
	 * Un costruttore con due parametri , il primo di tipo string 
	 * (che fornisce il messaggio di errore)e il secondo 
	 * di tipo Throwable che fornisce un oggetto che rappresenta la eccezione causa
	 * @param message messaggio di errore
	 * @param cause eccezione causa
	 * 
	 */
	public DaoException(String message, Throwable cause) {
		super(message);
	}
}
