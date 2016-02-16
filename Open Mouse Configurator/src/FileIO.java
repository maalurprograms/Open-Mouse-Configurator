import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FileIO {

	private Map<String, String> errorMessages = new HashMap<String, String>();

	protected void checkFile(File file) {
		if (file.exists()) {
			if (file.canRead() && file.canWrite()) {

			} else {
				terminate(this.errorMessages.get("permissions"), "Wrong permissions");
			}

		} else {
			terminate(this.errorMessages.get("inexistent"), ".omc_profiles does not exists");
		}
	}

	protected void terminate(String message, String title) {
		JOptionPane.showMessageDialog(new JFrame(), message, title, JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}

	protected void setErrorMessages() {
		this.errorMessages.put("inexistent",
				"The configuration file .omc_profiles does not exist.\nYou can just create it in youre home directory.\nExample: /home/username/.omc_profiles");
		this.errorMessages.put("permissions",
				"The configuration file .omc_profiles has wrong permissions.\nI cant read/write. Please check the permissions of the file and give youre user permission to read/write.\nYou can use(in terminal): chown [youre username] ~/.omc_profiles\nYou can also change the permissions via GUI.");
	}
}
