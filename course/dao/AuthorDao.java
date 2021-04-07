package it.esedra.corso.journal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.esedra.corso.helpers.PrintHelper;
import it.esedra.corso.journal.Author;
import it.esedra.corso.journal.AuthorBuilder;
import it.esedra.corso.journal.execeptions.DaoException;

public class AuthorDao implements DaoInterface<Author> {
 
	private Author author;
	private Connection conn;

	public AuthorDao() {
	}

	public AuthorDao(Author author) {
		super();
		this.author = author;
	}

	@Override
	public Author update() throws DaoException {

		
		if (author == null) {
			PrintHelper.out("author non può essere null.");
			return null;
		}
		Author authorCheck = this.get();
		Author copy = null;
		try {
			
			if (authorCheck != null) {
				String sql = "UPDATE author SET name = ?, email = ? WHERE id = ? ;";
				PreparedStatement stm = this.conn.prepareStatement(sql);

				stm.setString(1, author.getName());
				stm.setString(2, author.getEmail());
				stm.setInt(3, author.getId());
				if (stm.executeUpdate() > 0) {
					
				}
				stm.close();
			
				copy = new AuthorBuilder().setId(author.getId()).setName(author.getName()).setEmail(author.getEmail()).build();;
			
			}
			else {
				String sql = "INSERT INTO author ( name, email) VALUES ( ?, ?);";
				PreparedStatement stm = this.conn.prepareStatement(sql);

				stm.setString(1, author.getName());
				stm.setString(2, author.getEmail());

				if (stm.executeUpdate() > 0) {
					ResultSet genKeys = stm.getGeneratedKeys();
					if (genKeys.next()) {
						copy = new AuthorBuilder().setId(genKeys.getInt(1)).setEmail(author.getEmail())
								.setName(author.getName()).build();
					}
				}

				stm.close();
			} 

		} catch (SQLException e) {
			throw new DaoException("Errore durante update Author", e);
		}
		return copy;
	}

	@Override
	public boolean delete() throws DaoException  {
		boolean success = true;

		try {
			Statement stm = this.conn.createStatement();
			int rs = stm.executeUpdate("DELETE FROM author WHERE id = " + this.author.getId());

			if (rs > 0) {
				success = true;
			}
		} catch (SQLException e) {
			throw new DaoException("Errore durante delete Author", e);
		}

		return success;
	}

	@Override
	public List<Author> getAll() throws DaoException   {
		// istanzia una lista vuota di User
		List<Author> authors = new ArrayList<>();
		try {

			// crea lo statement
			Statement stm = this.conn.createStatement();
			// crea il result set al quale passa la query
			ResultSet rs = stm.executeQuery("SELECT * FROM author");

			// ottiene il result set
			while (rs.next()) {
				// e quindi per ogni tupla crea un oggetto di tipo User
				Author author = new AuthorBuilder().setId(rs.getInt("id")).setName(rs.getString("name"))
						.setEmail(rs.getString("email")).build();

				// aggiunge l'oggetto alla lista
				authors.add(author);
			}
			// chiude le connessioni e il result set
			rs.close();
		} catch (SQLException e) {
			throw new DaoException("Errore durante getAll Author", e);
		}
		// restituisce la lista
		return authors;
	}

	@Override
	public void setConnection(Connection con) {
		this.conn = con;
	
	}

	@Override
	public Author get()  throws DaoException {
		Author author = null;

		try {
			Statement stm = this.conn.createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM author WHERE id = " + this.author.getId());

			while (rs.next()) {

				author = new AuthorBuilder().setId(rs.getInt("id")).setName(rs.getString("name"))
						.setEmail(rs.getString("email")).build();

			}
			rs.close();
		} catch (SQLException e) {
			throw new DaoException("Errore durante get Author", e);
		}
		return author;
	}

}
