package it.esedra.corso.journal.http.handlers;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

public class ResponseHelper {
	/**
	 * Gestisce la Response in errore
	 * 
	 * @param t
	 * @param response
	 * @throws IOException
	 */
	protected static void responseFail(HttpExchange t, String response) throws IOException {
		t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		t.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
		t.sendResponseHeaders(500, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

	/**
	 * Gestisce la Response
	 * 
	 * @param t
	 * @param response
	 * @throws IOException
	 */
	protected static void response(HttpExchange t, String response) throws IOException {
		t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		t.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
		t.getResponseHeaders().add("Content-type", "plain/text");
		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

	

}
