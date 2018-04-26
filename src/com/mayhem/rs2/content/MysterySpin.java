package com.mayhem.rs2.content;

import java.util.*;

import com.mayhem.core.definitions.ItemDefinition;
import com.mayhem.core.util.Utility;
import com.mayhem.rs2.entity.item.Item;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendInterface;
import com.mayhem.rs2.entity.player.net.out.impl.SendMysteryBox;
import com.mayhem.rs2.entity.player.net.out.impl.SendString;

/**
 * Spinning Mystery Box Handler
 * 
 * @author Goon
 * @date Apr 17, 2018, 6:43:44 PM
 */
public class MysterySpin {

	/**
	 * The item id of the mystery box required to trigger the event
	 */
	private static final int MYSTERY_BOX = 8167;

	/**
	 * A map containing a List of {@link Item}'s that contain items relevant to
	 * their rarity.
	 */
	private static Map<Rarity, List<Item>> items = new HashMap<>();

	/**
	 * Stores an array of items into each map with the corresponding rarity to the
	 * list
	 */
	static {
		items.put(Rarity.COMMON, Arrays.asList(
				new Item(12934, 5000),
				new Item(13307, 10000),
				new Item(6686, 100),
				new Item(12696, 100),
				new Item(3025, 100),
				new Item(2445, 100),
				new Item(9242, 500),
				new Item(565, 2500),
				new Item(386, 400))
			
			
		
		
			


		);

		items.put(Rarity.UNCOMMON, Arrays.asList(
				new Item(2581, 1),
				new Item(11230, 1000),
				new Item(220, 500),
				new Item(218, 500),
				new Item(3050, 500),
				new Item(9244, 1000))
				);

		items.put(Rarity.RARE,
				Arrays.asList(
				new Item(19478, 1),
				new Item(11283, 1),
				
				 new Item(12765, 1),
				new Item (11235, 1),
				 new Item(3483, 1),
			
				 new Item(3481, 1), 
				 new Item(12596, 1),
				new Item(2581, 1), 
				 new Item(2577, 1),
				 new Item(12004, 1),
				 new Item(12389, 1),
				 new Item(11806, 1),
				 new Item(11808, 1),
				 new Item(11804, 1),
				new Item(12391, 1),
				 new Item(10330, 1),
				 new Item(10332, 1),
				 new Item(10334, 1),
				 new Item(10336, 1),
				 new Item(10338, 1),
				 new Item(10340, 1),
				 new Item(10342, 1),
				 new Item(10344, 1),
				 new Item(10346, 1),
				 new Item(10348, 1),
				 new Item(10350, 1),
				 new Item(10352, 1),
				 new Item(12422, 1), 
				 new Item(12424, 1), 
					new Item(12426, 1), 
				 new Item(20011, 1), 
				 new Item(20014, 1), 
					new Item(21012, 1), 
				new Item(4566, 1), 
				 new Item(12351, 1),
				new Item(12357, 1), 
				new Item(11335, 1), 
					new Item(11862, 1), 
				new Item(13307, Utility.randomNumber(100000)),
				 new Item(995, Utility.randomNumber(25000000)),
				 new Item(4083, 1),
				 new Item(9470, 1),
				 new Item(5607, 1),
					new Item(12926, 1),
					new Item(12904, 1),
					new Item(13199, 1),
					new Item(13197, 1),
					new Item(11802, 1),
					new Item(11832, 1),
					new Item(11834, 1)));
						
						
						
	}

	/**
	 * The player object that will be triggering this event
	 */
	private Player player;

	/**
	 * Constructs a new myster box to handle item receiving for this player and this
	 * player alone
	 * 
	 * @param player
	 *            the player
	 */
	public MysterySpin(Player player) {
		this.player = player;
	}

	/**
	 * Can the player open the mystery box
	 */
	private boolean canMysteryBox = true;

	/**
	 * The prize received
	 */
	private int mysteryPrize;

	private int mysteryAmount;

	private int spinNum = 0;

	private final int INTERFACE_ID = 47000;
	private final int ITEM_FRAME = 47101;

	/**
	 * The chance to obtain the item
	 */
	private int random;

	/**
	 * The rarity of the reward
	 */
	private Rarity rewardRarity;

	/**
	 * Represents the rarity of a certain list of items
	 */
	enum Rarity {
		UNCOMMON("<col=005eff>"), COMMON("<col=336600>"), RARE("<col=B80000>");

		private String color;

		Rarity(String color) {
			this.color = color;
		}

		public String getColor() {
			return color;
		}

		public static Rarity forId(int id) {
			for (Rarity tier : Rarity.values()) {
				if (tier.ordinal() == id)
					return tier;
			}
			return null;
		}
	}

	public void spin() {
		// Server side checks for spin
		if (!canMysteryBox) {
			player.sendMessage("Please finish your current spin.");
			return;
		}
		if (!player.getInventory().hasItemId(MYSTERY_BOX)) {
			player.sendMessage("You require a mystery box to do this.");
			return;
		}

		// Delete box
		player.getInventory().remove(MYSTERY_BOX, 1);
		player.sendMessage(":resetBox");
		for (int i = 0; i < 66; i++) {
			player.send(new SendMysteryBox(-1, 1, ITEM_FRAME, i));
		}
		spinNum = 0;
		// Initiate spin
		player.sendMessage(":spin");
		process();
	}

	public void process() {
		// Reset prize
		mysteryPrize = -1;

		mysteryAmount = -1;
		// Can't spin when already in progress
		canMysteryBox = false;

		random = Utility.random(200);
		List<Item> itemList = random < 105 ? items.get(Rarity.COMMON)
				: random >= 105 && random <= 190 ? items.get(Rarity.UNCOMMON) : items.get(Rarity.RARE);
		rewardRarity = random < 105 ? Rarity.COMMON : random >= 105 && random <= 190 ? Rarity.UNCOMMON : Rarity.RARE;

		Item item = Utility.getRandomItem(itemList);

		mysteryPrize = item.getId();

		mysteryAmount = item.getAmount();

		// Send items to interface
		// Move non-prize items client side if you would like to reduce server load
		if (spinNum == 0) {
			for (int i = 0; i < 66; i++) {
				Rarity notPrizeRarity = Rarity.values()[new Random().nextInt(Rarity.values().length)];
				Item NotPrize = Utility.getRandomItem(items.get(notPrizeRarity));
				final int NOT_PRIZE_ID = NotPrize.getId();
				sendItem(i, 55, mysteryPrize, NOT_PRIZE_ID, NotPrize.getAmount());
			}
		} else {
			for (int i = spinNum * 50 + 16; i < spinNum * 50 + 66; i++) {
				Rarity notPrizeRarity = Rarity.values()[new Random().nextInt(Rarity.values().length)];
				Item NotPrize = Utility.getRandomItem(items.get(notPrizeRarity));
				final int NOT_PRIZE_ID = NotPrize.getId();
				sendItem(i, (spinNum + 1) * 50 + 5, mysteryPrize, NOT_PRIZE_ID, mysteryAmount);
			}
		}
		spinNum++;
	}

	public void reward() {
		if (mysteryPrize == -1) {
			return;
		}

		// player.boxCurrentlyUsing = -1;

		player.getInventory().addOrCreateGroundItem(mysteryPrize, mysteryAmount, true);

		// Reward text colour
		String tier = rewardRarity.getColor();

		// Reward message
		String name = Item.getDefinition(mysteryPrize).getName();
		if (name.substring(name.length() - 1).equals("s")) {
			player.sendMessage("Congratulations, you have won " + tier + name + "@bla@!");
		} else {
			player.sendMessage("Congratulations, you have won a " + tier + name + "@bla@!");
		}

		// Can now spin again
		canMysteryBox = true;
	}

	public void sendItem(int i, int prizeSlot, int PRIZE_ID, int NOT_PRIZE_ID, int amount) {
		if (i == prizeSlot) {
			player.send(new SendMysteryBox(PRIZE_ID, mysteryAmount, ITEM_FRAME, i));
		} else {
			player.send(new SendMysteryBox(NOT_PRIZE_ID, amount, ITEM_FRAME, i));
		}
	}

	public void openInterface() {
		player.boxCurrentlyUsing = MYSTERY_BOX;

		// Open
		player.send(new SendString("Donor Chest", 47002));
		player.send(new SendInterface(INTERFACE_ID));

		// Reset interface
		player.sendMessage(":resetBox");
		for (int i = 0; i < 66; i++) {
			player.send(new SendMysteryBox(-1, 1, ITEM_FRAME, i));
		}
		spinNum = 0;
	}

}