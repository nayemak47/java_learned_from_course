
package it.esedra.corso.journal.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;

import it.esedra.corso.helpers.PrintHelper;
import it.esedra.corso.journal.User;
import it.esedra.corso.journal.UserBuilder;
import it.esedra.corso.journal.dao.UserDao;
import it.esedra.corso.journal.db.JournalDbConnect;
import it.esedra.corso.journal.execeptions.DaoException;

public class UserService {
	/**
	 * Gestisce la connessione dao-db dell'oggetto Json
	 * 
	 * @param json
	 * @return User
	 * @throws DaoException
	 */

	public static User update(JsonObject json) throws DaoException {

		Connection connection = JournalDbConnect.connect();
		User user = new UserBuilder().setId(json.getInt("id", -1)).setName(json.getString("name"))
				.setSurname(json.getString("surname")).setEmail(json.getString("email"))
				.setPassword(json.getString("password")).setRegistration(json.getString("registration")).build();

		UserDao userDao = new UserDao(user);
		userDao.setConnection(connection);

		return userDao.update();
	}

	/**
	 * Restituisce tutti gli oggetti user
	 * 
	 * @return Collection<User>
	 * @throws DaoException
	 */
	public static List<User> getAll() throws DaoException {
		List<User> userCollection = new ArrayList<>();

		Connection connection = null;
		try {
			// Effettua la connessione al database
			connection = JournalDbConnect.connect();
			UserDao userDao = new UserDao();
			userDao.setConnection(connection);
			// Chiamata metodo getAll() sulla Collection creata
			userCollection = userDao.getAll();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				PrintHelper.out("Errore nella chiususa della connessione");
			}
		}
		return userCollection;
	}
}
