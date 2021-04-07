package it.esedra.corso.journal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import it.esedra.corso.journal.Paragraph;
import it.esedra.corso.journal.ParagraphBuilder;
import it.esedra.corso.journal.execeptions.DaoException;

public class ParagraphDao implements DaoInterface<Paragraph> {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	

	private Paragraph paragraph;
	private Connection conn;

	public ParagraphDao() {

	}

	public ParagraphDao(Paragraph paragraph) {
		super();
		this.paragraph = paragraph;
	}

	@Override
	public Paragraph update() throws DaoException {

		if (paragraph == null) {
			throw new DaoException("Paragraph non può essere null");
			
		}
		if(paragraph.getIdChapter() == 0) {
			throw new DaoException("inserire un id_chapter valido.");
		}
		
			Paragraph paragraphCheck = this.get();
		Paragraph copy = null;
		try {
			if (paragraphCheck != null) {
				String sql = "UPDATE paragraph SET text= ? , id_chapter= ? WHERE id = ? ;";
				PreparedStatement stm = this.conn.prepareStatement(sql);

				stm.setString(1, paragraph.getText());
				stm.setInt(2, paragraph.getIdChapter());
				stm.setInt(3, paragraph.getId());

				if (stm.executeUpdate() > 0) {

					stm.close();
				}
				copy = new ParagraphBuilder().setId(paragraph.getId()).setText(paragraph.getText()).setIdJournal(paragraph.getIdChapter()).build();;

			} else {
				String sql = "INSERT INTO paragraph ( text, id_chapter) VALUES (?,?) ;";
				PreparedStatement stm = this.conn.prepareStatement(sql);
			
				stm.setString(1, paragraph.getText());
				stm.setInt(2, paragraph.getIdChapter());

				if (stm.executeUpdate() > 0) {
					ResultSet genKeys = stm.getGeneratedKeys();
					if (genKeys.next()) {
						
						stm.setString(1, paragraph.getText());
						stm.setInt(2, paragraph.getIdChapter());	

						copy = new ParagraphBuilder().setId(genKeys.getInt(1)).setText(paragraph.getText()).setIdJournal(paragraph.getIdChapter()).build();
					}

				}

				stm.close();

			}

		} catch (Exception e) {
			LOGGER.severe(Arrays.toString(e.getStackTrace()));
			throw new DaoException("Errore durante Update paragraph", e);
		}

		return copy;

	}

	@Override
	public List<Paragraph> getAll() throws DaoException {
		List<Paragraph> paragraphs = new ArrayList<>();

		// List<Paragraph> paragraphs = new ArrayList<>();
		try {

			Statement stm = this.conn.createStatement();

			ResultSet rs = stm.executeQuery("SELECT * FROM Paragraph");

			while (rs.next()) {
				Paragraph paragraph = new ParagraphBuilder().setId(rs.getInt("id")).setText(rs.getString("text"))
						.setIdJournal(rs.getInt("id_chapter")).build();

				paragraphs.add(paragraph);
			}

			rs.close();
		} catch (Exception e) {
			throw new DaoException("Errore durante GetAll Paragraph", e);
		}

		return paragraphs;

	}

	@Override
	public void setConnection(Connection con) {
		this.conn = con;

	}

	@Override
	public boolean delete() throws DaoException {

		boolean success = true;

		try {
			Statement stm = this.conn.createStatement();
			int rs = stm.executeUpdate("DELETE FROM paragraph WHERE id = " + this.paragraph.getId());

			if (rs > 0) {
				success = true;
			}
		} catch (Exception e) {
			throw new DaoException("Errore durante Delete paragraph", e);
		}

		return success;
	}

	@Override
	public Paragraph get() throws DaoException {
		Paragraph paragraph = null;

		try {
			Statement stm = this.conn.createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM paragraph WHERE id = " + this.paragraph.getId());

			while (rs.next()) {
				paragraph = new ParagraphBuilder().setId(rs.getInt("id")).setText(rs.getString("text"))
						.setIdJournal(rs.getInt("id_chapter")).build();

			}
			rs.close();
		} catch (Exception e) {
			throw new DaoException("Errore durante Get paragraph", e);
		}
		return paragraph;
	}
}
