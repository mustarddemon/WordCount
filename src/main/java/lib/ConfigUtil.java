package lib;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;

import Models.Config;

public class ConfigUtil {

	public Config getConfigObject() throws JsonParseException, JsonMappingException, IOException {
		URL url = Thread.currentThread().getContextClassLoader().getResource("config.json");

		File file = new File(url.getPath());

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(file, Config.class);

	}

}
