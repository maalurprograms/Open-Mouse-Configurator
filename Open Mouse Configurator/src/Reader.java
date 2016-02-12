import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Reader {

	private final Map<String, String> errorMessages = new HashMap<String, String>();
	private final String filePath;
	public final String data;

	public Reader(String filePath) {
		this.filePath = filePath;
		setErrorMessages();
		this.data = readFile();
	}

	private String readFile() {
		File file = new File(this.filePath);
		checkFile(file);
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

	private void checkFile(File file) {
		if (file.exists()) {
			if (file.canRead() && file.canWrite()) {

			} else {
				terminate(this.errorMessages.get("permissions"), "Wrong permissions");
			}

		} else {
			terminate(this.errorMessages.get("inexistent"), ".omc_profiles does not exists");
		}
	}

	private void terminate(String message, String title) {
		JOptionPane.showMessageDialog(new JFrame(), message, title, JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}

	private void setErrorMessages() {
		this.errorMessages.put("inexistent",
				"The configuration file .omc_profiles does not exist.\nYou can just create it in youre home directory.\nExample: /home/username/.omc_profiles");
		this.errorMessages.put("permissions",
				"The configuration file .omc_profiles has wrong permissions.\nI cant read/write. Please check the permissions of the file and give youre user permission to read/write.\nYou can use(in terminal): chown [youre username] ~/.omc_profiles\nYou can also change the permissions via GUI.");
	}
}
