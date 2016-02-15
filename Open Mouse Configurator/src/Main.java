
public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		Profile[] profiles = main.generateProfiles();
		UserInterface UI = new UserInterface(profiles);
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
}
