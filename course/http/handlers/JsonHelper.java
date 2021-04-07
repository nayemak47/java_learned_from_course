package it.esedra.corso.journal.http.handlers;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

public class JsonHelper<T> {
	/**
	 * Gestisce la creazione di oggetti Json
	 * 
	 * @param data
	 * @param message
	 * @return data
	 * @return message
	 */
	
	protected static JsonObject ok(JsonObject data) {
		return Json.createObjectBuilder().add("status", "ok").add("message", "").add("data", data).build();
	}
	
	protected static JsonObject ok(JsonArray data) {
		return Json.createObjectBuilder().add("status", "ok").add("message", "").add("data", data.toString()).build();
	}

	protected static JsonObject ko(String message) {
		return Json.createObjectBuilder().add("status", "ko").add("message", message).add("data", "").build();
	}

}
