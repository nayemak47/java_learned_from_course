package it.esedra.corso.journal.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;

import it.esedra.corso.helpers.PrintHelper;
import it.esedra.corso.journal.Journal;
import it.esedra.corso.journal.JournalBuilder;
import it.esedra.corso.journal.dao.JournalDao;
import it.esedra.corso.journal.db.JournalDbConnect;
import it.esedra.corso.journal.execeptions.DaoException;

public class JournalService {
	/**
	 * Gestisce la connessione dao-db dell'oggetto Json
	 * 
	 * @param json
	 * @return Journal
	 * @throws DaoException
	 */

	public static Journal update(JsonObject json) throws DaoException {
		Connection connection = JournalDbConnect.connect();
		Journal journal = new JournalBuilder().setId(json.getInt("id", -1)).setName(json.getString("name")).build();
		JournalDao journalDao = new JournalDao(journal);
		journalDao.setConnection(connection);

		return journalDao.update();
	}

	/**
	 * Restituisce tutti gli oggetti journal
	 * @return Collection<Journal>
	 * @throws DaoException
	 */
	public static List<Journal> getAll() throws DaoException {
		List<Journal> journalCollection = new ArrayList<>();

		Connection connection = null;
		try {
			// Effettua la connessione al database
			connection = JournalDbConnect.connect();
			JournalDao journalDao = new JournalDao();
			journalDao.setConnection(connection);

			// Chiamata metodo getAll() sulla Collection creata
			journalCollection = journalDao.getAll();

		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				PrintHelper.out("Errore nella chiususa della connessione");
			}
		}
		
		return journalCollection;
	}
}
