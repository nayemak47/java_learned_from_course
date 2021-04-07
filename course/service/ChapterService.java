package it.esedra.corso.journal.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;

import it.esedra.corso.helpers.PrintHelper;
import it.esedra.corso.journal.Chapter;
import it.esedra.corso.journal.ChapterBuilder;
import it.esedra.corso.journal.dao.ChapterDao;
import it.esedra.corso.journal.db.JournalDbConnect;
import it.esedra.corso.journal.execeptions.DaoException;

public class ChapterService {
	/**
	 * Gestisce la connessione dao-db dell'oggetto Json
	 * 
	 * @param json
	 * @return Chapter
	 * @throws DaoException
	 */
	
	public static Chapter update(JsonObject json) throws DaoException {
		Connection connection = JournalDbConnect.connect();
		Chapter chapter = new ChapterBuilder()
				.setId(json.getInt("id", -1))
				.setTitle(json.getString("title"))
				.setDate(json.getString("date"))
				.build();
		
		ChapterDao chapterDao = new ChapterDao(chapter);
		chapterDao.setConnection(connection);
		
		return chapterDao.update();
	}
	
	/**
	 * Restituisce tutti gli oggetti chapter
	 * @return Collection<Chapter>
	 * @throws DaoException
	 */
	public static List<Chapter> getAll() throws DaoException {
		List<Chapter> chapterCollection = new ArrayList<>();

		Connection connection = null;
		try {
			// Effettua la connessione al database
			connection = JournalDbConnect.connect();
			ChapterDao chapterDao = new ChapterDao();
			chapterDao.setConnection(connection);

			// Chiamata metodo getAll() sulla Collection creata
			chapterCollection = chapterDao.getAll();

		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				PrintHelper.out("Errore nella chiususa della connessione");
			}
		}
		
		return chapterCollection;
	}

}


