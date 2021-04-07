package it.esedra.corso.journal.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;

import it.esedra.corso.helpers.PrintHelper;
import it.esedra.corso.journal.Video;
import it.esedra.corso.journal.VideoBuilder;
import it.esedra.corso.journal.dao.VideoDao;
import it.esedra.corso.journal.db.JournalDbConnect;
import it.esedra.corso.journal.execeptions.DaoException;

public class VideoService {
	
	public static Video update(JsonObject json) throws DaoException {
		
		Connection connection = JournalDbConnect.connect();
		Video video = new VideoBuilder().setId(json.getInt("id", -1))
				.setSrc(json.getString("src"))
				.setName(json.getString("name"))
				.setTitle(json.getString("title"))
				.build();
		
		VideoDao videoDao = new VideoDao(video);
		videoDao.setConnection(connection);
		
		return videoDao.update();
		
	}
	
	public static List<Video> getAll() throws DaoException {
		List<Video> videoCollection = new ArrayList<>();

		Connection connection = null;
		try {
			
			connection = JournalDbConnect.connect();
			VideoDao videoDao = new VideoDao();
			videoDao.setConnection(connection);

			
			videoCollection = videoDao.getAll();

		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				PrintHelper.out("Errore nella chiususa della connessione");
			}
		}
		
		return videoCollection;
	}

}



