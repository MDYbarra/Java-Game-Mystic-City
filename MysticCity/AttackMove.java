/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * AttackMove.java
 * This file contains a class that implements the Move interface.
 * The class implementing Move performs the requests by calling the appropriate functions of other classes.
 * This class follows the command design pattern.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Daniel Rios (drios1) <drios8@uic.edu>
 */

/* ATTACK - attacks another character located in the same place. */
public class AttackMove implements Move {

	private Character performer;
	private String targetName;

	/* Constructs the Object containing the information to execute later. */
	public AttackMove(Character performer, String targetName) {
		this.performer = performer;
		this.targetName = targetName;
		return;
	}


	@Override
	/* void - Calls character method to perform important work with enclosed information. */
	public void execute() {
		performer.attack(targetName);
		return;
	}
}
