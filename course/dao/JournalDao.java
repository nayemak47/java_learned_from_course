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

import it.esedra.corso.journal.Journal;
import it.esedra.corso.journal.JournalBuilder;
import it.esedra.corso.journal.collections.JournalCollection;
import it.esedra.corso.journal.execeptions.DaoException;

public class JournalDao implements DaoInterface<Journal> {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private Journal journal;
	private Connection conn;

	public JournalDao() {

	}

	public JournalDao(Journal journal) {
		super();
		this.journal = journal;
	}

	@Override
	public List<Journal> getAll() throws DaoException {
		// istanzia una lista vuota di Journal
		List<Journal> journals = new ArrayList<>();
		try {

			// crea lo statement
			Statement stm = this.conn.createStatement();
			// crea il result set al quale passa la query
			ResultSet rs = stm.executeQuery("SELECT * FROM journal");

			// ottiene il result set
			while (rs.next()) {
				// e quindi per ogni tupla crea un oggetto di tipo Journal
				Journal journal = new JournalBuilder().setId(rs.getInt("id")).setName(rs.getString("Name"))
						.setIdAuthor(rs.getInt("id_author")).build();
				// aggiunge l'oggetto alla lista
				journals.add(journal);
			}
			// chiude le connessioni e il result set
			rs.close();
		} catch (SQLException e) {
			throw new DaoException("Errore durante getAll Journal", e);
		}
		// restituisce la lista
		return journals;

	}

	@Override
	public Journal update() throws DaoException {

		if (journal == null) {
			throw new DaoException("journal non può essere null.");

		}

		if (journal.getIdAuthor() == 0) {
			throw new DaoException("inserire un id_author valido.");
		}

		Journal journalCheck = this.get();
		Journal copy = null;

		try {
			if (journalCheck != null) {
				String sql = "UPDATE journal SET name = ? , id_author = ? WHERE id = ? ;";
				PreparedStatement stm = this.conn.prepareStatement(sql);
				stm.setString(1, journal.getName());
				stm.setInt(2, journal.getIdAuthor());
				stm.setInt(3, journal.getId());
				if (stm.executeUpdate() > 0) {
					stm.close();
				}
				copy = new JournalBuilder().setId(journal.getId()).setName(journal.getName())
						.setIdAuthor(journal.getIdAuthor()).build();
			} else {
				String sql = "INSERT INTO journal ( name, id_author) VALUES (?,?) ;";
				PreparedStatement stm = this.conn.prepareStatement(sql);
				stm.setString(1, journal.getName());
				stm.setInt(2, journal.getIdAuthor());
				if (stm.executeUpdate() > 0) {
					ResultSet genKeys = stm.getGeneratedKeys();
					if (genKeys.next()) {
						copy = new JournalBuilder().setId(genKeys.getInt(1)).setName(journal.getName())
								.setIdAuthor(journal.getIdAuthor()).build();
					}
				}
				stm.close();
			}
		} catch (SQLException e) {
			LOGGER.severe(Arrays.toString(e.getStackTrace()));
			throw new DaoException("Errore durante Update journal", e);
		}
		return copy;
	}

	@Override
	public void setConnection(Connection con) {
		this.conn = con;
	}

	@Override
	public Journal get() throws DaoException {
		// inizializza un nuovo oggetto Journal
		Journal journal = null;

		try {

			// crea lo statement
			Statement stm = this.conn.createStatement();
			// crea il result set al quale passa la query
			ResultSet rs = stm.executeQuery("SELECT * FROM journal WHERE id =" + this.journal.getId());

			while (rs.next()) {
				// istanzia l'elemento Journal
				journal = new JournalBuilder().setId(rs.getInt("id")).setName(rs.getString("Name"))
						.setIdAuthor(rs.getInt("id_author")).build();

			}
			// chiude le connessioni e il result set
			rs.close();
		} catch (SQLException e) {
			throw new DaoException("Errore durante Get Journal", e);
		}
		// restituisce l'oggetto
		return journal;

	}

	@Override
	public boolean delete() throws DaoException {

		boolean success = true;

		try {
			Statement stm = this.conn.createStatement();
			int rs = stm.executeUpdate("DELETE FROM journal WHERE id = " + this.journal.getId());

			if (rs > 0) {
				success = true;
			}
		} catch (SQLException e) {
			throw new DaoException("Errore durante Delete Journal", e);
		}

		return success;
	}

}
