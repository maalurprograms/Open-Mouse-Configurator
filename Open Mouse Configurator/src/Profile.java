
public class Profile {

	private String name;
	private String[] buttons;

	public Profile(String name, String[] buttons) {
		this.name = name;
		this.buttons = buttons;
	}

	public void changeName(String newName) {
		this.name = newName;
	}

	public String getName() {
		return this.name;
	}

	public String[] getButtons() {
		return this.buttons;
	}

}
