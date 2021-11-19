/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * UserInterface.java
 * This file contains the UserInterface interface.
 * Methods to be overrided by implementing GUI classes.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Daniel Rios (drios1) <drios8@uic.edu>
 */

public interface UserInterface {
	/* Displays the string passed as an argument. */
	public void display(String str);
	
	/* Gets a line of user input. */
	public String getLine();
}