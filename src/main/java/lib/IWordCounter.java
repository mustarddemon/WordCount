package lib;
import java.util.HashMap;

public interface IWordCounter {
	
	public HashMap<String, Integer> getWordHash(String line);

}
