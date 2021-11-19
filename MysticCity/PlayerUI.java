/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * PlayerUI.java
 * This file contains the PlayerUI class that implements DecisionMake.
 * These classes/interface create Moves for the Characters to execute later as part of the command design pattern.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Daniel Rios (drios1) <drios8@uic.edu>
 */

import java.util.Scanner;

/*
  TODO:
	- Change PlayerUI to use IO Objects
*/


public class PlayerUI implements DecisionMaker {

	@Override
	/* String – Used for debugging, prints out type of DescisionMaker. */
	public String print() {
		return "PlayerUI";
	}


	@Override
	/* Move - Gets user input and constructs a Move accordingly. */
	public Move getMove(Character chr, Place pl) {
		Move action;  //Delcare Move object to be assigned a value later

		//Display place to player
		IOService.getIO().display("\n---------------------------------------------------------");
		if(pl.getLightStatus() || chr.hasALamp())  //If place is lit or Player has a Lamp display area desc
			pl.display();
		else
			IOService.getIO().display("It's too dark in here to see anything.");

		
		//Call checks for hunger and health
		if( ((Player) chr).starve() ) 
			return null;  //Player has starved to death so return null
		if(pl.isHarmful()) {
			IOService.getIO().display("This area is not suitable to support life.");
			if(chr.damage()) 
				return null;  //Player has run out of health so return null
		}
		
		
		//Get user input
		IOService.getIO().display("What do you want to do, " + chr.charName() + "? >> ");
		String userInput = IOService.getIO().getLine();

		//Filter out beginning and trailing whitespace and force lowercase
		String filteredInput = userInput.toLowerCase();
		filteredInput = filteredInput.trim();

		//System.out.println("User input: " + filteredInput + ".");  //Debug print statemeent to inspect input

		while(true) {  //Loop until a proper command is given

			/* GO <direction> or <direction> - attempts to move the player in a given direction. */
			if ( filteredInput.matches("(go \\w+.*)")  && Direction.isADirection( filteredInput.substring(3,userInput.length()) ) ) {
				action = new GoMove(chr, filteredInput.substring(3, userInput.length()) );  //Extract requested Direction name from string input
				break;
			}

			/* GO <direction> or <direction> - attempts to move the player in a given direction. */
			else if( Direction.isADirection(filteredInput) ) {  //Checks if input given is a valid direction command
				action = new GoMove(chr, filteredInput);
				break;
			}

			/* LOOK - displays the artifacts in the currentPlace. */
			else if(filteredInput.matches("look")) {
				action = new LookMove(chr);
				break;
			}

			/* GET artifact – Will check to see if the named artifact is present and attainable,
				and if so,will transfer it from the Place to the player’s inventory. */
			else if( filteredInput.matches("get \\w+.*") ) {
				action = new GetMove(chr, filteredInput.substring(4,userInput.length()));  //Extract requested artifact name from string input
				break;
			}

			/* DROP artifact – Inverse of GET, if the artifact is in the player’s inventory. */
			else if( filteredInput.matches("drop \\w+.*") ) {
				action = new DropMove(chr, filteredInput.substring(5,userInput.length()));  //Extract requested artifact name from string input
				break;
			}

			/* USE artifact – Call the use( ) method of the artifact.
				At this point the only usable artifacts are those that have a non-zero keyPattern value. */
			else if( filteredInput.matches("use \\w+.*") ) {
				action = new UseMove(chr, filteredInput.substring(4,userInput.length()));  //Extract requested artifact name from string input
				break;
			}

			/* ATTACK – Player attacks another character. */
			else if( filteredInput.matches("attack \\w+.*") ) {
				action = new AttackMove(chr, filteredInput.substring(7,userInput.length()));  //Extract requested character name from string input
				break;
			}

			/* INVENTORY or INVE – List the player’s inventory of artifacts, providing the value and “mobility” of each, but not keyValues.
				You may express mobility in any desired units, such as pounds, kilograms, cubic inches, or a made-up unit of your own.
				Also report totals. */
			else if( filteredInput.matches("inventory|inve") ) {
				action = new InventoryMove(chr);
				break;
			}
			
			else if( filteredInput.matches("quit|exit") ) {
				action = new ExitMove(chr);
				break;
			}
			
			/* GUI # – Player swaps to the indicated GUI. */
			else if( filteredInput.matches("gui \\d") ) {
				action = new GUISwap(chr, filteredInput.substring(4,userInput.length()));  //Extract requested character name from string input
				break;
			}

			else
				IOService.getIO().display("Not a valid command. Please enter a valid command.");


			//Get another command from user
			IOService.getIO().display("");
			IOService.getIO().display("What do you want to do, " + chr.charName() + "? >> ");
			userInput = IOService.getIO().getLine();

			//Filter out beginning and trailing whitespace and force lowercase
			filteredInput = userInput.toLowerCase();
			filteredInput = filteredInput.trim();

			//System.out.println("User input: " + filteredInput + ".");
		}

		return action;  //Return the created Move
	}


}
