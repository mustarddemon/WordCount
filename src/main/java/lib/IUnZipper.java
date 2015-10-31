package lib;

import java.io.IOException;
import java.util.List;
import java.util.zip.ZipInputStream;

public interface IUnZipper {
	
	public List<String> getTextStringsFromZip(ZipInputStream zipFile) throws IOException;

}
