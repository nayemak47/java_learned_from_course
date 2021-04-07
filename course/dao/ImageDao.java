package it.esedra.corso.journal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.esedra.corso.helpers.PrintHelper;
import it.esedra.corso.journal.Image;
import it.esedra.corso.journal.ImageBuilder;
import it.esedra.corso.journal.execeptions.DaoException;

public class ImageDao implements DaoInterface<Image> {

	private Image image;
	private Connection conn;

	public ImageDao() {

	}
	
	public ImageDao(Image image) {
		super();
		this.image = image;

	}

	@Override
	public boolean delete() throws DaoException {

		boolean success = true;

		try {
			Statement stm = this.conn.createStatement();
			int rs = stm.executeUpdate("DELETE FROM image WHERE id = " + this.image.getId());

			if (rs > 0) {
				success = true;
			}
		} catch (SQLException e) {
			throw new DaoException("Errore durante delete Image ", e);
		}

		return success;

	}

	@Override
	public List<Image> getAll() throws DaoException {
		List<Image> images = new ArrayList<>();
		try {
			Statement stm = this.conn.createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM image");

			while (rs.next()) {

				Image image = new ImageBuilder()
						.setId(rs.getInt("id"))
						.setSrc(rs.getString("src"))
						.setName(rs.getString("name"))
						.setIdParagraph(rs.getInt("id_paragraph"))
						.build();

				images.add(image);
			}
			rs.close();
		} catch (SQLException e) {
			throw new DaoException("Errore durante getAll Image", e);
		}

		return images;

	}

	@Override
	public Image update() throws DaoException {

		if (image == null) {
			PrintHelper.out("image non può essere null.");
			return null;
		}
		
		if(image.getIdParagraph() == 0) {
			throw new DaoException("inserire un id_paragraph valido.");
		}
		
		Image imageCheck = this.get();
		Image copy = null;

		try {

			if (imageCheck != null) {
				String sql = "UPDATE image SET src= ?, name= ?, id_paragraph= ? WHERE id = ? ;";
				PreparedStatement stm = this.conn.prepareStatement(sql);

				stm.setString(1, image.getSrc());
				stm.setString(2, image.getName());
				stm.setInt(3, image.getIdParagraph());
				stm.setInt(4, image.getId());

				if (stm.executeUpdate() > 0) {
					stm.close();
				}
				copy = new ImageBuilder()
						.setId(image.getId())
						.setSrc(image.getSrc())
						.setName(image.getName())
						.setIdParagraph(image.getIdParagraph())
						.build();
			} else {
				String sql = "INSERT INTO image (src, name, id_paragraph) VALUES (?,?,?);";
				PreparedStatement stm = this.conn.prepareStatement(sql);

				stm.setString(1, image.getSrc());
				stm.setString(2, image.getName());
				stm.setInt(3, image.getIdParagraph());

				if (stm.executeUpdate() > 0) {

					ResultSet genKeys = stm.getGeneratedKeys();
					if (genKeys.next()) {

						copy = new ImageBuilder()
								.setId(genKeys.getInt(1))
								.setSrc(image.getSrc())
								.setName(image.getName())
								.setIdParagraph(image.getIdParagraph())
								.build();
					}
				}

				stm.close();

			}
		} catch (SQLException e) {
			throw new DaoException("Errore durante update Image ", e);
		}

		return copy;

	}

	@Override
	public void setConnection(Connection con) {
		this.conn = con;
	}

	@Override
	public Image get() throws DaoException {
		Image image = null;

		try {
			Statement stm = this.conn.createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM image WHERE id = " + this.image.getId());

			while (rs.next()) {

				image = new ImageBuilder()
						.setId(rs.getInt("id"))
						.setSrc(rs.getString("src"))
						.setName(rs.getString("name"))
						.setIdParagraph(rs.getInt("id_paragraph"))
						.build();

			}
			rs.close();
		} catch (SQLException e) {
			throw new DaoException("Errore durante get Image ", e);
		}
		return image;
	}
}