package com.mayhem.rs2.content.minigames.inferno;


import com.mayhem.Server;
import com.mayhem.core.task.Task;
import com.mayhem.core.task.TaskQueue;
import com.mayhem.core.task.Task.BreakType;
import com.mayhem.core.task.Task.StackType;
import com.mayhem.core.task.impl.TaskIdentifier;
import com.mayhem.core.util.Utility;
import com.mayhem.rs2.entity.Location;
import com.mayhem.rs2.entity.mob.Mob;
import com.mayhem.rs2.entity.object.ObjectManager;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

public class Tzkalzuk  {

	/**
	 * Player variables, start coordinates.
	 */
	private static final int START_X = 2271, START_Y = 5358;
	
	/**
	 * Npc variables, start coordinates.
	 */
	public static final int SPAWN_X = 2268, SPAWN_Y = 5365;
	
	public static final int GLYPH_SPAWN_X = 2271, GLYPH_SPAWN_Y = 5361;
	
	public boolean glyphCanMove, glyphMoveLeft, glyphMoveRight, singleJad, singleRanger, singleMager, cutsceneWalkBlock = false;
	
	
	//public void tzkalzukSpecials() {
		
	//	int random = Utility.random(11);
		

	
	/**
	public void initiateTzkalzuk() {
		
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (container.getOwner() == null || player == null || player.isDead || player.getInferno() == null) {
					player.sendMessage("tits");
					container.stop();
					return;
				}
				int cycle = container.getTotalTicks();
				player.sendMessage("tick "+cycle);
				if (cycle == 1) {
					cutsceneWalkBlock = true;
					player.getPA().sendScreenFade("TzKal-Zuk instance loading...", -1, 5);
					player.getPA().movePlayer(START_X, START_Y, height + 1);
				} else if (cycle == 2) {
					player.turnPlayerTo(START_X, SPAWN_Y);
				} else if (cycle == 3) {
					Server.getGlobalObjects().add(new GlobalObject(-1, 2267, 5368, (height + 1), 0, 10, -1, -1));  // Delete ceiling
					player.getPA().movePlayer(START_X, START_Y, height);
					Server.npcHandler.spawnNpc(player, InfernoWave.ANCESTRAL_GLYPH, GLYPH_SPAWN_X, GLYPH_SPAWN_Y, height, 0, 450, 38, 500, 700, false, false);
				} else if (cycle == 4) {
					Server.getGlobalObjects().add(new GlobalObject(30342, 2267, 5366, height, 1, 10, -1, -1));  // West Wall
					Server.getGlobalObjects().add(new GlobalObject(30341, 2275, 5366, height, 3, 10, -1, -1));  // East Wall
					Server.getGlobalObjects().add(new GlobalObject(30340, 2267, 5364, height, 1, 10, -1, -1));  // West Corner
					Server.getGlobalObjects().add(new GlobalObject(30339, 2275, 5364, height, 3, 10, -1, -1));  // East Corner
					Server.getGlobalObjects().add(new GlobalObject(30344, 2268, 5364, height, 3, 10, -1, -1)); // Set falling rocks - west
					Server.getGlobalObjects().add(new GlobalObject(30343, 2273, 5364, height, 3, 10, -1, -1)); // Set falling rocks - east
					Server.getGlobalObjects().add(new GlobalObject(-1, 2270, 5363, height, 3, 10, -1, -1)); // Delete ancestral glyph
					player.getPA().stillCamera(2271, 5365, 2, 0, 10);
				} else if (cycle == 10) {
					player.getPA().sendPlayerObjectAnimation(player, 2268, 5364, 7560, 10, 3, height); // Set falling rocks animation - west
					player.getPA().sendPlayerObjectAnimation(player, 2273, 5364, 7559, 10, 3, height); // Set falling rocks animation - east
					//player.getPA().sendPlayerObjectAnimation(player, 2270, 5363, 7560, 10, 3, height); // Set falling rocks animation - middle
					spawnNpcs();
					player.sendMessage("error");
				} else if (cycle >= 14) {
					player.getPA().resetCamera();
					cutsceneWalkBlock = false;
					glyphCanMove = true;
					player.sendMessage("Stop");
					container.stop();
				}
			}
		}, 1);
	}
	**/
public static void initiateTzkalzuk1(Player player) {
		
	TaskQueue.queue(new Task(player, 20, false, StackType.NEVER_STACK, BreakType.NEVER, TaskIdentifier.TZHAAR) {
		@Override
		public void execute() {
			// int index = Utility.random(InfernoWave.SPAWN_DATA.length - 1);

			// int random = Utility.random(10);
				//int cycle = player.time
				///player.sendMessage("tick "+cycle);-1, 2267, 5368, (height + 1)
			
			int height = player.getInferno().getHeight(player);
				Mob boss = new Mob(player, InfernoWave.TZKAL_ZUK, false, false, false, new Location(SPAWN_X, SPAWN_Y, player.getInferno().getHeight(player)));
				Mob glyph = new Mob(player, InfernoWave.ANCESTRAL_GLYPH, true, false, false, new Location(GLYPH_SPAWN_X, GLYPH_SPAWN_Y,player.getInferno().getHeight(player)));
				player.glyph= glyph;
				player.getInfernoDetails().addNpc(boss);
				player.getInfernoDetails().addNpc(glyph);
				player.getInfernoDetails().addGlyph(glyph);
				player.getInfernoDetails().addBoss(boss);
				boss.getCombat().setAttack(player);
				
				
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



}
