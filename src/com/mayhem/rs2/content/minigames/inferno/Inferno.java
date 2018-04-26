package com.mayhem.rs2.content.minigames.inferno;

import com.mayhem.core.task.Task;
import com.mayhem.rs2.entity.object.*;
import com.mayhem.core.task.TaskQueue;
import com.mayhem.core.task.Task.BreakType;
import com.mayhem.core.task.Task.StackType;
import com.mayhem.core.task.impl.MobWalkTask;
import com.mayhem.core.task.impl.TaskIdentifier;
import com.mayhem.core.util.Utility;
import com.mayhem.rs2.content.minigames.fightcave.TzharrController;
import com.mayhem.rs2.content.minigames.fightcave.TzharrData;
import com.mayhem.rs2.content.minigames.fightcave.TzharrData.NPCS_DETAILS;
import com.mayhem.rs2.entity.Location;
import com.mayhem.rs2.entity.World;
import com.mayhem.rs2.entity.mob.Mob;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.controllers.ControllerManager;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;
import com.mayhem.rs2.entity.player.net.out.impl.SendRemoveInterfaces;

public class Inferno {

	public static final InfernoController CONTROLLER = new InfernoController();
	private int killsRemaining;
	// change
	public static final Location LEAVE = new Location(2438, 5168, 0);

	public int getHeight(Player player) {
		return player.getIndex() * 4;
	}
	
	public boolean behindGlyph(Player player) {
		if(player.getInfernoDetails().getGlyph().getSize() == 0) {
			return false;
		}
		if(player.getX()  >= player.getInfernoDetails().getGlyph().getX()-1 && player.getX()  <= player.getInfernoDetails().getGlyph().getX()+1) {
			return true;
		}
		return false;
	}
	


	public void spawn(Player player) {
		TaskQueue.queue(new Task(player, 20, false, StackType.NEVER_STACK, BreakType.NEVER, TaskIdentifier.TZHAAR) {
			@Override
			public void execute() {
				if (player.getInfernoDetails().getStage() == 70) {

	
					Tzkalzuk.initiateTzkalzuk1(player);
					player.send(
							new SendMessage("Relog if the boss instance does not start within the next 10 seconds."));
					// initiateTzkalzuk();
				return;
				}
				// int index = Utility.random(InfernoWave.SPAWN_DATA.length - 1);

				// int random = Utility.random(10);

				for (short i : InfernoWave.InfernoData.values()[player.getInfernoDetails().getStage()].getNpcs()) {
					int startX = 2271 + Utility.random(12); // InfernoWave.SPAWN_DATA[index][0]
					int startY = 5342 + Utility.random(12); // InfernoWave.SPAWN_DATA[index][1]
					if(i == InfernoWave.JAL_NIB) {
						startX = 2268;
						startY = 5344;
					}
					Mob mob = new Mob(player, i, false, false, false, new Location(startX, startY, getHeight(player)));
					mob.getFollowing().setIgnoreDistance(true);
					if(mob.getId() == InfernoWave.JAL_NIB) {
						mob.getCombat().setAttack(player.getInfernoDetails().getPillars().get(Utility.random(player.getInfernoDetails().getPillars().size() - 1)));	
					}else {
					mob.getCombat().setAttack(player);
					}
					player.getInfernoDetails().addNpc(mob);
				}
				player.getClient().queueOutgoingPacket(
						new SendMessage("@red@Wave: " + (player.getInfernoDetails().getStage() + 1)));
				stop();

			}

			@Override
			public void onStop() {
				// TODO Auto-generated method stub

			}
		});
	}

	public static final void checkForFightCave(Player p, Mob mob) {
		if (p.getController().equals(CONTROLLER)) {
			
			p.getInfernoDetails().removeNpc(mob);
			
			if (mob.getId() == InfernoWave.JAL_AK) {

				short[] ids = new short[] { InfernoWave.JAL_AKREK_KET,InfernoWave.JAL_AKREK_MEJ, InfernoWave.JAL_AKREK_XIL};
				for (short i : ids) {
					Mob m = new Mob(p, i, false, false, false, mob.getLocation());
					m.getFollowing().setIgnoreDistance(true);
					m.getCombat().setAttack(p);
					p.getInfernoDetails().addNpc(m);
				}
			}
			if (p.getInfernoDetails().getKillAmount() == 0) {
				if (p.getInfernoDetails().getStage() == 70) {
					p.getInferno().stop(p);
					return;
				}
				p.getInfernoDetails().increaseStage();
				p.getInferno().spawn(p);
			}
		}
	}

	public void leaveGame(Player player) {
		if (System.currentTimeMillis() - player.infernoLeaveTimer < 15000) {
			player.send(new SendMessage("You cannot leave yet, wait a couple of seconds and try again."));
			return;
		}
		killAllSpawns(player);
		player.getInfernoDetails().getMobs().clear();
		player.getInfernoDetails().getPillars().clear();
		player.setController(ControllerManager.DEFAULT_CONTROLLER);
		player.send(new SendMessage("You have left the Inferno minigame."));
		player.teleport(new Location(2495, 5110, 0));
		player.infernoWaveType = 0;
		player.infernoWaveId = 0;
		player.singleMager = false;
		player.singleRanger = false;
		player.singleJad = false;
		player.healers = false;
	}

	public void create(Player player, int type) {
		player.setController(CONTROLLER);
		player.send(new SendRemoveInterfaces());
		player.teleport(new Location(2271, 5329, getHeight(player)));
		player.infernoWaveType = type;
		player.send(new SendMessage("Welcome to the Inferno. Your first wave will start soon."));
		player.infernoWaveId = 0;
		player.getInfernoDetails().setStage(0);
		player.infernoLeaveTimer = System.currentTimeMillis();
		spawn(player);
		spawnPillars(player);
		// spawn pillars

	}

	public void spawnPillars(Player player) {
		Mob pillar1 = new Mob(player, 7710, false, false, false, new Location(2257, 5351, getHeight(player)));
		Mob pillar2 = new Mob(player, 7710, false, false, false, new Location(2273, 5351, getHeight(player)));
		Mob pillar3 = new Mob(player, 7710, false, false, false, new Location(2268, 5336, getHeight(player)));
		
		pillar1.setCanAttack(false);;
		pillar2.setCanAttack(false);;
		pillar3.setCanAttack(false);;
		player.getInfernoDetails().addPillar(pillar1);
		player.getInfernoDetails().addPillar(pillar2);
		player.getInfernoDetails().addPillar(pillar3);
/**
	ObjectManager.deleteWithObject(2267, 5368, player.getInferno().getHeight(player)+1); //delete ceiling
		
		
		ObjectManager.spawnWithObject(30342, 2267, 5366, getHeight(player), 1, 10);  // West Wall
		ObjectManager.spawnWithObject(30341, 2275, 5366, getHeight(player), 3, 10);   // East Wall
		ObjectManager.spawnWithObject(30340, 2267, 5364, getHeight(player), 1, 10);   // West Corner
		ObjectManager.spawnWithObject(30339, 2275, 5364, getHeight(player), 3, 10);   // East Corner
		ObjectManager.spawnWithObject(30344, 2268, 5364, getHeight(player), 3, 10);  // Set falling rocks - west
		ObjectManager.spawnWithObject(30343, 2273, 5364, getHeight(player), 3, 10);  // Set falling rocks - east
		
		//ObjectManager.
		ObjectManager.deleteWithObject(2270, 5363, player.getInferno().getHeight(player)); //delete wall glyph
		*/
		// handle clipping
		ObjectManager.spawnWithObject(2376, 2270, 5336, getHeight(player), 10, 0);
		ObjectManager.spawnWithObject(2376, 2270, 5337, getHeight(player), 10, 0);
		ObjectManager.spawnWithObject(2376, 2270, 5338, getHeight(player), 10, 0);

		ObjectManager.spawnWithObject(2376, 2269, 5338, getHeight(player), 10, 0);
		ObjectManager.spawnWithObject(2376, 2268, 5338, getHeight(player), 10, 0);
		ObjectManager.spawnWithObject(2376, 2268, 5337, getHeight(player), 10, 0);
		ObjectManager.spawnWithObject(2376, 2268, 5336, getHeight(player), 10, 0);

		ObjectManager.spawnWithObject(2376, 2275, 5351, getHeight(player), 10, 0);
		ObjectManager.spawnWithObject(2376, 2275, 5352, getHeight(player), 10, 0);
		ObjectManager.spawnWithObject(2376, 2275, 5353, getHeight(player), 10, 0);

		ObjectManager.spawnWithObject(2376, 2274, 5353, getHeight(player), 10, 0);
		ObjectManager.spawnWithObject(2376, 2273, 5353, getHeight(player), 10, 0);
		ObjectManager.spawnWithObject(2376, 2273, 5352, getHeight(player), 10, 0);
		ObjectManager.spawnWithObject(2376, 2273, 5351, getHeight(player), 10, 0);
		ObjectManager.spawnWithObject(2376, 2274, 5351, getHeight(player), 10, 0);
		ObjectManager.spawnWithObject(2376, 2269, 5336, getHeight(player), 10, 0);
		
		
		
		
		ObjectManager.spawnWithObject(2376,2257,5351, getHeight(player), 10, 0);
		ObjectManager.spawnWithObject(2376,2258,5351, getHeight(player), 10, 0);
		ObjectManager.spawnWithObject(2376,2259,5351, getHeight(player), 10, 0);
		ObjectManager.spawnWithObject(2376,2259,5352, getHeight(player), 10, 0);
		ObjectManager.spawnWithObject(2376,2259,5353, getHeight(player), 10, 0);
		ObjectManager.spawnWithObject(2376,2258,5353, getHeight(player), 10, 0);
		ObjectManager.spawnWithObject(2376,2257,5353, getHeight(player), 10, 0);
/**
		// handle clipping
		ObjectManager.deleteWithObject(2270, 5336, getHeight(player));
		ObjectManager.deleteWithObject(2270, 5337, getHeight(player));
		ObjectManager.deleteWithObject(2270, 5338, getHeight(player));

		ObjectManager.deleteWithObject(2269, 5338, getHeight(player));
		ObjectManager.deleteWithObject(2268, 5338, getHeight(player));
		ObjectManager.deleteWithObject(2268, 5337, getHeight(player));
		ObjectManager.deleteWithObject(2268, 5336, getHeight(player));

		ObjectManager.deleteWithObject(2275, 5351, getHeight(player));
		ObjectManager.deleteWithObject(2275, 5352, getHeight(player));
		ObjectManager.deleteWithObject(2275, 5353, getHeight(player));

		ObjectManager.deleteWithObject(2274, 5353, getHeight(player));
		ObjectManager.deleteWithObject(2273, 5353, getHeight(player));
		ObjectManager.deleteWithObject(2273, 5352, getHeight(player));
		ObjectManager.deleteWithObject(2273, 5351, getHeight(player));
		ObjectManager.deleteWithObject(2274, 5351, getHeight(player));
		ObjectManager.deleteWithObject(2269, 5336, getHeight(player));
		**/
	}

	public void stop(Player player) {
		reward(player);
		player.teleport(new Location(2495, 5110, 0));
		player.sendMessage("@red@Congratulations on completing the Inferno! Enjoy your brand new cape!");
		player.waveInfo[player.infernoWaveType - 1] += 1;
		player.infernoWaveType = 0;
		player.infernoWaveId = 0;
		killAllSpawns(player);
		player.zukDead = false;
	}

	public void handleDeath(Player player) {
		player.teleport(new Location(2495, 5110, 0));
		player.send(new SendMessage(
				"@red@Unfortunately you died on wave " + player.infernoWaveId + ". Better luck next time."));
		player.infernoWaveType = 0;
		player.infernoWaveId = 0;
		killAllSpawns(player);
	}

	public void killAllSpawns(Player player) {
		for (Mob i : player.getInfernoDetails().getMobs()) {
			if (i != null) {
				i.remove();
			}
		}
		for (Mob i : player.getInfernoDetails().getPillars()) {
			if (i != null) {
				i.remove();
			}
		}

		player.getJadDetails().getMobs().clear();

		player.setController(ControllerManager.DEFAULT_CONTROLLER);
	}

	public void gamble(Player player) {
		if (!player.getInventory().hasItemId(INFERNAL_CAPE)) {
			player.send(new SendMessage("You do not have a infernal cape."));
			return;
		}
		player.getInventory().remove(INFERNAL_CAPE, 1);

		if (Utility.random(200) == 0) {
				World.sendGlobalMessage("[@red@PET@bla@] " + player.getUsername()
						+ "</col> received a pet from Inferno.");
				player.getInventory().addOrCreateGroundItem(13225, 1, true);
	
		} else {
			player.sendMessage("@red@You are not lucky.");
			//player.getDH().sendDialogues(73, 2180);
			return;
		}
	}

	private static final int[] REWARD_ITEMS = { 6571, 6528, 11128, 6523, 6524, 6525, 6526, 6527, 6568, 6523, 6524, 6525,
			6526, 6527, 6568 };

	public static final int FIRE_CAPE = 6570;
	public static final int INFERNAL_CAPE = 21295;
	public static final int ENRAGED_CAPE = 21296;
	public static final int ENSOULED_CAPE = 21297;

	public static final int TOKKUL = 6529;

	public void reward(Player player) {
		// Achievements.increase(player, AchievementType.FIGHT_CAVES_ROUNDS, 1);
		switch (player.infernoWaveType) {
		case 1:
			player.getInventory().addOrCreateGroundItem(INFERNAL_CAPE, 1, true);
			break;
		case 2:
			player.getInventory().addOrCreateGroundItem(ENSOULED_CAPE, 1, true);
			break;
		case 3:
			player.getInventory().addOrCreateGroundItem(ENRAGED_CAPE, 1, true);
			break;
		}
		player.getInventory().addOrCreateGroundItem(TOKKUL, (10000 * player.infernoWaveType) + Utility.random(5000),
				true);
	}

	public int getKillsRemaining() {
		return killsRemaining;
	}

	public void setKillsRemaining(int remaining) {
		this.killsRemaining = remaining;
	}

}
