package com.mayhem.rs2.content.shopping.impl;

import com.mayhem.rs2.content.interfaces.InterfaceHandler;
import com.mayhem.rs2.content.interfaces.impl.QuestTab;
import com.mayhem.rs2.content.shopping.Shop;
import com.mayhem.rs2.entity.item.Item;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

/**
 * Pvm store
 * 
 * @author Dez
 */
public class PvmShop extends Shop {

	/**
	 * Id of Pvm shop
	 */
	public static final int SHOP_ID = 353;

	/**
	 * Price of items in Pvm store
	 * 
	 * @param id
	 * @return
	 */
	public static final int getPrice(int id) {
	switch (id) {
	case 12695:
		return 15;
	case 989:
		return 10;
	case 12792:
		return 100;
	case 2379:
		return 50;
	case 3486:
		return 150;
	case 4692:
		return 25;
	case 3481:
		return 200;
	case 9433:
		return 25;
	case 3483:
		return 200;
	case 3485:
		return 200;
	case 12391:
		return 100;
	case 3488:
		return 150;
	case 12389:
		return 200;
	case 11840:
		return 50;
	case 6585:
		return 200;
	case 11335:
		return 250;
	case 3140:
		return 85;
	case 4087:
		return 35;
	case 7462:
		return 75;
	case 7461:
		return 45;
	case 7460:
		return 40;
	case 7459:
		return 35;
	case 7458:
		return 30;
	case 10552:
		return 45;
	case 6528:
		return 20;
	case 6524:
		return 25;
	case 6525:
		return 20;
	case 6526:
		return 35;
	case 6527:
		return 45;
	case 4153:
		return 20;
	case 10551:
		return 150;
	case 12954:
		return 250;
	case 8850:
		return 150;
	case 2677:
		return 35;
	case 20161:
		return 100;
	case 20050:
		return 500;
	case 20211:
	case 20214:
	case 20217:
		return 600;
	case 12806:
	case 12807:
		return 275;
		

	}
	return 2147483647;
}

/**
 * All items in Pvm store
 */
public PvmShop() {
	super(SHOP_ID, new Item[] {
			new Item(12695),
			new Item(989),
			new Item(2677),
			new Item(2379),
			new Item(20050),
			new Item(20211),
			new Item(20217),
			new Item(20214),
			new Item(6585), 
			new Item(11840),
			new Item(3140), 
			new Item(4087), 
			new Item(7462),  
			new Item(7460), 
			new Item(7459), 
			new Item(10551),
			new Item(6528), 
			new Item(6524),   
			new Item(4153),  
			new Item(12806),
			new Item(12807),
			},
			false, "PVM Points Shop");
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

		if (player.getpvmPoints() < amount * getPrice(id)) {
			player.getClient().queueOutgoingPacket(new SendMessage("You do not have enough PVM points to buy that."));
			return;
		}

		player.setpvmPoints(player.getpvmPoints() - amount * getPrice(id));

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
		return "PVM Points";
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
