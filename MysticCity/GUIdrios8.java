/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * GUIdrios8.java
 * This file contains the GUI interface made by Daniel Rios.
 * The GUI has:
 *	- a output window
 *	- bars to track player stats
 *	- a header message to notify the player should make a move 
 *	- and a input box and submit button to make moves
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Daniel Rios (drios1) <drios8@uic.edu>
 */
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import javax.swing.border.*;
//import javax.swing.text.DefaultCaret;

public class GUIdrios8 extends JFrame implements UserInterface {

	private Character user;
	private JTextArea outputArea;
	private JTextField inputArea;
	private JLabel headerLabel;
	private JProgressBar healthBar;
	private JProgressBar hungerBar;
	private boolean inputSent = false;
	private boolean takingInput = false;
	
	
	/* Constructs the JFrame interface. */
	public GUIdrios8(Character chr) {
		this.user = chr;  //Saves character reference so it can update info
		
		this.setLayout(new BorderLayout());
		//this.setLayout(new GridBagLayout());
		//GridBagConstraints constraints = new GridBagConstraints();
		
		//Test labels
		headerLabel = new JLabel("Current Location: " + user.current.name());
		this.add(headerLabel, BorderLayout.NORTH);
		
		// constraints.fill = GridBagConstraints.HORIZONTAL;
		// constraints.gridwidth = 2;   //2 columns wide
		// constraints.ipady = 40;      //make this component tall
		// constraints.gridx = 0;
		// constraints.gridy = 0;
		// this.add(L1, constraints);
		
		
		//Health and Hunger bars
		healthBar = new JProgressBar(SwingConstants.VERTICAL, 0, 10);
		TitledBorder pbTitle = new TitledBorder("Health: " + user.health + " / 10");
		healthBar.setBorder( new CompoundBorder( new EmptyBorder( 150, 10, 150, 10), pbTitle) );
		healthBar.setValue(user.health);

		
		hungerBar = new JProgressBar(SwingConstants.VERTICAL, 0, 10);
		TitledBorder pbTitle2 = new TitledBorder("Hunger: " + ((Player) user).getHunger() + " / 10");
		hungerBar.setBorder( new CompoundBorder( new EmptyBorder( 150, 10, 150, 10), pbTitle2) );
		hungerBar.setValue(((Player) user).getHunger());
		
		
		JPanel barsPanel = new JPanel();
		barsPanel.setLayout(new GridLayout(1,2));
		TitledBorder panelTitle = new TitledBorder("Player stats:");
		barsPanel.setBorder( new CompoundBorder( new EmptyBorder( 10, 1, 10, 1), panelTitle) );
		barsPanel.add(healthBar);
		barsPanel.add(hungerBar);
		
		
		//Hardcoded input buttons
		JButton lookButton = new JButton("Look");
		JButton inventoryButton = new JButton("Inventory");
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() { 
		  public void actionPerformed(ActionEvent e) { 
			if(takingInput)
				inputSent = true;
		  } 
		});
		
		
		//Creating output scroll area
		outputArea = new JTextArea();
		outputArea.setEditable(false);
		outputArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(outputArea);
		//DefaultCaret caret = (DefaultCaret)outputArea.getCaret();
		//caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);  //Enables auto scrolling
		
		
		//Split pane for Player bars and Output Text Area
		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, barsPanel);
		//jsp.setDividerLocation(0.5);  //Sets divider to middle - only works on realized panes!
		jsp.setDividerLocation(300);  //Sets divider to middle?
		this.add(jsp, BorderLayout.CENTER);
		
		//Input text box
		inputArea = new JTextField("Enter text here!");		
		
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(1,2));
		jp.add(inputArea);  //top left
		jp.add(submitButton);  //top right
		//jp.add(lookButton);  //bottom left
		//jp.add(inventoryButton);  //bottom right
		this.add(jp, BorderLayout.SOUTH);

		
		this.setLocationRelativeTo(null);  //middle of screen
		this.setVisible(false);
		this.setSize(600,600);
		this.setTitle("Player - " + this.user.name);
	}


	@Override
	/* void - Displays the string passed as an argument. */
	public void display(String str) {
		TitledBorder pbTitle = new TitledBorder("Health: " + user.health + " / 10");
		healthBar.setBorder( new CompoundBorder( new EmptyBorder( 150, 10, 150, 10), pbTitle) );
		healthBar.setValue(user.health);
		
		hungerBar.setValue(((Player) user).getHunger());
		TitledBorder pbTitle2 = new TitledBorder("Hunger: " + ((Player) user).getHunger() + " / 10");
		hungerBar.setBorder( new CompoundBorder( new EmptyBorder( 150, 10, 150, 10), pbTitle2) );
		hungerBar.setValue(((Player) user).getHunger());
		
		outputArea.append(str + "\n");
		outputArea.setCaretPosition(outputArea.getDocument().getLength());
		return;
	}
	
	
	@Override
	/* String - Gets a line of user input. */
	public String getLine() {
		takingInput = true;
		String input;
		this.setVisible(true);
		headerLabel.setText("Current Location: " + user.current.name());
		
		while(true) {  //Loop waiting until user gives input
			if(inputSent) {  //When Submit button is clicked send input
				input = inputArea.getText();
				inputArea.setText("");  //Clear text box before progressing
				this.display(input);  //display command on screen
				
				inputSent = false;  //Toggle checks for input
				takingInput = false;
				
				this.setVisible(false);
				return input;
			}
			
			try {  //Wait about 2 seconds before continuing
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}