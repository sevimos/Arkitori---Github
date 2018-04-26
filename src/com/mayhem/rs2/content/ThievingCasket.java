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
public class ThievingCasket {
	/**
	 * casket Identification
	 */
	private final static Item CASKET = new Item(3849);
	
	
	/**
	 * All possible loots from caskets
	 */
	public static Chance<Item> THIEVINGCASKET = new Chance<Item>(Arrays.asList(
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(5553, 1)),//ROGUE OUTFIT
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(5554, 1)), 
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(5555, 1)),
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(5556, 1)), 
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(5557, 1)),
			new WeightedChance<Item>(WeightedChance.RARE, new Item(20663, 1)), 
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(995, Utility.randomNumber(250000))),
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(995, Utility.randomNumber(2500000)))
			
			
	));
	
	
	/**
	 * Handles opening the casket
	 * @param player
	 */
	public static void openThievingCasket(Player player) {
		Item reward = THIEVINGCASKET.nextObject().get(); 
		String name = reward.getDefinition().getName();
		String formatted_name = Utility.getAOrAn(name) + " " + name;
		player.getInventory().remove(CASKET);
		player.getInventory().addOrCreateGroundItem(reward);
		player.send(new SendMessage("You get a " + formatted_name + " ."));
	}
	
	

}
