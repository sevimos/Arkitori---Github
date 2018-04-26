package com.mayhem.rs2.content.shopping.impl;

import com.mayhem.rs2.content.shopping.Shop;
import com.mayhem.rs2.entity.item.Item;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

/**
 * Shop for Blood MOney currency
 * 
 * @author Divine
 */
public class BloodMoneyShop extends Shop {
	
	/**
	 * Item id of Blood money
	 */
	public static final int BLOODMONEY = 13307;
	
	/**
	 * Id of blood money store
	 */
	public static final int SHOP_ID = 102;

	/**
	 * Prices of items in store
	 * @param id
	 * @return
	 */
	public static final int getPrice(int id) {
		switch (id) {
		case 5018:
			return 700000;
		case 19544:
		case 19547:
		case 19550:
		case 19553:
			return 750000;
		case 5012:
			return 200000;
		case 11235:
			return 130000;
		case 13272:
			return 600000;
		case 13235:
			return 550000;
		case 13237:
			return 600000;
		case 13239:
			return 620000;
		case 13271:
			return 800000;
		case 5016:
			return 650000;
		case 20002:
			return 75000;
		case 20143:
			return 175000;
		case 20068:
		case 20071:
		case 20074:
		case 20077:
			return 750000;
		case 12020:
			return 200000;
		case 6570:
			return 280000;
		case 12769:
		case 12771:
			return 250000;
		case 20784:
			return 800000;
		case 12783:
			return 1500000;
		case 20062:
			return 230000;
		case 3791:
			return 220000;
		case 3799:
			return 220000;
		case 20035:
		case 20038:
		case 20044:
		case 20047:
		return 350_000;
		case 19918:
			return 350000;
		case 20604:
			return 350_000;
		case 20601:
			return 400_000;
		case 21018:
		return 750000;
		case 21021:
			return 1250000;
		case 21024:
			return 1000000;
		case 21006:
			return 2000000;
			
			
		
		}

		return 2147483647;
	}

	/**
	 * Items in store
	 */
	public BloodMoneyShop() {
		super(SHOP_ID, new Item[] { 
				new Item(6570),
				new Item(19544),
				new Item(19547),
				new Item(19550),
				new Item(19553),
				new Item(13271),
				new Item(12783),
				new Item(20002),
				new Item(20143),
				new Item(20068),
				new Item(20071),
				new Item(20074),
				new Item(20077),
				new Item(20062),
				new Item(12771),
				new Item(12769),
				new Item(20035),
				new Item(20038),
				new Item(20044),
				new Item(20047),
				new Item(21018),
				new Item(21021),
				new Item(21024),
				new Item(21006),
			}, false, "Blood Money Shop");
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

		if (player.getInventory().getItemAmount(13307) < amount * getPrice(id)) {
			player.getClient().queueOutgoingPacket(new SendMessage("You do not have enough Blood money to buy that."));
			return;
		}

		player.getInventory().remove(13307, amount * getPrice(id));

		player.getInventory().add(buying);
		update();
	}

	@Override
	public int getBuyPrice(int id) {
		return 0;
	}

	@Override
	public String getCurrencyName() {
		return "Blood Money";
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

