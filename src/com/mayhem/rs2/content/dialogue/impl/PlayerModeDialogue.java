package com.mayhem.rs2.content.dialogue.impl;

import com.mayhem.core.util.FileHandler;
import com.mayhem.core.util.Utility;
import com.mayhem.rs2.content.PlayerTitle;
import com.mayhem.rs2.content.dialogue.Dialogue;
import com.mayhem.rs2.content.dialogue.DialogueConstants;
import com.mayhem.rs2.content.dialogue.DialogueManager;
import com.mayhem.rs2.content.dialogue.Emotion;
import com.mayhem.rs2.content.gambling.Gambling;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendEnterString;
import com.mayhem.rs2.entity.player.net.out.impl.SendInterface;
import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;
import com.mayhem.rs2.entity.player.net.out.impl.SendRemoveInterfaces;


public class PlayerModeDialogue extends Dialogue {
	
	public PlayerModeDialogue(Player player) {
		this.player = player;
	}

    @Override
    public boolean clickButton(int id) {
    
       switch (id) {
       
       case DialogueConstants.OPTIONS_4_2:
    	   player.setExtreme(false);
    	   player.send(new SendRemoveInterfaces());
    	   player.send(new SendInterface(3559));
    	   break;
    	   
       case DialogueConstants.OPTIONS_4_3:
    	   player.setExtreme(true);
    	   player.setPlayerTitle(PlayerTitle.create("Extreme", 0xB34700, false));
   		   player.setAppearanceUpdateRequired(true);
    	   player.send(new SendRemoveInterfaces());
    	   player.send(new SendInterface(3559));
    	   break;
    	   
       }
       
        return false;
    }

    @Override
    public void execute() {
    
       switch (next) {
       
       case 0:
    	   DialogueManager.sendStatement(player, "Please select your game mode.");
    	   next++;
    	   break;
    	   
       case 1:
    	   DialogueManager.sendOption(player, "","Normal Mode", "Extreme Mode (15% Drop Boost) (1/15 XP Rate)","");
    	   break;
    	     
       }
       
    }

}