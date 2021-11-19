/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * Book.java
 * Implementation of the Book class, which is an Artifact that contains a
 * message that can be read by a Player
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Alex James (ajames) <ajames36@uic.edu>
 */

import java.util.Scanner;

public class Book extends Artifact {
    private String text;

    public Book(Scanner gdfScanner, int version) {
        super(gdfScanner, version);
        // The second-to-last line contains the number of text lines
        Scanner lineScanner = new Scanner(
                                    CleanLineScanner.getCleanLine(gdfScanner));
        int numTextLines = lineScanner.nextInt();

        // The remaining lines (numTextLines) contain the text
        StringBuilder textBuilder = new StringBuilder();
        for (int i = 0; i < numTextLines; ++i) {
            lineScanner = new Scanner(
                                CleanLineScanner.getCleanLine(gdfScanner));
            textBuilder.append(CleanLineScanner.getCleanLine(lineScanner));
            textBuilder.append('\n');
        }

        // Update the text
        text = textBuilder.toString();
    }

    /**
     * Uses the current Book.
     *
     * @param character the specified Character
     * @param place     the specified Place
     */
    public void use(Character character, Place place) {
        IOService.getIO().display(character.charName() + " is reading " + name() + ':');
        // Print the text contents of the current Book
        IOService.getIO().display(text);
    }
}
