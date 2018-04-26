package com.mayhem.rs2.content.dialogue.impl;

import com.mayhem.rs2.content.dialogue.Dialogue;
import com.mayhem.rs2.content.dialogue.DialogueConstants;
import com.mayhem.rs2.content.dialogue.DialogueManager;
import com.mayhem.rs2.content.dialogue.Emotion;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.PlayerConstants;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;
import com.mayhem.rs2.entity.player.net.out.impl.SendRemoveInterfaces;

/**
 * Dialogue for Achievement
 * @author Daniel
 *
 */
public class AchievementDialogue extends Dialogue {
	
	public AchievementDialogue(Player player) {
		this.player = player;
	}

	@Override
	public boolean clickButton(int id) {
		switch(id) {
		case DialogueConstants.OPTIONS_3_1:
			
			boolean completed = true;
			for (com.mayhem.rs2.content.achievements.AchievementList achievement : player.getPlayerAchievements().keySet()) {
				if (achievement != null && player.getPlayerAchievements().get(achievement) != achievement.getCompleteAmount()) {
					completed = false;
					break;
				}
			}
			
			if (completed) {
				player.getInventory().addOrCreateGroundItem(13069, 1, true);
				player.getInventory().addOrCreateGroundItem(13070, 1, true);
				player.send(new SendMessage("You have been given an achievement cape and hood."));
			} else {
				player.send(new SendMessage("You have not completed all the achievements!"));				
			}
			player.send(new SendRemoveInterfaces());
			
			break;
		case DialogueConstants.OPTIONS_3_2:
			if (PlayerConstants.isDeveloper(player)) {
				player.getShopping().open(89);
			} else {
				DialogueManager.sendStatement(player, "Coming soon!");
			}
			break;
		case DialogueConstants.OPTIONS_3_3:
			player.send(new SendRemoveInterfaces());
			break;
			
		}
		return false;
	}

	@Override
	public void execute() {
		switch(next) {
		case 0:
			DialogueManager.sendNpcChat(player, 4249, Emotion.HAPPY, "Hello "+player.getUsername()+".", "How may I help you?");
			next++;
			break;
		case 1:
			DialogueManager.sendPlayerChat(player, Emotion.CALM, "What are you doing here?");
			next++;
			break;
		case 2:
			DialogueManager.sendNpcChat(player, 4249, Emotion.HAPPY, "I'm looking for the very best of course!");
			next++;			
			break;
		case 3:
			DialogueManager.sendPlayerChat(player, Emotion.CALM, "The very best?");
			next++;
			break;
		case 4:
			DialogueManager.sendNpcChat(player, 4249, Emotion.HAPPY, "Yes!", "Players that have completed all the achievements,", "will be rewarded with my cape.");
			next++;			
			break;
		case 5:
			DialogueManager.sendOption(player, "Obtain cape", "Nothing.");
			break;

		}
	}


}
