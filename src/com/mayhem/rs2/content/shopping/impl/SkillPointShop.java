package com.mayhem.rs2.content.shopping.impl;

import com.mayhem.rs2.content.interfaces.InterfaceHandler;
import com.mayhem.rs2.content.interfaces.impl.QuestTab;
import com.mayhem.rs2.content.shopping.Shop;
import com.mayhem.rs2.entity.item.Item;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

/**
 * Skill point store
 * 
 * @author Divine
 */
public class SkillPointShop extends Shop {

	/**
	 * Id of Skill point shop
	 */
	public static final int SHOP_ID = 103;

	/**
	 * Price of items in Skill point store
	 * 
	 * @param id
	 * @return
	 */
	public static final int getPrice(int id) {
	switch (id) {
	
		case 11738:
			return 10000;
		case 12789:
			return 12500;
		case 989:
			return 5000;
		case 12800:
			return 100000;
		case 13233:
			return 40000;
		case 20014:
		case 20011:
			return 125000;
		case 7956:
		case 19836:
		case 3849:
			return 25000;
		case 6199:
			return 30000;

	}
	return 2147483647;
}

/**
 * All items in skill point shop
 */
public SkillPointShop() {
	super(SHOP_ID, new Item[] { 
			new Item(11738, 10),
			new Item(12789, 10),
			new Item(989, 10),
			new Item(7956, 10),
			new Item(19836, 10),
			new Item(3849, 10),
			new Item(12800),
			new Item(13233),
			}, 
			false, "Skill point Shop");
}

	@Override
	public void buy(Player player, int slot, int id, int amount) {
		if (!hasItem(slot, id))
			return;
		if (get(slot).getAmount() == 0)
			return;
		if (amount > get(slot).getAmount()) {
			amount = get(slot).getAmount();
		}

		Item buying = new Item(id, amount);

		if (!player.getInventory().hasSpaceFor(buying)) {
			if (!buying.getDefinition().isStackable()) {
				int slots = player.getInventory().getFreeSlots();
				if (slots > 0) {
					buying.setAmount(slots);
					amount = slots;
				} else {
					player.getClient().queueOutgoingPacket(new SendMessage("You do not have enough inventory space to buy this item."));
				}
			} else {
				player.getClient().queueOutgoingPacket(new SendMessage("You do not have enough inventory space to buy this item."));
				return;
			}
		}

		if (player.getskillPoints() < amount * getPrice(id)) {
			player.getClient().queueOutgoingPacket(new SendMessage("You do not have enough Skill points to buy that."));
			return;
		}

		player.setskillPoints(player.getskillPoints() - amount * getPrice(id));

		//InterfaceHandler.writeText(new QuestTab(player));

		player.getInventory().add(buying);
		update();
	}

	@Override
	public int getBuyPrice(int id) {
		return 0;
	}

	@Override
	public String getCurrencyName() {
		return "Skill Points";
	}

	@Override
	public int getSellPrice(int id) {
		return getPrice(id);
	}

	@Override
	public boolean sell(Player player, int id, int amount) {
		player.getClient().queueOutgoingPacket(new SendMessage("You cannot sell items to this shop."));
		return false;
	}
}
