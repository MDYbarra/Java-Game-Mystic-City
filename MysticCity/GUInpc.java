/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * GUInpc.java
 * This file contains the GUI created for Npcs to use during the game.
 * The GUI shows what the Npcs are doing during their turns and automatically scrolls.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Daniel Rios (drios1) <drios8@uic.edu>
 */
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import javax.swing.text.DefaultCaret;

public class GUInpc extends JFrame implements UserInterface {
	
	private Character user;
	private JTextArea outputArea;
	
	/* Constructs the JFrame interface. */
	public GUInpc(Character chr) {
		this.user = chr;  //Saves character reference so it can update info
		
		this.setLayout(new BorderLayout());
		
		outputArea = new JTextArea();
		outputArea.setEditable(false);
		outputArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(outputArea);
		this.add(scrollPane, BorderLayout.CENTER);

		this.setLocationRelativeTo(null);  //middle of screen
		this.setVisible(true);
		this.setSize(500,300);
		this.setTitle("NPC - " + this.user.name);
	}
	
	
	@Override
	/* void - Displays the string passed as an argument. */
	public void display(String str) {
		outputArea.append(str + "\n");	
		outputArea.setCaretPosition(outputArea.getDocument().getLength());
		return;
	}
	
	
	@Override
	/* void - Gets a line of user input. */
	public String getLine() {
		return "GUInpc.getLine() should never be called.";
	}
}