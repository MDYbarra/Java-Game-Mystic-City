/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * UseMove.java
 * This file contains a class that implements the Move interface.
 * The class implementing Move performs the requests by calling the appropriate functions of other classes.
 * This class follows the command design pattern.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Daniel Rios (drios1) <drios8@uic.edu>
 */

/* USE artifact – Call the use( ) method of the artifact.
	At this point the only usable artifacts are those that have a non-zero keyPattern value. */
public class UseMove implements Move {

	private Character performer;
	private String requestedItem;

	public UseMove(Character performer, String requestedItem) {
		this.performer = performer;
		this.requestedItem = requestedItem;
		return;
	}


	@Override
	public void execute() {
		performer.useItem(requestedItem);
		return;
	}
}
