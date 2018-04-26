package com.mayhem.rs2.content.shopping.impl;

import com.mayhem.rs2.content.interfaces.InterfaceHandler;
import com.mayhem.rs2.content.interfaces.impl.QuestTab;
import com.mayhem.rs2.content.shopping.Shop;
import com.mayhem.rs2.entity.item.Item;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

/**
 * Voting store
 * 
 * @author Daniel
 */
public class VotingShop extends Shop {
	/**
	 * Item id of Vote Ticket
	 */
	public static final int VOTETICKET = 5020;

	/**
	 * Id of Bounty shop
	 */
	public static final int SHOP_ID = 92;

	/**
	 * Price of items in Bounty store
	 * 
	 * @param id
	 * @return
	 */
	public static final int getPrice(int id) {
	switch (id) {
	
	case 2528:
	case 12789:
		return 15;
	case 989:
		return 4;
	case 299:
	return 1;
	case 20851:
		return 250;
	case 2572:
		return 50;
	case 12964:
	case 12966:
	case 12968:
	case 12970:
		return 20;
	case 12976:
	case 12978:
	case 12980:
	case 12982:
		return 50;
	case 12992:
	case 12994:
	case 12996:
	case 12998:
		return 70;
	case 13004:
	case 13006:
	case 13008:
	case 13010:
		return 90;
	case 13016:
	case 13018:
	case 13020:
	case 13022:
		return 110;
	case 13028:
	case 13030:
	case 13032:
	case 13034:
		return 120;
	case 13036:
	case 13038:
		return 350;
	case 1837:
	case 5607:
	case 2643:	
		return 5;
	case 9470:
	case 9472:
		return 5;
	case 13171:
	case 13167:
	case 13169:
		return 50;
	case 7478:
		return 20;
	case 11840:
		return 60;
	case 12528:
	case 20834:
		return 50;
	case 20836:
		return 50;
	case 12538:
		return 50;
	case 12530:
		return 50;
	case 7462:
		return 15;
	case 12877:
		return 35;
	}
	return 2147483647;
}

/**
 * All items in Bounty store
 */
public VotingShop() {
	super(SHOP_ID, new Item[] {
			new Item(299, 100),
			new Item(989, 100),
			new Item(12789),
			new Item(2572),
			new Item(7462),
			new Item(1837), 
			new Item(5607), 
			new Item(9470),
			new Item(2643),
			new Item(12877),
			new Item(13171),
			new Item(13167),
			new Item(13169),
			new Item(20834),
			new Item(20836),
			new Item(12538),
			new Item(12528),
			new Item(12530),
			
	}, false, "Vote Point Store");
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

	if (player.getInventory().getItemAmount(5020) < amount * getPrice(id)) {
		player.getClient().queueOutgoingPacket(new SendMessage("You do not have enough Vote tickets to buy that."));
		return;
	}

	player.getInventory().remove(5020, amount * getPrice(id));

	player.getInventory().add(buying);
	update();
}

@Override
public int getBuyPrice(int id) {
	return 0;
}

	@Override
	public String getCurrencyName() {
		return "Vote tickets";
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
