/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * GoldenSkull.java
 * Implementation of the GoldenSkull class, which is an Artifact that ends the
 * game when used
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Alex James (ajames) <ajames36@uic.edu>
 */

import java.util.Scanner;

public class GoldenSkull extends Artifact {
    public GoldenSkull(Scanner gdfScanner, int version) {
        super(gdfScanner, version);
    }

    /**
     * Uses the current GoldenSkull.
     *
     * @param character the specified Character
     * @param place     the specified Place
     */
    public void use(Character character, Place place) {
        IOService.getIO().display("Something strange is happening... " +
                                  character.charName() +
                                  " caused the game to end!");
    }
}
