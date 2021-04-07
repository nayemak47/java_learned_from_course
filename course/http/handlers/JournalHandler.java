package it.esedra.corso.journal.http.handlers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import com.sun.net.httpserver.HttpExchange;

import it.esedra.corso.collections.interfaces.Collection;
import it.esedra.corso.journal.Journal;
import it.esedra.corso.journal.execeptions.HandleRequestException;
import it.esedra.corso.journal.service.JournalService;

public class JournalHandler extends Handler {

	/**
	 * Deve restituire tutti i dati presenti in tabella qualora non sia valorizzato
	 * uno dei campi in input.
	 * 
	 * "
	 * /journal Ottengo tutti i journal presenti 
	 * /journal/{id} Ottengo un Journal per specifico ID 
	 * /journal/{name} Ottengo un Journal per specifico Name
	 * "
	 * 
	 */
	public JsonObject handleGetRequest(HttpExchange httpExchange) throws HandleRequestException {
		try {
			URI url = httpExchange.getRequestURI();
			switch (url.getPath()) {
			case "/journal": {
				Collection<Journal> journals = JournalService.getAll();
				return JsonHelper.ok(journals.toJson());//JsonHelper.ok(journal.toJson());
			}
			default:
				throw new HandleRequestException("Metodo non supportato");
			}
		} catch (Exception e) {
			throw new HandleRequestException(e.getMessage(), e);
		}

	}

	public JsonObject handlePostRequest(HttpExchange httpExchange) throws HandleRequestException {

		Journal journal = null;

		try {
			InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();

			JsonReader reader = Json.createReader(new StringReader(query));
			JsonObject journalObject = reader.readObject();
			reader.close();

			journal = JournalService.update(journalObject);

		} catch (Exception e) {
			throw new HandleRequestException(e.getMessage(), e);
		}

		if (journal == null) {
			throw new HandleRequestException("Insert Error.");
		} else {
			return JsonHelper.ok(journal.toJson());
		}

	}

	public JsonObject handlePutRequest(HttpExchange httpExchange) throws HandleRequestException {
		throw new HandleRequestException("Not Implemented Yet.");
	}

	public JsonObject handleDeleteRequest(HttpExchange httpExchange) throws HandleRequestException {
		throw new HandleRequestException("Not Implemented Yet.");
	}
}
