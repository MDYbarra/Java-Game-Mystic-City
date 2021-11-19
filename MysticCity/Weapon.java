/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * Weapon.java
 * Implementation of the Weapon class, which is an Artifact used for attacking
 * other Characters
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Alex James (ajames) <ajames36@uic.edu>
 */

import java.util.Scanner;

public class Weapon extends Artifact {
    public Weapon(Scanner gdfScanner, int version) {
        super(gdfScanner, version);
    }

    /**
     * Uses the current Weapon.
     *
     * @param character the specified Character
     * @param place     the specified Place
     */
    public void use(Character character, Place place) {
        IOService.getIO().display("It looks really sharp!");
    }
}
