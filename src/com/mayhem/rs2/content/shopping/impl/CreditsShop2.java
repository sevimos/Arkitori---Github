package com.mayhem.rs2.content.shopping.impl;

import com.mayhem.rs2.content.interfaces.InterfaceHandler;
import com.mayhem.rs2.content.interfaces.impl.QuestTab;
import com.mayhem.rs2.content.shopping.Shop;
import com.mayhem.rs2.entity.item.Item;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

/**
 * Shop for pest credits
 * 
 * @author Daniel
 */
public class CreditsShop2 extends Shop {

	/**
	 * Id of shop
	 */
	public static final int SHOP_ID = 90;

	/**
	 * Prices of item in shop
	 * 
	 * @param id
	 * @return
	 */
	public static final int getPrice(int id) {
	switch (id) {
	case 13173:
		return 12500;
	case 1038:
	case 1040:
	case 1042:
	case 1044:
	case 1046:
	case 1048:
		return 2500;
		
	case 11863:
	case 11862:
	case 4084:
		return 3000;
	case 11847:
		return 1000;
		
	case 12399:
		return 2000;
		
	case 13175:
		return 4000;
	case 1050:
		return 1250;
	case 1037:
		return 1000;
	case 13182:
		return 1000;
		
	case 12004:
		return 750;
		
	case 6585:
		return 300;
		
		
		
	case 3140:
		return 150;
		
		
		
	case 4151:
	case 11335:
		return 500;
		
	case 1052:
	case 12954:
		return 500;
		
	case 11840:
		return 100;
		
	case 6570:
		return 500;
		
	case 7462:
		return 150;
		
	case 11283:
	case 11235:
		return 700;
		
	case 6731:
	case 6733:
	case 6735:
	case 6737:
		return 400;
		
	case 11907:
		return 850;
	case 1053:
	case 1055:
	case 1057:
		return 1500;

		
	}

	return 15000;
}

/**
 * Items in shop
 */
public CreditsShop2() {
	super(SHOP_ID, new Item[] { 
			new Item(13173),
			new Item(1038),
			new Item(1040),
			new Item(1042),
			new Item(1044),
			new Item(1046),
			new Item(1048),
			new Item(11863),
			new Item(11862),
			new Item(13175),
			new Item(12399),
			new Item(1053),
			new Item(1055),
			new Item(1057),
			new Item(11847),
			new Item(1050),
			new Item(1037),
			new Item(13182),
			new Item(4084),
			new Item(1052),										
			new Item(11335),
			new Item(3140),
			new Item(11840),
			new Item(12954),
			new Item(4151),
			new Item(6585),
			new Item(7462),
			new Item(11283),
			new Item(6570),						
			new Item(6731),
			new Item(6733),
			new Item(6735),
			new Item(6737),
			new Item(12004),
			new Item(11235),
			new Item(11907),
	}, false, "Arkitori Credits Store");
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

		if (player.getCredits() < amount * getPrice(id)) {
			player.getClient().queueOutgoingPacket(new SendMessage("You do not have enough Arkitori Credits to buy that."));
			return;
		}

		player.setCredits(player.getCredits() - (amount * getPrice(id)));

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
		return "Credits";
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
