/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * InventoryMove.java
 * This file contains a class that implements the Move interface.
 * The class implementing Move performs the requests by calling the appropriate functions of other classes.
 * This class follows the command design pattern.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Daniel Rios (drios1) <drios8@uic.edu>
 */

/* INVENTORY or INVE – List the player’s inventory of artifacts, providing the value and “mobility” of each, but not keyValues.
	You may express mobility in any desired units, such as pounds, kilograms, cubic inches, or a made-up unit of your own.
	Also report totals. */
public class InventoryMove implements Move {

	private Character performer;

	/* Constructs the Object containing the information to execute later. */
	public InventoryMove(Character performer) {
		this.performer = performer;
		return;
	}


	@Override
	/* void - Calls character method to perform important work with enclosed information. */
	public void execute() {
		((Player) performer).displayInventory();
		return;
	}
}
