package com.mayhem.rs2.content;

import com.mayhem.core.util.ItemNames;
import com.mayhem.core.util.Utility;
import com.mayhem.rs2.content.achievements.AchievementHandler;
import com.mayhem.rs2.content.achievements.AchievementList;
import com.mayhem.rs2.content.dialogue.DialogueManager;
import com.mayhem.rs2.entity.Animation;
import com.mayhem.rs2.entity.item.Item;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

/**
 * Handles crystal chest
 * 
 * @author Daniel
 *
 */
public class CrystalChest {

	/**
	 * Ids of key halves
	 */
	public static final Item[] KEY_HALVES = { new Item(985), new Item(987) };

	/**
	 * Crystal key Id
	 */
	public static final Item KEY = new Item(989);

	/**
	 * Creates the key
	 * 
	 * @param player
	 */
	public static void createKey(final Player player) {
		if (player.getInventory().contains(KEY_HALVES)) {
			player.getInventory().remove(KEY_HALVES[0]);
			player.getInventory().remove(KEY_HALVES[1]);
			player.getInventory().add(KEY);
			DialogueManager.sendItem1(player, "You have combined the two parts to form a key.", KEY.getId());
		}
	}

	public static final Item[] UNCOMMON_CHEST_REWARDS = { new Item(), new Item(12504), new Item(12502), new Item(12500), new Item(12498), 
			new Item(12506), new Item(12508), new Item(12510), new Item(12512), new Item(13113), new Item(13105), new Item(13117), new Item(13118), new Item(13130), new Item(13113), new Item(ItemNames.ZAMORAK_PLATEBODY), new Item(ItemNames.ZAMORAK_PLATELEGS), new Item(ItemNames.SARADOMIN_PLATEBODY), new Item(ItemNames.SARADOMIN_PLATELEGS), new Item(ItemNames.GUTHIX_PLATEBODY), new Item(ItemNames.GUTHIX_PLATELEGS), 
			new Item(ItemNames.ZAMORAK_FULL_HELM), new Item(ItemNames.GUTHIX_FULL_HELM), new Item(ItemNames.SARADOMIN_FULL_HELM), new Item(ItemNames.DRAGON_MED_HELM), new Item(ItemNames.DRAGON_PLATESKIRT), new Item(ItemNames.DRAGON_SQ_SHIELD), new Item(ItemNames.HELM_OF_NEITIZNOT), new Item(ItemNames.WARRIOR_HELM), new Item(ItemNames.ARCHER_HELM), new Item(ItemNames.FARSEER_HELM), new Item(ItemNames.RUNE_SCIMITAR), new Item(ItemNames.RUNE_2H_SWORD), new Item(ItemNames.DRAGON_DAGGER), new Item(ItemNames.DRAGON_SCIMITAR), new Item(ItemNames.DRAGON_MACE), new Item(ItemNames.DRAGON_BATTLEAXE),
			new Item(ItemNames.DRAGON_LONGSWORD), new Item(20002), new Item(4675), new Item(12193), new Item(12195), new Item(12197), new Item(12199), new Item(12203), new Item(12490), new Item(12492), new Item(12494), new Item(12496), new Item(12608), new Item(12610), new Item(12612), new Item(10384), new Item(10386), new Item(10388), new Item(10390), new Item(10368), new Item(10370), new Item(10372), new Item(10374), new Item(10376), new Item(10378), new Item(10380), new Item(10382), new Item(10444), new Item(10450), new Item(ItemNames.TZHAARKETEM), new Item(ItemNames.TZHAARKETOM),  
			new Item(20128), new Item(20131), new Item(20134), new Item(20137), new Item(20140)};

	public static final Item[] RARE_CHEST_REWARDS = {new Item(12026), new Item(12056), new Item(12086), new Item(20161), new Item(20143), new Item(ItemNames.UNCUT_DRAGONSTONE), new Item(ItemNames.UNCUT_ONYX), new Item(ItemNames.DRAGON_SPEAR), new Item(ItemNames.DRAGON_2H_SWORD), new Item(11935, Utility.random(100)), new Item(537, Utility.random(100)), new Item(11944, Utility.random(75)), new Item(4151),
	 new Item(12526), new Item(12829), };

	/**
	 * Chest rewards
	 */
	public static final Item[] COMMON_CHEST_REWARDS = {

			/* Armours */
			new Item(ItemNames.RUNE_FULL_HELM), new Item(ItemNames.RUNE_PLATEBODY), new Item(ItemNames.RUNE_KITESHIELD), new Item(ItemNames.RUNE_PLATELEGS), new Item(ItemNames.RUNE_PLATESKIRT), new Item(ItemNames.RUNE_BOOTS), new Item(ItemNames.RUNE_CHAINBODY), new Item(ItemNames.RUNE_CROSSBOW), new Item(ItemNames.RING_OF_RECOIL),

			/* Skilling */
			new Item(392, Utility.random(50)), new Item(372, Utility.random(50)), new Item(2364, Utility.random(5)), new Item(452, Utility.random(10)), new Item(212, Utility.random(10)), new Item(216, Utility.random(10)), new Item(218, Utility.random(10)), new Item(200, Utility.random(20)), new Item(206, Utility.random(20)), new Item(210, Utility.random(10)), new Item(1618, Utility.random(20)), new Item(1622, Utility.random(20)), new Item(1620, Utility.random(25)), new Item(1624, Utility.random(30)),

			/* Random */
			new Item(ItemNames.TAN_CAVALIER),new Item(20166), new Item(4740, Utility.random(500)), new Item(ItemNames.DARK_CAVALIER), new Item(ItemNames.BLACK_CAVALIER), new Item(ItemNames.BLACK_BERET), new Item(ItemNames.RED_HEADBAND), new Item(ItemNames.PIRATES_HAT), new Item(ItemNames.BROWN_HEADBAND), new Item(ItemNames.SHARK), new Item(ItemNames.MONKEY_NUTS), new Item(ItemNames.EYE_PATCH) };

	/**
	 * Searches the chest
	 * 
	 * @param player
	 * @param x
	 * @param y
	 */

	public static void searchChest(final Player player, final int x, final int y) {
		if (player.getInventory().contains(KEY)) {
			player.send(new SendMessage("You unlock the chest with your key."));
			player.getInventory().remove(KEY);
			AchievementHandler.activate(player, AchievementList.OPEN_70_CRYSTAL_CHESTS, 1);
			player.getUpdateFlags().sendAnimation(new Animation(881));
			player.getInventory().add(new Item(995, Utility.random(50000)));
			Item itemReceived;
			switch (Utility.random(50)) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				itemReceived = Utility.randomElement(UNCOMMON_CHEST_REWARDS);
				break;
			case 25:
				itemReceived = Utility.randomElement(RARE_CHEST_REWARDS);
				break;
			default:
				itemReceived = Utility.randomElement(COMMON_CHEST_REWARDS);
			}

			player.getInventory().addOrCreateGroundItem(itemReceived.getId(), itemReceived.getAmount(), true);
			player.send(new SendMessage("You find " + Utility.determineIndefiniteArticle(itemReceived.getDefinition().getName()) + " " + itemReceived.getDefinition().getName() + " in the chest."));
			if (itemReceived.getDefinition().getGeneralPrice() < 100_000) {
				switch (Utility.random(50)) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
				case 9:
				case 10:
					itemReceived = Utility.randomElement(UNCOMMON_CHEST_REWARDS);
					break;
				case 25:
					itemReceived = Utility.randomElement(RARE_CHEST_REWARDS);
					break;
				default:
					itemReceived = Utility.randomElement(COMMON_CHEST_REWARDS);
				}
				player.getInventory().addOrCreateGroundItem(itemReceived.getId(), itemReceived.getAmount(), true);
				player.send(new SendMessage("You find " + Utility.determineIndefiniteArticle(itemReceived.getDefinition().getName()) + " " + itemReceived.getDefinition().getName() + " in the chest."));
			}
		} else {
			player.send(new SendMessage("You need a key to open this chest."));
		}
	}

}
