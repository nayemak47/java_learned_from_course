package it.esedra.corso.journal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import it.esedra.corso.journal.Video;
import it.esedra.corso.journal.VideoBuilder;
import it.esedra.corso.journal.execeptions.DaoException;

public class VideoDao implements DaoInterface<Video> {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private Video video;
	private Connection conn;

	public VideoDao() {

	}

	public VideoDao(Video video) {
		super();
		this.video = video;
	}

	@Override
	public boolean delete() throws DaoException {

		boolean success = true;
		

		try {
			Statement stm = this.conn.createStatement();
			int rs = stm.executeUpdate("DELETE FROM video WHERE id = " + this.video.getId());

			if (rs > 0) {
				success = true;
			}
		} catch (Exception e) {
			LOGGER.severe(e.toString());
		}

		return success;

	}

	@Override
	public List<Video> getAll() throws DaoException {
		List<Video> videos = new ArrayList<>();
		try {
			Statement stm = this.conn.createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM video");

			while (rs.next()) {

				Video video = new VideoBuilder()
						.setId(rs.getInt("id"))
						.setSrc(rs.getString("src"))
						.setName(rs.getString("name"))
						.setTitle(rs.getString("title"))
						.setIdParagraph(rs.getInt("id_paragraph"))
						.build();

				videos.add(video);
			}
			rs.close();
		} catch (Exception e) {
			LOGGER.severe(e.toString());
		}

		return videos;

	}

	@Override
	public Video update() throws DaoException {

		if (video == null) {
			throw new DaoException("video non può essere null.");
		}
		
		if(video.getIdParagraph() == 0) {
			throw new DaoException("inserire un id_paragraph valido.");
		}
		
		Video videoCheck = this.get();
		Video copy = null;

		try {

			if (videoCheck != null) {
				String sql = "UPDATE video SET src= ?, name= ?, title= ?, id_paragraph= ?  WHERE id = ? ;";
				PreparedStatement stm = this.conn.prepareStatement(sql);

				stm.setString(1, video.getSrc());
				stm.setString(2, video.getName());
				stm.setString(3, video.getTitle());
				stm.setInt(4, video.getIdParagraph());
				stm.setInt(5, video.getId());

				if (stm.executeUpdate() > 0) {
					stm.close();

				}
				copy = new VideoBuilder()
						.setId(video.getId())
						.setSrc(video.getSrc())
						.setName(video.getName())
						.setTitle(video.getTitle())
						.setIdParagraph(video.getIdParagraph())
						.build();

			} else {
				String sql = "INSERT INTO video (src, name, title, id_paragraph) VALUES (?,?,?,?);";
				PreparedStatement stm = this.conn.prepareStatement(sql);

				stm.setString(1, video.getSrc());
				stm.setString(2, video.getName());
				stm.setString(3, video.getTitle());
				stm.setInt(4, video.getIdParagraph());
				
				if (stm.executeUpdate() > 0) {
					ResultSet genKeys = stm.getGeneratedKeys();
					if (genKeys.next()) {
						
						stm.setString(1, video.getSrc());
						stm.setString(2, video.getName());
						stm.setString(3, video.getTitle());
						stm.setInt(4, video.getIdParagraph());
						
						copy = new VideoBuilder()
								.setId(genKeys.getInt(1))
								.setSrc(video.getSrc())
								.setName(video.getName())
								.setTitle(video.getTitle())
								.setIdParagraph(video.getIdParagraph())
								.build();
					}
				}

				stm.close();

			}
		} catch (Exception e) {
			LOGGER.severe(Arrays.toString(e.getStackTrace()));
			throw new DaoException("Errore durante Update Video", e);
		}

		return copy;

	}

	@Override
	public void setConnection(Connection con) {
		this.conn = con;
	}

	@Override
	public Video get() throws DaoException  {
		Video video = null;

		try {
			Statement stm = this.conn.createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM video WHERE id = " + this.video.getId());

			while (rs.next()) {

				video = new VideoBuilder()
						.setId(rs.getInt("id"))
						.setSrc(rs.getString("src"))
						.setName(rs.getString("name"))
						.setTitle(rs.getString("title"))
						.setIdParagraph(rs.getInt("id_paragraph"))
						.build();

			}
			rs.close();
		} catch (Exception e) {
			LOGGER.severe(e.toString());
		}
		return video;
	}
}