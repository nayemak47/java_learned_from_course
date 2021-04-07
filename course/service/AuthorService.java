package it.esedra.corso.journal.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;

import it.esedra.corso.helpers.PrintHelper;
import it.esedra.corso.journal.Author;
import it.esedra.corso.journal.AuthorBuilder;
import it.esedra.corso.journal.dao.AuthorDao;
import it.esedra.corso.journal.db.JournalDbConnect;
import it.esedra.corso.journal.execeptions.DaoException;

public class AuthorService {
	/**
	 * Esempio di metodo gestito con transazione
	 * 
	 * @param json
	 * @return Journal
	 * @throws DaoException
	 */

	public static Author update(JsonObject json) throws DaoException {
		Connection connection = JournalDbConnect.connect();
		try {
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(connection.TRANSACTION_SERIALIZABLE); // isolamento totale
			Author author = new AuthorBuilder().setId(json.getInt("id", -1)).setName(json.getString("name"))
					.setEmail(json.getString("email")).build();
			AuthorDao authorDao = new AuthorDao(author);
			authorDao.setConnection(connection);

			Author updateAuthor = authorDao.update();
			connection.commit();
			return updateAuthor;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new DaoException("Author Service Rollback Error.", e1);
			}
			throw new DaoException("Author Service Error.", e);
		}
	}

	/**
	 * Restituisce tutti gli oggetti journal
	 * 
	 * @return Collection<Journal>
	 * @throws DaoException
	 */
	public static List<Author> getAll() throws DaoException {
		List<Author> authorCollection = new ArrayList<>();

		Connection connection = null;
		try {
			// Effettua la connessione al database
			connection = JournalDbConnect.connect();
			AuthorDao authorDao = new AuthorDao();
			authorDao.setConnection(connection);

			// Chiamata metodo getAll() sulla Collection creata
			authorCollection = authorDao.getAll();

		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				PrintHelper.out("Errore nella chiususa della connessione");
			}
		}

		return authorCollection;
	}

}
