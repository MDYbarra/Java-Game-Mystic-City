/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * Character.java
 * This file contains the Character class.
 * They are the entities that interact with the game environment and things within it by moving from place to place.
 * The class supports the command design pattern of the Move Class.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Daniel Rios (drios1) <drios8@uic.edu>
 */

/*
	TODO:
		- Character1 looks -> Place.display() -> Character2.display() 'what IO object is in use for this call? 1 or 2?'
		- Figure out how to resolve this conflicting IO issue.
*/

import java.util.HashMap;
import java.util.Collection;
import java.util.TreeMap;
import java.util.Map;
import java.lang.Integer;
import java.util.Scanner;
import java.util.Random;


public abstract class Character {

	private int id;
	private boolean isPlaying;
	protected int health;
	protected String name;
	protected String description;
	protected Place current;
	protected HashMap<String,Artifact> inventory;
	protected DecisionMaker actionMaker;
	protected IOService IO;
	private static TreeMap<Integer,Character> charactersInGame = new TreeMap<Integer,Character>();

	/* Character class constructor - prevents instancing Character class*/
	private Character() {
		return;
	}


	/* Character class constructor used when parsing gdf file. */
	protected Character(Scanner fileScanner, int version) {
		Scanner cleanLineScan = new Scanner(CleanLineScanner.getCleanLine(fileScanner));  //Gets a clean line from file

		if(version == Game.GDF_VERSION_3_1) {
				System.out.println("Character 3.1 should not have been called. Exiting the program.");
				System.exit(1);
		}

		else if(version == Game.GDF_VERSION_4_0 || version == Game.GDF_VERSION_5_0 || version == Game.GDF_VERSION_5_2) {
			//String typeOfChar = cleanLineScan.next("PLAYER|NPC");  //Removes type word from the line
			int placeID = cleanLineScan.nextInt();  //Get place for character to be located initially
			// PlaceID > 0 indicates starting place
			// PlaceID = 0 indicates a random starting place for the character
			//System.out.println("Place id: " + placeId);

			if(placeID > 0)
				this.current = Place.getPlaceByID(placeID);
			else if(placeID == 0)
				this.current = Place.getRandomPlace();
			else {
				System.out.println("This is not a valid place id. Exiting the program.");
				System.exit(1);
			}

			cleanLineScan = new Scanner(CleanLineScanner.getCleanLine(fileScanner));  //Gets a clean line from file
			this.id = cleanLineScan.nextInt();
			if(this.id <= 0) {  //IDs must be >0
				System.out.println("Attempted to construct a Character with a negative ID. ID: " + this.id);
				System.out.println("Exiting the program.");
				System.exit(1);
			}

			this.name = cleanLineScan.nextLine().trim();
			cleanLineScan = new Scanner(CleanLineScanner.getCleanLine(fileScanner));  //Gets another line
			int linesInDesc = cleanLineScan.nextInt();  //Records the number of lines for the artifact description
			if(linesInDesc <= 0) {  //Must be >0
				System.out.println("Attempted to construct a Character with a description of " + linesInDesc + " lines.");
				System.out.println("Exiting the program.");
				System.exit(1);
			}
			int counter = 0;
			String desc = "";
			while(counter != linesInDesc) {  //Gets N lines from file and conatenates them to form the description
				desc = desc.concat(CleanLineScanner.getCleanLine(fileScanner)).concat(" ");
				counter = counter + 1;  //Increment the counter
			}
			this.description = desc;
			this.inventory = new HashMap<String,Artifact>();  //Creates a hashmap for inventory

			this.isPlaying = false;
			this.health = 10;

			charactersInGame.put(this.id, this); //Put the Place into the TreeMap of Places in the game
			this.current.addCharacter(this);  //Adds Character to Place
			cleanLineScan.close();  //Close scanner

			System.out.println("Constructed Character with id: "+ id);
			return;
		}

		else {
			System.out.println("Version number " + version + " is not supported.");
			System.exit(1);
		}
	}


	/* Character constructor used when no players are given by data file. */
	private Character(int id, String name, String desc) {
		this.id = id;
		this.name = name;
		this.description = desc;
		this.isPlaying = false;
		this.health = 10;
		this.inventory = new HashMap<String,Artifact>();
		charactersInGame.put(this.id, this); //Put the Character into the TreeMap of Characters in the game
		return;
	}


	/* Character constructor used when no players are given by data file. */
	protected Character(Place pl, int id, String name, String desc) {
		this(id, name, desc);
		this.current = pl;
		this.current.addCharacter(this);  //Adds Character to Place
		return;
	}




	/* void – Used for debugging, prints out full details. */
	public void print() {
		System.out.println("Character information:");
		System.out.println("    id: "+ id + ".");
		System.out.println("    name: "+ name + ".");
		System.out.println("    health: "+ health + ".");
		System.out.println("    description: "+ description);
		System.out.println("    actionMaker: "+ actionMaker.print() + ".");
		System.out.println("    inventory: ");
		Collection<Artifact> artifactsInHash = inventory.values();
		for(Artifact a: artifactsInHash)
			a.print();
		return;
	}


	/* void – Prints the Character's name and description. */
	public void display() {
		IOService.getIO().display(name + ", health: " + health + ", " + description + "");
		return;
	}



	/* IOService - returns the Character's IOService. */
	public IOService getIO() {
		return IO;
	}

	/* int - Used for child classes to get id. */
	public int charId() {
		return id;
	}

	/* String - Used for PlayerUI to ask proper player for input. */
	public String charName() {
		return name;
	}

	/* String - Returns the character's description. */
	public String charDescription() {
		return description;
	}

	/* int - Returns the Character's current health. */
	public int getHealth() {
		return health;
	}

	/* String - Used for PlayerUI to ask proper player for input. */
	public boolean isPlaying() {
		return isPlaying;
	}


	/* int - Count up the value of all the character's artifacts. */
	public int getInventoryValue() {
		int sum = 0;
		Collection<Artifact> heldItems = inventory.values();
		for(Artifact item: heldItems) {
			sum += item.value();
		}
		return sum;
	}


	/* Character - Gets a character based on the id given. Returns null if not found. */
	public static Character getCharacterByID(int charId) {
		//debug info \/
		System.out.println("Entries: ");
		for(Map.Entry<Integer,Character> entry: Character.charactersInGame.entrySet())
			entry.getValue().print();  //Go through each entry and print the Character associated with it
		System.out.println("Size of Character Map: " +  Character.charactersInGame.size());
		System.out.println("Looking for id: " + charId);
		return Character.charactersInGame.get(charId);  //Gets associated value from the key given if it exists
	}

	/* boolean - Checks if the Character given is in the list. */
	public static boolean characterIsInGame(Character chr) {
		return Character.charactersInGame.containsValue(chr);  //Gets associated value from the key given if it exists
	}



	/* boolean - decrements the character's health level. */
	public boolean damage() {
		if(health > 0)
			health = health - 1;
		else {
			IO.display(name + "'s health has reached zero!");
			this.die();
			return true;  //Indicates player has run out of health
		}
		return false;  //Indicates player is still alive
	}


	/* void - restores the character's health and removes the used artifact. */
	public void restoreHealth(String artifactName) {
		health = 10;
		this.removeArtifact(artifactName);
		return;
	}


	/* void - calls the function */
	public void eat(String itemName) {
		if(this instanceof Player)
			((Player) this).restoreHunger(itemName);
		else
			System.out.println("Error: Npc" + name + "has used a Food artifact!");
		return;
	}



	/* boolean - returns a boolean indicating if a Character has a lamp in their inventory. */
	public boolean hasALamp() {
		for(Artifact item: inventory.values()) {
			if(item instanceof Lamp)
				return true;
		}
		return false;
	}


	/* boolean - returns a boolean indicating if a Character has a lamp in their inventory. */
	public boolean hasAWeapon() {
		for(Artifact item: inventory.values()) {
			if(item instanceof Weapon)
				return true;
		}
		return false;
	}


	/* void - drops all the items in the place the character is in when character dies or exits the game. */
	public void dropInventory() {
		Collection<Artifact> heldItems = inventory.values();
		for(Artifact item: heldItems) {
			current.addArtifact(item.name(), item);
		}
		return;
	}


	/* void – Adds an Artifact object to this Characters's collection of Artifacts. */
	public void addArtifact(String itemName, Artifact item) {
		inventory.put(itemName,item);
		return;
	}


	/* Artifact – Remove an Artifact object from this Characters's collection of Artifacts and return it. */
	public Artifact removeArtifact(String itemName) {
		return inventory.remove(itemName);
	}


	/* boolean – Return true the Artifact specified is in Characters's  collection of Artifacts, else return false. */
	public boolean artifactIsPresent(String itemName) {
		return inventory.containsKey(itemName);
	}


	/* String - Returns a random Direction string from the collection of Directions in this Place.
		Used to give NPCs options to move for go command. */
	public String getRandomArtifact() {
		Collection<String> artifactKeysInHash = inventory.keySet();  //Collection of keys in hash
		if(artifactKeysInHash.size() == 0)  //In case there are no directions
			return "Nothing";

		Random rand = new Random();  //create rand generator
		int index = rand.nextInt(artifactKeysInHash.size());  //Get a index based on number of places in the game
		String[] artifactKeysArray = new String[artifactKeysInHash.size()];
		artifactKeysArray = artifactKeysInHash.toArray(artifactKeysArray);  //Create an array of the artifact keys
		return artifactKeysArray[index];  //Return the string form of the Direction indexed
	}





	/* void - Creates and executes commands as part of the Command design pattern. */
	public void makeMove() {
		Move action;
		isPlaying = true;  //Sets status for player actively taking a turn to true
		action = actionMaker.getMove(this, current);
		if(action != null)  //Move is null indicating character has died during their turn
			action.execute();
		isPlaying = false;  //Toggles the status back off
		return;
	}


	/* void - Attempts to move in the desired direction. */
	public void goDir(String directionName) {
		Place newCurrent = current.followDirection(directionName);
		if(newCurrent.isExit()) {  //If character has gone to exit
			this.exitGame();
		}
		if(!(newCurrent == current)) {  //If the move is successful
			current.removeCharacter(this);  //Remove character from the place it's in
			IO.display(name + " followed direction " + directionName + ".");  //Inform of the move
			newCurrent.addCharacter(this);  //Add character to the new place
			current = newCurrent;  //Update current place variable
		}
		else {
			IO.display(name + " could not follow direction " + directionName + ".");  //Inform of the move
		}
		return;
	}


	/* void - Attempts to attack the character with the specified name. */
	public abstract void attack(String characterName);


	/* void - Attempts to get the specified item. */
	public abstract void getItem(String artifactName);


	/* void - Attempts to drop the specified item. */
	public abstract void dropItem(String artifactName);


	/* void - Attempts to use the specified item. */
	public abstract void useItem(String artifactName);


	/* void - Removes the Character from the game. */
	public void exitGame() {
		IO.display(name + " has exited the game.");  //Change to IO later?
		this.dropInventory();
		current.removeCharacter(this);
		charactersInGame.remove(this.id);
		return;
	}


	/* void - Removes the Character from the game.
		For Npcs this will only happen when their health has reached 0.
		For Players this will happen when health and hunger reaches 0. */
	public void die() {
		IO.display(name + " has died.");  //Change to IO later?
		this.dropInventory();
		current.removeCharacter(this);
		charactersInGame.remove(this.id);
		return;
	}

}
