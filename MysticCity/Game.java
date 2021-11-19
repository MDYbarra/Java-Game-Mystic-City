/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * Game.java
 * Implementation of the Game class, which contains elements of the game (such
 * as places) and is controlled by the player
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Alex James (ajames) <ajames36@uic.edu>
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Iterator;

public class Game {
    private String name;
    private static ArrayList<Character> characters;
    private static Character currentCharacter;

    private static final String GDF_MAGIC = "GDF";
    private static final String GDF_PLACES_MAGIC = "PLACES";
    private static final String GDF_DIRECTIONS_MAGIC = "DIRECTIONS";
    private static final String GDF_CHARACTERS_MAGIC = "CHARACTERS";
    private static final String GDF_ARTIFACTS_MAGIC = "ARTIFACTS";

    // Public constants for identifying GDF versions
    public static final int GDF_VERSION_3_1 = 31;
    public static final int GDF_VERSION_4_0 = 40;
    public static final int GDF_VERSION_5_0 = 50;
    public static final int GDF_VERSION_5_2 = 52;

    /**
     * Constructs a Game from a specified Scanner (containing GDF data).
     *
     * @param gdfScanner    the specified Scanner (containing GDF data)
     * @param minNumPlayers the minimum number of Players to add
     */
    public Game(Scanner gdfScanner, int minNumPlayers) {
        int gdfVersion = 0;
        Place firstPlace = null;
        boolean readPlaces = false;
        boolean readDirections = false;
        boolean readCharacters = false;
        boolean readArtifacts = false;
        int numPlayersCreated = 0;
        // Initialize collections
        characters = new ArrayList<>();

        // Parse the GDF line-by-line
        String currentLine = CleanLineScanner.getCleanLine(gdfScanner);
        // Main GDF parsing loop
        while (currentLine != null) {
            Scanner lineScanner = new Scanner(currentLine);
            // Check the GDF header (if it hasn't been read yet)
            if ((name == null || gdfVersion == 0) && lineScanner.hasNext() &&
                lineScanner.next().equals(GDF_MAGIC)) {
                // Get the GDF version
                String gdfVersionString = lineScanner.next().replace(".", "");
                gdfVersion = Integer.parseInt(gdfVersionString);
                if (gdfVersion != GDF_VERSION_3_1 &&
                    gdfVersion != GDF_VERSION_4_0 &&
                    gdfVersion != GDF_VERSION_5_0 &&
                    gdfVersion != GDF_VERSION_5_2) {
                    System.out.println("Error: Unsupported GDF version: " +
                                       gdfVersion);
                    System.exit(1);
                }

                // Get the environment name
                name = lineScanner.nextLine();

                // Continue parsing
                currentLine = CleanLineScanner.getCleanLine(gdfScanner);

                System.out.println("GDF CHECK");
                continue;
            }

            // Parse the places section (if it hasn't been read yet)
            if (!readPlaces && lineScanner.hasNext() &&
                lineScanner.next().equals(GDF_PLACES_MAGIC)) {
                readPlaces = true;
                // Get the number of places
                int numPlaces = lineScanner.nextInt();

                // Parse each place
                for (int i = 0; i < numPlaces; ++i) {
                    // Construct the new place
                    Place newPlace = new Place(gdfScanner, gdfVersion);

                    // Update firstPlace (the starting place for players if
                    // none are defined in the game data)
                    if (firstPlace == null) {
                        firstPlace = newPlace;
                    }
                }

                // Continue parsing
                currentLine = CleanLineScanner.getCleanLine(gdfScanner);

                System.out.println("PLACES CHECK");
                continue;
            }

            // Parse the directions section (if it hasn't been read yet)
            if (!readDirections && lineScanner.hasNext() &&
                lineScanner.next().equals(GDF_DIRECTIONS_MAGIC)) {
                readDirections = true;
                // Get the number of directions
                int numDirections = lineScanner.nextInt();

                // Parse each direction
                for (int i = 0; i < numDirections; ++i) {
                    // Construct the new direction
                    new Direction(gdfScanner, gdfVersion);
                }

                // Continue parsing
                currentLine = CleanLineScanner.getCleanLine(gdfScanner);

                System.out.println("DIRECTIONS CHECK");
                continue;
            }

            // Parse the characters section (if it hasn't been read yet)
            if (gdfVersion > GDF_VERSION_3_1 && !readCharacters &&
                lineScanner.hasNext() &&
                lineScanner.next().equals(GDF_CHARACTERS_MAGIC)) {
                readCharacters = true;
                // Get the number of characters
                int numCharacters = lineScanner.nextInt();

                // Parse each character
                for (int i = 0; i < numCharacters; ++i) {
                    Character newCharacter;
                    String characterType;
                    // Determine the character type
                    if (gdfVersion < GDF_VERSION_5_0) {
                        characterType = gdfScanner.next();
                    } else {
                        characterType = CleanLineScanner.getCleanLine(gdfScanner);
                    }

                    System.out.println("Keyword: " + characterType);
                    if (characterType.equals("PLAYER")) {
                        // Construct the new player
                        newCharacter = new Player(gdfScanner, gdfVersion);
                        ++numPlayersCreated;
                    } else {
                        // Construct the new NPC
                        newCharacter = new Npc(gdfScanner, gdfVersion);
                    }

                    // Add the character to the collection of characters
                    characters.add(newCharacter);
                }

                // Continue parsing
                currentLine = CleanLineScanner.getCleanLine(gdfScanner);

                System.out.println("CHARACTERS CHECK");
                continue;
            }

            // Parse the artifacts section (if it hasn't been read yet)
            if (!readArtifacts && lineScanner.hasNext() &&
                lineScanner.next().equals(GDF_ARTIFACTS_MAGIC)) {
                readArtifacts = true;
                // Get the number of artifacts
                int numArtifacts = lineScanner.nextInt();

                // Parse each artifact
                for (int i = 0; i < numArtifacts; ++i) {
                    if (gdfVersion < GDF_VERSION_5_2) {
                        // Construct the new artifact
                        new Artifact(gdfScanner, gdfVersion);
                    } else {
                        // Determine the artifact type
                        String artifactType = CleanLineScanner.getCleanLine(
                                                                 gdfScanner);
                        switch (artifactType) {
                            case "KEY":
                                // In order to maintain backwards compatibility
                                // with previous versions of GDFs, key-related
                                // functionality is kept in the Artifact class,
                                // which is why there isn't a Key class
                                new Artifact(gdfScanner, gdfVersion);
                                break;
                            case "LAMP":
                                new Lamp(gdfScanner, gdfVersion);
                                break;
                            case "BOOK":
                                new Book(gdfScanner, gdfVersion);
                                break;
                            case "FOOD":
                                new Food(gdfScanner, gdfVersion);
                                break;
                            case "MEDICINE":
                                new Medicine(gdfScanner, gdfVersion);
                                break;
                            case "MAGICWAND":
                                new MagicWand(gdfScanner, gdfVersion);
                                break;
                            case "GOLDENSKULL":
                                new GoldenSkull(gdfScanner, gdfVersion);
                                break;
                            case "OTHER":
                                new Artifact(gdfScanner, gdfVersion);
                                break;
                            default:
                                System.out.println("Error: " + artifactType +
                                                   " is not a valid artifact" +
                                                   " type.");
                                System.exit(1);
                        }
                    }
                }

                System.out.println("ARTIFACTS CHECK");
            }

            // Read the next line and keep parsing
            currentLine = CleanLineScanner.getCleanLine(gdfScanner);
        }

        // Sanity check (see if the user specified an invalid/empty file)
        if (gdfVersion == 0 || name == null || !readPlaces ||
            !readDirections || !readArtifacts) {
            System.out.println("Error: The specified file does not contain " +
                               "valid game data.");
            System.exit(1);
        }

        if (gdfVersion == GDF_VERSION_3_1) {
            // Create a single player if this is v3.1 data (no characters)
            Player singlePlayer = new Player(firstPlace, 1, "Player 1",
                                             "The only player.", gdfVersion);
            characters.add(singlePlayer);
        } else {
            if (minNumPlayers != -1) {
                // The user specified the minimum number of players
                while (numPlayersCreated < minNumPlayers) {
                    // Create additional players (as needed)
                    Player player = new Player(firstPlace,
                                               100 + numPlayersCreated,
                                               "Player " +
                                               Integer.toString(
                                                 numPlayersCreated + 1),
                                               "A human player.", gdfVersion);
                    characters.add(player);
                    ++numPlayersCreated;
                }
            } else {
                // The user didn't specify the minimum number of players
                if (numPlayersCreated == 0) {
                    // Create a single player if none were defined in the GDF
                    Player player = new Player(firstPlace, 101, "Player 1",
                                               "The only human player.", gdfVersion);
                    characters.add(player);
                    ++numPlayersCreated;
                }
            }
        }

        currentCharacter = characters.get(0);
    }

    /**
     * Constructs a Game from a specified Scanner (containing GDF data).
     *
     * @param gdfScanner the specified Scanner (containing GDF data)
     */
    public Game(Scanner gdfScanner) {
        this(gdfScanner, -1);
    }




    /**
     * Plays the current Game.
     */
    public void play() {
        IOService.getIO().display("You are now playing: " + name + '\n');

        Place.printAll();  //Debug info

        //Create an iterator to go through the list of characters
        Iterator<Character> charIter = characters.iterator();
        while(characters.size() > 0) {  //Loop through all Characters if any exist
            while(charIter.hasNext()) {  //Characters will take turns making a move
                currentCharacter = charIter.next();  //Get Character from iter
                currentCharacter.makeMove();  //Character makes a move

                if(!Character.characterIsInGame(currentCharacter)) {  //Check if the character has left the game
                    charIter.remove();  //If Character left the game remove them from the list
                }
            }
            charIter = characters.iterator();  //Create new iterator at start of list
        }

        IOService.getIO().display("Thanks for playing!");
    }

    /**
     * Ends the current Game and displays the scores of each Character.
     */
    public static void endGame() {
        IOService.getIO().display("Game over!");

        // Order the characters by the value of their inventory
        Comparator<Character> comp = Comparator.comparing(
                                         Character::getInventoryValue);
        characters.sort(comp.reversed());
        IOService.getIO().display("The winner is " + characters.get(0).charName() +
                   ", with $" + characters.get(0).getInventoryValue() +
                   " in their inventory!");

        IOService.getIO().display("\nHere are the remaining results:");
        for (int i = 1; i < characters.size(); i++) {
            IOService.getIO().display(i + 1 + " - " + characters.get(i).charName() +
                       ", with $" + characters.get(i).getInventoryValue());
        }

        // Remove each character from the game (to end it)
        characters.clear();
    }

    /**
     * Returns the current Character.
     */
    public static Character getCurrentCharacter() {
        return currentCharacter;
    }
}
