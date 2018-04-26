package com.mayhem.rs2.content.minigames.warriorsguild;

import com.mayhem.core.task.Task;
import com.mayhem.core.task.impl.TaskIdentifier;
import com.mayhem.rs2.entity.Location;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.controllers.ControllerManager;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

public class TokenTask extends Task {

	private Player player;

	public TokenTask(Player player, int delay) {
		super(player, delay, false, StackType.STACK, BreakType.NEVER, TaskIdentifier.CURRENT_ACTION);
		this.player = player;
	}

	public static final Location NO_TOKENS = new Location(2846, 3540, 2);

	@Override
	public void execute() {
		if (!player.inCyclops()) {
			stop();
			return;
		}
	
		player.getInventory().remove(8851, 10);

		player.getAttributes().set("warrguildtokensused", player.getAttributes().getInt("warrguildtokensused") + 10);
		player.send(new SendMessage("@red@25 tokens were taken away from you."));
		CyclopsRoom.updateInterface(player);
		if (player.getInventory().getItemAmount(8851) < 10) {
			player.teleport(NO_TOKENS);
			player.getAttributes().remove("cyclopsdefenderdrop");
			player.getAttributes().remove("warrguildtokentask");
			player.send(new SendMessage("@red@You have ran out of tokens!"));
			stop();
		}
	}

	@Override
	public void onStop() {
		player.setController(ControllerManager.DEFAULT_CONTROLLER);
	}
}
