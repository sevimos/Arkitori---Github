package com.mayhem.rs2.content;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.mayhem.core.task.Task;
import com.mayhem.core.task.TaskQueue;
import com.mayhem.core.util.Utility;
import com.mayhem.rs2.entity.World;

/**
 * Handles the global messages
 * @author Daniel
 *
 */
public class GlobalMessages {
	
	/**
	 * The logger for the class
	 */
	private static Logger logger = Logger.getLogger(GlobalMessages.class.getSimpleName());

	/**
	 * The news color text
	 */
	private static String newsColor = "<col=013B4F>";

	/**
	 * The news icon
	 */
	private static String iconNumber = "<img=8>";
	
	/**
	 * Holds all the announcements in a arraylist
	 */
	public final static ArrayList<String> announcements = new ArrayList<String>();

	/**
	 * The random messages that news will send
	 */
	public static final String[] ANNOUNCEMENTS = { 
		"The new Ancient Warriors boss drop the Korasi!",
		"Want to help us grow? Vote every 12 hours, you will also get a reward!",
		"Considering membership? Members have access to many custom content!",
		"Check out our forums for the latest news & updates at ::forums!",
		"Do you have an interesting idea? Suggest it to us on forums!",
		"Found a bug? Report it on forums and we will fix it for you!",
		"Kill Crystal monsters for Vesta & Statius!",
		"Check out the Skill points shop to spend your Skill points.",
		"You can kill rock crabs for a chance at a pet rock crab. Easy for new players!",
		"The Abyssal demon now has a rare chance of dropping the Abyssal Orphan pet!!!",
		"All RFD Bosses have their own pets. Think you can get one?",
	};
	
	/**
	 * Declares all the announcements
	 */
	public static void declare() {
		for (int i = 0; i < ANNOUNCEMENTS.length; i++) {
			announcements.add(ANNOUNCEMENTS[i]);
		}
		logger.info(Utility.format(announcements.size()) + " Announcements have been loaded successfully.");
	}

	/**
	 * Initializes the task
	 */
	public static void initialize() {
		TaskQueue.queue(new Task(250, false) {
			@Override
			public void execute() {
				final String announcement = Utility.randomElement(announcements);
				World.sendGlobalMessage(iconNumber + newsColor + " " + announcement);
			}

			@Override
			public void onStop() {
			}
		});
	}
	
}
