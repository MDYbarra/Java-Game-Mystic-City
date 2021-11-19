/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * CleanLineScanner.java
 * Used for getting a clean line when parsing a file.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Michael Ybarra (mybarra) (mybarr3@uic.edu)
 */

import java.util.*;

public class CleanLineScanner {

    private CleanLineScanner(){ }

    public static String getCleanLine(Scanner s) {
        String line;
        while (true){
            if (!s.hasNextLine())
                break;
            line = s.nextLine();
            int commentStart = line.indexOf("//");
            if(commentStart == 0)
                continue;
            if(commentStart > 0)
                line = line.substring(0,commentStart);
            line = line.trim();
            if(line.length() > 0)
                return line;
        }
        return null;
    }
}
