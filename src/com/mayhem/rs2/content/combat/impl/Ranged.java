package com.mayhem.rs2.content.combat.impl;

import com.mayhem.core.task.Task;
import com.mayhem.core.task.TaskQueue;
import com.mayhem.core.task.impl.GraphicTask;
import com.mayhem.core.task.impl.HitTask;
import com.mayhem.core.util.Utility;
import com.mayhem.rs2.content.combat.Combat;
import com.mayhem.rs2.content.combat.Hit;
import com.mayhem.rs2.content.combat.Combat.CombatTypes;
import com.mayhem.rs2.content.combat.formula.RangeFormulas;
import com.mayhem.rs2.entity.Animation;
import com.mayhem.rs2.entity.Entity;
import com.mayhem.rs2.entity.Graphic;
import com.mayhem.rs2.entity.Projectile;
import com.mayhem.rs2.entity.World;
//import com.mayhem.rs2.entity.player.Player;
//import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

public class Ranged {

	private final Entity entity;
	private Attack attack = null;
	private Animation animation = null;
	private Graphic start = null;
	private Graphic end = null;
	private Projectile projectile = null;

	private int pOffset = 0;
	private byte gOffset = 0;

	public Ranged(Entity entity) {
		this.entity = entity;
	}

	public void execute(Entity target) {
		if ((attack == null) || (target == null) || (target.isDead())) {
			return;
		}

		boolean success;
		if (Utility.randomNumber(RangeFormulas.calculateRangeDefence(entity)) > Utility.randomNumber(RangeFormulas.calculateRangeAttack(entity))) {
			success = false;
		} else {
			success = true;
		}

		int damage = entity.getCorrectedDamage(Combat.next(entity.getMaxHit(CombatTypes.RANGED) + 1));
		Hit hit = new Hit(entity, (success) || (entity.isIgnoreHitSuccess()) ? damage : 0, Hit.HitTypes.RANGED);

		entity.setLastDamageDealt(hit.getDamage());
		entity.setLastHitSuccess((success) || (entity.isIgnoreHitSuccess()));

		entity.getCombat().updateTimers(attack.getAttackDelay());

		if (animation != null) {
			entity.getUpdateFlags().sendAnimation(animation);
		}

		if (start != null) {
			executeStartGraphic();
		}

		if (projectile != null) {
			executeProjectile(target);
		}

		TaskQueue.queue(new HitTask(attack.getHitDelay(), false, hit, target));

		if (end != null) {
			TaskQueue.queue(new GraphicTask(attack.getHitDelay(), false, end, target));
		}

		target.getCombat().setInCombat(entity);
		entity.doConsecutiveAttacks(target);
		entity.onAttack(target, hit.getDamage(), CombatTypes.RANGED, success);
	}

	public void executeProjectile(Entity target) {
		final int lockon = target.isNpc() ? target.getIndex() + 1 : -target.getIndex() - 1;
		final byte offsetX = (byte) ((entity.getLocation().getY() - target.getLocation().getY()) * -1);
		final byte offsetY = (byte) ((entity.getLocation().getX() - target.getLocation().getX()) * -1);

		if (pOffset > 0) {
			final Projectile p = new Projectile(projectile);
			TaskQueue.queue(new Task(pOffset) {
				@Override
				public void execute() {
					World.sendProjectile(p, entity.getLocation(), lockon, offsetX, offsetY);
					stop();
				}

				@Override
				public void onStop() {
				}
			});
		} else {
			World.sendProjectile(projectile, entity.getLocation(), lockon, offsetX, offsetY);
		}
	}

	public void executeStartGraphic() {
		if (gOffset > 0) {
			final Graphic g = new Graphic(start);

			TaskQueue.queue(new Task(gOffset) {
				@Override
				public void execute() {
					entity.getUpdateFlags().sendGraphic(g);
					stop();
				}

				@Override
				public void onStop() {
				}
			});
		} else {
			entity.getUpdateFlags().sendGraphic(start);
		}
	}

	public Animation getAnimation() {
		return animation;
	}

	public Attack getAttack() {
		return attack;
	}

	public Projectile getProjectile() {
		return projectile;
	}

	public int getProjectileOffset() {
		return pOffset;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public void setAttack(Attack attack) {
		this.attack = attack;
	}

	public void setAttack(Attack attack, Animation animation, Graphic start, Graphic end, Projectile projectile) {
		this.attack = attack;
		this.animation = animation;
		this.start = start;
		this.end = end;
		this.projectile = projectile;
	}

	public void setEnd(Graphic end) {
		this.end = end;
	}

	public void setProjectile(Projectile projectile) {
		this.projectile = projectile;
	}

	public void setProjectileOffset(int pOffset) {
		this.pOffset = pOffset;
	}

	public void setStart(Graphic start) {
		this.start = start;
	}

	public void setStartGfxOffset(byte gOffset) {
		this.gOffset = gOffset;
	}
}
