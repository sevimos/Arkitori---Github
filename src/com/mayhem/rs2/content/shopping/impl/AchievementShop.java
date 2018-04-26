package com.mayhem.rs2.content.shopping.impl;

import com.mayhem.rs2.content.interfaces.InterfaceHandler;
import com.mayhem.rs2.content.interfaces.impl.QuestTab;
import com.mayhem.rs2.content.shopping.Shop;
import com.mayhem.rs2.entity.item.Item;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

/**
 * Shop for Achievements
 * 
 * @author Daniel
 */
public class AchievementShop extends Shop {

	/**
	 * Id of shop
	 */
	public static final int SHOP_ID = 89;

	/**
	 * Prices of item in shop
	 * 
	 * @param id
	 * @return
	 */
	public static final int getPrice(int id) {
	switch (id) {
	case 989:
		return 1;
	case 4079:
		return 5;
	case 11990:
		return 10;
	case 11898:
	case 11896:
	case 11897:
	case 11899:	
	case 11900:
		return 14;
	case 12361:
		return 15;
	case 13121:
		return 5;
	case 13122:
		return 10;
	case 13123:
		return 15;
	case 13124:
		return 20;
	case 7583:
		return 10;
	case 65:
		return 10394;
	case 13068:
		return 20;
	case 6570:
		return 50;
	case 12363:
		return 5;
	case 12365:
		return 7;
	case 12367:
		return 9;
	case 12369:
		return 11;
	case 12371:
		return 13;
	}

	return 10000;
}

	
	
	
/**
 * Items in shop
 */
public AchievementShop() {
	super(SHOP_ID, new Item[] { 
		new Item(989, 10),
		new Item(6570),
		new Item(11898),
		new Item(11896),
		new Item(11897),
		new Item(13068),
		new Item(13121),
		new Item(13122),
		new Item(13123),
		new Item(13124),
		new Item(11899),
		new Item(11900),
		new Item(12361),
		new Item(11990),
		new Item(12363),
		new Item(12365),
		new Item(12367),
		new Item(12369),
		new Item(12371),
		new Item(4079),
		
		
	}, false, "Achievement Store");
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

		if (player.getAchievementsPoints() < amount * getPrice(id)) {
			player.getClient().queueOutgoingPacket(new SendMessage("You do not have enough Achievements points to buy that."));
			return;
		}

		player.addAchievementPoints(player.getAchievementsPoints() - (amount * getPrice(id)));

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
		return "Achievements points";
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
