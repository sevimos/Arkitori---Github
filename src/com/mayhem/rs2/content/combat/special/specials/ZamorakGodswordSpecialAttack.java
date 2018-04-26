package com.mayhem.rs2.content.combat.special.specials;

import com.mayhem.rs2.content.combat.special.Special;
import com.mayhem.rs2.entity.Animation;
import com.mayhem.rs2.entity.Graphic;
import com.mayhem.rs2.entity.player.Player;

public class ZamorakGodswordSpecialAttack implements Special {
	
	@Override
	public boolean checkRequirements(Player player) {
		return true;
	}

	@Override
	public int getSpecialAmountRequired() {
		return 60;
	}

	@Override
	public void handleAttack(Player player) {
		player.getCombat().getMelee().setAnimation(new Animation(7638, 0));
		player.getUpdateFlags().sendGraphic(Graphic.highGraphic(1210, 0));
		//player.getCombat().getAttacking().getUpdateFlags().sendGraphic(Graphic.lowGraphic(2104, 0));
	}
}
