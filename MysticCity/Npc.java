/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * Npc.java
 * This file contains the NPC class.
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
import java.util.Random;


public class Npc extends Character {

	/* Npc class constructor used when parsing gdf file. */
	public Npc(Scanner fileScanner, int version) {
		super(fileScanner, version);
		super.IO = new IOService(IOService.GUI_Npc,this);
		this.actionMaker = new NpcAI();
		return;
	}


	@Override
	/* void - Attempts to get specified item. */
	public void getItem(String artifactName) {
		if(current.hasArtifact(artifactName)) {  //Check if artifact is in current Place
			if(current.hasMovableArtifact(artifactName)) {  //Check if it is moveable
				Artifact retrievedArtifact = current.removeArtifactByName(artifactName);  //If moveable, remove from current Place inventory
				if (retrievedArtifact instanceof Lamp || retrievedArtifact instanceof Food ||
					retrievedArtifact instanceof Medicine) {
					//NPCs cannot pick these up
					current.addArtifact(artifactName, retrievedArtifact);
					IO.display("The " + artifactName + " cannot be picked up by Npc, " + name + ".");
				} 
				else {
					inventory.put(artifactName, retrievedArtifact);  //Add to player inventory
					IO.display("NPC, " + name + ", has grabbed the '" + artifactName + "'.");  //Inform of action
				}
			}
			else {
				IO.display("NPC, " + name + ", can't pick up the '" + artifactName + "'. It is too heavy.");  //If not moveable, inform
			}
		}
		else {
			IO.display("There is no artifact '" + artifactName + "' for NPC, " + name + ", to pickup.");	//If does not exist, inform
		}
		return;
	}


	@Override
	/* void - Attempts to drop specified item. */
	public void dropItem(String artifactName) {
		if(inventory.containsKey(artifactName)) {  //Check if artifact is in player inventory
			Artifact retrievedArtifact = inventory.remove(artifactName);  //Remove from player inventory
			current.addArtifact(artifactName, retrievedArtifact);  //Add to current Place inventory
			IO.display("NPC, " + name + ", has dropped the '" + artifactName + "'.");  //Inform of action
		}
		else {
			IO.display("There is no artifact '" + artifactName + "' for NPC, " + name + ", to drop.");	//If does not exist, inform
		}
		return;
	}


	@Override
	/* void - Attempts to use specified item. */
	public void useItem(String artifactName) {
		if(inventory.containsKey(artifactName)) {  //Check if artifact is in player inventory
			Artifact item = inventory.get(artifactName);
			if(item instanceof Food || item instanceof Lamp)
				System.out.println("Error: Npcs cannot use food items or lamps.");
			else
				item.use(this, current);  //Get artifact from hashmap and call use() method with it
		}
		else {
			IO.display("There is no artifact '" + artifactName + "' for NPC, " + name + ", to use.");	//If does not exist, inform
		}
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
				IO.display("There is no one for Npc, " + this.name + ", to attack.");
		}
		else {
			IO.display("Npc, " + this.name + ", has no weapon.");
		}
		return;
	}	


}
