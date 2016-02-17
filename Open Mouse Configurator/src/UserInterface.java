import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;

//TODO Fixing bugs and eliminate redundant code

/**
 * This class is the interface between user and program. It contains the GUI.
 * @author Jonas Cosandey
 * @see JComponent
 * @see Event
 */

public class UserInterface {
	private Profile[] profiles;
	private JFrame mainWindow = new JFrame();
	private JComboBox<String> comboBox;
	private JButton edit;
	private JButton add;
	private int currentSelection;
	
	/**
	 * Used to initialize the program.
	 * @param args Standard
	 */
	
	public static void main(String[] args) {
		new UserInterface();
	}
	
	/**
	 * The constructor which calls the two methods buildElements and buildMainWindow.
	 */
	
	public UserInterface() {
		this.profiles = generateProfiles();
		buildElements();
		buildMainWindow();
	}

	private Profile[] generateProfiles() {
		String[] dataLines = Reader.readFile(System.getProperty("user.home") + "/.xbindkeyconf/omc_profiles").split("\n");
		Profile[] profiles = new Profile[dataLines.length];
		for (int i = 0; i < dataLines.length; i++) {
			String[] keys = new String[dataLines[i].split(";").length - 1];
			String[] commands = new String[dataLines[i].split(";").length - 1];
			for (int j = 1; j < dataLines[i].split(";").length; j++) {
				keys[j - 1] = dataLines[i].split(";")[j].split("=")[0];
				commands[j - 1] = dataLines[i].split(";")[j].split("=")[1];
			}
			profiles[i] = new Profile(dataLines[i].split(";")[0], keys, commands);
		}
		return profiles;
	}

	private void buildElements() {
		comboBox = new JComboBox<String>();
		for (int i = 0; i < profiles.length; i++) {
			comboBox.addItem(profiles[i].getName());
		}
		comboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String xbindkeysrcFileText = "";
				for (int j = 0; j < profiles.length; j++) {
					if (comboBox.getSelectedItem().toString() == profiles[j].getName()) {
						currentSelection = j;
						xbindkeysrcFileText = profiles[j].getSrcFileText();
						Writer.writeFile(System.getProperty("user.home") + "/.xbindkeysrc", xbindkeysrcFileText, false);
						restartService();
					}
				}			
			}
		});
		edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < profiles.length; i++) {
					if (i == comboBox.getSelectedIndex()) {
						edit(profiles[i].getName());
					}
				}
			}
		});
		add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				final JFrame addWindow = new JFrame();
				addWindow.setResizable(false);
				addWindow.setLayout(new FlowLayout());
				JTextField text = new JTextField("Name:");
				text.setEditable(false);
				addWindow.add(text);
				final JTextField name = new JTextField("Placeholder");
				addWindow.add(name);
				final JTextField[] keys = new JTextField[9]; // You can only use eight buttons
				final JTextField[] commands = new JTextField[9];
				for (int i = 0; i < 9; i++) {
					keys[i] = new JTextField("b:"+(i+1));
					keys[i].setEditable(false);
					addWindow.add(keys[i]);
					commands[i] = new JTextField("Placeholder");
					addWindow.add(commands[i]);
				}
				JButton save = new JButton("Save");
				save.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String newProfile = name.getText(); 
						for (int i = 0; i < keys.length; i++) {
							if (!commands[i].getText().equals("Placeholder") && !commands[i].getText().isEmpty() && !commands[i].getText().equals(null)) {
								newProfile += ";"+keys[i].getText()+"="+commands[i].getText();
							}
						}
						Writer.writeFile(System.getProperty("user.home") + "/.xbindkeyconf/omc_profiles", newProfile+"\n", true);
						addWindow.dispatchEvent(new WindowEvent(addWindow, WindowEvent.WINDOW_CLOSING));
						mainWindow.dispatchEvent(new WindowEvent(addWindow, WindowEvent.WINDOW_CLOSING));
						new UserInterface();
						restartService();
					}
				});
				addWindow.add(save);
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				double width = screenSize.getWidth();
				double height = screenSize.getHeight();
				addWindow.setLocation((int) width / 4, (int) height / 4);
				addWindow.setVisible(true);
				addWindow.pack();
			}
		});

	}

	private void buildMainWindow() {
		mainWindow.setResizable(false);
		mainWindow.setLayout(new FlowLayout());
		mainWindow.add(comboBox);
		mainWindow.add(edit);
		mainWindow.add(add);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		mainWindow.setLocation((int) width / 4, (int) height / 4);
		mainWindow.setVisible(true);
		mainWindow.pack();
	}
	
	private void edit(String profileName) {
		mainWindow.getContentPane().removeAll();
		buildMainWindow();
		for (int i = 0; i < profiles.length; i++) {
			if (profileName == profiles[i].getName()) {
				for (int j = 0; j < profiles[i].keys.length; j++) {
					profiles[i].keysTextFiled[j] = new JTextField(profiles[i].keys[j]);
					profiles[i].keysTextFiled[j].setEditable(false);
					mainWindow.add(profiles[i].keysTextFiled[j]);
					profiles[i].commandsTextFiled[j] = new JTextField(profiles[i].commands[j]);
					profiles[i].commandsTextFiled[j].setEditable(true);
					mainWindow.add(profiles[i].commandsTextFiled[j]);
				}
				JButton save = new JButton("Save");
				save.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String xbindkeysrcFileText = "";
						String omc_profilesText = "";
						for (int j = 0; j < profiles.length; j++) {
							if (j == currentSelection) {
								xbindkeysrcFileText = profiles[j].getSrcFileText();
							}
							omc_profilesText += profiles[j].getProfileFileText() + "\n";
						}
						//TODO Check if the directory .xbindkeyconf exists.
						Writer.writeFile(System.getProperty("user.home") + "/.xbindkeyconf/omc_profiles",omc_profilesText, false);
						Writer.writeFile(System.getProperty("user.home") + "/.xbindkeysrc", xbindkeysrcFileText, false);
						for (int j = 0; j < profiles.length; j++) {
							for (int k = 0; k < profiles[j].keys.length; k++) {
								try {
									profiles[j].keys[k] = profiles[j].keysTextFiled[k].getText();
									profiles[j].commands[k] = profiles[j].commandsTextFiled[k].getText();
								} catch (Exception e2) {}
							}
						}
					restartService();
					}
				});
				mainWindow.add(save);
				mainWindow.pack();
			}
		}
	}
	
	private void restartService(){
		Process p;
		try {
			p = Runtime.getRuntime().exec("killall xbindkey");
		    p.waitFor();
		    p = Runtime.getRuntime().exec("xbindkey");
		    p.waitFor();
		} catch(Exception e){}
	}
}
