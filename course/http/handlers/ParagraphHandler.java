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
import it.esedra.corso.journal.Paragraph;
import it.esedra.corso.journal.execeptions.HandleRequestException;
import it.esedra.corso.journal.service.ParagraphService;

public class ParagraphHandler extends Handler {
	
	/**
	 * Deve restituire tutti i dati presenti in tabella qualora non sia valorizzato
	 * uno dei campi in input.
	 * 
	 * /paragraph Ottengo tutti i paragraph presenti 
	 *paragraph/{id} Ottenga un Paragraph per specifico ID 
	 * /paragraph/{text} Ottengo un Paragraph per specifico Text
	 */
	
	
	
	public JsonObject handleGetRequest(HttpExchange httpExchange) throws HandleRequestException {
		
		try {
			URI url = httpExchange.getRequestURI();
			switch (url.getPath()) {
			case "/paragraph": {
				Collection<Paragraph> paragraphs = ParagraphService.getAll();
				return JsonHelper.ok(paragraphs.toJson());//JsonHelper.ok(paragraph.toJson());
			}
			default:
				throw new HandleRequestException("Metodo non supportato");
			}
		} catch (Exception e) {
			throw new HandleRequestException(e.getMessage(), e);
		}
	}

	public JsonObject handlePostRequest(HttpExchange httpExchange) throws HandleRequestException {

		Paragraph paragraph = null;
		
		try {
			InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();

			JsonReader reader = Json.createReader(new StringReader(query));
			JsonObject paragraphObject = reader.readObject();
			reader.close();
			
			paragraph = ParagraphService.update(paragraphObject);

		} catch (Exception e) {
			throw new HandleRequestException(e.getMessage(), e);
		}
		
		if(paragraph == null) {
			throw new HandleRequestException("Insert Error.");
		} else {
			return JsonHelper.ok(paragraph.toJson());
		}
		

	}

	public JsonObject handlePutRequest(HttpExchange httpExchange) throws HandleRequestException {
		throw new HandleRequestException("Not Implemented Yet.");
	}

	public JsonObject handleDeleteRequest(HttpExchange httpExchange) throws HandleRequestException {
		throw new HandleRequestException("Not Implemented Yet.");
	}
}
