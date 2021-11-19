/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * Food.java
 * Implementation of the Medicine class, which is an Artifact that can be
 * consumed by a Player to restore their health status
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Alex James (ajames) <ajames36@uic.edu>
 */

import java.util.Scanner;

public class Medicine extends Artifact {
    public Medicine(Scanner gdfScanner, int version) {
        super(gdfScanner, version);
    }

    /**
     * Uses the current Medicine.
     *
     * @param character the specified Character
     * @param place     the specified Place
     */
    public void use(Character character, Place place) {
        character.restoreHealth(name());
    }
}
