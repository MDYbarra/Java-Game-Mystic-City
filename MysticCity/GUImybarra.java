/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * GUImybarra.java
 * This file contains one of the GUI implementations of the UserInterface
 * interface.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Michael Ybarra (mybarra) <mybarr3@uic.edu>
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.DefaultCaret;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class GUImybarra  extends JFrame  implements UserInterface {

    private CountDownLatch latch;
	private Player player;
	private Character user;
	private JTextArea output, player_inv;
	private JTextField user_input;
	private JPanel panel;
	private JProgressBar health,hunger;
	

	public GUImybarra(Character chr){
		
		player = (Player)chr;
		this.user = chr;
		// commented for testing purposes
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
        latch = new CountDownLatch(1);

        
 		

		// status bars health and hunger
		// health
		health = new JProgressBar(SwingConstants.HORIZONTAL, 0, 10);
		TitledBorder pbTitle = new TitledBorder("Health: " + user.health + " / 10");
		health.setBackground(Color.GREEN);
		health.setBorder( new CompoundBorder( new EmptyBorder( 10, 10, 10, 10), pbTitle) );
		health.setValue(user.health);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		this.add(health, constraints);

		// hunger
		hunger = new JProgressBar(SwingConstants.HORIZONTAL, 0, 10);
		TitledBorder pbTitle2 = new TitledBorder("Hunger: " + ((Player) user).getHunger() + " / 10");
		hunger.setBackground(Color.GREEN);
		hunger.setBorder( new CompoundBorder( new EmptyBorder( 10, 10, 10, 10), pbTitle2) );
		hunger.setValue(((Player) user).getHunger());
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 0;
		this.add(hunger,constraints);


		// display player inventory with border no color incased in a panel
        player_inv = new JTextArea(15, 25);
        player_inv.setText(player.describeInventory());
        player_inv.setEditable(false);
        player_inv.setLineWrap(true);
        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setBorder(BorderFactory.createTitledBorder("Inventory"));
        JScrollPane pane = new JScrollPane(player_inv);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        inventoryPanel.add(pane);
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(inventoryPanel, constraints);


 		// Out put of the game incased in a panel
        output = new JTextArea(15, 40);
        output.setEditable(false);
        output.setLineWrap(true);
        JPanel outputPanel = new JPanel();
        outputPanel.setBorder(BorderFactory.createTitledBorder("Dialogue"));
        pane = new JScrollPane(output);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        outputPanel.add(pane);
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(outputPanel, constraints);


 		// Send a notification when the user enters a command
        Action enterAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (Game.getCurrentCharacter() != player) {
                    // Only accept user input when it is the character's turn
                    user_input.setText("");
                } else {
                    // Wake up getLine() when user input is present
                    latch.countDown();
                }
            }
        };


		// Input box 
        user_input = new JTextField(40);
        user_input.addActionListener(enterAction);
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Commands"));
        inputPanel.add(user_input);
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 3;
        add(inputPanel, constraints);
       	

        // set title with player name and visiblity
       	this.setTitle(chr.name);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		pack();


		// Play song when GUI 2 entered 
        try{
            AudioInputStream item = AudioSystem.getAudioInputStream(new File("secret.wav"));
            Clip myClip = AudioSystem.getClip();
            myClip.open(item);
            myClip.start();
            Thread.sleep(myClip.getMicrosecondLength()/1000);
        }
        catch (Exception e){e.printStackTrace();}

	}


	@Override
	/* void - Displays the string passed as an argument. */
	public void display(String str) {
		
		// updates screen
		// health
		TitledBorder pbTitle = new TitledBorder("Health: " + user.health + " / 10");
		health.setBorder( new CompoundBorder( new EmptyBorder( 10, 10, 10, 10), pbTitle) );
		health.setValue(user.health);
		// color will change depending on health status
		if(user.health <= 3){ health.setBackground(Color.RED);}
		else if(user.health <= 7 && user.health >= 4){ 
			health.setBackground(Color.YELLOW);
		}
		else if(user.health > 7){ health.setBackground(Color.GREEN);}

		// hunger
		hunger.setValue(((Player) user).getHunger());
		TitledBorder pbTitle2 = new TitledBorder("Hunger: " + ((Player) user).getHunger() + " / 10");
		hunger.setBorder( new CompoundBorder( new EmptyBorder( 10, 10, 10, 10), pbTitle2) );
		hunger.setValue(((Player) user).getHunger());
		// color will change depending on hunger status
		if(((Player) user).getHunger() <= 3)hunger.setBackground(Color.RED);
		else if(((Player) user).getHunger() <= 7 && ((Player) user).getHunger() >=4){ 
			hunger.setBackground(Color.YELLOW);
		}
		else if(((Player) user).getHunger() > 7){
			hunger.setBackground(Color.GREEN);
		}

		// inventory
		player_inv.setText(player.describeInventory());


		output.append(str + "\n");
		output.setCaretPosition(output.getDocument().getLength());
		return;
	}
	

 	public String getLine() {
        // Wait until the player presses enter or clicks a button
        try {
            pack();
            setVisible(true);
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        latch = new CountDownLatch(1);
        String text = user_input.getText();
        user_input.setText("");
        pack();
        setVisible(true);
        return text;
    }



}





