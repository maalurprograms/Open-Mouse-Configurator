import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class UserInterface {
	private Profile[] profiles;
	private JFrame mainWindow = new JFrame();
	private String currentSelection;
	
	public UserInterface() {
		generateProfiles();
		
		JComboBox comboBox = new JComboBox();
		for (int i = 0; i < profiles.length; i++) {
			comboBox.addItem(profiles[i].getName());
			comboBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					changeFront();
				}
			});
		}
		// c.setEditable(true);
		
		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				edit();
			}
		});
		
		buildMainWindow(new JComponent[] {comboBox, edit});
	}
	
	private void buildMainWindow(JComponent[] elements){
		mainWindow.setSize(500, 500);
		mainWindow.setResizable(false);
		mainWindow.setLayout(new FlowLayout());
		
		for (int i = 0; i < elements.length; i++) {
			mainWindow.add(elements[i]);
		}
		
		mainWindow.setVisible(true);
	}

	private void generateProfiles() {
		Reader unformattedProfiles = new Reader(System.getProperty("user.home") + "/.omc_profiles");
		String[] data = unformattedProfiles.data.split("\n");

		profiles = new Profile[data.length];
		for (int i = 0; i < data.length; i++) {
			String[] splittedData = data[i].split(";");
			String[] buttons = new String[splittedData.length - 1];
			for (int j = 1; j < splittedData.length; j++) {
				buttons[j - 1] = splittedData[j];
			}
			profiles[i] = new Profile(splittedData[0], buttons);
		}

	}

	private void changeFront() {
		System.out.println("ok");
	}
	
	private void edit() {
		System.out.println("ok");
	}
}
