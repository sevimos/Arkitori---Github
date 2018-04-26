package com.mayhem.rs2.content.minigames.inferno;

import com.mayhem.core.util.Utility;

public class InfernoWave {

	public static int[][] SPAWN_DATA = { { 2403, 5079 }, { 2396, 5074 }, { 2387, 5072 }, { 2388, 5085 }, { 2389, 5096 }, { 2403, 5097 }, { 2410, 5087 } };

	public static final int 
	JAL_NIB = 7691, 
	JAL_MEJRAH = 7692, 
	JAL_AK = 7693, 
	JAL_AKREK_MEJ = 7694,
	JAL_AKREK_XIL = 7695,
	JAL_AKREK_KET = 7696,
	JAL_IMKOT = 7697, 
	JAL_XIL = 7702,
	JAL_ZEK = 7699, 
	JALTOK_JAD = 7700, 
	YT_HURKOT = 7705,
	TZKAL_ZUK = 7706,
	ANCESTRAL_GLYPH = 7707,
	JAL_MEJJAK = 7708;

	public static int getHp(int npc) {
		switch (npc) {
		case JAL_NIB:
		case JAL_AKREK_XIL:
		case JAL_AKREK_MEJ:
			return 15;
		case JAL_MEJRAH:
			return 25;
		case JAL_AK:
			return 40;
		case YT_HURKOT:
			return 60;
		case JAL_IMKOT:
			return 75;
		case JAL_MEJJAK:
			return 80;
		case JAL_XIL:
			return 130;
		case JAL_ZEK:
			return 220;
		case JALTOK_JAD:
			return 350;
		case TZKAL_ZUK:
			return 1200;
		}
		return 50 + Utility.random(50);
	}

	public static int getMax(int npc) {
		switch (npc) {
		case JAL_NIB:
			return 2;
		case JAL_MEJJAK:
			return 10;
		case YT_HURKOT:
			return 14;
		case JAL_AKREK_XIL:
		case JAL_AKREK_MEJ:
			return 18;
		case JAL_MEJRAH:
			return 19;
		case JAL_AK:
			return 29;
		case JAL_XIL:
			return 46;
		case JAL_IMKOT:
			return 49;
		case JAL_ZEK:
			return 70;
		case JALTOK_JAD:
			return 113;
		case TZKAL_ZUK:
			return 251;
		}
		return 5 + Utility.random(5);
	}

	public static int getAtk(int npc) {
		switch (npc) {
		case JAL_NIB:
		case JAL_MEJJAK:
		case JAL_AKREK_XIL:
			return 1;
		case YT_HURKOT:
			return 140;
		case JAL_MEJRAH:
			return 0;
		case JAL_AK:
			return 160;
		case JAL_XIL:
			return 140;
		case JAL_IMKOT:
			return 49;
		case JAL_ZEK:
			return 370;
		case JALTOK_JAD:
			return 750;
		case TZKAL_ZUK:
			return 350;
		}
		return 50 + Utility.random(50);
	}

	public static int getDef(int npc) {
		switch (npc) {
		case JAL_NIB:
			return 15;
		case JAL_MEJJAK:
			return 100;
		case YT_HURKOT:
		case JAL_XIL:
			return 60;
		case JAL_MEJRAH:
			return 55;
		case JAL_AK:
		case JAL_AKREK_XIL:
			return 95;
		case JAL_IMKOT:
			return 49;
		case JAL_ZEK:
			return 260;
		case JALTOK_JAD:
			return 480;
		case TZKAL_ZUK:
			return 260;
		}
		return 50 + Utility.random(50);
	}

public static enum InfernoData{
		WAVE_1(new short[] {JAL_NIB, JAL_NIB, JAL_NIB, JAL_MEJRAH}), //1
		WAVE_2(new short[] {JAL_NIB, JAL_NIB, JAL_NIB, JAL_MEJRAH, JAL_MEJRAH}), 
		WAVE_3(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_NIB, JAL_NIB, JAL_NIB}), 
		WAVE_4(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_AK}), 
		WAVE_5(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_AK, JAL_MEJRAH}), //5
		WAVE_6(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_AK, JAL_MEJRAH, JAL_MEJRAH}), 
		WAVE_7(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_AK, JAL_AK}),
		WAVE_8(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_NIB, JAL_NIB}), 
		WAVE_9(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_IMKOT}), 
		WAVE_10(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_IMKOT, JAL_MEJRAH}), //10
		WAVE_11(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_IMKOT, JAL_MEJRAH, JAL_MEJRAH}), 
		WAVE_12(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_IMKOT, JAL_MEJRAH}), 
		WAVE_13(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_IMKOT, JAL_MEJRAH, JAL_AK}),
		WAVE_14(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_IMKOT, JAL_MEJRAH, JAL_MEJRAH, JAL_AK}),
		WAVE_15(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_AK, JAL_AK, JAL_IMKOT}), //15 -
		WAVE_16(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_IMKOT, JAL_IMKOT}), 
		WAVE_17(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_NIB, JAL_NIB }), 
		WAVE_18(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL}), 
		WAVE_19(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_MEJRAH}),
		WAVE_20(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_MEJRAH, JAL_MEJRAH}), //20
		WAVE_21(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_AK}), 
		WAVE_22(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_AK, JAL_MEJRAH}),
		WAVE_23(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_MEJRAH, JAL_MEJRAH, JAL_AK}), 
		WAVE_24(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_AK, JAL_AK}),
		WAVE_25(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_IMKOT}), //25 
		WAVE_26(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_IMKOT, JAL_MEJRAH}), 
		WAVE_27(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_MEJRAH, JAL_MEJRAH, JAL_IMKOT}), 
		WAVE_28(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_IMKOT, JAL_AK}),
		WAVE_29(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_AK, JAL_IMKOT}), 
		WAVE_30(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_IMKOT, JAL_MEJRAH, JAL_MEJRAH, JAL_AK}), //30
		WAVE_31(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_AK, JAL_AK, JAL_IMKOT}), 
		WAVE_32(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_IMKOT, JAL_IMKOT}), 
		WAVE_33(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_XIL}), 
		WAVE_34(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_NIB, JAL_NIB}),
		WAVE_35(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK}), //35
		WAVE_36(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH}), 
		WAVE_37(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_MEJRAH}), 
		WAVE_38(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_AK}), 
		WAVE_39(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_AK, JAL_MEJRAH}),
		WAVE_40(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_MEJRAH, JAL_AK}), //40
		WAVE_41(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_AK, JAL_AK}), 
		WAVE_42(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_IMKOT}), 
		WAVE_43(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_IMKOT, JAL_MEJRAH}),
		WAVE_44(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_MEJRAH, JAL_IMKOT}), 
		WAVE_45(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_IMKOT, JAL_AK}), //45
		WAVE_46(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_AK, JAL_IMKOT}), 
		WAVE_47(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_MEJRAH, JAL_AK, JAL_IMKOT}), 
		WAVE_48(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_AK, JAL_AK, JAL_IMKOT}),
		WAVE_49(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_IMKOT, JAL_IMKOT}), 
		WAVE_50(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_XIL}), //50
		WAVE_51(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_XIL}), 
		WAVE_52(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_XIL, JAL_MEJRAH, JAL_MEJRAH}),
		WAVE_53(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_XIL, JAL_AK}), 
		WAVE_54(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_XIL, JAL_AK}), 
		WAVE_55(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_MEJRAH, JAL_AK, JAL_XIL}), //55
		WAVE_56(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_AK, JAL_AK, JAL_XIL}),
		WAVE_57(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_ZEK, JAL_IMKOT}), 
		WAVE_58(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_IMKOT, JAL_XIL}), 
		WAVE_59(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_MEJRAH, JAL_IMKOT, JAL_XIL}),
		WAVE_60(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_AK, JAL_IMKOT, JAL_XIL}), //60
		WAVE_61(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_AK, JAL_IMKOT, JAL_XIL}), 
		WAVE_62(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_MEJRAH, JAL_AK, JAL_IMKOT, JAL_XIL}), 
		WAVE_63(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_AK, JAL_AK, JAL_IMKOT, JAL_XIL}), 
		WAVE_64(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_IMKOT, JAL_IMKOT, JAL_XIL}), 
		WAVE_65(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_XIL, JAL_XIL}), //65
		WAVE_66(new short[]{JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_ZEK, JAL_ZEK}),
		WAVE_67(new short[]{JALTOK_JAD}),
		WAVE_68(new short[]{JALTOK_JAD, JALTOK_JAD, JALTOK_JAD});
		
		private short[] npcs;

		private InfernoData(short[] npcs) {
			this.npcs = npcs;
		}

		public short[] getNpcs() {
			return npcs;
		}

		public int toInteger() {
			return ordinal();
		}
		
}
		

}
