/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * DropMove.java
 * This file contains a class that implements the Move interface.
 * The class implementing Move performs the requests by calling the appropriate functions of other classes.
 * This class follows the command design pattern.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Daniel Rios (drios1) <drios8@uic.edu>
 */

/* DROP artifact – Inverse of GET, if the artifact is in the player’s inventory. */
public class DropMove implements Move {

	private Character performer;
	private String requestedItem;

	/* Constructs the Object containing the information to execute later. */
	public DropMove(Character performer, String requestedItem) {
		this.performer = performer;
		this.requestedItem = requestedItem;
		return;
	}


	@Override
	/* void - Calls character method to perform important work with enclosed information. */
	public void execute() {
		performer.dropItem(requestedItem);
		return;
	}
}
