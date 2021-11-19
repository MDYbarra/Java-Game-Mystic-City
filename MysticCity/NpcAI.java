/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * NpcAI.java
 * This file contains the NpcAI class that implements DecisionMake.
 * These classes/interface create Moves for the Characters to execute later as part of the command design pattern.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Daniel Rios (drios1) <drios8@uic.edu>
 */

import java.util.Random;


public class NpcAI implements DecisionMaker {

	private String[] actions = {"go", "get", "drop", "use", "attack"};

	@Override
	/* String – Used for debugging, prints out type of DescisionMaker. */
	public String print() {
		return "NpcAI";
	}


	@Override
	/* Move - Randomly generates a Move for Npcs to use. */
	public Move getMove(Character chr, Place pl) {
		Move action;  //Delcare Move object to be assigned a value later
		IOService.getIO().display("\n---------------------------------------------------------");
		pl.display();
		
		if(pl.isHarmful()) {
			IOService.getIO().display("This area is not suitable to support life.");
			if(chr.damage())
				return null;  //Npc has run out of health so return null
		}
		
		IOService.getIO().display("What will the " + chr.charName() + " do...?");

		Random rand = new Random();  //create rand generator
		int index = rand.nextInt(actions.length);  //Get a index based on number of places in the game

		String RngAction = actions[index];  //Get a random action string
		switch(RngAction) {  //Constructs Moves based on RNG methods
			case "go":
				action = new GoMove(chr, pl.getAvailableDirection());
				break;

			case "get":
				action = new GetMove(chr, pl.getAvailableArtifact());
				break;

			case "drop":
				action = new DropMove(chr, chr.getRandomArtifact());
				break;

			case "use":
				action = new UseMove(chr, chr.getRandomArtifact());
				break;
				
			case "attack":
				action = new AttackMove(chr, pl.getAvailableCharacter(chr));
				break;
				
			default:
				action = new GoMove(chr, pl.getAvailableDirection());
				System.out.println("Error: Npc has triggered default case in RNG action selection!");
		}

		return action;  //Return the Constructed Random method
	}


}
