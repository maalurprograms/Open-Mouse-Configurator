import javax.swing.JTextField;

/**
 * This class saves the mapped buttons and generates the text for the config files.
 * It also saves the JTextField for the buttons in the GUI.
 * 
 * @author Jonas Cosandey
 * 
 * @see JTextField
 */

public class Profile {
	
	/**
	 * Array of all keyfields. As example: b:3=Ctrl+S. That is the part with b:3.
	 * This is a JTextField.
	 * @see JTextField
	 */
	public JTextField[] keysTextFiled;
	/**
	 * Array of all commandfields. As example: b:3=Ctrl+S. That is the part with Ctrl+S.
	 * This is a JTextField.
	 * @see JTextField
	 */
	public JTextField[] commandsTextFiled;
	/**
	 * This array contains all values of the keys.
	 */
	public String[] keys;
	/**
	 * This array contains all values of the commands.
	 */
	public String[] commands;
	private String name;

	/**
	 * Sets the instance variables.
	 * 
	 * @param name The name of the profile.
	 * @param keys All keys read from the profile file.
	 * @param commands All commands read from the profile file.
	 */
	public Profile(String name, String[] keys, String[] commands) {
		this.name = name;
		this.keys = keys;
		this.commands = commands;
		keysTextFiled = new JTextField[keys.length];
		commandsTextFiled = new JTextField[commands.length];
	}
	
	/**
	 * Get the name of the profile.
	 * 
	 * @return The name of the profile.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Generates the text for the configuration file .xbindkeysrc.
	 * @return The configuration text for the .xbindkeysrc file.
	 */
	public String getSrcFileText() {
		String xbindkeysrc = "";
		for (int i = 0; i < keys.length; i++) {
			xbindkeysrc += '"' + "python2 /home/jonas/.xbindkeyconf/sendkey.py " + commandsTextFiled[i].getText() + '"' + "\n  " + keysTextFiled[i].getText() + "\n";
		}
		return xbindkeysrc;
	}
	
	/**
	 * Generates the text for the configuration file omc_profiles.
	 * @return The configuration text for the omc_profiles file.
	 */
	public String getProfileFileText() {
		String omc_profiles = name;
		for (int i = 0; i < commands.length; i++) {
			try{
				omc_profiles += ";"+keysTextFiled[i].getText()+"="+commandsTextFiled[i].getText();
			} catch(NullPointerException e) {
				omc_profiles += ";"+keys[i]+"="+commands[i];
			}
		}
		return omc_profiles;
	}

}
