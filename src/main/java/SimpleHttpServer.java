import java.io.IOException;
import java.net.InetSocketAddress;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.sun.net.httpserver.HttpServer;
import Handlers.UploadHandler;
import lib.ConfigUtil;

public class SimpleHttpServer {
	
	private static Integer getPort() {
		try {
			return new ConfigUtil().getConfigObject().getHttpPort();
		} catch (Exception e) {
			System.out.println("WARNING COULD NOT PARSE CONFIG FILE DEFAULTING TO PORT 8000");
			return 8000;
		}
	}

	@SuppressWarnings("restriction")
	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(getPort()), 0);
		server.createContext("/zipfile", new UploadHandler());
		server.setExecutor(null); // creates a default executor
		server.start();
	}

}