package lib;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class SfUnzipper implements IUnZipper {

	public List<String> getTextStringsFromZip(ZipInputStream zipFile) throws IOException {

		byte[] buffer = new byte[1024];

		List<String> results = new ArrayList<String>();

		ZipEntry ze = zipFile.getNextEntry();

		while (ze != null) {

			String fileName = ze.getName();
			StringBuilder fileContents = new StringBuilder();
			if (fileName.endsWith(".txt")) {
				
				int read = 0;
				while ((read = zipFile.read(buffer, 0, 1024)) >= 0) {
					fileContents.append(new String(buffer, 0, read));
				}
				results.add(fileContents.toString());
			}
			ze = zipFile.getNextEntry();
		}
		return results;
	}

	public Boolean isTextFile(File file) {
		// TODO Auto-generated method stub
		return null;
	}

}
