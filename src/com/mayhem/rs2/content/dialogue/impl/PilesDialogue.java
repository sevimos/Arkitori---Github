package com.mayhem.rs2.content.dialogue.impl;

import com.mayhem.core.util.Utility;
import com.mayhem.rs2.content.achievements.AchievementHandler;
import com.mayhem.rs2.content.achievements.AchievementList;
import com.mayhem.rs2.content.dialogue.Dialogue;
import com.mayhem.rs2.content.dialogue.DialogueConstants;
import com.mayhem.rs2.content.dialogue.DialogueManager;
import com.mayhem.rs2.content.dialogue.Emotion;
import com.mayhem.rs2.entity.item.Item;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendRemoveInterfaces;

/**
 * Handles the wilderness resource arena
 * @author Daniel
 *
 */
public class PilesDialogue extends Dialogue {

	/**
	 * Piles dialogue
	 * @param player
	 */
	public PilesDialogue(Player player) {
		this.player = player;
	}

	/**
	 * The items that may be converted in the arena
	 */
	public int ITEMS[][] = { { 3142, 3143 }, { 451, 452 }, { 11934, 11935 }, { 440, 441 }, { 453, 454 }, { 444, 445 }, { 447, 448 }, { 449, 450 }, { 1515, 1516 }, { 1513, 1514 } };

	@Override
	public boolean clickButton(int id) {
		switch (id) {
		case DialogueConstants.OPTIONS_3_1:
			player.send(new SendRemoveInterfaces());

			for (int i = 0; i < ITEMS.length; i++) {
				if (player.getInventory().hasItemId(new Item(ITEMS[i][0]))) {
					int amount = player.getInventory().getItemAmount(ITEMS[i][0]);
					int payment = player.getInventory().getItemAmount(ITEMS[i][0]) * 100;
					
					if (!player.getInventory().hasItemId(new Item(995, payment))) {
						DialogueManager.sendStatement(player, Utility.format(payment) + " coins is required to do this; which you do not have!"); 
						break;
					}
					player.getInventory().remove(new Item(ITEMS[i][0], amount));
					player.getInventory().add(new Item(ITEMS[i][1], amount));
					player.getInventory().remove(995, payment);
					DialogueManager.sendInformationBox(player, "Piles", "You have noted:", "@blu@" + amount + " </col>items", "You have paid:", "@blu@" + Utility.format(payment) + " </col>coins");
					AchievementHandler.activate(player, AchievementList.EXCHANGE_1000_ITEMS_PILES, amount);
					break;
				} else {
					DialogueManager.sendStatement(player, "You do not have any items that are allowed to be noted!");
				}
			}

			break;

			case DialogueConstants.OPTIONS_3_2:
				for (int i = 0; i < ITEMS.length; i++) {
					if (player.getInventory().hasItemId(new Item(ITEMS[i][1]))) {
						int unnotedItem = ITEMS[i][0];
						int notedItem = ITEMS[i][1];
						int amount = player.getInventory().getItemAmount(notedItem);
						int payment = amount * 100;
						if (!player.getInventory().hasItemId(new Item(995, payment))) {
							DialogueManager.sendStatement(player, Utility.format(payment) + " coins is required to do this; which you do not have!");
							break;
						}
						player.getInventory().remove(new Item(notedItem, amount));
						player.getInventory().add(new Item(unnotedItem, amount));
						player.getInventory().remove(995, payment);
						DialogueManager.sendInformationBox(player, "Piles", "You have unnoted:", "@blu@" + amount + " </col>items", "You have paid:", "@blu@" + Utility.format(payment) + " </col>coins");
						AchievementHandler.activate(player, AchievementList.EXCHANGE_1000_ITEMS_PILES, amount);
						break;
					} else {
						DialogueManager.sendStatement(player, "You do not have any items that are allowed to be unnoted!");
					}
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
		switch (next) {

		case 0:
			DialogueManager.sendNpcChat(player, 13, Emotion.HAPPY, "I can note/unnote any items obtained from the resource", "arena for 100 coins an item.");
			next++;
			break;
		case 1:
			DialogueManager.sendOption(player, "Note items", "Unnote items", "Nevermind");
			break;

		}
	}

}
