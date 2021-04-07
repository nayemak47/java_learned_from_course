package it.esedra.corso.journal.dao;

import java.sql.Connection;
import java.util.List;

import it.esedra.corso.journal.execeptions.DaoException;

/**
 * Questa è una semplice implementazione del DAO Pattern in Java
 * 
 * @author bauhausk
 *
 * @param <T>
 */
public interface DaoInterface<T> {

	/**
	 * Imposta una connessione
	 * 
	 * @param con
	 */
	void setConnection(Connection con);

	/**
	 * Aggiorna o inserisce un record Si verifica l'esistenza del record tramite
	 * query su chiave primaria Object.get() Nel caso il record sia presente
	 * eseguiamo un update altrimenti un insert Utiliziamo un oggetto
	 * PreparedStatement per gestire gli SQL statement precompilati Restituisce il
	 * numero di righe interessate all'aggiornamento (affected) In caso di insert
	 * valorizzera automaticamente la proprietà chiave (database generated key)
	 */
	T update() throws DaoException;

	/**
	 * Elimina un record
	 * 
	 * 
	 */
	boolean delete() throws DaoException;

	/**
	 * Restituisce tutti i record della tabella
	 * 
	 * @return T
	 */
	List<T> getAll() throws DaoException;

	/**
	 * Restituisce tutti i record della tabella
	 * 
	 * @return T
	 */
	T get() throws DaoException;

}
