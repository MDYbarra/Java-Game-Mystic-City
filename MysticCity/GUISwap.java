/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * GUISwap.java
 * This file contains a class that implements the Move interface.
 * The class implementing Move performs the requests by calling the appropriate functions of other classes.
 * This class follows the command design pattern.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Daniel Rios (drios1) <drios8@uic.edu>
 */

/* GUI # – Swaps the player's GUI in use. */
public class GUISwap implements Move {

	private Character performer;
	private int requestedGUI;

	/* Constructs the Object containing the information to execute later. */
	public GUISwap(Character performer, String guiNumber) {
		this.performer = performer;
		this.requestedGUI = Integer.parseInt(guiNumber);
		return;
	}


	@Override
	/* void - Calls character method to perform important work with enclosed information. */
	public void execute() {
		((Player) performer).swapGUI(requestedGUI);
		return;
	}
}