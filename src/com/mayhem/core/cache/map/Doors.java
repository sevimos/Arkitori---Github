package com.mayhem.core.cache.map;


import com.mayhem.Constants;
import com.mayhem.core.task.TaskQueue;
import com.mayhem.core.task.impl.TickDoorTask;
import com.mayhem.rs2.entity.object.GameObject;
import com.mayhem.rs2.entity.object.ObjectManager;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;
import com.mayhem.rs2.entity.player.net.out.impl.SendSound;

public class Doors {

	public static final int[][] JAMMED_DOORS = { { 1852, 5212, 0 }, { 1832, 5224, 0 }, { 1832, 5225, 0 } };

	public static boolean clickDoor(int object, int x, int y, int z) {
		for (int i = 0; i < Constants.BLOCKED_DOORS.length; i++) {
			if (object == Constants.BLOCKED_DOORS[i]) {
				return false;
			}
		}
		if (Region.isDoor(x, y, z)) {
			Door door = Region.getDoor(x, y, z);

			if (door == null) {
				return false;
			}

			if (door.getId() == 733 || door.getId() == 734) {
				return false;
			}

			if (door.getX() == 2998 && door.getY() == 3931 || door.getX() == 2997 && door.getY() == 3931
					|| door.getX() == 2998 && door.getY() == 3917 || door.getX() == 3044 && door.getY() == 3956
					|| door.getX() == 3041 && door.getY() == 3959 || door.getX() == 3038 && door.getY() == 3956
					|| door.getX() == 3190 && door.getY() == 3957 || door.getX() == 3191 && door.getY() == 3963) {
				return false;
			}

			if (z > 3) {
				z %= 4;
			}

			boolean original = door.original();

			if (original) {
				ObjectManager.registerUnclippedObject(new GameObject(2376, x, y, z, door.getType(), door.getCurrentFace(), true));
			} else {
				ObjectManager.remove(new GameObject(door.getCurrentId(), x, y, z, door.getType(), door.getCurrentFace(), true));
			}

			Region.getRegion(x, y).appendDoor(door.getCurrentId(), x, y, z);

			if (door.original()) {
				ObjectManager.removeActive(
						new GameObject(2376, door.getX(), door.getY(), z, door.getType(), door.getCurrentFace(), true));
				ObjectManager.registerObject(new GameObject(door.getCurrentId(), door.getX(), door.getY(), z, door.getType(),
						door.getCurrentFace(), true));
			} else {
				ObjectManager.registerUnclippedObject(new GameObject(door.getCurrentId(), door.getX(), door.getY(), z, door.getType(),
						door.getCurrentFace(), true));

				TaskQueue.queue(new TickDoorTask(door));
			}
			return true;
		}

		if (Region.isDoubleDoor(x, y, z)) {
			DoubleDoor door = Region.getDoubleDoor(x, y, z);

			if (door == null) {
				return false;
			}

			if (door.getX1() == 2328 && door.getY1() == 3805 || door.getX1() == 2328 && door.getY1() == 3805) {
				return false;
			}

			if (door.getX1() == 2998 && door.getY1() == 3931 || door.getX1() == 2997 && door.getY1() == 3931) {
				return false;
			}

			if (z > 3) {
				z %= 4;
			}

			ObjectManager.removeActive(new GameObject(door.getCurrentId1(), door.getX1(), door.getY1(), z,
					door.getType(), door.getCurrentFace1(), true));
			ObjectManager.registerUnclippedObject(
					new GameObject(2376, door.getX1(), door.getY1(), z, door.getType(), door.getCurrentFace1(), true));

			ObjectManager.removeActive(new GameObject(door.getCurrentId2(), door.getX2(), door.getY2(), z,
					door.getType(), door.getCurrentFace2(), true));
			ObjectManager.registerUnclippedObject(
					new GameObject(2376, door.getX2(), door.getY2(), z, door.getType(), door.getCurrentFace2(), true));

			Region.getRegion(x, y).appendDoubleDoor(door.getCurrentId1(), door.getX1(), door.getY1(), z);

			ObjectManager.removeActive(
					new GameObject(2376, door.getX1(), door.getY1(), z, door.getType(), door.getCurrentFace1(), true));
			ObjectManager.removeActive(
					new GameObject(2376, door.getX2(), door.getY2(), z, door.getType(), door.getCurrentFace2(), true));

			ObjectManager.registerUnclippedObject(new GameObject(door.getCurrentId1(), door.getX1(), door.getY1(), z, door.getType(),
					door.getCurrentFace1(), true));
			ObjectManager.registerUnclippedObject(new GameObject(door.getCurrentId2(), door.getX2(), door.getY2(), z, door.getType(),
					door.getCurrentFace2(), true));
			return true;
		}

		return false;
	}

	public static boolean clickDoor(Player player, int object, int x, int y, int z) {
		if (clickDoor(object, x, y, z)) {
			player.getClient().queueOutgoingPacket(new SendSound(326, 0, 0));
			return true;
		}
		return false;
	}

	public static boolean isDoorJammed(Player player, int x, int y, int z) {
		for (int[] i : JAMMED_DOORS) {
			if ((x == i[0]) && (y == i[1]) && (z == i[2])) {
				return true;
			}
		}
		return false;
	}
}