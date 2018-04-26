package com.mayhem.rs2.content.shopping.impl;

import com.mayhem.rs2.content.shopping.Shop;
import com.mayhem.rs2.entity.item.Item;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

/**
 * Shop for graceful currency
 * 
 * @author Daniel
 */
public class GracefulShop extends Shop {
	
	/**
	 * Item id of graceful
	 */
	public static final int GRACE_MARKS = 11849;
	
	/**
	 * Id of graceful store
	 */
	public static final int SHOP_ID = 3;

	/**
	 * Prices of items in store
	 * @param id
	 * @return
	 */
	public static final int getPrice(int id) {
		switch (id) {
		case 11850:
		case 13579:
		case 13591:
		case 13603:
		case 13615:
		case 13627:
			return 35;
		case 11854:
		case 13583:
		case 13595:
		case 13607:
		case 13619:
		case 13631:
			return 55;
		case 11856:
		case 13585:
		case 13597:
		case 13609:
		case 13621:
		case 13633:
			return 60;
		case 11858:
		case 13587:
		case 13599:
		case 13611:
		case 13623:
		case 13635:
			return 30;
		case 11860:
		case 11852:
		case 13581:
		case 13593:
		case 13605:
		case 13617:
		case 13629:
		case 13589:
		case 13601:
		case 13613:
		case 13625:
		case 13637:
			return 40;	
		}

		return 2147483647;
	}

	/**
	 * Items in store
	 */
	public GracefulShop() {
		super(SHOP_ID, new Item[] { 
				new Item(11850, 1),
				new Item(11852, 1),
				new Item(11854, 1),
				new Item(11856, 1),
				new Item(11858, 1),
				new Item(11860, 1),
				new Item(13579, 1),
				new Item(13581, 1),
				new Item(13583, 1),
				new Item(13585, 1),
				new Item(13587, 1),
				new Item(13589, 1),
				new Item(13591, 1),
				new Item(13593, 1),
				new Item(13595, 1),
				new Item(13597, 1),
				new Item(13599, 1),
				new Item(13601, 1),
				new Item(13603, 1),
				new Item(13605, 1),
				new Item(13607, 1),
				new Item(13609, 1),
				new Item(13611, 1),
				new Item(13613, 1),
				new Item(13615, 1),
				new Item(13617, 1),
				new Item(13619, 1),
				new Item(13621, 1),
				new Item(13623, 1),
				new Item(13625, 1),
				new Item(13627, 1),
				new Item(13629, 1),
				new Item(13631, 1),
				new Item(13633, 1),
				new Item(13635, 1),
				new Item(13637, 1),
				
			}, false, "Graceful Store");
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

		if (player.getInventory().getItemAmount(GRACE_MARKS) < amount * getPrice(id)) {
			player.getClient().queueOutgoingPacket(new SendMessage("You do not have enough grace marks to buy that."));
			return;
		}

		player.getInventory().remove(GRACE_MARKS, amount * getPrice(id));

		player.getInventory().add(buying);
		update();
	}

	@Override
	public int getBuyPrice(int id) {
		return 0;
	}

	@Override
	public String getCurrencyName() {
		return "Graceful";
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
