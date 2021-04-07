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
import it.esedra.corso.journal.Chapter;
import it.esedra.corso.journal.ChapterBuilder;
import it.esedra.corso.journal.dao.ChapterDao;
import it.esedra.corso.journal.db.DbUtil;
import it.esedra.corso.journal.db.JournalDbConnect;
import it.esedra.corso.journal.execeptions.DaoException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChapterTest {

	private static int ID = 1;
	private static final String TITLE = "TITLE";
	private static final String DATE = "25/07/1900";
	public static final String PREFIX = "$$";
	public static final int IDJOURNAL = 1;

	@Test
	public void testAUpdate() {
		try {
			Connection connection = JournalDbConnect.connect();

			Chapter chapter = new ChapterBuilder().setTitle(PREFIX + TITLE).setDate(PREFIX + DATE).setIdJournal(IDJOURNAL).build();

			ChapterDao chapterDao = new ChapterDao(chapter);
			chapterDao.setConnection(connection);
			
			chapter = chapterDao.update();
			assertTrue(chapter != null);
			ID = chapter.getId();

			chapter = new ChapterBuilder().setId(ID).setTitle(PREFIX + TITLE).setDate(PREFIX + DATE).setIdJournal(IDJOURNAL).build();

			chapterDao = new ChapterDao(chapter);
			chapterDao.setConnection(connection);
			chapter = chapterDao.update();
			assertTrue(chapter != null);
			
			connection.close();

		} catch (DaoException | SQLException e) {
			fail(e.getMessage());
		}

		
	}

	@Test
	public void testGetAll() {

		List<Chapter> chapterCollection = new ArrayList<>();

		Connection connection = null;
		try {
			// Effettua la connessione al database

			connection = JournalDbConnect.connect();
			ChapterDao chapterdao = new ChapterDao(null);
			chapterdao.setConnection(connection);

			chapterCollection = chapterdao.getAll();

			Iterator<Chapter> chapterIterator = chapterCollection.iterator();

			boolean found = false;
			while (chapterIterator.hasNext()) {

				Chapter chapter1 = chapterIterator.next();

				if (chapter1.getId() == ID && chapter1.getTitle().equals(PREFIX + TITLE)
						&& chapter1.getDate().equals(PREFIX + DATE) && chapter1.getIdJournal() == IDJOURNAL) {
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
			Chapter chapterMock = new ChapterBuilder().setId(ID).build();

			ChapterDao chapterDao = new ChapterDao(chapterMock);
			chapterDao.setConnection(connection);

			Chapter chapter = chapterDao.get();
			boolean found = false;

			if (chapter.getId() == ID && (chapter.getTitle().equals(PREFIX + TITLE)
					&& chapter.getDate().equals(PREFIX + DATE )) && chapter.getIdJournal() == IDJOURNAL) {
				
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
			Chapter chapterMock = new ChapterBuilder().setId(ID).build();

			ChapterDao chapterDao = new ChapterDao(chapterMock);
			chapterDao.setConnection(connection);
			boolean deleted = chapterDao.delete();
			assertTrue(deleted);

			Chapter chapter = chapterDao.get();
			assertNull(chapter);

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
}
