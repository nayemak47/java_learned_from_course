package it.esedra.corso.journal.http.handlers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.sun.net.httpserver.HttpExchange;
import it.esedra.corso.collections.interfaces.Collection;
import it.esedra.corso.journal.Video;
import it.esedra.corso.journal.execeptions.HandleRequestException;
import it.esedra.corso.journal.service.VideoService;

public class VideoHandler extends Handler {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public JsonObject handleGetRequest(HttpExchange httpExchange) throws HandleRequestException {
		try {
			URI url = httpExchange.getRequestURI();
			switch (url.getPath()) {
			case "/video": {
				Collection<Video> videos = VideoService.getAll();
				return JsonHelper.ok(videos.toJson()); //JsonHelper.ok(video.toJson());
			}
			default:
				throw new HandleRequestException("Metodo non supportato");
			}
		} catch (Exception e) {
			throw new HandleRequestException(e.getMessage(), e);
		}
	}
	public JsonObject handlePostRequest(HttpExchange httpExchange) throws HandleRequestException {

		Video video = null;
		
		try {
			InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();

			JsonReader reader = Json.createReader(new StringReader(query));
			JsonObject videoObject = reader.readObject();
			reader.close();

			video = VideoService.update(videoObject);

		} catch (Exception e) {
			LOGGER.severe(e.toString());
		}
		
		if(video == null) {
			throw new HandleRequestException("Insert Error.");
		} else {
			return JsonHelper.ok(video.toJson());
		}

	}

	public JsonObject handlePutRequest(HttpExchange httpExchange) throws HandleRequestException {
		throw new HandleRequestException("Not Implemented Yet.");
	}

	public JsonObject handleDeleteRequest(HttpExchange httpExchange) throws HandleRequestException {
		throw new HandleRequestException("Not Implemented Yet.");
	}
}
