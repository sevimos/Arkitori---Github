package com.mayhem.rs2.content.minigames.inferno;

import java.util.ArrayList;
import java.util.List;

import com.mayhem.rs2.entity.mob.Mob;
import com.mayhem.rs2.entity.player.Player;

public final class InfernoDetails {
	
	private int stage = 0;
	private List<Mob> mobs = new ArrayList<Mob>();
	private Mob boss;
	private List<Mob> pillars = new ArrayList<Mob>();
	private Mob glyph;
	private int z;

	public void addNpc(Mob mob) {
		mobs.add(mob);
	}

	public int getKillAmount() {
		return mobs.size();
	}

	public List<Mob> getMobs() {
		return mobs;
	}
	public void addPillar(Mob mob) {
		pillars.add(mob);
	}

	public void addGlyph(Mob mob) {
		glyph=mob;
	}
	public void addBoss(Mob zuk) {
		boss=zuk;
	}
	public List<Mob> getPillars() {
		return pillars;
	}
	public Mob getGlyph() {
		return glyph;
	}
	
	public Mob getBoss() {
		return boss;
	}


	public int getStage() {
		return stage;
	}

	public int getZ() {
		return z;
	}

	public void increaseStage() {
		stage += 1;
	}

	public boolean removeNpc(Mob mob) {
		int index = mobs.indexOf(mob);

		if (index == -1) {
			return false;
		}

		mobs.remove(mob);
		return true;
	}

	public void reset() {
		stage = 0;
	}


	public void setStage(int stage) {
		this.stage = stage;
	}

	public void setZ(Player p) {
		z = (p.getIndex() * 4);
	}

}
