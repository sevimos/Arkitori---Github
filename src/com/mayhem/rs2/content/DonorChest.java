package com.mayhem.rs2.content;

import java.util.Arrays;

import com.mayhem.core.util.Utility;
import com.mayhem.core.util.chance.Chance;
import com.mayhem.core.util.chance.WeightedChance;
import com.mayhem.rs2.entity.World;
import com.mayhem.rs2.entity.item.Item;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

/**
 * Handles thieving caskets
 * @author Trebble
 *
 */
public class DonorChest {
	/**
	 * casket Identification
	 */
	private final static Item CHEST = new Item(8167);
	
	
	/**
	 * All possible loots from chests
	 */
	public static Chance<Item> DONORCHEST = new Chance<Item>(Arrays.asList(
			new WeightedChance<Item>(WeightedChance.RARE, new Item(11838, 1)),
			new WeightedChance<Item>(WeightedChance.RARE, new Item(19478, 1)),
			new WeightedChance<Item>(WeightedChance.RARE, new Item(11283, 1)),
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(12934, 5000)),//zulrah scales
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(13307, 10000)),//blood money
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(6686, 100)),//sara brew
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(12696, 100)),//super combat
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(3025, 100)),//super restore
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(2445, 100)),//ranging pot
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(386, 400)),//sharks
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(565, 2500)),//blood rune
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(9242, 500)),//ruby bolts(e)
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(12765, 1)),
			new WeightedChance<Item>(WeightedChance.RARE, new Item (11235, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(3483, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(12004, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(3481, 1)), 
			new WeightedChance<Item>(WeightedChance.RARE, new Item(12596, 1)),
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(2581, 1)), //robin hood hat
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(12829, 1)), //spirit shield
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(9244, 1000)), //dragon bolts(e)
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(11230, 1500)), //dragon darts
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(220, 1000)), //grimy torstol
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(218, 1000)), //grimy dwarf weed
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(3050, 1000)), //grimy toadflax
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(2577, 1)),
			new WeightedChance<Item>(WeightedChance.RARE, new Item(3486, 1)),
			new WeightedChance<Item>(WeightedChance.RARE, new Item(3488, 1)),
			new WeightedChance<Item>(WeightedChance.RARE, new Item(3485, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(12389, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(11806, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(11808, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(11804, 1)),
			new WeightedChance<Item>(WeightedChance.RARE, new Item(12391, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(10330, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(10332, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(10334, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(10336, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(10338, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(10340, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(10342, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(10344, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(10346, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(10348, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(10350, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(10352, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(12422, 1)), 
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(12424, 1)), 
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(12426, 1)), 
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(20011, 1)), 
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(20014, 1)), 
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(21012, 1)), 
			new WeightedChance<Item>(WeightedChance.RARE, new Item(4566, 1)), 
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(12351, 1)),
			new WeightedChance<Item>(WeightedChance.RARE, new Item(12357, 1)), 
			new WeightedChance<Item>(WeightedChance.RARE, new Item(11335, 1)), 
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(11862, 1)), 
			new WeightedChance<Item>(WeightedChance.RARE, new Item(13307, 100000)),//blood money
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(995, Utility.randomNumber(25000000))),//coins
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(4083, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(9470, 1)),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(5607, 1)),
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(12926, 1)),
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(12904, 1)),
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(13199, 1)),
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(13197, 1)),
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(11802, 1)),
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(11832, 1)),
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(11834, 1))
			
			
			
			
	));
	
	
	/**
	 * Handles opening the chests
	 * @param player
	 */
	public static void openDonorChest(Player player) {
		Item reward = DONORCHEST.nextObject().get(); 
		Item secondReward = new Item(995, Utility.randomNumber(2000000));
		player.send(new SendMessage("Error Message"));
		String name = reward.getDefinition().getName();
		String formatted_name = Utility.getAOrAn(name) + " " + name;
		player.getInventory().remove(CHEST);
		player.getInventory().addOrCreateGroundItem(reward);
		player.getInventory().addOrCreateGroundItem(secondReward);
		player.send(new SendMessage("You get a " + formatted_name + " ."));
	}
	
	

}
