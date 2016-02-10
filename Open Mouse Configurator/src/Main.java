import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;

public class Main {

	private Profile[] profiles;
	private final JFrame frame = new JFrame();

	public static void main(String[] args) {

		Main main = new Main();
		main.generateProfiles();

		main.frame.setSize(100, 500);
		main.frame.setResizable(false);

		JComboBox c = new JComboBox();
		for (int i = 0; i < main.profiles.length; i++) {
			c.addItem(main.profiles[i].getName());
			c.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.out.println("changed");
				}
			});
		}
		c.setEditable(true);
		main.frame.add(c);

		main.frame.setVisible(true);

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
}
