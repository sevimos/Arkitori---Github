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
public class ExtremeMysterySpin {

	/**
	 * The item id of the mystery box required to trigger the event
	 */
	private static final int MYSTERY_BOX = 8152;

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

				new Item(452, 1500), // runite ore
				new Item(9244, 1250), // dragon bolts (e)
				new Item(12934, 25000), // zulrah scales
				new Item(12877, 1), // dh set
				new Item(12873, 1), // guthans set
				new Item(19484, 2500))// dragon jav


		);

		items.put(Rarity.UNCOMMON, Arrays.asList(
				new Item(12774, 1), // frozen whip
				new Item(12773, 1), // lava whip
				new Item(11230, 1500), // dragon dart
				new Item(12006, 1), // abyssal tentacle
				new Item(1514, 2500), // magic logs
				new Item(12881, 1), // Ahrims Set
				new Item(12883, 1), // Karils set
				new Item(11806, 1), // sgs
				new Item(11804, 1), // bgd
				new Item(11802, 1), // ags
				new Item(11905, 1), // trident of the seas
				new Item(12603, 1), // tyrannical
				new Item(19481, 1), // heavy ballista
				new Item(19496, 1), // zenyte uncut
				new Item(13227, 1), // eternal crystal
				new Item(13229, 1), // pegasian crystal
				new Item(11832, 1), // Bandos Chest
				new Item(11834, 1), // Bandos Tassets
				new Item(13231, 1), // primordial crystal
				new Item(12899, 1)) // trident of the swamp
				);

		items.put(Rarity.RARE,
				Arrays.asList(new Item(9067, 2), // dream log
						new Item(19677, 2), // ancient shard
						new Item(19685, 1), // dark totem
						new Item(6051, 1), // magic root
						new Item(12603, 1), // tyrannical ring
						new Item(20784, 1), // dragon claws
						new Item(12027, 1), // cyan partyhat
						new Item(12031, 1), // dark green partyhat
						new Item(12033, 1), // magenta partyhat
						new Item(13271, 1),// abyssal dagger
						new Item(12817, 1),// Elysian
						new Item(12821, 1),// spectral
						new Item(12825, 1),// Arcane
						new Item(2402, 1)));// silver light));
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
	public ExtremeMysterySpin(Player player) {
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
		player.send(new SendString("@mag@Extreme Donor Chest", 47002));
		player.send(new SendInterface(INTERFACE_ID));

		// Reset interface
		player.sendMessage(":resetBox");
		for (int i = 0; i < 66; i++) {
			player.send(new SendMysteryBox(-1, 1, ITEM_FRAME, i));
		}
		spinNum = 0;
	}

}