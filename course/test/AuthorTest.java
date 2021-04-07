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

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import it.esedra.corso.journal.Author;
import it.esedra.corso.journal.AuthorBuilder;
import it.esedra.corso.journal.dao.AuthorDao;
import it.esedra.corso.journal.db.DbUtil;
import it.esedra.corso.journal.db.JournalDbConnect;
import it.esedra.corso.journal.execeptions.DaoException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthorTest {
	public static int ID = 1;
	public static final String NAME = " Mbrain Linda";
	public static final String EMAIL = "info@mcbrain.com";
	public static final String PREFIX = "$$";

	@BeforeClass
	public static void setup() {

		try {
			DbUtil.rebuildDb();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testGetAll() {

		List<Author> authorCollection = new ArrayList<>();

		Connection connection = null;
		try {
			// Effettua la connessione al database
			connection = JournalDbConnect.connect();
			AuthorDao authordao = new AuthorDao();
			authordao.setConnection(connection);

			//
			authorCollection = authordao.getAll();

			//
			Iterator<Author> authorIterator = authorCollection.iterator();

			boolean found = false;
			while (authorIterator.hasNext()) {

				Author author = authorIterator.next();

				if (author.getId() == ID && author.getName().equals(PREFIX + NAME)
						&& author.getEmail().equals(PREFIX + EMAIL)) {
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
	public void testAUpdate() {
		try {
			Connection connection = JournalDbConnect.connect();

			Author author = new AuthorBuilder().setName(NAME).setEmail(EMAIL).build();

			AuthorDao authorDao = new AuthorDao(author);
			authorDao.setConnection(connection);

			author = authorDao.update();
			assertTrue(author != null);
			// setto l'ID con il valore della chiave generata dal database
			ID = author.getId();

			author = new AuthorBuilder().setId(ID).setName(PREFIX + NAME).setEmail(PREFIX + EMAIL).build();
			authorDao = new AuthorDao(author);
			authorDao.setConnection(connection);
			author = authorDao.update();
			assertTrue(author != null);

			connection.close();

		} catch (DaoException | SQLException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testGet() {

		try {

			// Effettua la connessione al database
			Connection connection = JournalDbConnect.connect();
			Author authorMock = new AuthorBuilder().setId(ID).build();

			AuthorDao authorDao = new AuthorDao(authorMock);
			authorDao.setConnection(connection);

			Author author = authorDao.get();

			boolean found = false;

			if (author.getId() == ID && author.getName().equals(PREFIX + NAME)
					&& author.getEmail().equals(PREFIX + EMAIL)) {

				found = true;

			}

			connection.close();

			assertTrue(found);

		} catch (DaoException | SQLException e) {

			fail(e.getMessage());

		}

	}

	@Test
	public void testZDelete() {

		try {

			// Effettua la connessione al database

			Connection connection = JournalDbConnect.connect();
			Author authorMock = new AuthorBuilder().setId(ID).build();

			AuthorDao authorDao = new AuthorDao(authorMock);
			authorDao.setConnection(connection);
			boolean deleted = authorDao.delete();
			assertTrue(deleted);

			Author author = authorDao.get();
			assertNull(author);

			connection.close();

		} catch (DaoException | SQLException e) {

			fail(e.getMessage());

		}

	}
}