import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;

import Models.Config;
import Models.ResultEntry;
import Models.WordCountResults;
import lib.ConfigUtil;
import lib.IUnZipper;
import lib.IWordCounter;
import lib.ParseFile;
import lib.ParseFileUtil;
import lib.SfUnzipper;
import lib.SfWordCounter;

public class WordCounterTest {
	String SIMPLE_ZIP_FILE = "oneword.txt.zip";
	String NO_TEXT_ZIP = "nottext.nottext.zip";
	String WITH_AND_WITHOUT_TEXT = "onetextonenot.zip";
	String MANY_SEPARATORS = "manyseperators.txt.zip";
	String MULTI_THREAD = "multithread.zip";

	private ZipInputStream getInputStream(String fileName) throws FileNotFoundException {
		URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);

		File file = new File(url.getPath());

		ZipInputStream zis = new ZipInputStream(new FileInputStream(file));

		return zis;

	}

	@Test
	public void simpleZipFile() throws IOException {

		ZipInputStream zis = getInputStream(SIMPLE_ZIP_FILE);

		IUnZipper test = new SfUnzipper();
		List<String> results = test.getTextStringsFromZip(zis);

		assertTrue(results.size() == 1);
		assertTrue(results.get(0).equals("one"));

		IWordCounter counter = new SfWordCounter();
		HashMap<String, Integer> hash = counter.getWordHash(results.get(0));

		assertTrue(hash.size() == 1);
		assertTrue(hash.containsKey("one"));
		assertTrue(hash.get("one") == 1);
	}

	@Test
	public void zipWithoutText() throws IOException {

		ZipInputStream zis = getInputStream(NO_TEXT_ZIP);

		IUnZipper test = new SfUnzipper();
		List<String> results = test.getTextStringsFromZip(zis);

		assertTrue(results.size() == 0);
	}

	@Test
	public void zipWithAndWithoutText() throws IOException {

		ZipInputStream zis = getInputStream(WITH_AND_WITHOUT_TEXT);

		IUnZipper test = new SfUnzipper();
		List<String> results = test.getTextStringsFromZip(zis);

		assertTrue(results.size() == 1);
		assertTrue(results.get(0).equals("one"));

		IWordCounter counter = new SfWordCounter();
		HashMap<String, Integer> hash = counter.getWordHash(results.get(0));

		assertTrue(hash.size() == 1);
		assertTrue(hash.containsKey("one"));
		assertTrue(hash.get("one") == 1);
	}

	@Test
	public void knownFileWithDiffSeperators() throws IOException {
		ZipInputStream zis = getInputStream(MANY_SEPARATORS);

		IUnZipper test = new SfUnzipper();
		List<String> results = test.getTextStringsFromZip(zis);

		assertTrue(results.size() == 1);

		IWordCounter counter = new SfWordCounter();
		HashMap<String, Integer> hash = counter.getWordHash(results.get(0));
		assertTrue(hash.size() == 13);
		assertTrue(hash.containsKey("Common"));
		assertTrue(hash.get("Common") == 6);
	}

	@Test
	public void singleThread() throws IOException, InterruptedException {
		ZipInputStream zis = getInputStream(MANY_SEPARATORS);

		IUnZipper test = new SfUnzipper();
		List<String> results = test.getTextStringsFromZip(zis);

		assertTrue(results.size() == 1);

		ParseFile thread = new ParseFile(results.get(0));
		thread.run();
		while (thread.isDone() == false) {
			Thread.sleep(1);
		}

		HashMap<String, Integer> hash = thread.getResults();
		assertTrue(hash.size() == 13);
		assertTrue(hash.containsKey("Common"));
		assertTrue(hash.get("Common") == 6);
	}

	@Test
	public void multipleThread() throws IOException, InterruptedException {
		ZipInputStream zis = getInputStream(MULTI_THREAD);

		IUnZipper test = new SfUnzipper();
		List<String> results = test.getTextStringsFromZip(zis);
		HashMap<String, Integer> hash = new ParseFileUtil().runThreads(results, 3);

		assertTrue(hash.size() == 13);
		assertTrue(hash.containsKey("Common"));
		assertTrue(hash.get("Common") == 30);
	}
	
	@Test
	public void resultSortingTest() {
		HashMap<String, Integer> fakeResults = new HashMap<String, Integer>();
		fakeResults.put("one", 1);
		fakeResults.put("ten", 10);
		fakeResults.put("five", 5);
		
		WordCountResults test = new WordCountResults(fakeResults, 3);
		List<ResultEntry> sortedResults = test.getResults();
		assertTrue(sortedResults.get(0).getWord() == "ten");
		assertTrue(sortedResults.get(1).getWord() == "five");
		assertTrue(sortedResults.get(2).getWord() == "one");
		
	}
	
	@Test
	public void resultTruncatingTest() {
		HashMap<String, Integer> fakeResults = new HashMap<String, Integer>();
		fakeResults.put("one", 1);
		fakeResults.put("ten", 10);
		fakeResults.put("five", 5);
		
		WordCountResults test = new WordCountResults(fakeResults, 1);
		List<ResultEntry> sortedResults = test.getResults();
		assertTrue(sortedResults.size() == 1);
		assertTrue(sortedResults.get(0).getWord() == "ten");

		
	}
	
	@Test
	public void testConfig() throws JsonParseException, JsonMappingException, IOException {
		Config cfg = new ConfigUtil().getConfigObject();
		assertTrue(cfg.getNumberOfThreads() ==3);
	}

}
