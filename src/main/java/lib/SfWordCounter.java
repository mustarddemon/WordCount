package lib;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SfWordCounter implements IWordCounter {

	public HashMap<String, Integer> getWordHash(String text) {
		HashMap<String, Integer> results = new HashMap<String, Integer>();
		 Pattern pattern = Pattern.compile("\\w+");
		   
		    Matcher matcher = pattern.matcher(text);
		  
		    while (matcher.find()) {
		  
		         String key = matcher.group();
		        
		   
		         if (results.containsKey(matcher.group())) {
		        	 Integer value = results.get(key);
		        	 results.put(key, value +1);
		        	 
		         } else {
		        	 results.put(matcher.group(), 1);
		         }
		    }
		    
		    return results;

	}


}
