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
import it.esedra.corso.journal.Author;
import it.esedra.corso.journal.execeptions.HandleRequestException;
import it.esedra.corso.journal.service.AuthorService;

public class AuthorHandler extends Handler {
	
	/**
	 * Deve restituire tutti i dati presenti in tabella qualora non sia valorizzato
	 * uno dei campi in input.
	 * 
	 * /journal Ottengo tutti i journal presenti /journal/{id} Ottengo un Journal
	 * per specifico ID /journal/{name} Ottengo un Journal per specifico Name
	 */
	
	public JsonObject handleGetRequest(HttpExchange httpExchange) throws HandleRequestException {
		try {
			URI url = httpExchange.getRequestURI();
			switch (url.getPath()) {
			case "/author": {
				Collection<Author> authors = AuthorService.getAll();
				return JsonHelper.ok(authors.toJson());//JsonHelper.ok(author.toJson());
			}
			default:
				throw new HandleRequestException("Metodo non supportato");
			}
		} catch (Exception e) {
			throw new HandleRequestException(e.getMessage(), e);
		}

	}
	
	public JsonObject handlePostRequest(HttpExchange httpExchange) throws HandleRequestException {
		Author author = null;
		
		try {
			InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();

			JsonReader reader = Json.createReader(new StringReader(query));
			JsonObject authorObject = reader.readObject();
			reader.close();
			
			author = AuthorService.update(authorObject);

		} catch (Exception e) {
			throw new HandleRequestException(e.getMessage(), e);
		}
		if(author == null) {
			throw new HandleRequestException("Insert Error.");
		} else {
			return JsonHelper.ok(author.toJson());
		}

	}
	public JsonObject handlePutRequest(HttpExchange httpExchange) throws HandleRequestException {
		throw new HandleRequestException("Not Implemented Yet.");
	}

	public JsonObject handleDeleteRequest(HttpExchange httpExchange) throws HandleRequestException {
		throw new HandleRequestException("Not Implemented Yet.");
	}
}

	
