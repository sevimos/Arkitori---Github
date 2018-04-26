package com.mayhem.rs2.content;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mayhem.core.definitions.ItemDefinition;
import com.mayhem.rs2.entity.Entity;
import com.mayhem.rs2.entity.World;
import com.mayhem.rs2.entity.item.Item;
import com.mayhem.rs2.entity.item.impl.GroundItemHandler;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.Client;
import com.mayhem.rs2.entity.player.net.out.OutgoingPacket;
import com.mayhem.rs2.entity.player.net.out.impl.SendConfig;
import com.mayhem.rs2.entity.player.net.out.impl.SendEnterString;
import com.mayhem.rs2.entity.player.net.out.impl.SendEnterXInterface;
import com.mayhem.rs2.entity.player.net.out.impl.SendItemToContainer;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;
import com.mayhem.rs2.entity.player.net.out.impl.SendSidebarInterface;
import com.mayhem.rs2.entity.player.net.out.impl.SendUpdateItems;
import com.mayhem.rs2.entity.player.net.out.impl.SendUpdateItemsAlt;

public class LootingBag {
	public Player player;
	public List<Item> items;

	public boolean viewingLootBag = false;
	public boolean addingItemsToLootBag = false;

	public int selectedItem = -1;
	public int selectedSlot = -1;

	public LootingBag(Player player) {
		this.player = player;
		items = new ArrayList<>();
	}

	public void onDeath() {
		player.getInventory().remove(11941);
		Entity killer = player.getCombat().getDamageTracker().getKiller();
		for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
			Item item = iterator.next();

			if (item == null) {
				continue;
			}
			if (item.getId() <= 0 || item.getAmount() <= 0) {
				continue;
			}
			if (!Item.getDefinition(item.getId()).isTradable()) {
				continue;
			}
			GroundItemHandler.add(item, player.getLocation(), World.getPlayers()[killer.getIndex()]);

			iterator.remove();
		}
		sendItems();
	}

	public static final int LOOTING_BAG = 11941;

	public static boolean isLootBag(Player player, int itemId) {
		return itemId == 11941;
	}

	public boolean handleButton(int buttonId) {
		if (buttonId == 142131) {
			closeLootbag();
			return true;
		}
		return false;
	}

	public void openLootbagWithdraw() {
		onClose();
		sendItems();
		player.getClient().queueOutgoingPacket(new SendSidebarInterface(3, 37342));
		viewingLootBag = true;
	}

	public void openLootbagView() {
		onClose();
		sendItemsViewOnly();
		player.getClient().queueOutgoingPacket(new SendSidebarInterface(3, 26700));
		viewingLootBag = true;
	}

	public void openLootbagAdd() {
		onClose();
		sendInventoryItems();
		player.getClient().queueOutgoingPacket(new SendSidebarInterface(3, 37343));
		addingItemsToLootBag = true;
	}

	public boolean handleClickItem(int id, int amount) {
		if (viewingLootBag) {
			// removeItemFromLootBag(id, amount);
			removeMultipleItemsFromBag(id, amount);
			return true;
		}
		if (addingItemsToLootBag) {
			addItemToLootBag(id, amount);
			return true;
		}
		return false;
	}

	public int findIndexInLootBag(int id) {
		for (Item item : items) {
			if (item.getId() == id) {
				return items.indexOf(item);
			}
		}
		return -1;
	}

	public boolean containsItem(int id) {
		for (Item item : items) {
			if (item.getId() == id) {
				return true;
			}
		}
		return false;
	}

	public void removeMultipleItemsFromBag(int id, int amount) {
		if (!player.getInterfaceManager().hasBankOpen()) {
			player.send(new SendMessage("You can only withdraw in a bank."));
			return;
		}
		if (amount >= Integer.MAX_VALUE) {
			amount = countItems(id);
		}
		int count = 0;
		while (containsItem(id)) {
			if (!removeItemFromLootBag(id, amount)) {
				return;
			}
			if (Item.getDefinition(id).isStackable()) {
				count += amount;
			} else {
				count++;
			}
			if (count >= amount) {
				return;
			}
		}
	}

	public boolean hasLootingBag() {
		if (player.getBank().hasItemId(LOOTING_BAG)) {
			return true;
		}
		if (player.getInventory().hasItemId(LOOTING_BAG)) {
			return true;
		}
		return false;
	}

	public int xItem;

	public int getXItem() {
		return xItem;
	}

	public void setXItem(int item) {
		xItem = item;
	}

	public boolean isRemoving() {
		return removing;
	}

	public boolean removing = false;

	public void removeFromBag(boolean removing) {
		this.removing = removing;
		player.setEnterXInterfaceId(26706);
		if (!isRemoving()) {
			player.send(new SendConfig(1012, 0));
		} else {
			player.send(new SendEnterString());
		}
		player.send(new OutgoingPacket() {
			@Override
			public void execute(Client paramClient) {
			}

			@Override
			public int getOpcode() {
				return 187;
			}
		});
	}

	public boolean removeItemFromLootBag(int id, int amount) {
		if (items.size() <= 0) {
			return false;
		}
		int index = findIndexInLootBag(id);
		if (index == -1) {
			return false;
		}
		Item item = items.get(index);
		if (item == null) {
			return false;
		}
		if (item.getId() <= 0 || item.getAmount() <= 0) {
			return false;
		}
		if (player.getInventory().getFreeSlots() <= 0) {
			return false;
		}
		if (player.getInventory().getItemAmount(id) + amount >= Integer.MAX_VALUE
				|| player.getInventory().getItemAmount(id) + amount <= 0) {
			return false;
		}

		int amountToAdd = 0;
		if ((items.get(items.indexOf(item)).getAmount()) > amount) {
			amountToAdd = amount;
			items.get(items.indexOf(item)).add(-amount);
		} else {
			amountToAdd = item.getAmount();
			items.remove(index);
		}

		player.getInventory().add(item.getId(), amountToAdd);

		System.out.println("Removing item: " + item.getId());
		sendItems();
		sendInventoryItems();

		return true;
	}

	public int countItems(int id) {
		int count = 0;
		for (Item item : items) {
			if (item.getId() == id) {
				count += item.getAmount();
			}
		}
		return count;
	}

	public void addItemToLootBag(int id, int amount) {

		if (items.size() >= 28) {
			player.send(new SendMessage("Bag is full."));
			return;
		}

		if (amount >= Integer.MAX_VALUE) {
			amount = player.getInventory().getItemAmount(id);
		}
		if (id <= 0 || amount <= 0) {
			return;
		}
		if (id == 11942 || id == 11941) {
			return;
		}

		if (countItems(id) + amount >= Integer.MAX_VALUE || countItems(id) + amount <= 0) {
			return;
		}

		int bagSpotsLeft = 28 - items.size();
		boolean stackable = Item.getDefinition(id).isStackable();
		boolean bagContainsItem = containsItem(id);
		if (amount > bagSpotsLeft) {
			if (!(stackable)) {
				amount = bagSpotsLeft;
				System.out.println("test");

				System.out.println(stackable);

				System.out.println(bagContainsItem);
			}
		}

		player.getInventory().remove(id, amount);

		// int amt = player.getItems().deleteItemAndReturnAmount(id, amount);
		if (!addItemToList(id, amount)) {
			return;
		}
		resetItems();
		sendItems();
		// sendInventoryItems();
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> setItems) {
		items = setItems;
	}

	public boolean addItemToList(int id, int amount) {
		for (Item item : items) {
			if (item.getId() == id) {
				if (item.getAmount() + amount >= Integer.MAX_VALUE) {
					return false;
				}
				if (player.getInventory().isStackable(id)) {
					item.setAmount(item.getAmount() + amount);
					return true;
				}
			}
		}
		items.add(new Item(id, amount));
		return true;
	}

	public void closeLootbag() {
		Player c = (Player) player;
		player.getClient().queueOutgoingPacket(new SendSidebarInterface(3, 3213));
		onClose();
	}

	public void deposit() {
		if (!player.getInventory().hasItemId(LOOTING_BAG)) {
			return;
		}
		if (!player.inWilderness()) {
			player.sendMessage("You can only deposit in the wilderness.");
			return;
		}
		openLootbagAdd();
	}

	public void withdraw() {
		if (!player.getInventory().hasItemId(LOOTING_BAG)) {
			return;
		}
		if (player.inWilderness()) {
			player.sendMessage("You can not withdraw in the wilderness.");
			return;
		}
		openLootbagWithdraw();
	}

	public void onClose() {
		viewingLootBag = false;
		addingItemsToLootBag = false;
	}

	public void sendItems() {
		if (!player.getInventory().hasItemId(LOOTING_BAG)) {
			return;
		}

		final int START_ITEM_INTERFACE = 26706;
		for (int i = 0; i < 28; i++) {
			int id = 0;
			int amt = 0;

			if (i < items.size()) {
				Item item = items.get(i);

				if (item != null) {
					id = item.getId();
					amt = item.getAmount();

				}
			}

			Player c = (Player) player;

			if (id <= 0) {
				id = -1;
			}
			// player.getClient().queueOutgoingPacket(new
			// SendItemToContainer(START_ITEM_INTERFACE, id, amt, i));
			// System.out.println("Sent bag item: " + id);

			player.send(new SendUpdateItemsAlt(26706, id, amt, i));
		}
	}

	public void sendItemsViewOnly() {
		if (!player.getInventory().hasItemId(LOOTING_BAG)) {
			return;
		}

		final int START_ITEM_INTERFACE = 26706;
		for (int i = 0; i < 28; i++) {
			int id = 0;
			int amt = 0;

			if (i < items.size()) {
				Item item = items.get(i);
				if (item != null) {
					id = item.getId();
					amt = item.getAmount();
				}
			}

			Player c = (Player) player;

			if (id <= 0) {
				id = -1;
			}
			player.getClient().queueOutgoingPacket(new SendUpdateItemsAlt(26706, id, amt, i));

			// Sending exactly how they sent it lol
			// System.out.println("Sent bag item: " + id);
		}
	}

	public void sendInventoryItems() {
		if (!player.getInventory().hasItemId(LOOTING_BAG)) {
			return;
		}
		final int START_ITEM_INTERFACE = 27342;
		for (int i = 0; i < 28; i++) {
			int id = 0;
			int amt = 0;

			if (i < player.getInventory().getSize()) {
				id = player.getInventory().getSlotId(i);
				amt = player.getInventory().getSlotAmount(i);
				;
			}

			Player c = (Player) player;
			player.getClient().queueOutgoingPacket(new SendItemToContainer(START_ITEM_INTERFACE, id, amt, i));

			// System.out.println("Sent inventory item: " + id);
		}
	}

	private String getShortAmount(int amount) {
		if (amount <= 1) {
			return "";
		}
		String amountToString = "" + amount;
		if (amount > 1000000000) {
			amountToString = "@gre@" + (amount / 1000000000) + "B";
		} else if (amount > 1000000) {
			amountToString = "@gre@" + (amount / 1000000) + "M";
		} else if (amount > 1000) {
			amountToString = "@whi@" + (amount / 1000) + "K";
		}
		return amountToString;
	}

	private void resetItems() {
		player.getInventory().update();
		player.getUpdateFlags().setUpdateRequired(true);
	}
}
