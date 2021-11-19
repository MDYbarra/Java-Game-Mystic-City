/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * Player.java
 * This file contains the Player class.
 * They are the entities that interact with the game environment and things within it by moving from place to place.
 * The class supports the command design pattern of the Move Class.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Daniel Rios (drios1) <drios8@uic.edu>
 */

import java.util.HashMap;
import java.util.Collection;
import java.util.TreeMap;
import java.lang.Integer;
import java.util.Scanner;


public class Player extends Character {

	private int hunger;
	private int version;
	private static TreeMap<Integer,Player> playersInGame = new TreeMap<Integer,Player>();

	/* Player class constructor used when parsing gdf file. */
	public Player(Scanner fileScanner, int version) {
		super(fileScanner, version);
		super.IO = new IOService(IOService.TEXT,this);
		this.hunger = 10;
		this.version = version;
		this.actionMaker = new PlayerUI();
		playersInGame.put(this.charId(), this);
		System.out.println("Player constructor called");
		return;
	}


	/* Player constructor used when no players are given by data file. */
	public Player(Place pl, int id, String name, String desc, int version) {
		super(pl, id, name, desc);
		super.IO = new IOService(IOService.TEXT,this);
		this.hunger = 10;
		this.version = version;
		this.actionMaker = new PlayerUI();
		playersInGame.put(this.charId(), this);
		return;
	}



	@Override
	/* void – Used for debugging, prints out full details. */
	public void print() {
		System.out.println("Character information:");
		System.out.println("    id: "+ charId() + ".");
		System.out.println("    name: "+ name + ".");
		System.out.println("    health: "+ health + ".");
		System.out.println("    hunger: "+ hunger + ".");
		System.out.println("    description: "+ description);
		System.out.println("    actionMaker: "+ actionMaker.print() + ".");
		System.out.println("    inventory: ");
		Collection<Artifact> artifactsInHash = inventory.values();
		for(Artifact a: artifactsInHash)
			a.print();
		return;
	}


	@Override
	/* void - displays Player details. */
	public void display() {
		IOService.getIO().display(name + ", hunger: " + hunger +  ", health: " + health + ", " + description + "");
		return;
	}


	/* boolean - decrements the player's hunger level. */
	public boolean starve() {
		if (version == Game.GDF_VERSION_5_2) {
			if(hunger > 0)
				hunger = hunger - 1;
			else {
				IO.display(name + " has gone too long without eating!");
				this.die();
				return true;  //Indicates player has starved to death
			}
		}
			return false;  //Indicates player has not starved to death
	}

	/* int - Returns the Player's current hunger. */
	public int getHunger() {
		return hunger;
	}

	/* void - restores the player's hunger and removes the used artifact. */
	public void restoreHunger(String artifactName) {
		hunger = 10;
		this.removeArtifact(artifactName);
		IO.display(name + " has eaten " + artifactName + ".");
		return;
	}


	/* void - swaps player's GUI to the one corresponding to the integer given. */
	public void swapGUI(int guiSelection) {
		IO.selectInterface(guiSelection, this);
	}


	/* void - Displays the player's surroundings. Npcs don't call this. */
	public void lookAround() {
		if(!current.getLightStatus() && !this.hasALamp()) {
			IO.display("It's too dark in here to see anything.");  //If not a lit environment, don't allow picking up artifacts
		}
		else {
			IO.display("\nYou look around and see:");
			current.displayArtifacts();
			current.displayCharacters();
		}
		return;
	}


	/* void - Displays the player's inventory. */
	public void displayInventory() {
		Collection<Artifact> artifactsInHash = inventory.values();  //Collection of keys in hash
		int totalValue = 0, totalWeight = 0;  //Variables for tracking sums
		IO.display("\nPlayer inventory contains:");
		for(Artifact a: artifactsInHash) {  //Iterate through the inventory hashmap displaying artifact details
			a.display();  //Print each artifact's name and desc
			totalWeight = totalWeight + a.weight();  //Sum weight of inventory artifacts
			totalValue = totalValue + a.value();  //Sum value of inventory artifacts
		}
		IO.display("Total weight: " + totalWeight +" lbs, Total Value: $" + totalValue + ".");  //Print sums
		return;
	}

	/* String - Returns a description of the player's inventory. */
	public String describeInventory() {
		Collection<Artifact> artifactsInHash = inventory.values();  //Collection of keys in hash
		int totalValue = 0, totalWeight = 0;  //Variables for tracking sums
		StringBuilder sb = new StringBuilder();
		if(artifactsInHash.size() == 0) {
			sb.append("No items present");
		} else {
			for(Artifact a: artifactsInHash) {  //Iterate through the inventory hashmap displaying artifact details
				sb.append(a.name() + " - $" + a.value() + ", " + a.weight() + " lb(s)\n");
				totalWeight = totalWeight + a.weight();  //Sum weight of inventory artifacts
				totalValue = totalValue + a.value();  //Sum value of inventory artifacts
			}
		}
		sb.append("\nTotal weight: " + totalWeight +" lbs, Total value: $" + totalValue + "\n");  //Print sums
		return sb.toString();
	}

	@Override
	/* void - Attempts to get the specified item. */
	public void getItem(String artifactName) {
		if(!current.getLightStatus() && !this.hasALamp()) {
			IO.display("It's too dark in here to see anything.");  //If not a lit environment, don't allow picking up artifacts
		}

		if(current.hasArtifact(artifactName)) {  //Check if artifact is in current Place
			if(current.hasMovableArtifact(artifactName)) {  //Check if it is moveable
				Artifact retrievedArtifact = current.removeArtifactByName(artifactName);  //If moveable, remove from current Place inventory
				inventory.put(artifactName, retrievedArtifact);  //Add to player inventory
				IO.display("You have grabbed the '" + artifactName + "'.");  //Inform player of action
			}
			else {
				IO.display("You can't pick this up. It is too heavy.");  //If not moveable, inform player
			}
		}
		else {
			IO.display("There is no artifact '" + artifactName + "'.");	//If does not exist, inform player
		}
		return;
	}


	@Override
	/* void - Attempts to drop the specified item. */
	public void dropItem(String artifactName) {
		if(inventory.containsKey(artifactName)) {  //Check if artifact is in player inventory
			Artifact retrievedArtifact = inventory.remove(artifactName);  //Remove from player inventory
			current.addArtifact(artifactName, retrievedArtifact);  //Add to current Place inventory
			IO.display("You have dropped the '" + artifactName + "'.");  //Inform player of action
		}
		else {
			IO.display("There is no artifact '" + artifactName + "'.");	//If does not exist, inform player
		}
		return;
	}


	@Override
	/* void - Attempts to use the specified item. */
	public void useItem(String artifactName) {
		if(inventory.containsKey(artifactName)) {  //Check if artifact is in player inventory
			Artifact item = inventory.get(artifactName);
			item.use(this, current);  //Get artifact from hashmap and call use() method with it
		}
		else
			IO.display("There is no artifact '" + artifactName + "'.");	//If does not exist, inform player
		return;
	}


	@Override
	/* void - Attempts to attack the character with the specified name. */
	public void attack(String characterName) {
		if(this.hasAWeapon()) {
			Character target = current.getCharacter(characterName);
			if(target != null) {
				if(target.damage())
					IO.display(this.name + " has slain " + target.name);
				else
					IO.display(this.name + " has attacked " + target.name);
			}
			else
				IO.display("There is no character with the name" + characterName + ".");
		}
		else {
			IO.display("You have no weapon.");
		}
		return;
	}


	@Override
	/* void - Removes the player from the game. */
	public void exitGame() {
		super.exitGame();
		playersInGame.remove(this.charId());
		if(playersInGame.size() == 0) {  //Only exit program when all players are gone
			IO.display("Thanks for playing!");
			System.exit(0);
		}
		return;
	}


	@Override
	/* void - Removes the Character from the game if health or hunger reached 0. */
	public void die() {
		super.die();
		playersInGame.remove(this.charId());
		if(playersInGame.size() == 0) {  //Only exit program when all players are gone
			IO.display("Thanks for playing!");
			System.exit(0);
		}
		return;
	}

}
