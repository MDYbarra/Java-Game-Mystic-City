/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * Food.java
 * Implementation of the Food class, which is an Artifact that can be consumed
 * by a Player to restore their hunger status
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Alex James (ajames) <ajames36@uic.edu>
 */

import java.util.Scanner;

public class Food extends Artifact {
    public Food(Scanner gdfScanner, int version) {
        super(gdfScanner, version);
    }

    /**
     * Uses the current Food.
     *
     * @param character the specified Character
     * @param place     the specified Place
     */
    public void use(Character character, Place place) {
        character.eat(name());
    }
}
