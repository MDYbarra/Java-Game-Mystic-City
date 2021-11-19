======================
Name: Daniel Rios, Alex James, Michael Ybarra
NetId: drios8, ajames36, mybarr3
CS User: drios1, ajames, mybarra
Homework 5
CS 342
======================

=================
  Who did what?
=================
drios8:
- Wrote initial IOService and GUInpc implementations
- Created GUI implementation (GUIdrios8)

ajames36:
- Extended Artifact class to add a game-ending artifact (Golden Skull)
- Added additional getter methods to the Character class (for GUIs to use)
- Created GUI implementation (GUIajames36)

mybarr3:
- Created GUI implementation (GUImybarr3)
- Updated UML diagram


==========================
  How to run the program
==========================
  At the command line type the following commands:
    make
    java GameTester <filename> <requested amount of players>

  Note: 'filename' and 'requested amount of players' are optional parameters
  Examples:
		java GameTester
	or
		java GameTester MysticCity52.gdf
	or
		java GameTester MysticCity52.gdf 4



======================
  Compatibility Note
======================
  This program runs on GDF version 5.2.
  Also, it is backwards compatible with GDF versions 5.0, 4.0, 3.1.


======================
  Available commands
======================
  All input is not case sensitive.

  go <direction> : Attempts to move in the desired direction given.
  <direction> : Attempts to move in the desired direction given.
  Note: Available direction inputs are the 16 cardinal directions, both spelled out and abbreviated.

  look : Displays the name and description of users surroundings.

  inventory: Displays the player's inventory
  inve: Displays the player's inventory

  get <artifact>: Attempts to add an artifact of the specified name in the current place to the player's inventory
  drop <artifact>: Attempts to remove an artifact from the player's inventory and leave the it in the current place
  use <artifact>: Attempts to use an artifact in the player's inventory

  exit : Exits the program.
  quit : Exits the program.

  gui <ui number> : Switches to the specified GUI.

  Example commands:
    go north
    Go North
    N
    Go n
    look
    Look
	get leather bag
	drop leather bag
	use brass key
	inventory
	inve
    Quit
    EXIT
    GUI 1



=======================
  Allowed NPC Actions
=======================
  Go, Use, Drop, Get are actions Npcs can perform.
  Look, Inventory, Quit, and Exit are not available to Npcs.
  Note: Npc actions are output to terminal so players can see what is happening.


========================================
  Examples of how the program responds
========================================

	 --Response to look command--
	---------------------------------------------------------
	You are at the Entrance Hall.
	You are standing in the entrance hall of the great six-room dungeon
	There are doors to the east and north, and a stairway leading down
	The main exit ( from the game ) is to the west

	What do you want to do, Zaphod Beeblrox? >> look

	You look around and see:
	dusty lamp - $20, 1 lb(s)
	This lamp is coated in dust, but may still work.
	leather bag - $20, 1 lb(s)
	This large leather bag looks like it would hold a lot.
	sandwich - $100, 1 lb(s)
	It looks edible...
	Zaphod Beeblrox, hunger: 9, health: 10, Zaphod Beeblebrox is the President of the Universe He has 2 heads and 3 arms, and likes to have a good time.



	 --Response to available direction to move being input--
	---------------------------------------------------------
	You are at the Entrance Hall.
	You are standing in the entrance hall of the great six-room dungeon
	There are doors to the east and north, and a stairway leading down
	The main exit ( from the game ) is to the west

	What do you want to do, Zaphod Beeblrox? >> go north
	Zaphod Beeblrox followed direction north.



	 --Response to unavailable direction to move being input--
	---------------------------------------------------------
	You are at the Potions Lab.
	There is a cauldron of thick green goop here,
	bubbling slowly over a cool blue flame.
	Doors lead to the west and east.

	This area is not suitable to support life.
	What do you want to do, Lost Wanderer? >> go up
	Lost Wanderer could not follow direction up.



	 --Response to Direction being available but locked--
	---------------------------------------------------------
	You are at the Entrance Hall.
	You are standing in the entrance hall of the great six-room dungeon
	There are doors to the east and north, and a stairway leading down
	The main exit ( from the game ) is to the west

	What do you want to do, Player 3? >> go down
	That way is locked.
	Player 3 could not follow direction down.



	 --Response to use <artifact> command (that's usable)--
	---------------------------------------------------------
	You are at the Entrance Hall.
	You are standing in the entrance hall of the great six-room dungeon
	There are doors to the east and north, and a stairway leading down
	The main exit ( from the game ) is to the west

	What do you want to do, Zaphod Beeblrox? >> use brass key
	The path Down has been unlocked.



	 --Response to use <artifact> command (that's not usable)--
	---------------------------------------------------------
	You are at the Entrance Hall.
	You are standing in the entrance hall of the great six-room dungeon
	There are doors to the east and north, and a stairway leading down
	The main exit ( from the game ) is to the west

	What do you want to do, Zaphod Beeblrox? >> use leather bag
	Zaphod Beeblrox tried to use leather bag but nothing happened.



	 --Response to inventory/inve command--
	---------------------------------------------------------
	You are at the Entrance Hall.
	You are standing in the entrance hall of the great six-room dungeon
	There are doors to the east and north, and a stairway leading down
	The main exit ( from the game ) is to the west

	What do you want to do, Zaphod Beeblrox? >> inve

	Player inventory contains:
	sandwich - $100, 1 lb(s)
	It looks edible...
	hitchiker's guide - $20, 10 lb(s)
	The Hitchiker's Guide appears as a small leather-bound book,
	with images of stars and planets on the cover.
	Total weight: 11 lbs, Total Value: $120.



	 --Response to get <artifact> command--
	---------------------------------------------------------
	You are at the Entrance Hall.
	You are standing in the entrance hall of the great six-room dungeon
	There are doors to the east and north, and a stairway leading down
	The main exit ( from the game ) is to the west

	What do you want to do, Zaphod Beeblrox? >> get sandwich
	You have grabbed the 'sandwich'.



	 --Response to drop <artifact> command--
	---------------------------------------------------------
	You are at the Entrance Hall.
	You are standing in the entrance hall of the great six-room dungeon
	There are doors to the east and north, and a stairway leading down
	The main exit ( from the game ) is to the west

	What do you want to do, Zaphod Beeblrox? >> drop sandwich
	You have dropped the 'sandwich'.



	 --Exit or quit command is input--
	---------------------------------------------------------
	You are at the Entrance Hall.
	You are standing in the entrance hall of the great six-room dungeon
	There are doors to the east and north, and a stairway leading down
	The main exit ( from the game ) is to the west

	What do you want to do, Player 4? >> exit
	Player 4 has exited the game.



	 --Character Death Response--
	---------------------------------------------------------
	You are at the Potions Lab.
	There is a cauldron of thick green goop here,
	bubbling slowly over a cool blue flame.
	Doors lead to the west and east.

	Lost Wanderer has gone too long without eating!
	Lost Wanderer has died.
