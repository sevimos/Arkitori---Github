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
public class CreditsShop3 extends Shop {

	/**
	 * Id of shop
	 */
	public static final int SHOP_ID = 87;

	/**
	 * Prices of item in shop
	 * 
	 * @param id
	 * @return
	 */
	public static final int getPrice(int id) {
	switch (id) {
	case 7582:
	case 11047:
	case 10998:
	case 7993:
	case 9953:
	case 9970:
	case 12840:
		return 100;
	case 10731:
	case 8887:
	case 8132:
	case 7914:
	case 8888:
		return 150;
	case 5077:
		return 50;
	case 5076:
		return 50;
	case 12924:
		return 2000;
		
		
	
	case 11832:
		return 500;
	case 11804:
		return 250;
	case 11834:
		return 1000;
		
	case 11802:
		return 1200;
		
	case 11808:
		return 350;
		
	case 11806:
		return 450;
		
	
	case 11824:
		return 1000;
		
	case 2572:
		return 150;
		
	
	case 6916:
	case 6918:
	case 6924:
		return 100;
	case 12526:
		return 150;
	case 12528:
	case 12530:
		return 150;
	case 6920:
	case 6922:
		return 100;
	case 19478:
		return 1000;
	case 19481:
		return 1500;
	case 13263:
		return 1600;
	case 13271:
		return 1000;
	case 13576:
		return 1000;
	case 5016:
		return 1500;
	case 12817:
		return 1250;
	case 12821:
		return 1200;
	case 12825:
		return 1000;
	case 5012:
		return 800;
	case 5000:
	case 5001:
		return 650;
	case 5002:
		return 700;
	case 5003:
		return 800;
	case 5008:
		return 500;
	case 5009:
		return 600;
	case 5010:
		return 500;
	case 5011:
		return 550;
	case 5004:
		return 500;
	case 20784:
		return 1500;
		
	}

	return 42000;
}

/**
 * Items in shop
 */
public CreditsShop3() {
	super(SHOP_ID, new Item[] {
		new Item(5016),
		new Item(12817),
		new Item(12821),
		new Item(12825),
		new Item(5012),
		new Item(19478),
		new Item(19481),
		new Item(13263),
		new Item(13271),
		new Item(13576),
		new Item(2572),
		new Item(12924),
		new Item(11832),
		new Item(11834),
		new Item(11804),
		new Item(11802),
		new Item(11806),
		new Item(11808),
		new Item(11824),
		new Item(12526),
		new Item(12528),
		new Item(12530),
		
		new Item(6918),
		new Item(6916),
		new Item(6924),
		new Item(6920),
		new Item(6922),
		new Item(5000),
		new Item(5001),
		new Item(5002),
		new Item(5003),
		new Item(5008),
		new Item(5009),
		new Item(5010),
		new Item(5011),
		new Item(20784),
			
	}, false, "Arkitori Credits Store 3");
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
