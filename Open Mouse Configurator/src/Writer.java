import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer extends FileIO{

	public static void writeFile(String filePath, String content) {
		Writer writer = new Writer();
		writer.setErrorMessages();
		File file = new File(filePath);
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
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
