package com.mayhem.rs2.content.combat.special.specials;

import com.mayhem.core.util.Utility;
import com.mayhem.rs2.content.combat.impl.Attack;
import com.mayhem.rs2.content.combat.special.Special;
import com.mayhem.rs2.entity.Animation;
import com.mayhem.rs2.entity.Graphic;
import com.mayhem.rs2.entity.player.Player;

public class SaradominSwordSpecialAttack implements Special {

	@Override
	public boolean checkRequirements(Player paramPlayer) {
		return true;
	}

	@Override
	public int getSpecialAmountRequired() {
		return 100;
	}

	@Override
	public void handleAttack(Player player) {
		player.getCombat().getMagic().setNextHit(Utility.randomNumber(20));

		player.getCombat().getMagic().setAttack(new Attack(1, player.getCombat().getAttackCooldown()), null, new Graphic(1213, 0, true),null,null);
		player.getCombat().getMagic().execute(player.getCombat().getAttacking());

		if (player.getEquipment().getItems()[3].getId() == 11838) {
			player.getCombat().getMelee().setAnimation(new Animation(1132, 0));
		} else if (player.getEquipment().getItems()[3].getId() == 12809) {
			player.getCombat().getMelee().setAnimation(new Animation(1133, 0));
		}
		
		player.getCombat().getMelee().setDamageBoost(1.4);
	}

}
