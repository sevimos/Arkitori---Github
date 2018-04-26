package com.mayhem.rs2.entity.player;

import com.mayhem.rs2.content.achievements.AchievementHandler;
import com.mayhem.rs2.content.achievements.AchievementList;
import com.mayhem.rs2.content.dialogue.DialogueManager;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

/**
 * Handle Account security
 * @author Daniel
 *
 */
public class AccountSecurity {

	/**
	 * Player
	 */
	private Player player;
	
	/**
	 * Bank Pins
	 * @param player
	 */
	public AccountSecurity(Player player) {
		this.player = player;
	}
	
	/**
	 * Sets the bank pin
	 * @param input
	 */
	public void setPin(String input) {
		if (input.length() != 4) {
			DialogueManager.sendStatement(player, "Your pin must consist of four numbers!");
			return;
		}
		player.setPin(input); 
		DialogueManager.sendInformationBox(player, "Bank Pin", "You have successfully set your bank pin!", "Your new pin is:", "@red@"+player.getPin(), "Write it down so you don't forget!");
		AchievementHandler.activate(player, AchievementList.SETUP_A_BANK_PIN, 1);
	}
	
	/**
	 * Enters the pin
	 * @param input
	 */
	public void enterPin(String input) {
		if (player.getPin().equalsIgnoreCase(input)) {
			player.enteredPin = true;
			player.send(new SendMessage("You have successfully entered your pin"));
			player.getBank().openBank();
		} else {
			DialogueManager.sendStatement(player, "Wrong pin entered!");
		}
	}
	
	/**
	 * Removes the pin
	 * @param input
	 */
	public void removePin(String input) {
		if (player.getPin().equalsIgnoreCase(input)) {
			player.setPin(null);
			DialogueManager.sendStatement(player, "You have successfully removed your bank pin!");
		} else {
			DialogueManager.sendStatement(player, "You have entered the wrong pin!");
		}
	}
	
	/**
	 * Sets IP address
	 * @param input
	 */
	public void setIP(String input) {
		if (player.ipSet) {
			DialogueManager.sendStatement(player, "You already have an IP address set!");
			System.out.println("stored value is: " + player.getIP());
			return;
		}
		player.setIP(input);
		player.ipSet = true;
		DialogueManager.sendStatement(player, "You have set your IP address to:", player.getIP());
		AchievementHandler.activate(player, AchievementList.SET_YOUR_ACCOUNT_DETAILS, 1);
	}
	
	/**
	 * Sets full name
	 * @param input
	 */
	public void setName(String input) {
		if (player.nameSet) {
			DialogueManager.sendStatement(player, "You already have your name set!");
			System.out.println("stored value is: " + player.getFullName());
			return;
		}
		player.setFullName(input);
		player.nameSet = true;
		DialogueManager.sendStatement(player, "You have set your name to:", player.getFullName());
		AchievementHandler.activate(player, AchievementList.SET_YOUR_ACCOUNT_DETAILS, 1);
	}	
	
	/**
	 * Sets recovery
	 * @param input
	 */
	public void setRecovery(String input) {
		if (player.recoverySet) {
			DialogueManager.sendStatement(player, "You already have a recovery set!");
			System.out.println("stored value is: " + player.getRecovery());
			return;
		}
		player.setRecovery(input);
		player.recoverySet = true;
		DialogueManager.sendStatement(player, "You have set your recovery to:", player.getRecovery());
		AchievementHandler.activate(player, AchievementList.SET_YOUR_ACCOUNT_DETAILS, 1);
	}	
	
	/**
	 * Sets email
	 * @param input
	 */
	public void setEmail(String input) {
		if (player.emailSet) {
			DialogueManager.sendStatement(player, "You already have a email address set!");
			System.out.println("stored value is: " + player.getEmailAddress());
			return;
		}
		player.setEmailAddress(input);
		player.emailSet = true;
		DialogueManager.sendStatement(player, "You have set your email address to:", player.getEmailAddress());
		AchievementHandler.activate(player, AchievementList.SET_YOUR_ACCOUNT_DETAILS, 1);
	}	

}
