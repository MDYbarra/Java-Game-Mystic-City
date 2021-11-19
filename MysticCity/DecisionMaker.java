/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * DecisionMaker.java
 * This file contains the DecisionMaker Interface that the PlayerUI & NpcAI classes will implement.
 * These classes/interface create Moves for the Characters to execute later as part of the command design pattern.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Daniel Rios (drios1) <drios8@uic.edu>
 */

import java.util.Random;
import java.util.Scanner;

public interface DecisionMaker {

	/* Move - Constructs a Move to be executed later. */
	public Move getMove(Character ch, Place pl);

	/* String - Prints the type of DescisionMaker in use. */
	public String print();

}
