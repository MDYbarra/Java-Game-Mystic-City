/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * LookMove.java
 * This file contains a class that implements the Move interface.
 * The class implementing Move performs the requests by calling the appropriate functions of other classes.
 * This class follows the command design pattern.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Daniel Rios (drios1) <drios8@uic.edu>
 */

/* LOOK - displays the artifacts in the location. */
public class LookMove implements Move {

	private Character performer;

	/* Constructs the Object containing the information to execute later. */
	public LookMove(Character performer) {
		this.performer = performer;
		return;
	}


	@Override
	/* void - Calls character method to perform important work with enclosed information. */
	public void execute() {
		((Player) performer).lookAround();
		return;
	}
}
