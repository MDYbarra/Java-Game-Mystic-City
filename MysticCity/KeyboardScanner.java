/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * KeyboardScanner.java
 * Implementation of the KeyboardScanner class, which provides a singleton
 * scanner for reading user input.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Alex James (ajames) <ajames36@uic.edu>
 */

import java.util.Scanner;

public class KeyboardScanner {
    private static Scanner keyboardScanner;

    /**
     * Constructs a KeyboardScanner.
     * As this class is not supposed to be instantiated, the constructor is
     * private (and is empty).
     */
    private KeyboardScanner() { }

    /**
     * Returns the Scanner for reading user input.
     * @return the Scanner for reading user input
     */
    public static Scanner getKeyboardScanner() {
        // Construct the scanner if it hasn't been initialized
        if (keyboardScanner == null) {
            keyboardScanner = new Scanner(System.in);
        }

        return keyboardScanner;
    }
}
