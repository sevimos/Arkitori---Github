package com.mayhem.rs2.content;

import java.util.Arrays;

import com.mayhem.core.util.Utility;
import com.mayhem.core.util.chance.Chance;
import com.mayhem.core.util.chance.WeightedChance;
import com.mayhem.rs2.entity.item.Item;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

/**
 * Handles beta chest
 * @author Sevimos
 *
 */
public class BetaChest {
	/**
	 * casket Identification
	 */
	private final static Item CHEST = new Item(6759);
	
	
	/**
	 * All possible loots from chests
	 */
	public static Chance<Item> BETACHEST = new Chance<Item>(Arrays.asList(

			new WeightedChance<Item>(WeightedChance.COMMON, new Item(1149, 1)),// dragon med
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(535, 75 + Utility.random(250))),//baby dragon bones
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(4585, 1)),//dragon skirt
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(4587, 1)),//dragon scimitar
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(386, 500 + Utility.random(250))),//dragon scimitar
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(384, 500 + Utility.random(250))),//dragon scimitar
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(8850, 1)),//rune defender
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(454, 150 + Utility.random(750))),//coal
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(1514, 150 + Utility.random(500))),//magic logs
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(2437, 25 + Utility.random(100))),//SUPER ATTACK 4
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(2441, 25 + Utility.random(100))),//SUPER STR 4
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(2443, 25 + Utility.random(100))),//SUPER DEF 4
			new WeightedChance<Item>(WeightedChance.COMMON, new Item(1618, 250 + Utility.random(250))),//UNCUT DIAMONDS
			
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(450, 100 + Utility.random(400))),//ADDY ORE
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(537, 100 + Utility.random(125))),//dragon bones
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(1187, 1)),//dragon sq
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(4087, 1)),//dragon platelegs
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(10551, 1)),//fighter torso
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(7937, 100 + Utility.random(500))),//pure essence
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(9740, 25 + Utility.random(25))),//SUPER COMBATS 4
			new WeightedChance<Item>(WeightedChance.UNCOMMON, new Item(1632, 1 )),//UNCUT DRAGONSTONES (FOR PLAYERS TO MAKE RINGS OF WEALTHS)
			
			new WeightedChance<Item>(WeightedChance.RARE, new Item(452, 50 + Utility.random(225))),//RUNE ORE
			new WeightedChance<Item>(WeightedChance.RARE, new Item(3140, 1)),//dragon chain
			new WeightedChance<Item>(WeightedChance.RARE, new Item(6571, 1)),//UNCUT ONYX
			new WeightedChance<Item>(WeightedChance.RARE, new Item(11818, 1)),//godsword shard 1
			new WeightedChance<Item>(WeightedChance.RARE, new Item(11820, 1)),//godsword shard 2
			new WeightedChance<Item>(WeightedChance.RARE, new Item(11822, 1)),//godsword shard 3
			new WeightedChance<Item>(WeightedChance.RARE, new Item(11812, 1)),//godsword hilt bandos
			new WeightedChance<Item>(WeightedChance.RARE, new Item(11816, 1)),//godsword hilt zammy

			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(9244, 50 + Utility.random(150))),//dragonSTONE BOLTS (E)
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(12954, 1)),//dragon defender
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(537, 100 + Utility.random(250))),//dragon bones
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(6737, 1)),//berserker ring
			new WeightedChance<Item>(WeightedChance.VERY_RARE, new Item(11814, 1)),//godsword hilt sara

			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(6739, 1)),//dragon axe
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(11920, 1)),//dragon pick
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(12873, 1)),//guthans armour set
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(12875, 1)),//verac armour set
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(12877, 1)),//dharok armour set
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(12879, 1)),//torag armour set
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(12881, 1)),//ahrim armour set
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(12883, 1)),//karil armour set
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(4151, 1)),//abyssal whip
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(11810, 1)),//armadyl hilt
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(6585, 1)),//AMULET OF FURY
			new WeightedChance<Item>(WeightedChance.LEGENDARY, new Item(11773, 1))//berserker ring (i)
			
			
			
	));
	
	
	/**
	 * Handles opening the chests
	 * @param player
	 */
	public static void openBetaChest(Player player) {
		Item reward = BETACHEST.nextObject().get(); 
		Item secondReward = BETACHEST.nextObject().get();
		Item thirdReward = BETACHEST.nextObject().get();
		Item fourthReward = BETACHEST.nextObject().get();
		Item fifthReward = new Item(995, Utility.randomNumber(250000));
		player.send(new SendMessage("Error Message"));
		String name = reward.getDefinition().getName();
		String formatted_name = Utility.getAOrAn(name) + " " + name;
		player.getInventory().remove(CHEST);
		player.getInventory().addOrCreateGroundItem(reward);
		player.getInventory().addOrCreateGroundItem(secondReward);
		player.getInventory().addOrCreateGroundItem(thirdReward);
		player.getInventory().addOrCreateGroundItem(fourthReward);
		player.getInventory().addOrCreateGroundItem(fifthReward);
		player.send(new SendMessage("You get a " + formatted_name + " ."));
	}
}
