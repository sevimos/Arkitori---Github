package com.mayhem.rs2.entity.mob.abilities;

import com.mayhem.Server;
import com.mayhem.rs2.content.combat.CombatEffect;
import com.mayhem.rs2.content.combat.Combat.CombatTypes;
import com.mayhem.rs2.content.minigames.inferno.InfernoWave;
import com.mayhem.rs2.entity.Entity;
import com.mayhem.rs2.entity.Graphic;
import com.mayhem.rs2.entity.Location;
import com.mayhem.rs2.entity.World;
import com.mayhem.rs2.entity.mob.Mob;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.PlayerConstants;

public class ZukAbility implements CombatEffect {
	@Override
	public void execute(Entity zuk, Entity target) {
		Player player = null;
		if(!target.isDead()) {
		for (Player players : World.getPlayers()) {
			if (players != null) {
				if(players.getLocation().getZ() == zuk.getLocation().getZ()) {
					player = players;
				}
			}
		}
	}
		if(player == null) {
			return;
		}
			
			if (zuk.getLevels()[3] < 850 && !player.singleRanger && !player.singleMager) {

				Mob ranger = new Mob(player, InfernoWave.JAL_XIL, false, false, false,
						new Location(2276, 5350, player.getInferno().getHeight(player)));
				Mob mager = new Mob(player, InfernoWave.JAL_ZEK, false, false, false,
						new Location(2265, 5350, player.getInferno().getHeight(player)));
				player.getInfernoDetails().addNpc(ranger);
				player.getInfernoDetails().addNpc(mager);
				ranger.getCombat().setAttack(player);
				mager.getCombat().setAttack(player);
				player.singleMager = true;
				player.singleRanger = true;
			}

			if (zuk.getLevels()[3] < 480 && !player.singleJad) {
				Mob healer1 = new Mob(player, InfernoWave.JAL_MEJJAK, false, false, false,
						new Location(2280, 5363, player.getInferno().getHeight(player)));
				Mob healer2 = new Mob(player, InfernoWave.JAL_MEJJAK, false, false, false,
						new Location(2271, 5362, player.getInferno().getHeight(player)));
				Mob healer3 = new Mob(player, InfernoWave.JAL_MEJJAK, false, false, false,
						new Location(2262, 5363, player.getInferno().getHeight(player)));
				Mob jad = new Mob(player, InfernoWave.JALTOK_JAD, false, false, false,
						new Location(2273, 5349, player.getInferno().getHeight(player)));
				player.getInfernoDetails().addNpc(healer1);
				player.getInfernoDetails().addNpc(healer2);
				player.getInfernoDetails().addNpc(healer3);
				player.getInfernoDetails().addNpc(jad);
				jad.getCombat().setAttack(player);
				healer1.getCombat().setAttack(player);
				healer2.getCombat().setAttack(player);
				healer3.getCombat().setAttack(player);
				player.singleJad = true;
			} else if (zuk.getLevels()[3] < 240 && !player.healers) {
				Mob healer1 = new Mob(player, InfernoWave.JAL_MEJJAK, false, false, false,
						new Location(2280, 5363, player.getInferno().getHeight(player)));
				Mob healer2 = new Mob(player, InfernoWave.JAL_MEJJAK, false, false, false,
						new Location(2267, 5362, player.getInferno().getHeight(player)));
				Mob healer3 = new Mob(player, InfernoWave.JAL_MEJJAK, false, false, false,
						new Location(2275, 5362, player.getInferno().getHeight(player)));
				Mob healer4 = new Mob(player, InfernoWave.JAL_MEJJAK, false, false, false,
						new Location(2262, 5363, player.getInferno().getHeight(player)));
				player.getInfernoDetails().addNpc(healer1);
				player.getInfernoDetails().addNpc(healer2);
				player.getInfernoDetails().addNpc(healer3);
				player.getInfernoDetails().addNpc(healer4);
				healer1.getCombat().setAttack(player);
				healer2.getCombat().setAttack(player);
				healer3.getCombat().setAttack(player);
				healer4.getCombat().setAttack(player);
				player.healers = true;
			}
		}

}
