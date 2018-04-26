package com.mayhem.rs2.entity.mob.impl;

import java.util.List;

import com.mayhem.rs2.entity.Location;
import com.mayhem.rs2.entity.World;
import com.mayhem.rs2.entity.mob.Mob;
import com.mayhem.rs2.entity.player.Player;

/**
 * @author Andy || ReverendDread Mar 23, 2017
 */
public class DarkAnkou extends Mob {

	//Fields
	private Player target;
	private Mob creator;
	
	/**
	 * Constructs a Dark Ankou Entity.
	 * @param loc {@link Location} The location.
	 * @param target {@link Player} The target.
	 */
	public DarkAnkou(Location loc, Player target, Mob creator) {
		super(7296, true, loc, target, false, false, null);
		if (getOwner() != null) {
			getCombat().setAttack(getOwner());
			getFollowing().setIgnoreDistance(true);
			getUpdateFlags().faceEntity(getOwner().getIndex());
		}
		this.target = target;
		this.creator = creator;
	}

	@Override
	public void process() {
		if (target.isDead() || !target.isActive() || creator.isDead() || creator.getCombatants().contains(target)) {
			remove();
			return;
		}
	}
	
}
