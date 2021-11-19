/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * Place.java
 * Implementation of the Place class, which represents an individual
 * place/location in the game
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Alex James (ajames) <ajames36@uic.edu>
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Collection;
import java.util.Scanner;

public class Place {
    private int id;
    private boolean lightStatus;
    private boolean isHarmful;
    private boolean isMagical;
    private String name;
    private String description;
    private ArrayList<Direction> directions;
    private ArrayList<Character> characters;
    private HashMap<String, Artifact> artifacts;
    private static HashMap<Integer, Place> places = new HashMap<>();

    /**
     * Constructs a Place from a specified Scanner (containing GDF data).
     *
     * @param gdfScanner the specified Scanner (containing GDF data)
     * @param version    the version of the source Game Data File
     */
    public Place(Scanner gdfScanner, int version) {
        // Initialize collections
        directions = new ArrayList<>();
        artifacts = new HashMap<>();
        characters = new ArrayList<>();

        // The first line contains the ID, the light status, if it's harmful, and name
        Scanner lineScanner = new Scanner(CleanLineScanner.getCleanLine(gdfScanner));
        id = lineScanner.nextInt();
        if (version == Game.GDF_VERSION_5_2) {
            int lightValue = lineScanner.nextInt();
            if (lightValue == 1) {
                lightStatus = true;
            } else {
                lightStatus = false;
            }

            int hazard = lineScanner.nextInt();
            if (hazard == 1) {
                isHarmful = true;
            } else {
                isHarmful = false;
            }

            int magical = lineScanner.nextInt();
            if (magical == 1) {
                isMagical = true;
            } else {
                isMagical = false;
            }
        } else {
            lightStatus = true;
            isHarmful = false;
            isMagical = false;
        }

        name = CleanLineScanner.getCleanLine(lineScanner);

        // The second line contains the number of description lines
        lineScanner = new Scanner(CleanLineScanner.getCleanLine(gdfScanner));
        int numDescriptionLines = lineScanner.nextInt();

        // The remaining lines (numDescriptionLines) contain the description
        StringBuilder descriptionBuilder = new StringBuilder();
        for (int i = 0; i < numDescriptionLines; ++i) {
            lineScanner = new Scanner(CleanLineScanner.getCleanLine(gdfScanner));
            descriptionBuilder.append(CleanLineScanner.getCleanLine(lineScanner));
            descriptionBuilder.append('\n');
        }

        // Update the description
        description = descriptionBuilder.toString();

        // Add the newly created Place to the static collection of Places
        places.put(id, this);
    }

    /**
     * Constructs a Place with specified details.
     *
     * @param id          the specified ID of the Place
     * @param name        the specified name of the Place
     * @param description the specified description of the Place
     */
    public Place(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        directions = new ArrayList<>();
        characters = new ArrayList<>();
        artifacts = new HashMap<>();
        places.put(id, this);
    }

    /**
     * Returns the name of the current Place.
     *
     * @return the name of the current Place
     */
    public String name() { return name; }

    /**
     * Returns the description of the current Place.
     *
     * @return the description of the current Place
     */
    public String description() { return description; }

    /**
     * Returns if the current Place is the exit.
     *
     * @return if the current Place is the exit
     */
    public boolean isExit() { return id == 1; }

    /**
     * Adds a direction to the collection of directions in the current Place.
     *
     * @param dir the direction to be added to the current game
     */
    public void addDirection(Direction dir) { directions.add(dir); }

    /**
     * Attempts to follow the specified direction.
     *
     * @param s the specified direction string
     * @return the destination place for the current direction (when locked),
     *         or the current place for the current direction (when unlocked)
     */
    public Place followDirection(String s) {
        for (Direction d : directions) {
            if (d.match(s)) {
                return d.follow();
            }
        }

        return this;
    }
	
    /**
     * Checks if the current Place has an Artifact with a specified name in its
     * collection of Artifacts.
     *
     * @param name the name of the specified Artifact
     * @return if the current Place has the specified Artifact in its
     *         collection of Artifacts
     */
    public boolean hasArtifact(String name) {
        return artifacts.containsKey(name);
    }

    /**
     * Checks if the current Place has a movable Artifact with a specified name
     * in its collection of Artifacts.
     *
     * @param name the name of the specified Artifact
     * @return if the current Place has the specified movable Artifact in its
     *         collection of Artifacts
     */
    public boolean hasMovableArtifact(String name) {
        return hasArtifact(name) && artifacts.get(name).weight() >= 0;
    }

    /**
     * Adds a specified Artifact to the current Place's collection of
     * Artifacts.
     *
     * @param artifact name and the specified Artifact to be added
     */
    public void addArtifact(String artifactName, Artifact artifact) {
        artifacts.put(artifactName, artifact);
    }

    /**
     * Attempts to remove a specified Artifact from the current Place's
     * collection of Artifacts.
     *
     * @param name the name of the specified Artifact
     * @return the specified Artifact to be removed
     */
    public Artifact removeArtifactByName(String name) {
        // Only movable artifacts can be removed
        if (!hasMovableArtifact(name)) {
            return null;
        }

        return artifacts.remove(name);
    }


    /**
     * Attempts to toggle the lock state of adjacent Directions with a
     * specified key Artifact.
     *
     * @param key the specified key Artifact to be used
     */
    public void useKey(Artifact key) {
        for (Direction d : directions) {
            d.useKey(key);
        }
    }

    /**
     * Adds the specified Character to the current Place's collection of
     * Characters.
     *
     * @param character the specified Character to be added
     */
    public void addCharacter(Character character) {
        characters.add(character);
    }

    /**
     * Removes the specified Character from the current Place's collection of
     * Characters.
     *
     * @param character the specified Character to be removed
     */
    public void removeCharacter(Character character) {
        characters.remove(character);
    }


	/* void - Displays the name and description of a particular Place. */
	public void display() {
		IOService.getIO().display("You are at the " + name + ".\n" + description);
		return;
	}


	/* void – Displays the artifacts in the place. */
	public void displayArtifacts() {
		Collection<Artifact> artifactsInHash = artifacts.values();  //Collection of keys in hash
		for(Artifact a: artifactsInHash) { //Iterate through the inventory hashmap displaying artifacts' names
			a.display();
		}
		return;
	}


	/* void - Displays the characters in the Place. */
	public void displayCharacters() {
		for(Character chr: characters) {  //Iterate through the inventory hashmap displaying artifacts' names
			chr.display();
		}
		return;
	}

    /**
     * Prints a detailed description of the current Place.
     */
    public void displayTwo() {
        System.out.print(description);

        System.out.println("Available directions:");
        for (Direction d : directions) {
            System.out.print(" - ");
            d.display();
        }

        if (characters.size() > 1) {
            System.out.println("You see some other characters:");
            for (Character c : characters) {
                // Don't print out the currently playing Character
                if (!c.isPlaying()) {
                    System.out.print(" - ");
                    c.display();
                }
            }
        }

        if (artifacts.size() > 0) {
            System.out.println("Available artifacts:");
            for (HashMap.Entry<String, Artifact> e : artifacts.entrySet()) {
                System.out.print(" - ");
                e.getValue().display();
            }
        }
    }

    /**
     * Prints a description of the current Place for debugging purposes.
     */
    public void print() {
        System.out.println("Place " + id + ':');
        System.out.print(description);

        System.out.println("Available directions:");
        for (Direction d : directions) {
            System.out.print(" - ");
            d.print();
        }

        if (characters.size() > 0) {
            System.out.println("The following characters are here:");
            for (Character c : characters) {
                System.out.print(c.isPlaying() ? " - (current) " : " - ");
                c.print();
            }
        }

        if (artifacts.size() > 0) {
            System.out.println("Available artifacts:");
            for (HashMap.Entry<String, Artifact> e : artifacts.entrySet()) {
                System.out.print(" - ");
                e.getValue().print();
            }
        }
    }

    /**
     * Prints out debugging information for every Place in the static
     * collection of Places.
     */
    public static void printAll() {
        System.out.println("All places:");
        for (HashMap.Entry<Integer, Place> e : places.entrySet()) {
            System.out.print(" - ");
            e.getValue().print();
			System.out.println("");
        }
    }


    /**
     * Returns the Place associated with the given ID number.
     *
     * @param id the ID number of the requested Place
     * @return the Place associated with the given ID number
     */
    public static Place getPlaceByID(int id) {
        // Create reserved places (invalid/exit) when requested
        if (id == 0 && places.get(0) == null) {
            places.put(id, new Place(0, "Nowhere", "An invalid place\n"));
        } else if (id == 1 && places.get(1) == null) {
            places.put(id, new Place(1, "Exit", "The exit\n"));
        }

        return places.get(id);
    }

    /**
     * Returns a random, valid (unreserved) Place.
     *
     * @return a random, unreserved Place
     */
    public static Place getRandomPlace() {
        // Generate an ArrayList of the values in the places HashMap
        ArrayList<Place> placesList = new ArrayList<>(places.values());
        // Remove reserved places
        placesList.remove(places.get(0));
        placesList.remove(places.get(1));

        // Generate a random number (to use as an index in the ArrayList)
        Random rand = new Random();
        int randomNumber = rand.nextInt(placesList.size());
        // Select a random place
        return placesList.get(randomNumber);
    }


	/* String - Returns a random Direction string from the collection of Directions in this Place.
		Used to give NPCs options to move for go command. */
	public String getAvailableArtifact() {
		Collection<String> artifactKeysInHash = artifacts.keySet();  //Collection of keys in hash
		if(artifactKeysInHash.size() == 0)  //In case there are no directions
			return "Nothing";

		Random rand = new Random();  //create rand generator
		int index = rand.nextInt(artifactKeysInHash.size());  //Get a index based on number of places in the game
		String[] artifactKeysArray = new String[artifactKeysInHash.size()];
		artifactKeysArray = artifactKeysInHash.toArray(artifactKeysArray);  //Create an array of the artifacts
		return artifactKeysArray[index];  //Return the string form of the Direction indexed
	}


	/* String - Returns a random Artifact string from the Hashmap of Artifacts in this Place.
		Used to give NPCs options for the get command. */
	public String getAvailableDirection() {
		if(directions.size() == 0)  //In case there are no directions
			return "None";

		Random rand = new Random();  //create rand generator
		int index = rand.nextInt(directions.size());  //Get a index based on number of places in the game
		Direction[] directionsArray = new Direction[directions.size()];
		directionsArray = directions.toArray(directionsArray);  //Create an array of the places
		return directionsArray[index].getDir();  //Return the string form of the Direction indexed
	}
	
	
	/* String - Returns null or a random available character for the NPCs to attack. Does not allow self to be an option. */
	public String getAvailableCharacter(Character self) {
		if(characters.size() == 0)  //In case there are no characters at all
			return "none";
			
		//Create a shallow copy of the list of characters
		ArrayList<Character> charactersInPlace = new ArrayList<>();
		for(Character chr: characters) {
			charactersInPlace.add(chr);
		}
        charactersInPlace.remove(self);  //Do not allow self as a target option
		
		if(charactersInPlace.size() == 0)  //In case there are no characters that are not self
			return "none";

		Random rand = new Random();  //create rand generator
		int index = rand.nextInt(charactersInPlace.size());  //Get a index based on number of characters in the place
		Character[] charactersArray = new Character[charactersInPlace.size()];
		charactersArray = charactersInPlace.toArray(charactersArray);  //Create an array of the characters
		return charactersArray[index].charName();  //Return the character from the array indexed
	}

	
	/* Character - Returns the character whose name matches the parameter passed, or null if no character with the name is present. */
    public Character getCharacter(String name) {
        for(Character chr: characters) {
			if(name.equals(chr.charName()))
				return chr;
		}
		return null;
    }

    public boolean getLightStatus(){
        return lightStatus;
    }


    public boolean isHarmful(){
        return isHarmful;
    }


    public boolean isMagical(){
        return isMagical;
    }


}
