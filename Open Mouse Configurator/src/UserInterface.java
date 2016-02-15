import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class UserInterface{
	private Profile[] profiles;
	private JFrame mainWindow = new JFrame();
	private JComboBox<String> comboBox;
	private JButton edit;
	
	public static void main(String[] args) {
		new UserInterface();
	}
	
	private Profile[] generateProfiles() {
		Reader unformattedProfiles = new Reader(System.getProperty("user.home") + "/.omc_profiles");
		String[] data = unformattedProfiles.data.split("\n");

		Profile[] profiles = new Profile[data.length];
		for (int i = 0; i < data.length; i++) {
			String[] splittedData = data[i].split(";");
			String[] buttons = new String[splittedData.length - 1];
			for (int j = 1; j < splittedData.length; j++) {
				buttons[j - 1] = splittedData[j];
			}
			profiles[i] = new Profile(splittedData[0], buttons);
		}
		return profiles;
	}

	public UserInterface() {
		this.profiles = generateProfiles();
		buildElements();
		buildMainWindow();
	}
	
	private void buildElements(){
		comboBox = new JComboBox<String>();
		for (int i = 0; i < profiles.length; i++) {
			comboBox.addItem(profiles[i].getName());
		}

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
				for (int j = 0; j < profiles[i].getButtons().length; j++) {
					JTextField field = new JTextField(profiles[i].getButtons()[j].split("=")[0]);
					field.setEditable(false);
					mainWindow.add(field);
					field = new JTextField(profiles[i].getButtons()[j].split("=")[1]);
					field.setEditable(true);
					mainWindow.add(field);
				}
				mainWindow.add(new JButton("Save"));
				mainWindow.pack();
			}
		}
	}
}
