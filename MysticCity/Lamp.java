/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * Lamp.java
 * Implementation of the Lamp class, which is an Artifact used for increasing
 * visibility in dark Places
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Alex James (ajames) <ajames36@uic.edu>
 */

import java.util.Scanner;

public class Lamp extends Artifact {
    public Lamp(Scanner gdfScanner, int version) {
        super(gdfScanner, version);
    }

    /**
     * Uses the current Lamp.
     *
     * @param character the specified Character
     * @param place     the specified Place
     */
    public void use(Character character, Place place) {
        IOService.getIO().display("The lamp emits a bright light!");
    }
}
