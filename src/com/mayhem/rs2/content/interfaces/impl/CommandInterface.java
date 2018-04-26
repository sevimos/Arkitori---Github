package com.mayhem.rs2.content.interfaces.impl;

import com.mayhem.rs2.content.interfaces.InterfaceHandler;
import com.mayhem.rs2.entity.player.Player;

public class CommandInterface extends InterfaceHandler {

	public CommandInterface(Player player) {
		super(player);
	}

	private final String[] text = {
			"::players - shows amount of active players",
			"::vote - opens the voting link",
			"::store - opens the store link",
			"::forum - opens the forum link",
			"::changepassword - changes password",
			"::yell - does a global yell(<img=4>Members only)",
			"::empty - deletes inventory",
			"::home - teleports home",
			"::teleport - opens the teleporting menu",
			"::dz - takes you to regular donorzone",
			"::sdz - take you to super donorzone",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",			

	};

	@Override
	protected String[] text() {
		return text;
	}

	@Override
	protected int startingLine() {
		return 8145;
	}
	
}
