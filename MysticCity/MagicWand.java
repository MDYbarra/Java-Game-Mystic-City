/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * Lamp.java
 * Implementation of the MagicWand class, which is an Artifact that glows
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Alex James (ajames) <ajames36@uic.edu>
 */

import java.util.Scanner;

public class MagicWand extends Artifact {
    public MagicWand(Scanner gdfScanner, int version) {
        super(gdfScanner, version);
    }

    /**
     * Uses the current MagicWand.
     *
     * @param character the specified Character
     * @param place     the specified Place
     */
    public void use(Character character, Place place) {
        if (place.isMagical()) {
            // Display a message in magic rooms
            IOService.getIO().display("It emits a mysterious glow...");
        } else {
            IOService.getIO().display("It doesn't do anything...");
        }
    }
}
