package com.mayhem.rs2.content.minigames.inferno;

import com.mayhem.core.task.TaskQueue;
import com.mayhem.core.task.impl.MobWalkTask;
import com.mayhem.rs2.content.combat.Combat.CombatTypes;
import com.mayhem.rs2.entity.Entity;
import com.mayhem.rs2.entity.Location;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.controllers.Controller;

public final class InfernoController extends Controller {

	public static final String MINIGAME = "Tzharr Fight Caves";

	@Override
	public boolean allowMultiSpells() {
		return true;
	}

	@Override
	public boolean allowPvPCombat() {
		return false;
	}

	@Override
	public boolean canAttackNPC() {
		return true;
	}

	@Override
	public boolean canAttackPlayer(Player p, Player p2) {
		return false;
	}

	@Override
	public boolean canClick() {
		return true;
	}

	@Override
	public boolean canDrink(Player p) {
		return true;
	}

	@Override
	public boolean canEat(Player p) {
		return true;
	}

	@Override
	public boolean canEquip(Player p, int id, int slot) {
		return true;
	}

	@Override
	public boolean canUnequip(Player player) {
		return true;
	}

	@Override
	public boolean canDrop(Player player) {
		return true;
	}

	@Override
	public boolean canLogOut() {
		return true;
	}

	@Override
	public boolean canMove(Player p) {
		return true;
	}

	@Override
	public boolean canSave() {
		return true;
	}

	@Override
	public boolean canTalk() {
		return true;
	}

	@Override
	public boolean canTeleport() {
		return false;
	}

	@Override
	public boolean canTrade() {
		return false;
	}

	@Override
	public boolean canUseCombatType(Player p, CombatTypes type) {
		return true;
	}

	@Override
	public boolean canUsePrayer(Player p, int id) {
		return true;
	}

	@Override
	public boolean canUseSpecialAttack(Player p) {
		return true;
	}

	@Override
	public Location getRespawnLocation(Player player) {
		return new Location(2495, 5110, 0);
	}

	@Override
	public boolean isSafe(Player player) {
		return true;
	}

	@Override
	public void onControllerInit(Player p) {
	}

	@Override
	public void onDeath(Player p) {
		p.getInferno().leaveGame(p);
	}

	@Override
	public void onDisconnect(Player p) {
		p.getInferno().leaveGame(p);
	}

	@Override
	public void onTeleport(Player p) {
	}

	int bossTick = 0;

	@Override
	public void tick(Player p) {

		if (p.getInfernoDetails().getStage() == 70 && p.getInfernoDetails().getGlyph() != null) {
			bossTick++;
			if (bossTick == 1) { // move right
				//p.getInfernoDetails().getGlyph().getMovementHandler().walkTo(2284, 5362);
				 TaskQueue.queue(new MobWalkTask(p.getInfernoDetails().getGlyph(), new Location(2284,5361), true));
				p.sendMessage("1");
				p.sendMessage(p.glyph.getDefinition().getName());
			}
			if (bossTick == 18) { // move left to home
				//p.getInfernoDetails().getGlyph().getMovementHandler().walkTo(Tzkalzuk.GLYPH_SPAWN_X,
					//	Tzkalzuk.GLYPH_SPAWN_Y);
				 TaskQueue.queue(new MobWalkTask(p.getInfernoDetails().getGlyph(), new Location(Tzkalzuk.GLYPH_SPAWN_X,
				 Tzkalzuk.GLYPH_SPAWN_Y), true));
				p.sendMessage("2");
			}
			if (bossTick == 32) { // move left again
				//p.getInfernoDetails().getGlyph().getMovementHandler().walkTo(2258, 5362);
				 TaskQueue.queue(new MobWalkTask(p.getInfernoDetails().getGlyph(), new Location(2257,5361), true));
				p.sendMessage("3");
			}
			if (bossTick == 48) { // move right to home
				//p.getInfernoDetails().getGlyph().getMovementHandler().walkTo(Tzkalzuk.GLYPH_SPAWN_X,
						//Tzkalzuk.GLYPH_SPAWN_Y);
				 TaskQueue.queue(new MobWalkTask(p.getInfernoDetails().getGlyph(), new Location(Tzkalzuk.GLYPH_SPAWN_X,
				 Tzkalzuk.GLYPH_SPAWN_Y), true));
				p.sendMessage("4");
			}
			if (bossTick == 59) {
				bossTick = 0;
				p.sendMessage("5");
			}

		} else {
			bossTick = 0;
		}
		if (p.getInfernoDetails().getGlyph() != null && p.getInfernoDetails().getBoss() != null) {
			if (p.getInferno().behindGlyph(p)) {
				p.getInfernoDetails().getBoss().getCombat().setAttack(p.getInfernoDetails().getGlyph());
			} else {
				p.getInfernoDetails().getBoss().getCombat().setAttack(p);
			}
		}
	}

	@Override
	public String toString() {
		return "Tzharr Fight Caves";
	}

	@Override
	public boolean transitionOnWalk(Player p) {
		return false;
	}

	@Override
	public void onKill(Player player, Entity killed) {

	}
}
