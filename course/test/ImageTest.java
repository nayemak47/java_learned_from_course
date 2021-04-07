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

import it.esedra.corso.journal.Image;
import it.esedra.corso.journal.ImageBuilder;

import it.esedra.corso.journal.dao.ImageDao;

import it.esedra.corso.journal.db.DbUtil;
import it.esedra.corso.journal.db.JournalDbConnect;
import it.esedra.corso.journal.execeptions.DaoException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ImageTest {

	public static int ID = 1;
	public static final String SRC = "https:www.youtube.com";
	public static final String NAME = "Gulia";

	public static final String PREFIX = "$$";
	public static final int IDPARAGRAPH = 1;

	@Test
	public void testAUpdate() {

		try {
			Connection connection = JournalDbConnect.connect();

			Image image = new ImageBuilder()
					.setSrc(SRC)
					.setName(NAME)
					.setIdParagraph(IDPARAGRAPH)
					.build();

			ImageDao imageDao = new ImageDao(image);
			imageDao.setConnection(connection);

			image = imageDao.update();

			ID = image.getId();

			image = new ImageBuilder()
					.setId(ID)
					.setSrc(PREFIX + SRC)
					.setName(PREFIX + NAME)
					.setIdParagraph(IDPARAGRAPH)
					.build();

			imageDao = new ImageDao(image);
			imageDao.setConnection(connection);
			image = imageDao.update();
			assertTrue(image != null);
			
			connection.close();

		} catch (DaoException | SQLException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testGetAll()  {

		List<Image> imageCollection = new ArrayList<>();

		Connection connection = null;
		try {

			connection  = JournalDbConnect.connect();
			ImageDao imageDao = new ImageDao(null);
			imageDao.setConnection(connection);

			imageCollection = imageDao.getAll();

			Iterator<Image> imageIterator = imageCollection.iterator();

			boolean found = false;
			while (imageIterator.hasNext()) {

				Image image = imageIterator.next();

				if (image.getId() == ID
						&& image.getSrc()
						.equals(PREFIX + SRC)
						&& image.getName()
						.equals(PREFIX + NAME)
						&& image
						.getIdParagraph() 
						== IDPARAGRAPH) {
					found = true;
					break;

				}

			}
			
			assertTrue(found);

		} catch (DaoException e) {
			fail(e.getMessage());
		}finally {
			try {
		
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				fail(e.getMessage());
			}

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
	public void testGet() {

		try {

			Connection connection = JournalDbConnect.connect();
			Image imageMock = new ImageBuilder().setId(ID).build();

			ImageDao imageDao = new ImageDao(imageMock);
			imageDao.setConnection(connection);

			Image image = imageDao.get();

			boolean found = false;
			if (image.getId() == ID
					&& image.getSrc()
					.equals(PREFIX + SRC)
					&& image.getName()
					.equals(PREFIX + NAME)
					&& image
					.getIdParagraph() 
					== IDPARAGRAPH) {
				found = true;

			}
			connection.close();
			assertTrue(found);
		} catch ( DaoException | SQLException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testZDelete() {

		try {

			Connection connection = JournalDbConnect.connect();
			Image imageMock = new ImageBuilder().setId(ID).build();

			ImageDao imageDao = new ImageDao(imageMock);
			imageDao.setConnection(connection);

			boolean deleted = imageDao.delete();
			assertTrue(deleted);

			Image image = imageDao.get();

			assertNull(image);

			connection.close();
		} catch (DaoException | SQLException e) {

			fail(e.getMessage());

		}

	}
}
