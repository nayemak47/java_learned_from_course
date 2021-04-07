
package it.esedra.corso.journal.http.handlers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.sun.net.httpserver.HttpExchange;

import it.esedra.corso.journal.User;
import it.esedra.corso.journal.execeptions.HandleRequestException;

import it.esedra.corso.journal.service.UserService;

public class UserHandler extends Handler {
	/**
	 * Deve restituire tutti i dati presenti in tabella qualora non sia valorizzato
	 * uno dei campi in input.
	 * 
	 * /user Ottengo tutti gli user presenti 
	 * /user/{id} Ottengo un User per specifico ID 
	 * /user/{surname} Ottengo un User per specifico Surname
	 * /user/{email} Ottengo un User per specifico Email	
	 * /user/{password} Ottengo un User per specifico Password
	 * /user/{registration} Ottengo un User per specifico Registration
	 */	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public JsonObject handleGetRequest(HttpExchange httpExchange) throws HandleRequestException {
		try {
			URI url = httpExchange.getRequestURI();
			switch (url.getPath()) {
			case "/user": {
				List<User> users = UserService.getAll();
				return JsonHelper.ok(users.toJson()); //JsonHelper.ok(user.toJson());
			}
			default:
				throw new HandleRequestException("Metodo non supportato");
			}
		} catch (Exception e) {
			throw new HandleRequestException(e.getMessage(), e);
		}
	}

	public JsonObject handlePostRequest(HttpExchange httpExchange) throws HandleRequestException {

		User user = null;
		
		try {
			InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();

			JsonReader reader = Json.createReader(new StringReader(query));
			JsonObject userObject = reader.readObject();
			reader.close();

			user = UserService.update(userObject);

		} catch (Exception e) {
			LOGGER.severe(e.toString());
		}
		if(user == null) {
			throw new HandleRequestException("Insert Error.");
		} else {
			return JsonHelper.ok(user.toJson());
		}
	}

	public JsonObject handlePutRequest(HttpExchange httpExchange) throws HandleRequestException {
		throw new HandleRequestException("Not Implemented Yet.");
	}

	public JsonObject handleDeleteRequest(HttpExchange httpExchange) throws HandleRequestException {
		throw new HandleRequestException("Not Implemented Yet.");
	}	
}
