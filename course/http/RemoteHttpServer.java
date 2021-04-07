package it.esedra.corso.journal.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpServer;

import it.esedra.corso.journal.http.handlers.AuthorHandler;
import it.esedra.corso.journal.http.handlers.ChapterHandler;
import it.esedra.corso.journal.http.handlers.ImageHandler;
import it.esedra.corso.journal.http.handlers.JournalHandler;
import it.esedra.corso.journal.http.handlers.ParagraphHandler;
import it.esedra.corso.journal.http.handlers.UserHandler;
import it.esedra.corso.journal.http.handlers.VideoHandler;
import it.esedra.corso.journal.log.JournalLogger;


public class RemoteHttpServer {
	 private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	 
	public static void main(String[] args) throws Exception {
		try {
            JournalLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
        }
		new RemoteHttpServer();
	}

	public RemoteHttpServer() throws IOException {
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
			server.createContext("/author", new AuthorHandler());
			server.createContext("/journal", new JournalHandler());	
			server.createContext("/image", new ImageHandler());	
			server.createContext("/user", new UserHandler());			
			server.createContext("/video", new VideoHandler());					
			server.createContext("/paragraph", new ParagraphHandler());
			server.createContext("/chapter", new ChapterHandler());
			
			server.setExecutor(null); // creates a default executor
			server.start();			
		} catch (Exception e) {
			LOGGER.severe(e.toString());
		}

	}

	

	private static String streamToString(InputStream inputStream) {
		String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
		return text;
	}

	public static String jsonGetRequest(String urlQueryString) {
		String json = null;
		try {
			URL url = new URL(urlQueryString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("charset", "utf-8");
			connection.connect();
			InputStream inStream = connection.getInputStream();
			json = streamToString(inStream); // input stream to string
		} catch (IOException ex) {
			LOGGER.severe(ex.toString());
		}
		return json;
	}

}
