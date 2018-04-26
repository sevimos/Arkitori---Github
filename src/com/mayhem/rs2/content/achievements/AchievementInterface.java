package com.mayhem.rs2.content.achievements;

import java.util.Arrays;
import java.util.List;

import com.mayhem.core.util.Utility;
import com.mayhem.rs2.content.achievements.AchievementHandler.AchievementDifficulty;
import com.mayhem.rs2.content.interfaces.InterfaceHandler;
import com.mayhem.rs2.entity.player.Player;
import com.mayhem.rs2.entity.player.net.out.impl.SendBanner;
import com.mayhem.rs2.entity.player.net.out.impl.SendColor;
import com.mayhem.rs2.entity.player.net.out.impl.SendConfig;
import com.mayhem.rs2.entity.player.net.out.impl.SendScrollInterface;
import com.mayhem.rs2.entity.player.net.out.impl.SendString;
import com.sun.xml.internal.ws.util.StringUtils;



/**
 * Handles the achievement interfaces
 * 
 * @author Daniel
 * @author Michael
 */
public class AchievementInterface extends InterfaceHandler {

	/**
	 * Sends the achievement completion interface
	 * 
	 * @param player
	 * @param achievement
	 */
	public static void sendCompleteInterface(final Player player, final AchievementList achievement) {
		int color = 0;

		switch (achievement.getDifficulty()) {
		case EASY:
			color = 0x1C889E;
			break;
		case MEDIUM:
			color = 0xD9750B;
			break;
		case HARD:
			color = 0xC41414;
			break;
		}

		//player.send(new SendBanner("You've completed an achievement!", achievement.getName(), color));
		player.send(new SendBanner("You've completed the achievement: " + achievement.getName() + "!", color));

	}

	/**
	 * Sends the achievement information interface
	 * 
	 * @param player
	 * @param achievement
	 */
	public static void sendInterfaceForAchievement(final Player player, AchievementList achievement) {
		final String difficulty = StringUtils.capitalize(achievement.getDifficulty().name().toLowerCase());
		final int completed = player.getPlayerAchievements().get(achievement);
		final int progress = (int) (completed * 100 / (double) achievement.getCompleteAmount());
		player.send(new SendString("<col=ff9040>" + achievement.getName(), 35006));
		player.send(new SendString("<col=ff7000>" + achievement.getDescription(), 35008));
		player.send(new SendString("<col=ff7000>" + difficulty, 35010));
		player.send(new SendString("<col=ff7000>" + Utility.format(completed) + " / " + Utility.format(achievement.getCompleteAmount()) + " ( " + progress + "% )", 35012));
		player.send(new SendString("<col=ff7000>" + achievement.getReward() + " achievement point" + (achievement.getReward() == 1 ? "" : "s") + ".", 35014));
		boolean isCompleted = completed >= achievement.getCompleteAmount();
		player.send(new SendConfig(694, isCompleted ? 1 : 0));
	}

	private final String[] text;

	public AchievementInterface(Player player, AchievementDifficulty difficulty) {
		super(player);
		int shift = 0;

		final int total = AchievementList.values().length;
		
		switch (difficulty) {
		case EASY:
			player.send(new SendScrollInterface(35030, 0));
			break;
		case MEDIUM:
			player.send(new SendScrollInterface(35030, 0));
			break;
		case HARD:
			player.send(new SendScrollInterface(35030, 0));
			break;
		}

		player.send(new SendString("</col>Completed: <col=65280>" + player.getPA().achievementCompleted() + "</col>/" + total, 35015));
		player.send(new SendString("</col>Points: <col=65280>" + player.getAchievementsPoints(), 35016));

		final List<AchievementList> list = AchievementList.asList(difficulty);

		text = new String[total];

		Arrays.fill(text, "");

		for (final AchievementList achievement : list) {
			int completed = player.getPlayerAchievements().get(achievement);
			if (completed > achievement.getCompleteAmount()) {
				completed = achievement.getCompleteAmount();
			}
			
			int color = completed == achievement.getCompleteAmount() ? 0x00FF00 : completed > 0 ? 0xFFFF00 : 0xFF0000;
			
			player.send(new SendColor(startingLine() + shift, color));
			
			text[shift++] = " " + achievement.getName();
		}
	}

	public String getColor(int amount, int max) {
		if (amount == 0) {
			return "<col=FF0000>";
		}
		if (amount >= max) {
			return "<col=00FF00>";
		}
		return "<col=FFFF00>";
	}

	@Override
	protected int startingLine() {
		return 35031;
	}

	@Override
	protected String[] text() {
		return text;
	}

}