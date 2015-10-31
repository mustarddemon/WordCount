import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import Handlers.UploadHandler;

public class SimpleHttpServer {

	@SuppressWarnings("restriction")
	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/zipfile", new UploadHandler());
		server.setExecutor(null); // creates a default executor
		server.start();
	}

}