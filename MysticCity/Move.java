/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * Move.java
 * This file contains the Move interface.
 * The classes implementing Move perform the requests by calling the appropriate functions of other classes.
 * This class follows the command design pattern.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Daniel Rios (drios1) <drios8@uic.edu>
 */

public interface Move {
	/* Method to be overrided by implementing classes. Part of the Command design pattern. */
	public void execute();
}
