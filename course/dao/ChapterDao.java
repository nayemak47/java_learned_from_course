package it.esedra.corso.journal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import it.esedra.corso.journal.Chapter;
import it.esedra.corso.journal.ChapterBuilder;
import it.esedra.corso.journal.execeptions.DaoException;

public class ChapterDao implements DaoInterface<Chapter> {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private Chapter chapter;
	private Connection conn;

    public ChapterDao() {
		
	}
	
	public ChapterDao(Chapter chapter) {
		super();
		this.chapter = chapter;
	}

	@Override
	public Chapter update() throws DaoException {
		
		if (chapter == null) {
			throw new DaoException("chapter non può essere null.");
		}
		if(chapter.getIdJournal() == 0) {
			throw new DaoException("inserire un id_journal valido.");
		}
		Chapter chapterCheck = this.get();
		Chapter copy = null;
		try {
			if ((chapterCheck != null)) {
				String sql = "UPDATE chapter SET title = ?, date= ?, id_journal= ? WHERE id = ? ;";
				PreparedStatement stm = this.conn.prepareStatement(sql);

				stm.setString(1, chapter.getTitle());
				stm.setString(2, chapter.getDate());
				stm.setInt(3, chapter.getIdJournal());
				stm.setInt(4, chapter.getId());
				
				if (stm.executeUpdate() > 0) {	
				stm.close();
				} 
				copy = new ChapterBuilder().setId(chapter.getId()).setTitle(chapter.getTitle()).setDate(chapter.getDate()).setIdJournal(chapter.getIdJournal()).build();

			} else {
				String sql = "INSERT INTO chapter ( title, date, id_journal ) VALUES (?, ?, ?);";
				PreparedStatement stm = this.conn.prepareStatement(sql);

				stm.setString(1, chapter.getTitle());
				stm.setString(2, chapter.getDate());
				stm.setInt(3, chapter.getIdJournal());

				if (stm.executeUpdate() > 0) {
					ResultSet genKeys = stm.getGeneratedKeys();
					if (genKeys.next()) {

						stm.setString(1, chapter.getTitle());
						stm.setString(2, chapter.getDate());
						stm.setInt(3, chapter.getIdJournal());
						copy = new ChapterBuilder().setId(genKeys.getInt(1)).setTitle(chapter.getTitle())
								.setDate(chapter.getDate()).setIdJournal(chapter.getIdJournal()).build();				
				}
				stm.close();			
				}			
			}
		
		} catch (Exception e) {
			LOGGER.severe(Arrays.toString(e.getStackTrace()));
			throw new DaoException("Errore durante Update Chapter", e);
		}
		return copy;
	}

	@Override
	public boolean delete() throws DaoException {

		boolean success = true;

		try {
			Statement stm = this.conn.createStatement();
			int rs = stm.executeUpdate("DELETE FROM chapter WHERE id = " + this.chapter.getId());

			if (rs > 0) {
				success = true;
			}
		} catch (SQLException e) {
			throw new DaoException("Errore durante Delete Chapter", e);
		}
		return success;
	}

	@Override
	public List<Chapter> getAll() throws DaoException {
		List<Chapter> chapters = new ArrayList<>();
		try {
			Statement stm = this.conn.createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM chapter");

			while (rs.next()) {
				Chapter chapter = new ChapterBuilder()
						.setId(rs.getInt("id"))
						.setTitle(rs.getString("title"))
						.setDate(rs.getString("date"))
						.setIdJournal(rs.getInt("id_journal"))
						.build();

				chapters.add(chapter);
			}
			rs.close();		
		} catch (SQLException e) {
			throw new DaoException("Errore durante GetAll Chapter", e);
		}
		return chapters;
	}

	@Override
	public void setConnection(Connection con) {
		this.conn = con;
	}

	@Override
	public Chapter get() throws DaoException {
		Chapter chapter = null;

		try {
			Statement stm = this.conn.createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM chapter WHERE id = " + this.chapter.getId());

			while (rs.next()) {
				chapter = new ChapterBuilder()
						.setId(rs.getInt("id"))
						.setTitle(rs.getString("title"))
						.setDate(rs.getString("date"))
						.setIdJournal(rs.getInt("id_journal"))
						.build();
			}
			rs.close();
		} catch (SQLException e) {
			throw new DaoException("Errore durante Get Chapter", e);
		}
		return chapter;
	}
}