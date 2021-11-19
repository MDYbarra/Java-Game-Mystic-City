/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * TextInterface.java
 * This file contains the TextInterface interface.
 * It runs IO through the terminal window how IO is normally handled.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Daniel Rios (drios1) <drios8@uic.edu>
 */

public class TextInterface implements UserInterface {
	
	public TextInterface() {
		return;
	}
	
	@Override
	/* Displays the string passed as an argument. */
	public void display(String str) {
		//decide if newlines should be done by println or put into the string by hand
		System.out.println(str);
		return;
	}
	
	
	@Override
	/* Gets a line of user input. */
	public String getLine() {
		return KeyboardScanner.getKeyboardScanner().nextLine();
	}
}