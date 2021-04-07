package it.esedra.corso.journal.http.handlers;

import java.io.IOException;

import javax.json.JsonObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import it.esedra.corso.journal.execeptions.HandleRequestException;

public abstract class Handler implements HttpHandler {
	/**
	 * Handler invocato per gestire gli scambi HTTP
	 * @param t
	 * @throws IOException 
	 */

	@Override
	public void handle(HttpExchange t) throws IOException {

		try {

			switch (t.getRequestMethod()) {
			case "GET": {
				ResponseHelper.response(t, this.handleGetRequest(t).toString());
				break;
			}
			case "POST": {
				ResponseHelper.response(t, this.handlePostRequest(t).toString());
				break;
			}
			case "PUT": {
				this.handlePutRequest(t);
				break;				
			}
			case "DELETE": {
				this.handleDeleteRequest(t);
				break;	
			}
			default:
				ResponseHelper.responseFail(t, "Invalid HTTP method");
				break;
			}

		} catch (HandleRequestException e) {
			ResponseHelper.responseFail(t, JsonHelper.ko(e.getMessage()).toString());
		}

	}
	
	public JsonObject handleGetRequest(HttpExchange httpExchange) throws HandleRequestException {
		return null;
	}

	public JsonObject handlePostRequest(HttpExchange httpExchange) throws HandleRequestException {
		return null;
	}

	public  JsonObject handlePutRequest(HttpExchange httpExchange) throws HandleRequestException {
		return null;
	}

	public  JsonObject handleDeleteRequest(HttpExchange httpExchange) throws HandleRequestException {
		return null;
	}

}
