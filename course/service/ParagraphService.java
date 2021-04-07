package it.esedra.corso.journal.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;

import it.esedra.corso.helpers.PrintHelper;
import it.esedra.corso.journal.Paragraph;
import it.esedra.corso.journal.ParagraphBuilder;
import it.esedra.corso.journal.dao.ParagraphDao;
import it.esedra.corso.journal.db.JournalDbConnect;
import it.esedra.corso.journal.execeptions.DaoException;

public class ParagraphService {

	/**
	 * Esempio di metodo gestito con transazione
	 * 
	 * @param json
	 * @return Journal
	 * @throws DaoException
	 */
	
	
	public static Paragraph update(JsonObject json) throws DaoException {
		Connection connection = JournalDbConnect.connect();
	try {
		connection.setAutoCommit(false);
		connection.setTransactionIsolation(connection.TRANSACTION_SERIALIZABLE); // isolamento totale		
		Paragraph paragraph = new ParagraphBuilder().setId(json.getInt("id", -1))
				.setText(json.getString("text")).build();
				
		ParagraphDao paragraphDao = new ParagraphDao(paragraph);
		paragraphDao.setConnection(connection);

		return paragraphDao.update();
		} catch (Exception e) {
	try {
		connection.rollback();
	} catch (SQLException e1) {
		throw new DaoException("Paragraph Service Rollback Error.", e1);
	}
	throw new DaoException("Paragraph Service Error.", e);
	}
		
	}
	/**
	 * Restituisce tutti gli oggetti journal
	 * @return Collection<Journal>
	 * @throws DaoException
	 */
	public static List<Paragraph> getAll() throws DaoException {
		List<Paragraph> paragraphCollection = new ArrayList<>();

		Connection connection = null;
		try {
			// Effettua la connessione al database
			connection = JournalDbConnect.connect();
			ParagraphDao paragraphDao = new ParagraphDao();
			paragraphDao.setConnection(connection);

			// Chiamata metodo getAll() sulla Collection creata
			paragraphCollection = paragraphDao.getAll();

		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				PrintHelper.out("Errore nella chiususa della connessione");
			}
		}
		
		return paragraphCollection;
	}

}
