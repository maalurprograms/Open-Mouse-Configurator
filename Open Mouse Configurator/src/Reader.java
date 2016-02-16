import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class reads the content of a file in a string using a file object.
 * @author Jonas Cosandey
 *@see File
 *@see FileIO
 */

public class Reader extends FileIO {

	/**
	 * Static method which reads the content of a file into a string variable. Returns a string variable. 
	 * @param filePath The path to the file which should be read.
	 * @return String of the files content.
	 */
	
	public static String readFile(String filePath) {
		Reader reader = new Reader();
		reader.setErrorMessages();
		File file = new File(filePath);
		reader.checkFile(file);
		int length = (int) file.length();

		FileReader fr = null;
		try {
			fr = new FileReader(file);
			char[] chars = new char[length];
			fr.read(chars);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < chars.length; i++) {
				sb.append(chars[i]);
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
}
