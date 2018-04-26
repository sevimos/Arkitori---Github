package com.mayhem.rs2.entity.mob;

import java.util.HashMap;
import java.util.Map;

public enum MobFollowDistance {
	
	BOSSES(new int[] { 1233, 1498, 1833, 8133, 6247, 84, 3358, 7152, 6766, 1540, 6619, 6248, 6252, 6250, 6204, 6203, 6208, 6265,
			6263, 6261, 6260, 6225, 6227, 6223, 6222 }, 40);

	private int[] mobId;
	private int distance;
	private static Map<Integer, MobFollowDistance> mobs;

	static {
		mobs = new HashMap<Integer, MobFollowDistance>();

		for (MobFollowDistance def : values()) {
			for (int k = 0; k < def.mobId.length; k++) {
				mobs.put(def.mobId[k], def);
			}
		}
	}

	private static MobFollowDistance forId(int id) {
		return mobs.get(Integer.valueOf(id));
	}

	public static int getDistance(int mobId) {
		MobFollowDistance def = forId(mobId);
		switch(mobId) {
		case 7690:
		case 7691:
		case 7692:
		case 7693:
		case 7694:
		case 7695:
		case 7696:
		case 7697:
		case 7698:
		case 7699:
		case 7700:
		case 7701:
		case 7702:
		case 7703:
		case 7704:
		case 7705:
		case 7706:
		case 7708:
		return 40;
		}
		return def == null ? 8 : def.getDistance();
	}

	private MobFollowDistance(int[] mobId, int distance) {
		this.mobId = mobId;
		this.distance = distance;
	}

	public int getDistance() {
		return distance;
	}

	public int[] getMobId() {
		return mobId;
	}
}
