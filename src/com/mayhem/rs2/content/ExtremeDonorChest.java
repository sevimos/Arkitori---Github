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
public class ExtremeDonorChest {
	/**
	 * casket Identification
	 */
	private final static Item CHEST = new Item(8152);
	
	
	/**
	 * All possible loots from chests
	 */
	public static Chance<Item> EXTREMEDONORCHEST = new Chance<Item>(Arrays.asList(

			new WeightedChance<Item>(WeightedChance.COMMON, new Item(452, 1500)), //runite ore
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(9244, 2500)), //dragon bolts (e)
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(12934, 25000)), //zulrah scales
			new WeightedChance<Item>(WeightedChance.RARE, new Item(12877, 1)), //dh set
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(12873, 1)), //guthans set
			new WeightedChance<Item>(WeightedChance.RARE, new Item(19484, 2500)),//dragon jav
			new WeightedChance<Item>(WeightedChance.RARE, new Item(12774, 1)),//frozen whip
			new WeightedChance<Item>(WeightedChance.RARE, new Item(12773, 1)),//lava whip
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(11230, 1500)),//dragon dart
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(12006, 1)),//abyssal tentacle
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(1514, 2500)),//magic logs
			new WeightedChance<Item>(WeightedChance.RARE, new Item(12881, 1)),//Ahrims Set
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(12883, 1)),//Karils set
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(11806, 1)),//sgs
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(11804, 1)),//bgd
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(11802, 1)),//ags
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(11905, 1)),//trident of the seas
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(19481, 1)),//heavy ballista
			new WeightedChance<Item>(WeightedChance.RARE, new Item(19496, 1)),//zenyte uncut
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(13227, 1)),//eternal crystal
			new WeightedChance<Item>(WeightedChance.RARE, new Item(13229, 1)),//pegasian crystal
			new WeightedChance<Item>(WeightedChance.RARE, new Item(11832, 1)),//Bandos Chest
			new WeightedChance<Item>(WeightedChance.RARE, new Item(11834, 1)),//Bandos Tassets
			new WeightedChance<Item>(WeightedChance.RARE, new Item(13231, 1)),//primordial crystal
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(12899, 1)),//trident of the swamp
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(12027, 1)),//cyan partyhat
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(12031, 1)),//dark green partyhat
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(12033, 1)),//magenta partyhat
			new WeightedChance<Item>(WeightedChance.RARE, new Item(13271, 1)),//abyssal dagger
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(9067, 2)),//dream log		
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(19677, 2)),//ancient shard
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(19685, 1)),//dark totem
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(6051, 1)),//magic root
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(12603, 1)),//tyrannical ring
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(20784, 1)),//dragon claws
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(12817, 1)),//dragon claws
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(12821, 1)),//dragon claws
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(12825, 1)),//dragon claws
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(2402, 1))//silverlight
			
			
			
	));
	
	
	/**
	 * Handles opening the chests
	 * @param player
	 */
	public static void openExtremeDonorChest(Player player) {
		Item reward = EXTREMEDONORCHEST.nextObject().get(); 
		Item secondReward = new Item(995, Utility.randomNumber(7500000));
		player.send(new SendMessage("Error Message"));
		String name = reward.getDefinition().getName();
		String formatted_name = Utility.getAOrAn(name) + " " + name;
		player.getInventory().remove(CHEST);
		player.getInventory().addOrCreateGroundItem(reward);
		player.getInventory().addOrCreateGroundItem(secondReward);
		player.send(new SendMessage("You get a " + formatted_name + " ."));
	}
	
	

}
