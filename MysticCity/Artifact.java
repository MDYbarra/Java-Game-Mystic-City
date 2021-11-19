/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * Artifact.java
 * Implementation of the Artifact class, which represents an individual
 * artifact/item (either in a place or in the player's inventory)
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Alex James (ajames) <ajames36@uic.edu>
 */

import java.util.Scanner;

public class Artifact {
    private int id;
    private String name;
    private String description;
    private int value;
    private int mobility;
    private int keyPattern;

    /**
     * Constructs an Artifact from a specified Scanner (containing GDF data).
     *
     * @param gdfScanner the specified Scanner (containing GDF data)
     * @param version    the version of the source Game Data File
     */
    public Artifact(Scanner gdfScanner, int version) {
        Scanner lineScanner = new Scanner(
                                    CleanLineScanner.getCleanLine(gdfScanner));
        int placeOrCharID = lineScanner.nextInt();

        // The second line contains the ID, the value, the mobility, the key
        // pattern, and the name
        lineScanner = new Scanner(CleanLineScanner.getCleanLine(gdfScanner));
        id = lineScanner.nextInt();
        value = lineScanner.nextInt();
        // Negative mobility indicates that the artifact cannot be moved
        mobility = lineScanner.nextInt();
        // Positive key patterns indicate key artifacts
        keyPattern = lineScanner.nextInt();
        // The name must be lowercase to avoid case sensitivity in user input
        name = CleanLineScanner.getCleanLine(lineScanner).toLowerCase();

        // The third line contains the number of description lines
        lineScanner = new Scanner(CleanLineScanner.getCleanLine(gdfScanner));
        int numDescriptionLines = lineScanner.nextInt();

        // The remaining lines (numDescriptionLines) contain the description
        StringBuilder descriptionBuilder = new StringBuilder();
        for (int i = 0; i < numDescriptionLines; ++i) {
            lineScanner = new Scanner(
                                CleanLineScanner.getCleanLine(gdfScanner));
            descriptionBuilder.append(
                                 CleanLineScanner.getCleanLine(lineScanner));
            descriptionBuilder.append('\n');
        }

        // Update the description
        description = descriptionBuilder.toString();

        if (version == Game.GDF_VERSION_3_1) {
            if (placeOrCharID == 0) {
                // Add this artifact to the (single) player's inventory
                Character.getCharacterByID(1).addArtifact(this.name,this);
            } else {
                // Add this artifact to the specified place
                Place.getPlaceByID(placeOrCharID).addArtifact(this.name, this);
            }
        } else {
            if (placeOrCharID < 0) {
                // Add this artifact to the specified character
                //System.out.println("placeOrCharID: " + placeOrCharID);
                Character.getCharacterByID(
                  Math.abs(placeOrCharID)).addArtifact(this.name, this);  //This is the cause of the error
            } else if (placeOrCharID == 0) {
                // Add this artifact to a random place
                Place.getRandomPlace().addArtifact(this.name, this);
            } else {
                // Add this artifact to the specified place
                Place.getPlaceByID(placeOrCharID).addArtifact(this.name, this);
            }
        }
    }



    /**
     * Returns the value of the current Artifact.
     *
     * @return the value of the current Artifact
     */
    public int value() { return value; }

    /**
     * Returns the weight of the current Artifact.
     *
     * @return the weight of the current Artifact
     */
    public int weight() { return mobility; }

    /**
     * Returns the name of the current Artifact.
     *
     * @return the name of the current Artifact
     */
    public String name() { return name; }

    /**
     * Returns the description of the current Artifact.
     *
     * @return the description of the current Artifact
     */
    public String description() { return description; }



    /**
     * Checks if the specified lock pattern matches the current Key pattern.
     *
     * @param  lockPattern the specified lock pattern to check
     * @return if the lock pattern matches the current Key pattern
     */
    public boolean keyFits(int lockPattern) {
        return lockPattern == keyPattern;
    }

    /**
     * Checks if the specified name matches the current Artifact.
     *
     * @param name the specified name
     * @return if the specified name matches the current Artifact
     */
    public boolean match(String name) {
        return name.equalsIgnoreCase(this.name);
    }



    /**
     * Uses the current Artifact (only applicable for key Artifacts).
     *
     * @param character the specified Character
     * @param place     the specified Place
     */
    public void use(Character character, Place place) {
        // Only artifacts with positive key patterns can be used as keys
        if (keyPattern > 0) {
            // Try to use the key in the specified place
            place.useKey(this);
        } else {
            // For now, other artifacts cannot be used
            IOService.getIO().display(character.charName() + " tried to use " +
                               this.name + " but nothing happened.");
        }
    }



    /**
     * Prints a description of the current Artifact.
     */
    public void display() {
        IOService.getIO().display(name + " - $" + value + ", " + mobility + " lb(s)");
        IOService.getIO().display(description);
    }

    /**
     * Prints a description of the current Artifact for debugging purposes.
     */
    public void print() {
        System.out.println("Artifact " + id + ": " + name + " - $" + value +
                           ", " + mobility + " lb(s)" +
                           " [key pattern: " + keyPattern + ']');
        System.out.print(description);
    }
}
