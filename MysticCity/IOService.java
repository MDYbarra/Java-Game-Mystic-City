/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * IOService.java
 * 
 * This file contains the IOService subsystem which manages how the program will direct output.
 * It is used for creating GUIs for characters and swapping GUIs during runtime.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Daniel Rios (drios1) <drios8@uic.edu>
 */
 
 
public class IOService {
	public static final int TEXT = 0;
	public static final int GUI_1 = 1;
	public static final int GUI_2 = 2;
	public static final int GUI_3 = 3;
	public static final int GUI_Npc = 4;
	
	private UserInterface implementor;

	public IOService(int setting, Character user) {
		this.selectInterface(setting, user);
		return;
	}
	
	public static IOService getIO() {
		return Game.getCurrentCharacter().getIO();
	}
	
	
	/* void - Displays the string passed as an argument. */
	public void display(String str) {
		implementor.display(str);
	}
	
	/* String - Gets a line of user input. */
	public String getLine() {
		return implementor.getLine();
	}
	
	/* void - Sets the UI according to the integer passed as a parameter. */
	public void selectInterface(int selectedOption, Character chr) {
		switch(selectedOption) {
			case TEXT:
				implementor = new TextInterface();
				break;
			case GUI_1:
				implementor = new GUIdrios8(chr);
				break;
			case GUI_2:
				implementor = new GUImybarra(chr);
				break;
			case GUI_3:
				implementor = new GUIajames36(chr);
				break;
			case GUI_Npc:
				implementor = new GUInpc(chr);
				break;
			default:
				System.out.println("Error default case in UI selection triggered.");
		}
		return;
	}
}
