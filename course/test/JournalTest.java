package it.esedra.corso.journal.test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import it.esedra.corso.journal.Author;
import it.esedra.corso.journal.AuthorBuilder;
import it.esedra.corso.journal.Journal;
import it.esedra.corso.journal.JournalBuilder;
import it.esedra.corso.journal.dao.AuthorDao;
import it.esedra.corso.journal.dao.JournalDao;
import it.esedra.corso.journal.db.DbUtil;
import it.esedra.corso.journal.db.JournalDbConnect;
import it.esedra.corso.journal.execeptions.DaoException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JournalTest {
	public static int ID = 1;
	public static final String NAME = "Claudio";
	public static final String EMAIL = "cb@prova.it";
	public static final String TEST_OK = "passed";
	public static final String TEST_FAIL = "failed";
	public static final String PREFIX = "$$";
	public static final int IDAUTHOR = 1;
	
	public Author tempAuthor;
	
	private Author getAuthor() {
		return tempAuthor;
	}
	
	private void setAuthor(Author author) {
		this.tempAuthor = author;
	}

	@Test
	public void testAUpdate() {
		try {
			Connection connection = JournalDbConnect.connect();

			Journal journal = new JournalBuilder().setName(NAME).setAuthor(getAuthor()).build();

			JournalDao journalDao = new JournalDao(journal);
			journalDao.setConnection(connection);

			journal = journalDao.update();
			assertTrue(journal != null);
			ID = journal.getId();

			journal = new JournalBuilder().setId(ID).setName(PREFIX + NAME).setAuthor(getAuthor()).build();
			journalDao = new JournalDao(journal);
			journalDao.setConnection(connection);
			journalDao.update();
			assertTrue(journal != null);
			
			connection.close();

		} catch (SQLException | DaoException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testGetAll() {

		List<Journal> journalCollection = new ArrayList<>();
		
		Connection connection = null;
		try {
			// Effettua la connessione al database
			connection = JournalDbConnect.connect();
			JournalDao journalDao = new JournalDao();
			journalDao.setConnection(connection);

			// Chiamata metodo getAll() sulla Collection creata
			journalCollection = journalDao.getAll();

			// Inizializzazione iterator per ciclare sulla Collection
			Iterator<Journal> journalIterator = journalCollection.iterator();

			/**
			 * Cicla sugli elementi Journal della journalCollection e restituisce per ogni
			 * elemento i valori delle colonne della tabella journal.
			 * 
			 * L'if controlla l'effettiva esistenza dei dati del test case nelle colonne
			 * della tabella journal.
			 * 
			 */

			boolean found = false;
			while (journalIterator.hasNext()) {

				Journal journal = journalIterator.next();

				if (journal.getId() == ID && journal.getName().equals(PREFIX + NAME) && journal.getAuthor().getId() == getAuthor().getId()) {
					found = true;
					break;
				
				}

			}

			assertTrue(found);

		} catch (DaoException e) {
			fail(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				fail(e.getMessage());
			}
		}

	}

	@Test
	public void testGet() {

		try {
			// Effettua la connessione al database
			Connection connection = JournalDbConnect.connect();

			Journal journalMock = new JournalBuilder().setId(ID).build();
			JournalDao journalDao = new JournalDao(journalMock);
			journalDao.setConnection(connection);

			Journal journal = journalDao.get();
			boolean found = false;

			if (journal.getId() == ID && journal.getName().equals(PREFIX + NAME) && journal.getAuthor() == getAuthor())  {
				found = true;
			}

			connection.close();

			assertTrue(found);

		} catch (DaoException | SQLException e) {
			fail(e.getMessage());
		}

	}
	
	@Before
	public void init() {
		try {
			Connection connection = JournalDbConnect.connect();
			
			Author author = new AuthorBuilder().setName(NAME).setEmail(EMAIL).build();

			AuthorDao authorDao = new AuthorDao(author);
			authorDao.setConnection(connection);

			setAuthor(authorDao.update());

			connection.close();

		} catch (SQLException | DaoException e) {
			fail(e.getMessage());
		}
	}
	
	@After
	public void finalize() {
		try {

			// Effettua la connessione al database

			Connection connection = JournalDbConnect.connect();
			
			AuthorDao authorDao = new AuthorDao(getAuthor());
			authorDao.setConnection(connection);
			authorDao.delete();

			connection.close();

		} catch (DaoException | SQLException e) {

			fail(e.getMessage());

		}
	}
 	
	@BeforeClass
	public static void setup() {

		try {
			DbUtil.rebuildDb();

		} catch (IOException e) {

			fail(e.getMessage());
		}
		


	}

	@Test
	public void testZDelete() {

		try {

			// Effettua la connessione al database

			Connection connection = JournalDbConnect.connect();
			
			Journal journalMock = new JournalBuilder().setId(ID).build();
			JournalDao journalDao = new JournalDao(journalMock);
			journalDao.setConnection(connection);
			boolean deleted = journalDao.delete();
			assertTrue(deleted);

			Journal journal = journalDao.get();
			assertNull(journal);

			connection.close();

		} catch (DaoException | SQLException e) {

			fail(e.getMessage());

		}

	}

}
