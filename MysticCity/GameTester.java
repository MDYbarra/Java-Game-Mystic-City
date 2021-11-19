/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * GameTester.java
 * Command-line entry point for the game, which loads a user-supplied game data
 * file
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Alex James (ajames) <ajames36@uic.edu>
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameTester {
    public static void main(String[] args) {
        // Print assignment information
        System.out.println("CS 342 (Fall 2018) - Homework 05");
        System.out.println("Authors (group 55):\n" +
                           " - Daniel Rios (drios1) <drios8@uic.edu>\n" +
                           " - Michael Ybarra (mybarra) (mybarr3@uic.edu)\n" +
                           " - Alex James (ajames) <ajames36@uic.edu>\n");

        File inputGdf = null;
        Scanner gdfScanner = null;
        Scanner keyboardScanner = KeyboardScanner.getKeyboardScanner();
        int numPlayers = -1;

        // Use command line arguments (if present)
        if (args.length >= 1) {
            // The first (optional) argument specifies a game data file
            inputGdf = new File(args[0]);
            // The second (optional) argument specifies the number of players
            if (args.length >= 2) {
                numPlayers = Integer.parseInt(args[1]);
            }
        } else {
            // Prompt for an input file name
            System.out.print("Please specify a game data filename: ");
            inputGdf = new File(keyboardScanner.nextLine());
        }

        // Attempt to open the GDF
        do {
            try {
                gdfScanner = new Scanner(inputGdf);
            } catch (FileNotFoundException e) {
                System.out.println("Failed to open " + inputGdf + '.');
                System.out.print("Please specify a different filename: ");

                inputGdf = new File(keyboardScanner.nextLine());
                gdfScanner = null;
            }
        } while (gdfScanner == null);

        // Create the game from the GDF
        Game userGame = numPlayers > 1 ? new Game(gdfScanner, numPlayers) :
                                         new Game(gdfScanner);
        System.out.println("Loading " + inputGdf + "...");
        userGame.play();
    }
}
