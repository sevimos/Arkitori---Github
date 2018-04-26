package com.mayhem.rs2.content.shopping.impl;

import com.mayhem.rs2.content.interfaces.InterfaceHandler;
import com.mayhem.rs2.content.interfaces.impl.QuestTab;
import com.mayhem.rs2.content.shopping.Shop;
import com.mayhem.rs2.entity.item.Item;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

/**
 * Boss store
 * 
 * @author Dez
 */
public class BossShop extends Shop {

	/**
	 * Id of Boss shop
	 */
	public static final int SHOP_ID = 349;

	/**
	 * Price of items in Boss Skill store
	 * 
	 * @param id
	 * @return
	 */
	public static final int getPrice(int id) {
	switch (id) {
	
	case 4212:
		return 50;
	case 4224:
		return 100;
	case 13091:
		return 150;
	case 13073:
	case 13072:
		return 125;
	case 11664:
	case 11663:
	case 11665:
		return 50;
	case 8842:
		return 15;
	case 10547:
		return 30;
	case 10548:
		return 30;
	case 10549:
		return 30;
	case 10550:
		return 30;
	case 10551:
		return 50;
	case 10555:
		return 40;
	case 10553:
		return 20;
	case 10552:
		return 20;
	case 6585:
		return 40;
	case 12954:
		return 30;
	case 11824:
		return 150;
	case 11832:
		return 200;
	case 11834:
		return 350;
	case 11836:
		return 100;
	case 11826:
		return 150;
	case 11828:
		return 200;
	case 11830:
		return 200;
	case 11785:
		return 350;
	case 11802:
		return 500;
	case 11804:
		return 200;
	case 11806:
		return 350;
	case 11808:
		return 150;
	case 12006:
		return 450;
	case 2902:
	case 2904:
	case 2906:
		return 250;
	case 13576:
		return 750;
	case 13271:
		return 500;
	case 13263:
		return 425;
	case 13239:
	case 13237:
	case 13235:
		return 250;
	case 19553:
		return 350;
	case 19547:
		return 200;
	case 19550:
		return 225;
	case 19544:
		return 175;
	case 12929:
		return 180;
	case 13197:
	case 13199:
		return 230;
		

	}
	return 2147483647;
}

/**
 * All items in hunter
 */
public BossShop() {
	super(SHOP_ID, new Item[] {
			new Item(13576),
			new Item(13271),
			new Item(13263),
			new Item(12006),
			new Item(19553),
			new Item(19547),
			new Item(19544),
			new Item(19550),
			new Item(12954),
			new Item(11824),
			new Item(8842),
			new Item(11663),
			new Item(11664),
			new Item(11665),
			new Item(13072),
			new Item(13073),
			new Item(10547),
			new Item(10548),
			new Item(10549),
			new Item(10550),
			new Item(10551),
			new Item(10555),
			new Item(10552),
			
			}, 
			false, "Boss Point Shop");
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

		if (player.getbossPoints() < amount * getPrice(id)) {
			player.getClient().queueOutgoingPacket(new SendMessage("You do not have enough Boss points to buy that."));
			return;
		}

		player.setbossPoints(player.getbossPoints() - amount * getPrice(id));

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
		return "Boss Points";
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
