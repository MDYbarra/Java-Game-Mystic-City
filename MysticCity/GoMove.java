/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * GoMove.java
 * This file contains a class that implements the Move interface.
 * The class implementing Move performs the requests by calling the appropriate functions of other classes.
 * This class follows the command design pattern.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Daniel Rios (drios1) <drios8@uic.edu>
 */

/* GO <direction> or <direction> - attempts to move the target player in a given direction. */
public class GoMove implements Move {

	private Character performer;
	private String inputDir;

	/* Constructs the Object containing the information to execute later. */
	public GoMove(Character performer, String inputDir) {
		this.performer = performer;
		this.inputDir = inputDir;
		return;
	}


	@Override
	/* void - Calls character method to perform important work with enclosed information. */
	public void execute() {
		performer.goDir(inputDir);
		return;
	}
}
