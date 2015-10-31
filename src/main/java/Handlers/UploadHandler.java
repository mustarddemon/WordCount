package Handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipInputStream;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import Models.ErrorResponse;
import Models.WordCountResults;
import lib.ConfigUtil;
import lib.IUnZipper;
import lib.ParseFileUtil;
import lib.SfUnzipper;

@SuppressWarnings("restriction")
public class UploadHandler implements HttpHandler {

	private Integer getNumberOfThreads() {
		try {
			return new ConfigUtil().getConfigObject().getNumberOfThreads();
		} catch (Exception e) {
			System.out.println("WARNING:  UNABLE TO READ CONFIG FILE DEFAULTING TO 1 THREAD");
			return 1;
		}
	}

	private void respondWithError(HttpExchange ex, Integer code, String message) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		OutputStream os = ex.getResponseBody();
		ErrorResponse error = new ErrorResponse(code, message);
		String response;
		try {
			response = mapper.writeValueAsString(error);
			ex.sendResponseHeaders(405, response.length());

			os.write(response.getBytes());
			os.close();
		} catch (Exception e) {
			response = "Unexpected server error occurred trying to process error " + message;
			os.write(response.getBytes());
			os.close();

		}

	}

	public void handle(HttpExchange ex) throws IOException {
		String verb = ex.getRequestMethod();
		OutputStream os = ex.getResponseBody();
		InputStream requestBody = ex.getRequestBody();
		if (verb.equals("POST") == false) {
			respondWithError(ex, 405, "Unexpected verb " + verb + "only POST is supported");
			return;
		}

		ZipInputStream zis = new ZipInputStream(requestBody);
		IUnZipper unzipper = new SfUnzipper();
		List<String> results = unzipper.getTextStringsFromZip(zis);
		if (results.isEmpty()) {
			respondWithError(ex, 400, "Either no file received, or file received had no text files");
			return;
		}
		
		HashMap<String, Integer> hash = new ParseFileUtil().runThreads(results, getNumberOfThreads());
		ObjectMapper mapper = new ObjectMapper();
		WordCountResults finalResults = new WordCountResults(hash, 10);
		String response = mapper.writeValueAsString(finalResults);
		ex.sendResponseHeaders(200, response.length());
		os.write(response.getBytes());
		os.close();

	}
}
