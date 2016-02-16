import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;

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
				// TODO Auto-generated method stub
				for (int j = 0; j < profiles.length; j++) {
					if (comboBox.getSelectedItem().toString() == profiles[j].getName()) {
						currentSelection = j;
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

	}

	private void buildMainWindow() {
		mainWindow.setSize(500, 500);
		mainWindow.setResizable(false);
		mainWindow.setLayout(new FlowLayout());
		mainWindow.add(comboBox);
		mainWindow.add(edit);
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
						Writer.writeFile(System.getProperty("user.home") + "/.xbindkeyconf/omc_profiles",
								omc_profilesText);
						Writer.writeFile(System.getProperty("user.home") + "/Desktop/xbindkeysrc", xbindkeysrcFileText);
					}
				});
				mainWindow.add(save);
				mainWindow.pack();
			}
		}
	}
}
