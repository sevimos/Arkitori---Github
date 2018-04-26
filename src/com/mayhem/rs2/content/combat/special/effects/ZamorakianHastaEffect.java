package com.mayhem.rs2.content.combat.special.effects;

import com.mayhem.core.cache.map.Region;
import com.mayhem.rs2.content.combat.impl.CombatEffect;
import com.mayhem.rs2.entity.Entity;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

public class ZamorakianHastaEffect implements CombatEffect {

	private void walk(Entity e) {
		if (!Region.getRegion(e.getLocation().getX(), e.getLocation().getY()).blockedWest(e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ())) {
			e.getMovementHandler().walkTo(-2, 0);
		} else if (!Region.getRegion(e.getLocation().getX(), e.getLocation().getY()).blockedEast(e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ())) {
			e.getMovementHandler().walkTo(2, 0);
		} else if (!Region.getRegion(e.getLocation().getX(), e.getLocation().getY()).blockedNorth(e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ())) {
			e.getMovementHandler().walkTo(0, 2);
		} else if (!Region.getRegion(e.getLocation().getX(), e.getLocation().getY()).blockedSouth(e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ())) {
			e.getMovementHandler().walkTo(0, -2);
		}
	}

	@Override
	public void execute(Player p, Entity e) {
		if (!e.isStunned()) {
			e.stun(3);
		}

		walk(e);

		p.send(new SendMessage("You stun your opponent."));
	}
}
