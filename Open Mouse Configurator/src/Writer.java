import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Writes text in a file. Extends FileIO
 * @author Jonas Cosandey
 * @see File
 * @see FileIO
 */

public class Writer extends FileIO{

	/**
	 * Static method that opens a File object and writes in it.
	 * @param filePath Path to the file which should be written.
	 * @param content Content which should be written into the file.
	 * @see FileWriter
	 */
	
	public static void writeFile(String filePath, String content, boolean append) {
		Writer writer = new Writer();
		writer.setErrorMessages();
		File file = new File(filePath);
		file.getParentFile().mkdirs();
		FileWriter fw = null;
		try {
			fw = new FileWriter(file, append);
			fw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
