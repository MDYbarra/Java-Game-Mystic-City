/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * GetMove.java
 * This file contains a class that implements the Move interface.
 * The class implementing Move performs the requests by calling the appropriate functions of other classes.
 * This class follows the command design pattern.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Daniel Rios (drios1) <drios8@uic.edu>
 */

/* GET artifact – Will check to see if the named artifact is present and attainable,
	and if so, will transfer it from the Place to the player’s inventory. */
public class GetMove implements Move {

	private Character performer;
	private String requestedItem;

	/* Constructs the Object containing the information to execute later. */
	public GetMove(Character performer, String requestedItem) {
		this.performer = performer;
		this.requestedItem = requestedItem;
		return;
	}


	@Override
	/* void - Calls character method to perform important work with enclosed information. */
	public void execute() {
		performer.getItem(requestedItem);
		return;
	}
}
