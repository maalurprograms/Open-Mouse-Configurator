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
	
	public JTextField[] keysTextFiled;
	public JTextField[] commandsTextFiled;
	public String[] keys;
	public String[] commands;
	private String name;

	public Profile(String name, String[] keys, String[] commands) {
		this.name = name;
		this.keys = keys;
		this.commands = commands;
		keysTextFiled = new JTextField[keys.length];
		commandsTextFiled = new JTextField[commands.length];
	}

	public String getName() {
		return this.name;
	}
	
	public String getSrcFileText() {
		String xbindkeysrc = "";
		for (int i = 0; i < keys.length; i++) {
			xbindkeysrc += '"' + "python2 /home/jonas/.xbindkeyconf/sendkey.py " + commandsTextFiled[i].getText() + '"' + "\n  " + keysTextFiled[i].getText() + "\n";
		}
		return xbindkeysrc;
	}
	
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
