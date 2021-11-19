/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * Direction.java
 * Implements the Direction class, which denotes a path between Places.
 * Used for getting a clean line when parsing a file.
 *
 *  Methods:
 *      private enum DirType
 *          DirType(String t, String a)
 *          public boolean match(String s)
 *          public String toString()
 *      public Direction(Scanner infile)
 *      public Place follow()
 *      void useKey(Artifacts a)
 *      public boolean match(String s)
 *      public void lock()
 *      public void unlock()
 *      public boolean islocked()
 *      public void print()
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Michael Ybarra (mybarra) (mybarr3@uic.edu)
 */

import java.util.*;

public class Direction {

    private int id, lockPattern;
    private Place from, to;
    private boolean locked;
    private DirType type;


    private enum DirType {

        N("North", "N"),
        W("West", "W"),
        S("South", "S"),
        E("East", "E"),
        U("Up", "U"),
        D("Down", "D"),
        NE("NorthEast", "NE"),
        NW("NorthWest", "NW"),
        SE("SouthEast", "SE"),
        SW("SouthWest", "SW"),
        NNE("North-NorthEast", "NNE"),
        NNW("North-NorthWest", "NNW"),
        ENE("East-NorthEAST", "ENE"),
        WNW("West-NorthWest", "WNW"),
        ESE("East-SouthEast", "ESE"),
        WSW("West-SouthWest", "WSW"),
        SSE("South-SouthEast", "SSE"),
        SSW("South-SouthWest", "SSW"),
        H("Help", "H"),
        NONE("NONE", "NONE");

        private final String text, abrev;

        DirType(String t, String a) {
            text = t;
            abrev = a;
        }

        public boolean match(String s) {
            if (this == NONE)
                return false;
            s = s.trim();
            return s.equalsIgnoreCase(text) || s.equalsIgnoreCase(abrev);
        }

        public String toString() {
            return text;
        }


		/* boolean - Returns true if the given string matches any text or the abbreviation of a direction, ignoring case. */
		public static boolean isADirection(String s) {
			for(DirType d: DirType.values()) {
				if( s.equalsIgnoreCase(d.text) || s.equalsIgnoreCase(d.abrev) ) {
					return true;
				}
			}
			return false;
		}

    }


        public Direction(Scanner infile, int version) {
			Scanner cleanLineScan = new Scanner(CleanLineScanner.getCleanLine(infile));  //Gets a clean line from file

			if(version == Game.GDF_VERSION_3_1) {
				this.id = cleanLineScan.nextInt();  //Currently unique without any specific meaning.
				this.from = Place.getPlaceByID(cleanLineScan.nextInt());
				this.type = DirType.valueOf(cleanLineScan.next().trim());  //Assigns the Enum constant that matches the string given

				int statusTo = cleanLineScan.nextInt();
				this.locked = (statusTo > 0) ? false : true;  //If to input is greater than 0, path is unlocked, else locked

				this.to = Place.getPlaceByID(Math.abs(statusTo));  //Absolute value of input is place id
				this.lockPattern = cleanLineScan.nextInt();  //Defaults to lock status cannot be changed
			}

			else if(version >= Game.GDF_VERSION_4_0) {
				this.id = cleanLineScan.nextInt();  //Currently unique without any specific meaning.
				if(this.id < 0) {  //IDs must be >0
					System.out.println("Attempted to construct a Direction with a negative ID. ID: " + this.id);
					System.out.println("Exiting the program.");
					System.exit(1);
				}
				this.from = Place.getPlaceByID(cleanLineScan.nextInt());
				this.type = DirType.valueOf(cleanLineScan.next().trim());  //Assigns the Enum constant that matches the string given

				int statusTo = cleanLineScan.nextInt();
				this.locked = (statusTo > 0) ? false : true;  //If to input is greater than 0, path is unlocked, else locked

				this.to = Place.getPlaceByID(Math.abs(statusTo));  //Absolute value of input is place id
				this.lockPattern = cleanLineScan.nextInt();  //Defaults to lock status cannot be changed
				if(this.lockPattern < 0) {  //lockPatterns must be >=0
					System.out.println("Attempted to construct a Direction with a negative lockPattern. lockPattern: " + this.lockPattern);
					System.out.println("Exiting the program.");
					System.exit(1);
				}
			}

			else {
				System.out.println("Version number " + version + " is not supported.");
				System.exit(1);
			}

			this.from.addDirection(this);  //Adds direction to the from place specified
			cleanLineScan.close();  //Close scanner
			return;
        }


        public Place follow() {
            if(!locked)
                return to;
			else
				IOService.getIO().display("That way is locked.");
            return from;
        }


        void useKey(Artifact a) {
            if (lockPattern > 0 && a.keyFits(lockPattern)) {
                locked = !locked;
				IOService.getIO().display("The path " + getDir() + " has been " +
										 (locked ? "locked." : "unlocked."));
			}
            return;
        }


        public boolean match(String s) {
            return type.match(s);
        }


		/* boolean – Returns true if the String passed is a direction. */
		public static boolean isADirection(String s) {
			return DirType.isADirection(s);  //Calls enum method to test if matches
		}



        // locking and unlocking
        public void lock() { locked = true; return; }
        public void unlock() { locked = false; return; }
        public boolean isLocked() { return locked; }

        // Print directions
        public void print() {
			System.out.println("Direction " + id + " travels " + type + " from " +
                                from.name() + " to " + to.name() + ",\n\t\tand is "+
                                (locked ? "locked" : "unlocked") + " with a lock pattern of " + lockPattern);
            return;
        }


		/* Prints a description of the current Direction. */
		public void display() {
			IOService.getIO().display(type + " to " +
									 (to == null ? "nowhere" : to.name()) +
									 (locked ? " - locked" : ""));
		}


		/* String - used by the Place class in getAvailableDirection() to get dirType as a string. */
		public String getDir() {
			return type.toString();
		}

}
