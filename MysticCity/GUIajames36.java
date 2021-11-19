/**
 * CS 342 (Fall 2018) - Homework 05
 *
 * GUIajames36.java
 * This file contains one of the GUI implementations of the UserInterface
 * interface.
 *
 * Author(s) [name, ACCC username, NetID]:
 *  - Alex James (ajames) <ajames36@uic.edu>
 */

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.CountDownLatch;
import javax.swing.*;
import javax.swing.text.*;

public class GUIajames36 extends JFrame implements UserInterface {
    private Player player;
    private CountDownLatch latch;
    private JTextArea playerInventory;
    private JLabel playerHealth;
    private JLabel playerHunger;
    private JTextField textInput;
    private JTextArea gameOutput;

    public GUIajames36(Character chr) {
        player = (Player) chr;
        latch = new CountDownLatch(1);

        setTitle("CS 342 HW 05 - " + player.charName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Create the player inventory area
        playerInventory = new JTextArea(15, 25);
        playerInventory.setText(player.describeInventory());
        playerInventory.setEditable(false);

        // Enclose the player inventory area in a panel
        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setBorder(BorderFactory.createTitledBorder("Player Inventory"));
        // Make the game output area scrollable
        JScrollPane pane = new JScrollPane(playerInventory);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        inventoryPanel.add(pane);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(inventoryPanel, constraints);

        // Create the player health label
        playerHealth = new JLabel("Health level: " + player.getHealth());
        // Create the player hunger label
        playerHunger = new JLabel("Hunger level: " + player.getHunger());

        // Enclose the player stats in a panel
        JPanel statPanel = new JPanel();
        statPanel.setBorder(BorderFactory.createTitledBorder("Player Stats"));
        statPanel.setLayout(new GridLayout());
        statPanel.add(playerHunger);
        statPanel.add(playerHealth);
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(statPanel, constraints);

        // Create the game output area
        gameOutput = new JTextArea(15, 40);
        gameOutput.setEditable(false);

        // Enclose the game output area in a panel
        JPanel outputPanel = new JPanel();
        outputPanel.setBorder(BorderFactory.createTitledBorder("Game Output"));
        // Make the game output area scrollable
        pane = new JScrollPane(gameOutput);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        outputPanel.add(pane);
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(outputPanel, constraints);

        // Send a notification when the user enters a command
        Action enterAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (Game.getCurrentCharacter() != player) {
                    // Only accept user input when it is the character's turn
                    textInput.setText("");
                } else {
                    // Wake up getLine() when user input is present
                    latch.countDown();
                }
            }
        };

        // Create the command input field
        textInput = new JTextField(40);
        // Listen for text input notifications
        textInput.addActionListener(enterAction);

        // Enclose the command input field in a panel
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createTitledBorder("Command Input"));
        inputPanel.add(textInput);
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(inputPanel, constraints);

        pack();
    }

    private void updatePlayerInfo() {
        // Update the player inventory
        playerInventory.setText(player.describeInventory());

        // Update the player stats
        playerHealth.setText("Health level: " + player.getHealth());
        playerHunger.setText("Hunger level: " + player.getHunger());

        pack();
    }

    public void display(String str) {
        // Update the player stats
        updatePlayerInfo();
        if (str.contains("------------------------------------------------") ||
            str.contains(player.charDescription())) {
            // Ignore these
        } else if (str.contains("Not a valid command")) {
            JOptionPane.showMessageDialog(this, "Invalid command specified. " +
                                          "Please try again.",
                                          "Invalid command",
                                          JOptionPane.ERROR_MESSAGE);
        } else if (str.contains("has gone too long without eating") ||
                   str.contains("has died")) {
            JOptionPane.showMessageDialog(this, str, "Too bad!",
                                          JOptionPane.WARNING_MESSAGE);
        } else if (str.contains("has exited the game")) {
            JOptionPane.showMessageDialog(this, str);
        } else {
            gameOutput.append(str + "\n");
            // Autoscroll down if needed
            gameOutput.setCaretPosition(gameOutput.getDocument().getLength());
            pack();
        }
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
        String text = textInput.getText();
        textInput.setText("");
        pack();
        setVisible(false);
        return text;
    }
}
