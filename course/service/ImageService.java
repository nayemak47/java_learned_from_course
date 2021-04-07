package it.esedra.corso.journal.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;

import it.esedra.corso.helpers.PrintHelper;
import it.esedra.corso.journal.Image;
import it.esedra.corso.journal.ImageBuilder;

import it.esedra.corso.journal.dao.ImageDao;

import it.esedra.corso.journal.db.JournalDbConnect;
import it.esedra.corso.journal.execeptions.DaoException;

public class ImageService {
	/**
	 * Gestisce la connessione dao-db dell'oggetto Json
	 * 
	 * @param json
	 * @return Image
	 * @throws DaoException
	 */

	public static Image update(JsonObject json) throws DaoException {
		Connection connection = JournalDbConnect.connect();
		Image image = new ImageBuilder().setId(json.getInt("id", -1)).setSrc(json.getString("src"))
				.setName(json.getString("name")).build();
		
		ImageDao imageDao = new ImageDao(image);
		imageDao.setConnection(connection);

		return imageDao.update();
	}
	
	public static List<Image> getAll() throws DaoException {
		List<Image> imageCollection = new ArrayList<>();

		Connection connection = null;
		try {
			
			connection = JournalDbConnect.connect();
			ImageDao imageDao = new ImageDao();
			imageDao.setConnection(connection);

			
			imageCollection = imageDao.getAll();

		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				PrintHelper.out("Errore nella chiusura della connessione");
			}
		}
		
		return imageCollection;
	}

}
