package it.esedra.corso.journal.http;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class LocalHttpServer {

	private static final String BASEDIR = "web/journal";

	private static final int PORT = 9999;

	private HttpServer server;

	public static void main(String[] args) throws Exception {
		LocalHttpServer server = new LocalHttpServer();
		server.start();
	}

	void start() throws IOException {
		server = HttpServer.create(new InetSocketAddress(PORT), 0);

		server.createContext("/", new StaticFileHandler(BASEDIR));
		server.createContext("/js", new StaticFileHandler(BASEDIR + "/js"));
		server.createContext("/css", new StaticFileHandler(BASEDIR + "/css"));

		server.start();
	}

	public void stop() {
		server.stop(0);
	}
}