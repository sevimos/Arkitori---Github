package com.mayhem.rs2.entity.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.mayhem.Server;
import com.mayhem.Constants;
import com.mayhem.core.cache.map.Region;
import com.mayhem.core.network.StreamBuffer;
import com.mayhem.core.network.mysql.ExtremeHiscores;
import com.mayhem.core.network.mysql.Highscores;
import com.mayhem.core.network.mysql.IronmanHiscores;
import com.mayhem.core.network.mysql.UltimateHiscores;
import com.mayhem.core.task.Task;
import com.mayhem.core.task.TaskQueue;
import com.mayhem.core.task.impl.FinishTeleportingTask;
import com.mayhem.core.util.FileHandler;
import com.mayhem.core.util.NameUtil;
import com.mayhem.core.util.Utility;
import com.mayhem.core.util.Utility.Stopwatch;
import com.mayhem.rs2.content.Emotes;
import com.mayhem.rs2.content.ExtremeMysterySpin;
import com.mayhem.rs2.content.Inventory;
import com.mayhem.rs2.content.LootingBag;
import com.mayhem.rs2.content.LoyaltyManager;
import com.mayhem.rs2.content.LoyaltyShop;
import com.mayhem.rs2.content.MoneyPouch;
import com.mayhem.rs2.content.MysterySpin;
import com.mayhem.rs2.content.PlayerProperties;
import com.mayhem.rs2.content.PlayerTitle;
import com.mayhem.rs2.content.PriceChecker;
import com.mayhem.rs2.content.PrivateMessaging;
//import com.mayhem.rs2.content.Referral;
import com.mayhem.rs2.content.RunEnergy;
import com.mayhem.rs2.content.achievements.AchievementHandler;
import com.mayhem.rs2.content.achievements.AchievementList;
import com.mayhem.rs2.content.achievements.AchievementHandler.AchievementDifficulty;
import com.mayhem.rs2.content.bank.Bank;
import com.mayhem.rs2.content.clanchat.Clan;
import com.mayhem.rs2.content.combat.CombatInterface;
import com.mayhem.rs2.content.combat.Hit;
import com.mayhem.rs2.content.combat.PlayerCombatInterface;
import com.mayhem.rs2.content.combat.Combat.CombatTypes;
import com.mayhem.rs2.content.combat.impl.Skulling;
import com.mayhem.rs2.content.combat.impl.SpecialAttack;
import com.mayhem.rs2.content.consumables.Consumables;
import com.mayhem.rs2.content.dialogue.Dialogue;
import com.mayhem.rs2.content.dialogue.impl.Tutorial;
import com.mayhem.rs2.content.dwarfcannon.DwarfMultiCannon;
import com.mayhem.rs2.content.event.EventManager;
import com.mayhem.rs2.content.houses.HouseManager;
import com.mayhem.rs2.content.interfaces.InterfaceHandler;
//import com.mayhem.rs2.content.interfaces.impl.CreditTab;
import com.mayhem.rs2.content.interfaces.impl.MiscInterfaces;
import com.mayhem.rs2.content.interfaces.impl.QuestTab;
import com.mayhem.rs2.content.interfaces.impl.QuestTab.TabType;
import com.mayhem.rs2.content.interfaces.impl.oldQuestTab;
import com.mayhem.rs2.content.io.PlayerSave;
import com.mayhem.rs2.content.io.PlayerSaveUtil;
import com.mayhem.rs2.content.membership.CreditPurchase;
import com.mayhem.rs2.content.minigames.PlayerMinigames;
import com.mayhem.rs2.content.minigames.barrows.Barrows;
import com.mayhem.rs2.content.minigames.barrows.Barrows.Brother;
import com.mayhem.rs2.content.minigames.duelarena.Dueling;
import com.mayhem.rs2.content.minigames.fightcave.TzharrDetails;
import com.mayhem.rs2.content.minigames.fightcave.TzharrGame;
import com.mayhem.rs2.content.minigames.godwars.GodWarsData;
import com.mayhem.rs2.content.minigames.godwars.GodWarsData.GodWarsNpc;
import com.mayhem.rs2.content.minigames.inferno.Inferno;
import com.mayhem.rs2.content.minigames.inferno.InfernoDetails;
import com.mayhem.rs2.content.minigames.weapongame.WeaponGame;
import com.mayhem.rs2.content.pets.BossPets;
import com.mayhem.rs2.content.quest.QuestManager;
import com.mayhem.rs2.content.shopping.Shopping;
import com.mayhem.rs2.content.skill.Skill;
import com.mayhem.rs2.content.skill.Skills;
import com.mayhem.rs2.content.skill.farming.Farming;
import com.mayhem.rs2.content.skill.fishing.Fishing;
import com.mayhem.rs2.content.skill.magic.MagicSkill;
import com.mayhem.rs2.content.skill.magic.weapons.TridentOfTheSeas;
import com.mayhem.rs2.content.skill.magic.weapons.TridentOfTheSwamp;
import com.mayhem.rs2.content.skill.melee.Melee;
import com.mayhem.rs2.content.skill.melee.SerpentineHelmet;
import com.mayhem.rs2.content.skill.prayer.PrayerBook;
import com.mayhem.rs2.content.skill.prayer.PrayerBook.Prayer;
import com.mayhem.rs2.content.skill.ranged.RangedSkill;
import com.mayhem.rs2.content.skill.ranged.ToxicBlowpipe;
import com.mayhem.rs2.content.skill.slayer.Slayer;
import com.mayhem.rs2.content.skill.summoning.Summoning;
import com.mayhem.rs2.content.trading.Trade;
import com.mayhem.rs2.content.wilderness.TargetSystem;
import com.mayhem.rs2.entity.Entity;
import com.mayhem.rs2.entity.InterfaceManager;
import com.mayhem.rs2.entity.Location;
import com.mayhem.rs2.entity.World;
import com.mayhem.rs2.entity.following.Following;
import com.mayhem.rs2.entity.following.PlayerFollowing;
import com.mayhem.rs2.entity.item.Equipment;
import com.mayhem.rs2.entity.item.EquipmentConstants;
import com.mayhem.rs2.entity.item.ItemDegrading;
import com.mayhem.rs2.entity.item.impl.LocalGroundItems;
import com.mayhem.rs2.entity.mob.Mob;
import com.mayhem.rs2.entity.mob.MobConstants;
import com.mayhem.rs2.entity.mob.RareDropEP;
import com.mayhem.rs2.entity.movement.MovementHandler;
import com.mayhem.rs2.entity.movement.PlayerMovementHandler;
import com.mayhem.rs2.entity.object.LocalObjects;
import com.mayhem.rs2.entity.player.controllers.Controller;
import com.mayhem.rs2.entity.player.controllers.ControllerManager;
import com.mayhem.rs2.entity.player.net.Client;
import com.mayhem.rs2.entity.player.net.in.impl.ChangeAppearancePacket;
import com.mayhem.rs2.entity.player.net.out.OutgoingPacket;
import com.mayhem.rs2.entity.player.net.out.impl.*;


import com.mayhem.rs2.content.EloRatingSystem;
//import com.mayhem.core.network.mysql.Highscores;

public class Player extends Entity {
	
	public boolean isWoodcutting = false;
	
	
	public boolean isInCutScene = false;
	
	public int movingLocation = 0;
	
	public int lastLocation = 0;
	
	public boolean isMoving = false;
	public Mob glyph;
	

	/* Client */
	private final Client client;
	
	/* Quest */
	public int[] questStatus = {0, 0, 0, 0, 0};//WP, ST, AMP, TFP, DS
	public int[] pointRewards = {1, 2, 3, 3};
	public short questPoints = 0;
	
	public void calculateQuestPoints(){
		for(int i = 0; i < questStatus.length; i++){
			if(questStatus[i] == -2){
				questPoints += pointRewards[i];
			}
		}
	}
	
	/**
	 * A set of hits done in this entity during the current update cycle.
	 */
	private final List<Hit> hits = new LinkedList<Hit>();

	
	/**
	 * Gets the hit queue.
	 * @return The hit queue.
	 */
	public List<Hit> getHitQueue() {
		return hits;
	}
	/* Stopwatch used for delaying anything */
	private Stopwatch delay = new Stopwatch();
	
	private boolean prestigeColors;
	
	
	
	//Inferno
	public int infernoWaveId = 0;
	public int infernoWaveType = 0;
	public boolean zukDead = false;
	private ExtremeMysterySpin extremeBox= new ExtremeMysterySpin(this);
	
	public ExtremeMysterySpin getExtremeSpin() {
		return extremeBox;
	}
private MysterySpin donorBox= new MysterySpin(this);
	
	public MysterySpin getMysterySpin() {
		return donorBox;
	}
	public int boxCurrentlyUsing = -1;
	
	/* Account Security */
	private String fullName = "";
	private String emailAddress = "";
	private String recovery = "";
	private String IP = "";
	public boolean nameSet = false;
	public boolean emailSet = false;
	public boolean recoverySet = false;
	public boolean ipSet = false;
	
	/* Uids */
	private String uid;
	private String lastKnownUID;
	
	/* Drop Table */
	public int monsterSelected = 0;
	
	private boolean hitZulrah;
	public boolean playingMB = false;
	
	/* Weapon Game */
	private int weaponKills;
	public int weaponPoints;
	
	/* Amulet of Recall */
	public Location recall = null;
	
	/* Report */
	public long lastReport = 0;
	public String lastReported = "";
	public String reportName = "";
	public int reportClicked = 0;
		
	/* Iron Man */
	private boolean isIron = false;
	private boolean isUltimateIron = false;
	private boolean isMember = false;
	private boolean isExtreme = false;

	public boolean ironPlayer() {
		if (this.isIron()) {
			return true;
		}
		if (this.isUltimateIron()) {
			return true;
		}
		return false;
	}
	
	/* Money Pouch */
	private long moneyPouch;
	private boolean pouchPayment;
	
	public boolean alreadySpawned = false;
	
	/*Dice bag */
	public long diceDelay;
	
	public boolean payment(int amount) {
		if (isPouchPayment()) {
			if (getMoneyPouch() < amount) {
				send(new SendMessage("You do not have enough coins in your pouch to do this!"));
				return false;
			}
			setMoneyPouch(getMoneyPouch() - amount);
			send(new SendString(getMoneyPouch() + "", 8135));
			return true;
		} else {
			if (!getInventory().hasItemAmount(995, amount)) {
				send(new SendMessage("You do not have enough coins to do this!"));
				return false;
			}
			getInventory().remove(995, amount);
			return true;
		}
	}

	
	/* Slayer helmet */
	public boolean slayerHelmetEffect;
	
	/* Elo Rating */
	public Integer eloRating = EloRatingSystem.DEFAULT_ELO_START_RATING;
	
	/* Delay */
	public long shopDelay;
	public long tradeDelay;

	/* Mage Arena Points */
	private int arenaPoints;

	/* TriviaBot variable */
	private int triviaWinningStreak;
	private boolean wantsTrivia;
	private boolean Notification;

	/* Player Profiler */
	private long lastLike;
	private byte likesGiven;
	private int likes, dislikes, profileViews;
	public String viewing;

	public List<PlayerTitle> unlockedTitles = new ArrayList<>();
	
	/* Kraken */
	public int whirlpoolsHit = 0;
	public List<Mob> tentacles = new ArrayList<>();

	/* Teleport variable */
	private int teleportTo;
	public boolean homeTeleporting;

	/* The achievement variables */
	private HashMap<AchievementList, Integer> playerAchievements = new HashMap<AchievementList, Integer>(AchievementList.values().length) {
		{
			for (AchievementList achievement : AchievementList.values()) {
				put(achievement, 0);
			}
		}

		private static final long serialVersionUID = -4629357800141530574L;
	};

	/* The credits variables */
	private Set<CreditPurchase> unlockedCredits = new HashSet<CreditPurchase>(CreditPurchase.values().length);;

	/* Wilderness variables */
	public String targetName = "";
	public int targetIndex;
	private int bountyPoints;
	public int skillPoints;
	public int triviaPoints;
	public int bossPoints;
	public int pvmPoints;
	public int thievePoints;
	public int farmingPoints;
	public int woodcuttingPoints;
	public int firemakingPoints;
	public int cookingPoints;
	public int fishingPoints;
	public int smithingPoints;
	public int miningPoints;
	public int fletchingPoints;
	public int craftingPoints;
	public int herblorePoints;
	public int puroPoints;
	public int runecraftingPoints;
	public int prayerPoints;

	/* PvP variables */
	private ArrayList<String> lastKilledPlayers = new ArrayList<String>();
	private int kills = 0;
	private int deaths = 0;
	private int rogueKills = 0;
	private int rogueRecord = 0;
	private int hunterKills = 0;
	private int hunterRecord = 0;

	/* Player Profiler */
	private boolean profilePrivacy;

	/* Pets */
	private Mob bossPet;
	private int bossID;

	/* Credits */
	private int moneySpent;
	private int credits;

	/* Prestige variable */
	private int totalPrestiges;
	public int prestigePoints;
	private int[] skillPrestiges = new int[Skills.SKILL_COUNT];

	private int[] cluesCompleted = new int[4];

	/* Bank */
	public boolean enteredPin = false;
	private String pin;

	/* Shopping variables */
	private String shopMotto;
	private String shopColor;
	private long shopCollection;

	/* Thieving variable */
	public boolean isCracking;

	/* Clan Chat variables */
	public Clan clan;
	public String lastClanChat = "";

	public Clan getClan() {
		if (Server.clanManager.clanExists(getUsername())) {
			return Server.clanManager.getClan(getUsername());
		}
		return null;
	}

	private AccountSecurity accountSecurity = new AccountSecurity(this);

	private Location currentRegion = new Location(0, 0, 0);

	private final List<Player> players = new LinkedList<Player>();

	private PlayerAnimations playerAnimations = new PlayerAnimations();

	private RunEnergy runEnergy = new RunEnergy(this);

	private MovementHandler movementHandler = new PlayerMovementHandler(this);

	private final CombatInterface combatInterface = new PlayerCombatInterface(this);

	private Following following = new PlayerFollowing(this);

	private PrivateMessaging privateMessaging = new PrivateMessaging(this);

	private Inventory inventory = new Inventory(this);

	private Bank bank = new Bank(this);
	
	private MoneyPouch pouch = new MoneyPouch(this);

	private Trade trade = new Trade(this);
	
	private PlayerAssistant playerAssistant = new PlayerAssistant(this);

	private Shopping shopping = new Shopping(this);

	private Equipment equipment = new Equipment(this);

	private SpecialAttack specialAttack = new SpecialAttack(this);

	private Consumables consumables = new Consumables(this);

	private LocalGroundItems groundItems = new LocalGroundItems(this);

	private final ItemDegrading degrading = new ItemDegrading();

	private Skill skill = new Skill(this);
	
	private LoyaltyManager loyaltyManager = new LoyaltyManager(this);

	private MagicSkill magic = new MagicSkill(this);

	private RangedSkill ranged = new RangedSkill(this);

	private Melee melee = new Melee();

	private PrayerBook prayer = new PrayerBook(this);

	private Fishing fishing = new Fishing(this);

	private Slayer slayer = new Slayer(this);
	
	private QuestManager questManager = new QuestManager(this);

	private final Summoning summoning = new Summoning(this);

	private final PriceChecker priceChecker = new PriceChecker(this, 28);

	private final RareDropEP rareDropEP = new RareDropEP();

	private PlayerOwnedShops playerShop = new PlayerOwnedShops(this);

	private Dialogue dialogue = null;

	private Skulling skulling = new Skulling();

	private LocalObjects objects = new LocalObjects(this);

	private Controller controller = ControllerManager.DEFAULT_CONTROLLER;

	private InterfaceManager interfaceManager = new InterfaceManager();

	private PlayerMinigames minigames = new PlayerMinigames(this);

	private Dueling dueling = new Dueling(this);

	private TzharrDetails jadDetails = new TzharrDetails();
	private InfernoDetails infernoDetails = new InfernoDetails();
	private Inferno inferno = new Inferno();

	private Farming farming = new Farming(this);

	private PlayerTitle playerTitle;

	private ToxicBlowpipe toxicBlowpipe = new ToxicBlowpipe(null, 0);
	
	private TridentOfTheSeas seasTrident = new TridentOfTheSeas(0);
	
	private TridentOfTheSwamp swampTrident = new TridentOfTheSwamp(0);
	
	private SerpentineHelmet serpentineHelment = new SerpentineHelmet(0);
	
	private PlayerProperties properties = new PlayerProperties(this);

	private boolean starter = false;
	private String username;
	private String password;
	private long usernameToLong;
	private int rights = 0;
	private int sufferingCharges = 0;
	
	public String referralID;

	private long generalDelay;

	private long lastRequestedLookup;

	private boolean visible = true;
	private int currentSongId = -1;
	private int chatColor;
	private int chatEffects;
	private byte[] chatText;
	private byte gender = 0;
	private int[] appearance = new int[7];
	private byte[] colors = new byte[5];

	private short npcAppearanceId = -1;
	private boolean appearanceUpdateRequired = false;
	private boolean chatUpdateRequired = false;
	private boolean needsPlacement = false;

	private boolean resetMovementQueue = false;
	private byte screenBrightness = 3;
	private byte multipleMouseButtons = 0;
	private byte chatEffectsEnabled = 0;
	private byte splitPrivateChat = 0;
	private byte transparentPanel = 0;
	private byte transparentChatbox = 0;
	private byte sideStones = 0;
	
	private byte acceptAid = 0;
	private long currentStunDelay;
	private long setStunDelay;

	private long lastAction = System.currentTimeMillis();
	private int enterXSlot = -1;
	private int enterXInterfaceId = -1;

	private int enterXItemId = 1;
	
	private boolean jailed = false;
	private long jailLength = 0;
	private long banLength = 0;
	private long muteLength = 0;
	private boolean banned = false;
	private boolean muted = false;

	private boolean yellMuted = false;

	private String yellTitle = "Member";

	public long timeout = 0L;

	public long aggressionDelay = System.currentTimeMillis();

	private int prayerInterface;
	private int yearCreated = 0;

	private int dayCreated = 0;
	private int lastLoginDay = 0;

	private int lastLoginYear = 0;
	private byte musicVolume = 3;

	private byte soundVolume = 3;

	public int votePoints = 0;
	private int slayerPoints = 0;
	private int pestPoints = 0;

	private int blackMarks = 0;

	private byte[] pouches = new byte[4];
	
	public LootingBag lootBag = new LootingBag(this);

	public Player() {
		ChangeAppearancePacket.setToDefault(this);
		client = new Client(null);
		usernameToLong = 0L;
	}

	public Player(Client client) {
		this.client = client;

		getLocation().setAs(new Location(PlayerConstants.HOME));

		setNpc(false);
	}
	
	public PrayerBook getPrayer() {
		return prayer;
	}

	public PlayerProperties getProperties() {
		return properties;
	}
	public LootingBag getLootBag() {
		return lootBag;
	}
	
	public ToxicBlowpipe getToxicBlowpipe() {
		return toxicBlowpipe;
	}

	public void setToxicBlowpipe(ToxicBlowpipe toxicBlowpipe) {
		this.toxicBlowpipe = toxicBlowpipe;
	}

	public long getGeneralDelay() {
		return generalDelay;
	}

	public void setGeneralDelay(long generalDelay) {
		this.generalDelay = generalDelay;
	}

	public long getLastRequestedLookup() {
		return lastRequestedLookup;
	}

	public void setLastRequestedLookup(long lastRequestedLookup) {
		this.lastRequestedLookup = lastRequestedLookup;
	}

	public Farming getFarming() {
		return farming;
	}

	public void setFarming(Farming farming) {
		this.farming = farming;
	}

	public PlayerTitle getPlayerTitle() {
		return playerTitle;
	}

	public void setPlayerTitle(PlayerTitle playerTitle) {
		this.playerTitle = playerTitle;
	}

	public void setLastLike(long lastLike) {
		this.lastLike = lastLike;
	}

	public long getLastLike() {
		return lastLike;
	}

	public void addLike() {
		likesGiven++;
	}

	public void setLikesGiven(byte likesGiven) {
		this.likesGiven = likesGiven;
	}

	public byte getLikesGiven() {
		return likesGiven;
	}

	public boolean canLike() {
		if (likesGiven < 3) {
			return true;
		}
		return lastLike == 0 || TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis() - lastLike) == 24;
	}

	@Override
	public void afterCombatProcess(Entity attack) {
		combatInterface.afterCombatProcess(attack);
	}

	@Override
	public boolean canAttack() {
		return combatInterface.canAttack();
	}

	public boolean canSave() {
		return controller.canSave();
	}

	public void changeZ(int z) {
		getLocation().setZ(z);
		needsPlacement = true;

		objects.onRegionChange();
		groundItems.onRegionChange();

		getMovementHandler().reset();

		send(new SendMapRegion(this));
	}

	@Override
	public void checkForDeath() {
		combatInterface.checkForDeath();
	}

	public void checkForRegionChange() {
		int deltaX = getLocation().getX() - getCurrentRegion().getRegionX() * 8;
		int deltaY = getLocation().getY() - getCurrentRegion().getRegionY() * 8;

		if ((deltaX < 16) || (deltaX >= 88) || (deltaY < 16) || (deltaY > 88))
			send(new SendMapRegion(this));
	}

	public void clearClanChat() {
		;
		send(new SendString("Talking in: ", 18139));
		send(new SendString("Owner: ", 18140));
		for (int j = 18144; j < 18244; j++) {
			send(new SendString("", j));
		}
	}

	public void setClanData() {
		boolean exists = Server.clanManager.clanExists(getUsername());
		if (!exists || clan == null) {
			send(new SendString("Join chat", 18135));
			send(new SendString("Talking in: Not in chat", 18139));
			send(new SendString("Owner: None", 18140));
		}
		if (!exists) {
			send(new SendString("Chat Disabled", 53706));
			String title = "";
			for (int id = 53707; id < 53717; id += 3) {
				if (id == 53707) {
					title = "Anyone";
				} else if (id == 53710) {
					title = "Anyone";
				} else if (id == 53713) {
					title = "General+";
				} else if (id == 53716) {
					title = "Only Me";
				}
				send(new SendString(title, id + 2));
			}
			for (int index = 0; index < 100; index++) {
				send(new SendString("", 53723 + index));
			}
			for (int index = 0; index < 100; index++) {
				send(new SendString("", 18424 + index));
			}
			return;
		}
		Clan clan = Server.clanManager.getClan(getUsername());
		send(new SendString(clan.getTitle(), 53706));
		String title = "";
		for (int id = 53707; id < 53717; id += 3) {
			if (id == 53707) {
				title = clan.getRankTitle(clan.whoCanJoin) + (clan.whoCanJoin > Clan.Rank.ANYONE && clan.whoCanJoin < Clan.Rank.OWNER ? "+" : "");
			} else if (id == 53710) {
				title = clan.getRankTitle(clan.whoCanTalk) + (clan.whoCanTalk > Clan.Rank.ANYONE && clan.whoCanTalk < Clan.Rank.OWNER ? "+" : "");
			} else if (id == 53713) {
				title = clan.getRankTitle(clan.whoCanKick) + (clan.whoCanKick > Clan.Rank.ANYONE && clan.whoCanKick < Clan.Rank.OWNER ? "+" : "");
			} else if (id == 53716) {
				title = clan.getRankTitle(clan.whoCanBan) + (clan.whoCanBan > Clan.Rank.ANYONE && clan.whoCanBan < Clan.Rank.OWNER ? "+" : "");
			}
			send(new SendString(title, id + 2));
		}
		if (clan.rankedMembers != null) {
			for (int index = 0; index < 100; index++) {
				if (index < clan.rankedMembers.size()) {
					send(new SendString("<clan=" + clan.ranks.get(index) + ">" + clan.rankedMembers.get(index), 43723 + index));
				} else {
					send(new SendString("", 43723 + index));
				}
			}
		}
		if (clan.bannedMembers != null) {
			for (int index = 0; index < 100; index++) {
				if (index < clan.bannedMembers.size()) {
					send(new SendString(clan.bannedMembers.get(index), 43824 + index));
				} else {
					send(new SendString("", 43824 + index));
				}
			}
		}
	}

	public void doAgressionCheck() {

		if (!controller.canAttackNPC()) {
			return;
		}

		short[] override = new short[3];

		if ((getCombat().inCombat()) && (!inMultiArea())) {
			return;
		}

		if ((getCombat().inCombat()) && (getCombat().getLastAttackedBy().isNpc())) {
			Mob m = World.getNpcs()[getCombat().getLastAttackedBy().getIndex()];

			if (m != null) {
				if (m.getId() == 2215) {
					override[0] = 2216;
					override[1] = 2217;
					override[2] = 2218;
				} else if (m.getId() == 3162) {
					override[0] = 3163;
					override[1] = 3164;
					override[2] = 3165;
				} else if (m.getId() == 2205) {
					override[0] = 2206;
					override[1] = 2207;
					override[2] = 2208;
				} else if (m.getId() == 3129) {
					override[0] = 3130;
					override[1] = 3131;
					override[2] = 3132;
				}
			}

		}

		for (Mob i : getClient().getNpcs())
			if ((i.getCombat().getAttacking() == null) && (i.getCombatDefinition() != null)) {
				boolean overr = false;

				for (short j : override) {
					if ((short) i.getId() == j) {
						overr = true;
						break;
					}
				}

				if (overr && i.inWilderness()) {
					continue;
				}

				if (!overr && GodWarsData.forId(i.getId()) == null) {
					if (System.currentTimeMillis() - aggressionDelay >= 60000 * 8) {
						continue;
					}
				}

				if ((i.getLocation().getZ() == getLocation().getZ()) && (!i.isWalkToHome())) {
					
					if (getController().equals(ControllerManager.GOD_WARS_CONTROLLER)) {
						GodWarsNpc npc = GodWarsData.forId(i.getId());
						
						if (npc != null) {
							if (!GodWarsData.isProtected(this, npc) && !i.getCombat().inCombat()) {
								if (Math.abs(getLocation().getX() - i.getLocation().getX()) + Math.abs(getLocation().getY() - i.getLocation().getY()) <= 25) {
									i.getCombat().setAttack(this);
									i.getFollowing().setFollow(this, Following.FollowType.COMBAT);
								}
							}
						}
						
						continue;
					}
					
					if (MobConstants.isAggressive(i.getId()) && (!i.getCombat().inCombat() || i.inMultiArea())) {
						if ((MobConstants.isAgressiveFor(i, this))) {
							if ((overr) || (Math.abs(getLocation().getX() - i.getLocation().getX()) + Math.abs(getLocation().getY() - i.getLocation().getY()) <= i.getSize() * 2))
								i.getCombat().setAttack(this);
						}
					}
				}
			}
	}

	public void doFadeTeleport(final Location l, final boolean setController) {
		send(new SendInterface(18460));

		setController(Tutorial.TUTORIAL_CONTROLLER);

		final Player player = this;

		TaskQueue.queue(new Task(this, 1) {
			byte pos = 0;

			@Override
			public void execute() {
				if (pos++ >= 3) {
					if (pos == 3) {
						teleport(l);
						send(new SendInterface(18452));
					} else if (pos == 5) {
						send(new SendRemoveInterfaces());

						if (setController) {
							setController(ControllerManager.DEFAULT_CONTROLLER);
							ControllerManager.setControllerOnWalk(player);
						}

						stop();
					}
				}
			}

			@Override
			public void onStop() {
			}
		});
	}

	@Override
	public boolean equals(Object o) {
		if ((o instanceof Player)) {
			return ((Player) o).getUsernameToLong() == getUsernameToLong();
		}

		return false;
	}
	
	public Location getRecallLocation() {
		return recall;
	}
	
	public void setRecallLocation(Location loc) {
		recall = loc;
	}

	public byte getAcceptAid() {
		return acceptAid;
	}

	public PlayerAnimations getAnimations() {
		return playerAnimations;
	}

	public int[] getAppearance() {
		return appearance;
	}

	public Bank getBank() {
		return bank;
	}
	
	public MoneyPouch getPouch() {
		return pouch;
	}

	public long getBanLength() {
		return banLength;
	}

	public int getBlackMarks() {
		return blackMarks;
	}

	public int getChatColor() {
		return chatColor;
	}

	public int getChatEffects() {
		return chatEffects;
	}

	public byte getChatEffectsEnabled() {
		return chatEffectsEnabled;
	}

	public byte[] getChatText() {
		return chatText;
	}

	public Client getClient() {
		return client;
	}

	public byte[] getColors() {
		return colors;
	}

	public Consumables getConsumables() {
		return consumables;
	}

	public Controller getController() {
		if (controller == null) {
			setController(ControllerManager.DEFAULT_CONTROLLER);
		}

		return controller;
	}

	@Override
	public int getCorrectedDamage(int damage) {
		return combatInterface.getCorrectedDamage(damage);
	}

	public Location getCurrentRegion() {
		return currentRegion;
	}

	public int getCurrentSongId() {
		return currentSongId;
	}

	public long getCurrentStunDelay() {
		return currentStunDelay;
	}

	public int getDayCreated() {
		return dayCreated;
	}

	public ItemDegrading getDegrading() {
		return degrading;
	}

	public Dialogue getDialogue() {
		return dialogue;
	}

	public Dueling getDueling() {
		return dueling;
	}

	public int getEnterXInterfaceId() {
		return enterXInterfaceId;
	}

	public int getEnterXItemId() {
		return enterXItemId;
	}

	public int getEnterXSlot() {
		return enterXSlot;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public Fishing getFishing() {
		return fishing;
	}

	@Override
	public Following getFollowing() {
		return following;
	}

	public byte getGender() {
		return gender;
	}

	public LocalGroundItems getGroundItems() {
		return groundItems;
	}

	public InterfaceManager getInterfaceManager() {
		return interfaceManager;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public ItemDegrading getItemDegrading() {
		return degrading;
	}

	public TzharrDetails getJadDetails() {
		return jadDetails;
	}
	
	public InfernoDetails getInfernoDetails() {
		return infernoDetails;
	}
	
	public Inferno getInferno() {
		return inferno;
	}

	public long getLastAction() {
		return lastAction;
	}

	public int getLastLoginDay() {
		return lastLoginDay;
	}

	public int getLastLoginYear() {
		return lastLoginYear;
	}

	public MagicSkill getMagic() {
		return magic;
	}

	@Override
	public int getMaxHit(CombatTypes type) {
		return combatInterface.getMaxHit(type);
	}

	public Melee getMelee() {
		return melee;
	}

	public PlayerMinigames getMinigames() {
		return minigames;
	}

	@Override
	public MovementHandler getMovementHandler() {
		return movementHandler;
	}

	public byte getMultipleMouseButtons() {
		return multipleMouseButtons;
	}

	public byte getMusicVolume() {
		return musicVolume;
	}

	public long getMuteLength() {
		return muteLength;
	}

	public int getNpcAppearanceId() {
		return npcAppearanceId;
	}

	public LocalObjects getObjects() {
		return objects;
	}

	public String getPassword() {
		return password;
	}

	public int getPestPoints() {
		return pestPoints;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public PlayerOwnedShops getPlayerShop() {
		return playerShop;
	}

	public byte[] getPouches() {
		return pouches;
	}

	public int getPrayerInterface() {
		return prayerInterface;
	}

	public PrivateMessaging getPrivateMessaging() {
		return privateMessaging;
	}

	public RangedSkill getRanged() {
		return ranged;
	}

	public RareDropEP getRareDropEP() {
		return rareDropEP;
	}

	public int getRights() {
		return rights;
	}

	public RunEnergy getRunEnergy() {
		return runEnergy;
	}

	public byte getScreenBrightness() {
		return screenBrightness;
	}

	public long getSetStunDelay() {
		return setStunDelay;
	}

	public Shopping getShopping() {
		return shopping;
	}

	public Skill getSkill() {
		return skill;
	}

	public Skulling getSkulling() {
		return skulling;
	}

	public Slayer getSlayer() {
		return slayer;
	}

	public int getSlayerPoints() {
		return slayerPoints;
	}

	public void addSlayerPoints(int amount) {
		slayerPoints += amount;
	}

	public byte getSoundVolume() {
		return soundVolume;
	}

	public SpecialAttack getSpecialAttack() {
		return specialAttack;
	}

	public byte getSplitPrivateChat() {
		return splitPrivateChat;
	}

	public Summoning getSummoning() {
		return summoning;
	}

	public PriceChecker getPriceChecker() {
		return priceChecker;
	}

	public Trade getTrade() {
		return trade;
	}
	
	public PlayerAssistant getPA() {
		return playerAssistant;
	}

	public String getUsername() {
		return username;
	}

	public long getUsernameToLong() {
		return usernameToLong;
	}

	public int getVotePoints() {
		return votePoints;
	}

	public int getYearCreated() {
		return yearCreated;
	}

	@Override
	public void hit(Hit hit) {
		combatInterface.hit(hit);
	}

	public void incrDeaths() {
		deaths = ((short) (deaths + 1));
		InterfaceHandler.writeText(new oldQuestTab(this));
	}

	public boolean isAppearanceUpdateRequired() {
		return appearanceUpdateRequired;
	}

	public boolean isBanned() {
		return banned;
	}

	public boolean isBusy() {
		return (interfaceManager.hasBankOpen()) || interfaceManager.hasInterfaceOpen() || (trade.trading()) || (dueling.isStaking());
	}

	public boolean isBusyNoInterfaceCheck() {
		return (interfaceManager.hasBankOpen()) || (trade.trading()) || (dueling.isStaking());
	}

	public boolean isChatUpdateRequired() {
		return chatUpdateRequired;
	}

	@Override
	public boolean isIgnoreHitSuccess() {
		return combatInterface.isIgnoreHitSuccess();
	}

	public boolean isMuted() {
		return muted;
	}

	public boolean isResetMovementQueue() {
		return resetMovementQueue;
	}

	public boolean isStarter() {
		return starter;
	}

	public boolean isVisible() {
		return visible;
	}

	public boolean isYellMuted() {
		return yellMuted;
	}

	/**
	 * Adds the connecting user the the default clan chat channel
	 */
	public void addDefaultChannel() {
		if (clan == null) {
			Clan localClan = Server.clanManager.getClan("sevimos");
			if (localClan != null)
				localClan.addMember(this);
			else {
				send(new SendMessage(Utility.formatPlayerName("") + " has not created a clan yet."));
			}
		}
	}

	/**
	 * Handles login
	 * 
	 * @param starter
	 * @return
	 * @throws Exception
	 */
	public boolean login(boolean starter) throws Exception {
		this.starter = starter;
		
		username = NameUtil.uppercaseFirstLetter(username);

		usernameToLong = Utility.nameToLong(username.toLowerCase());

		int response = 2;

		if ((password.length() == 0) || (username.length() == 0) || (username.length() > 12)) {
			response = 3;
		} else if ((banned) || (PlayerSaveUtil.isIPBanned(this))) {
			response = 4;
		} else if ((password != null) && (!password.equals(client.getEnteredPassword()))) {
			response = 3;
		} else if (World.isUpdating()) {
			response = 14;
		} else if (World.getPlayerByName(username) != null) {
			response = 5;
		} else if (World.register(this) == -1) {
			response = 7;
		}

		if (response != 2) {
			StreamBuffer.OutBuffer resp = StreamBuffer.newOutBuffer(3);
			resp.writeByte(response);
			resp.writeByte(rights);
			resp.writeByte(0);
			client.send(resp.getBuffer());
			return false;
		}

		new SendLoginResponse(response, this.getRights()).execute(client);

		send(new SendString(getMoneyPouch() + "", 8135));
		
		if (PlayerSaveUtil.isIPMuted(this)) {
			setMuted(true);
			setMuteLength(-1);
		}
		
		if (this.inCyclops()) {
			this.teleport(PlayerConstants.HOME);
		}

		ControllerManager.setControllerOnWalk(this);

		if (Region.getRegion(getLocation().getX(), getLocation().getY()) == null) {
			teleport(new Location(PlayerConstants.HOME));
			send(new SendMessage("You have been retrieved from an unknown location."));
		}
		
		if (isJailed() && !inJailed()) {
			teleport(new Location(PlayerConstants.JAILED_AREA));
			send(new SendMessage("You were jailed!"));
		}
		
		if (this.inWGGame()) {
			WeaponGame.leaveGame(this, false);
		}

		movementHandler.getLastLocation().setAs(new Location(getLocation().getX(), getLocation().getY() + 1, getLocation().getZ()));

		for (int i = 0; i < PlayerConstants.SIDEBAR_INTERFACE_IDS.length; i++) {
			if (i != 5 && i != 6) {
				send(new SendSidebarInterface(i, PlayerConstants.SIDEBAR_INTERFACE_IDS[i]));
			}
		}

		if (magic.getMagicBook() == 0) {
			magic.setMagicBook(1151);
		}

		if (prayerInterface == 0) {
			prayerInterface = 5608;
			prayer = new PrayerBook(this);
		}

		send(new SendSidebarInterface(5, prayerInterface));

		if (starter) {
			ChangeAppearancePacket.setToDefault(this);

			this.start(new Tutorial(this));

			if (lastLoginYear == 0) {
				yearCreated = Utility.getYear();
				dayCreated = Utility.getDayOfYear();
			}
		}

		if (!ChangeAppearancePacket.validate(this)) {
			ChangeAppearancePacket.setToDefault(this);
		}

		equipment.onLogin();
		skill.onLogin();
		magic.onLogin();
		this.setScreenBrightness((byte) 4);
		privateMessaging.connect();
		runEnergy.update();
		prayer.disable();
		
		bank.update();

		if (this.getEquipment().getItems()[5] != null && this.getEquipment().getItems()[5].getId() == 13742) {
			this.getEquipment().getItems()[5].setId(11283);
			this.getEquipment().update();
		}

		jadDetails.setStage(0);

		this.getRunEnergy().setRunning(true);

		Emotes.onLogin(this);

		//InterfaceHandler.writeText(new oldQuestTab(this));
		QuestTab.startUp(this);
		//InterfaceHandler.writeText(new CreditTab(this));
		send(new SendString("</col>Arkitori Credits: @gre@" + Utility.format(this.getCredits()), 52504));

		inventory.update();

		send(new SendPlayerOption("Follow", 4));
		send(new SendPlayerOption("Trade with", 5));

		send(new SendConfig(166, screenBrightness));
		send(new SendConfig(171, multipleMouseButtons));
		send(new SendConfig(172, chatEffectsEnabled));
		send(new SendConfig(287, splitPrivateChat));
		send(new SendConfig(427, acceptAid));
		send(new SendConfig(172, isRetaliate() ? 1 : 0));
		send(new SendConfig(173, getRunEnergy().isRunning() ? 1 : 0));
		send(new SendConfig(168, musicVolume));
		send(new SendConfig(169, soundVolume));
		send(new SendConfig(876, 0));
		send(new SendConfig(1032, profilePrivacy ? 1 : 2));

		farming.doCalculations();
		farming.getAllotment().updateAllotmentsStates();
		farming.getHerbs().updateHerbsStates();
		farming.getTrees().updateTreeStates();
		farming.getFruitTrees().updateFruitTreeStates();
		farming.getFlowers().updateFlowerStates();
		farming.getSpecialPlantOne().updateSpecialPlants();
		farming.getSpecialPlantTwo().updateSpecialPlants();
		farming.getHops().updateHopsStates();
		farming.getBushes().updateBushesStates();

		for (int i = 0; i < 4; i++) {
			farming.getCompost().updateCompostBin(i);
		}

		send(new SendExpCounter(0, 0));

		LoyaltyShop.load(this);

		for (int i = 0; i < skillGoals.length; i++) {
			send(new SendSkillGoal(i, skillGoals[i][0], skillGoals[i][1], skillGoals[i][2]));
		}

		send(new SendConfig(77, 0));

		getUpdateFlags().setUpdateRequired(true);
		appearanceUpdateRequired = true;
		needsPlacement = true;
		
		if (getRights() == 4) {
			World.sendGlobalMessage("<img=3>@dre@Owner/Developer " + username + " has logged in!");
		} 		
		else if (getRights() == 3) {
			World.sendGlobalMessage("<img=3>@dre@Community Manager " + username + " has logged in!");
		}
		else if (getRights() == 2) {
			World.sendGlobalMessage("<img=1><col=D17417>Server Administrator " + username + " has logged in!</col>");
		}
		else if (getRights() == 1) {
			World.sendGlobalMessage("<img=0>@blu@Player Moderator " + username + " has logged in!");
		}
		else if (getRights() == 13) {
			World.sendGlobalMessage("<img=12>@blu@Player Support " + username + " has logged in!");
		}
		else if (getRights() == 15) {
			World.sendGlobalMessage("<img=15>@red@Youtuber " + username + " has logged in!");
		}
		
		
		send(new SendMessage("<img=8>@blu@Welcome to Arkitori!"));
		send(new SendMessage("<img=8>@blu@Make sure you register on our active community forums: www.Arkitori.com"));
		send(new SendMessage("<img=8>@blu@Don't forget to ::Vote every 12 hours for great rewards and to help the server grow!"));
		send(new SendMessage("<img=8>@blu@Type ::Donate to visit our online store!"));
		
		
		if (World.getActivePlayers() > Constants.MOST_ONLINE) {
			Constants.MOST_ONLINE = World.getActivePlayers();
			FileHandler.saveMaxPlayers();
			World.sendGlobalMessage("[<col=910D0D>Arkitori</col>] We have broken our most players online record! New record: <col=910D0D>" + Constants.MOST_ONLINE + "</col>!");
		}
		
		if (Constants.doubleExperience) {			
			send(new SendMessage("<img=8>@dre@[EVENT] DOUBLE EXPERIENCE WEEKEND IS:</col> @gre@[ACTIVE]"));
		}

		controller.onControllerInit(this);

		playerShop.setName(username);

		for (Prayer prayer : Prayer.values()) {
			send(new SendConfig(630 + prayer.ordinal(), this.prayer.isQuickPrayer(prayer) ? 1 : 0));
			send(new SendConfig(prayer.getConfigId(), 0));
		}
		
		clearClanChat();
		setClanData();
		if (lastClanChat != null && lastClanChat.length() > 0) {
			Clan clan = Server.clanManager.getClan(lastClanChat);
			if (clan != null)
				clan.addMember(this);
		} else {
			addDefaultChannel();
		}
		Barrows.updateInterface(this);
		MiscInterfaces.startUp(this);
		
		if (PlayerConstants.isStaff(this)) {
			send(new SendString("Staff tab", 29413));
		} else {
			send(new SendString("", 29413));
		}
		send(new SendConfig(1990, getTransparentPanel()));
		send(new SendConfig(1991, getTransparentChatbox()));
		send(new SendConfig(1992, getSideStones()));
		return true;
	}

	public void logout(boolean force) {
		if (isActive()) {
			if (force){
				ControllerManager.onForceLogout(this);
			
			
		}else if ((controller != null) && (!controller.canLogOut())) {
				return;
			}

			HouseManager.leaveHouse(this);
			
			//this is as good as its guna get lel, disgusting ik
			if (isIron()) {
				new Thread(new IronmanHiscores(this)).start();
			}
			
			else if (isUltimateIron()) {
				new Thread(new UltimateHiscores(this)).start();
			}
			
			else if (isExtreme()) {
				new Thread(new ExtremeHiscores(this)).start();
			}
			
			else {
				new Thread(new Highscores(this)).start();
			}
			
			World.remove(client.getNpcs());

			if (controller != null) {
				controller.onDisconnect(this);
			}

			if (trade.trading()) {
				trade.end(false);
			}
			
			if (this.getInterfaceManager().main == 48500) {
				this.getPriceChecker().withdrawAll();
			}

			if (clan != null) {
				clan.removeMember(getUsername());
			}

			if (TargetSystem.getInstance().playerHasTarget(this)) {
				TargetSystem.getInstance().resetTarget(this, true);
			}

			if (dueling.isStaking()) {
				dueling.decline();
			}
			
			if (getBossPet() != null) {
				BossPets.onLogout(this);
			}

			if (summoning.hasFamiliar()) {
				summoning.removeForLogout();
			}

			if (DwarfMultiCannon.hasCannon(this)) {
				DwarfMultiCannon.getCannon(this).onLogout();
			}

			PlayerSave.save(this);

			if (!Constants.DEV_MODE && !PlayerConstants.isStaff(this)) {
				boolean debugMessage = false;
				int[] playerXP = new int[23];
				for (int i = 0; i < playerXP.length; i++) {
					playerXP[i] = (int) this.getSkill().getExperience()[i];
				}
				String mode = (isIron()) ? "Ironman Mode" : (isUltimateIron()) ? "Ultimate Ironman Mode" : (isExtreme()) ? "Extreme Mode" : "Normal Mode";
				com.everythingrs.hiscores.Hiscores.update("bre6zmdoy9xeejyek0fk9y66rnlcubyp0phxovci47qym5z5mivyvjrd3mclh8ryh08m4lblnmi",  mode,  this.getUsername(), 0, playerXP, debugMessage);
				
			}
		}
		
		World.unregister(this);
		client.setStage(Client.Stages.LOGGED_OUT);
		setActive(false);

		new SendLogout().execute(client);
		client.disconnect();
	}

	public boolean needsPlacement() {
		return needsPlacement;
	}

	@Override
	public void onAttack(Entity attack, int hit, CombatTypes type, boolean success) {
		combatInterface.onAttack(attack, hit, type, success);
	}

	@Override
	public void onCombatProcess(Entity attack) {
		combatInterface.onCombatProcess(attack);
	}

	public void onControllerFinish() {
		controller = ControllerManager.DEFAULT_CONTROLLER;
	}

	@Override
	public void onHit(Entity e, Hit hit) {
		combatInterface.onHit(e, hit);

		if (e.isNpc()) {
			Mob m = World.getNpcs()[e.getIndex()];

			if (m != null) {
				rareDropEP.forHitOnMob(this, m, hit);
			}
		}
	}

	@Override
	public void poison(int start) {
		if (isPoisoned()) {
			return;
		}

		super.poison(start);

		if (isActive())
			send(new SendMessage("You have been poisoned!"));
	}

	@Override
	public void process() throws Exception {

		if (Math.abs(World.getCycles() - client.getLastPacketTime()) >= 9) {
			if (getCombat().inCombat() && !getCombat().getLastAttackedBy().isNpc()) {
				if (timeout == 0) {
					timeout = System.currentTimeMillis() + 180000;
				} else if (timeout <= System.currentTimeMillis() || !getCombat().inCombat()) {
					logout(false);
					System.out.println("Player timed out: " + getUsername());
				}
			} else {
				System.out.println("Player timed out: " + getUsername());
				logout(false);
			}
		}

		if (controller != null) {
			controller.tick(this);
		}

		shopping.update();

		prayer.drain();

		following.process();
		
		//InterfaceHandler.writeText(new oldQuestTab(this));
		//QuestTab.startUp(this);
		getCombat().process();

		doAgressionCheck();
		
	}

	@Override
	public void reset() {
		following.updateWaypoint();
		appearanceUpdateRequired = false;
		chatUpdateRequired = false;
		resetMovementQueue = false;
		needsPlacement = false;
		getMovementHandler().resetMoveDirections();
		getUpdateFlags().setUpdateRequired(false);
		getUpdateFlags().reset();
	}

	public void resetAggression() {
		aggressionDelay = System.currentTimeMillis();
	}

	@Override
	public void retaliate(Entity attacked) {
		if (attacked != null) {
			if (isRetaliate() && getCombat().getAttacking() == null && !getMovementHandler().moving()) {
				getCombat().setAttack(attacked);
			}
		}
	}

	public void send(OutgoingPacket o) {
		client.queueOutgoingPacket(o);
	}

	public void setAcceptAid(byte acceptAid) {
		this.acceptAid = acceptAid;
	}

	public void setAppearance(int[] appearance) {
		this.appearance = appearance;
	}

	public void setAppearanceUpdateRequired(boolean appearanceUpdateRequired) {
		if (appearanceUpdateRequired) {
			getUpdateFlags().setUpdateRequired(true);
		}
		this.appearanceUpdateRequired = appearanceUpdateRequired;
	}

	public void setBanLength(long banLength) {
		this.banLength = banLength;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	public void setBlackMarks(int blackMarks) {
		this.blackMarks = blackMarks;
	}

	public void setChatColor(int chatColor) {
		this.chatColor = chatColor;
	}

	public void setChatEffects(int chatEffects) {
		this.chatEffects = chatEffects;
	}

	public void setChatEffectsEnabled(byte chatEffectsEnabled) {
		this.chatEffectsEnabled = chatEffectsEnabled;
	}

	public void setChatText(byte[] chatText) {
		this.chatText = chatText;
	}

	public void setChatUpdateRequired(boolean chatUpdateRequired) {
		if (chatUpdateRequired) {
			getUpdateFlags().setUpdateRequired(true);
		}
		this.chatUpdateRequired = chatUpdateRequired;
	}

	public void setColors(byte[] colors) {
		this.colors = colors;
	}

	public boolean setController(Controller controller) {
		this.controller = controller;
		controller.onControllerInit(this);
		return true;
	}

	public boolean setControllerNoInit(Controller controller) {
		this.controller = controller;
		return true;
	}

	public void setCurrentRegion(Location currentRegion) {
		this.currentRegion = currentRegion;
	}

	public void setCurrentSongId(int currentSongId) {
		this.currentSongId = currentSongId;
	}

	public void setCurrentStunDelay(long delay) {
		currentStunDelay = delay;
	}

	public void setDayCreated(int dayCreated) {
		this.dayCreated = dayCreated;
	}

	public void setDialogue(Dialogue d) {
		dialogue = d;
	}

	public void setEnterXInterfaceId(int enterXInterfaceId) {
		this.enterXInterfaceId = enterXInterfaceId;
	}

	public void setEnterXItemId(int enterXItemId) {
		this.enterXItemId = enterXItemId;
	}

	public void setEnterXSlot(int enterXSlot) {
		this.enterXSlot = enterXSlot;
	}

	public void setGender(byte gender) {
		this.gender = gender;
	}

	public void setLastAction(long lastAction) {
		this.lastAction = lastAction;
	}

	public void setLastLoginDay(int lastLoginDay) {
		this.lastLoginDay = lastLoginDay;
	}

	public void setLastLoginYear(int lastLoginYear) {
		this.lastLoginYear = lastLoginYear;
	}

	public void setMultipleMouseButtons(byte multipleMouseButtons) {
		this.multipleMouseButtons = multipleMouseButtons;
	}

	public void setMusicVolume(byte musicVolume) {
		this.musicVolume = musicVolume;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}

	public void setMuteLength(long muteLength) {
		this.muteLength = muteLength;
	}

	public void setNeedsPlacement(boolean needsPlacement) {
		this.needsPlacement = needsPlacement;
	}

	public void setNpcAppearanceId(short npcAppearanceId) {
		this.npcAppearanceId = npcAppearanceId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPestPoints(int pestPoints) {
		this.pestPoints = pestPoints;
	}

	public void setPouches(byte[] pouches) {
		this.pouches = pouches;
	}

	public void setPrayerInterface(int prayerInterface) {
		this.prayerInterface = prayerInterface;
	}

	public void setResetMovementQueue(boolean resetMovementQueue) {
		this.resetMovementQueue = resetMovementQueue;
	}

	public void setRights(int rights) {
		this.rights = rights;
	}

	public void setScreenBrightness(byte screenBrightness) {
		this.screenBrightness = screenBrightness;
	}

	public void setSetStunDelay(long delay) {
		setStunDelay = delay;
	}

	public void setSlayerPoints(int slayerPoints) {
		this.slayerPoints = slayerPoints;
	}

	public void setSoundVolume(byte soundVolume) {
		this.soundVolume = soundVolume;
	}

	public void setSplitPrivateChat(byte splitPrivateChat) {
		this.splitPrivateChat = splitPrivateChat;
	}

	public void setStarter(boolean starter) {
		this.starter = starter;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String display;

	private int[][] skillGoals = new int[Skills.SKILL_COUNT + 1][3];

	private double expCounter;

	private int achievementsPoints;

	/* Barrows variable */
	private boolean[] killRecord = new boolean[Brother.values().length];
	private Brother hiddenBrother;
	private Mob brotherNpc;
	private int killCount;
	private boolean chestClicked;

	private boolean boughtSmallPlot;

	private boolean boughtMediumPlot;

	private boolean boughtLargePlot;

	private TabType tabType;

	private AchievementDifficulty achievementPage;

	public String achievementPoints;

	public boolean isChestClicked() {
		return chestClicked;
	}

	public Brother getHiddenBrother() {
		return hiddenBrother;
	}

	public Mob getBrotherNpc() {
		return brotherNpc;
	}

	public void setBarrowsKC(int killCount) {
		this.killCount = killCount;
	}

	public int getBarrowsKC() {
		return killCount;
	}

	public void setChestClicked(boolean chestClicked) {
		this.chestClicked = chestClicked;
	}

	public void setKillRecord(boolean[] killRecord) {
		this.killRecord = killRecord;
	}

	public boolean[] getKillRecord() {
		return killRecord;
	}

	public void setBrotherNpc(Mob brotherNpc) {
		this.brotherNpc = brotherNpc;
	}

	public void setHiddenBrother(Brother hiddenBrother) {
		this.hiddenBrother = hiddenBrother;
	}

	public String getDisplay() {
		return display;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setVotePoints(int votePoints) {
		this.votePoints = votePoints;
	}

	public void setYearCreated(int yearCreated) {
		this.yearCreated = yearCreated;
	}

	public void setYellMuted(boolean yellMuted) {
		this.yellMuted = yellMuted;
	}

	public void start() {
		runEnergy.tick();
		startRegeneration();
		specialAttack.tick();
		summoning.onLogin();
		skulling.tick(this);
		if (jadDetails.getStage() != 0) {
			TzharrGame.loadGame(this);
		}
		if (getTeleblockTime() > 0) {
			tickTeleblock();
		}
	}

	public void start(Dialogue dialogue) {
		this.dialogue = dialogue;
		if (dialogue != null) {
			dialogue.setNext(0);
			dialogue.setPlayer(this);
			dialogue.execute();
		} else if (getAttributes().get("pauserandom") != null) {
			getAttributes().remove("pauserandom");
		}
	}

	@Override
	public void teleblock(int i) {
		super.teleblock(i);
	}

	public void teleport(Location location) {
		boolean zChange = location.getZ() != getLocation().getZ();
		
		setTakeDamage(false);
		getLocation().setAs(location);
		setResetMovementQueue(true);
		setNeedsPlacement(true);
		movementHandler.getLastLocation().setAs(new Location(getLocation().getX(), getLocation().getY() + 1));
		getAttributes().remove("combatsongdelay");

		send(new SendRemoveInterfaces());
		send(new SendWalkableInterface(-1));

		ControllerManager.setControllerOnWalk(this);
		
		TaskQueue.cancelHitsOnEntity(this);
		//TaskQueue.queue(new FinishTeleportingTask(this, 0));
		//System.out.println("queue'd finish teleport task.");
		
		setTakeDamage(true);

		movementHandler.reset();
		

		if (!inClanWarsFFA()) {
			if (zChange) {
				send(new SendMapRegion(this));
			} else {
				checkForRegionChange();
			}
		}

		if (trade.trading()) {
			trade.end(false);
		} else if (dueling.isStaking()) {
			dueling.decline();
		}
		
		if (this.getBossPet() != null) {
			this.getBossPet().remove();
			final Mob mob = new Mob(this, this.getBossID(), false, false, true, this.getLocation());
			mob.getFollowing().setIgnoreDistance(true);
			mob.getFollowing().setFollow(this);
			this.setBossPet(mob);
		}
		HouseManager.exitHouse(this, false);
		
		TaskQueue.onMovement(this);
		
	}

	@Override
	public String toString() {
		return "Player(" + getUsername() + ":" + getPassword() + " - " + client.getHost() + ")";
	}

	@Override
	public void updateCombatType() {
		/**
		 * This shit was gone?
		 */
		CombatTypes type;
		if (magic.getSpellCasting().isCastingSpell())
			type = CombatTypes.MAGIC;
		else {
			type = EquipmentConstants.getCombatTypeForWeapon(this);
		}
		
		if (type != CombatTypes.MAGIC) {
			send(new SendConfig(333, 0));
		}

		getCombat().setCombatType(type);

		switch (type) {
		case MELEE:
			equipment.updateMeleeDataForCombat();
			break;
		case RANGED:
			equipment.updateRangedDataForCombat();
			break;
		default:
			break;
		}
	}

	public boolean withinRegion(Location other) {
		int deltaX = other.getX() - currentRegion.getRegionX() * 8;
		int deltaY = other.getY() - currentRegion.getRegionY() * 8;

		if ((deltaX < 2) || (deltaX > 110) || (deltaY < 2) || (deltaY > 110)) {
			return false;
		}

		return true;
	}

	/**
	 * Detemines the rank
	 * 
	 * @return
	 */
	public String determineRank(Player player) {
		switch (this.getRights()) {

		case 0:
			return "Player";
		case 1:
			return "<col=006699>Moderator</col>";
		case 2:
			return "<col=E6E600>Adminstrator</col>";
		case 3:
			return "<col=AB1818>Owner</col>";
		case 4:
			return "<col=CF1DCF>Developer</col>";
		case 5:
			return "<col=4D8528>Normal Donator</col>";//green
		case 6:
			return "<col=2EB8E6>Super Donator</col>";//blue
		case 7:
			return "<col=B20000>Uber Donator</col>";//red
		case 8:
			return "<col=971FF2>Elite Donator</col>";//purple
		case 11:
			return "@gry@Iron Man</col>";
		case 12:
			return "@gry@Ultimate Iron</col>";
		case 13:
			return "<col=006699>Support</col>";
		case 14:
			return "<col=971FF2>Beta</col>";
		case 15:
			return "<col=E6E600>Youtuber</col>";

		}
		return "Unknown!";
	}

	public String determineIcon(Player player) {
		switch (this.getRights()) {
		case 0:
			return "";
		case 1:
			return "<img=0>";
		case 2:
			return "<img=1>";
		case 3:
			return "<img=2>";
		case 4:
			return "<img=3>";
		case 5:
			return "<img=4>";
		case 6:
			return "<img=5>";
		case 7:
			return "<img=6>";
		case 8:
			return "<img=7>";
		case 9:
			return "<img=8>";
		case 11:
			return "<img=9>";
		case 12:
			return "<img=10>";
		case 13:
			return "<img=11>";
		case 14:
			return "<img=13>";
		case 15:
			return "<img=15>";
		}
		return "";
	}

	public int getAchievementsPoints() {
		return achievementsPoints;
	}

	public void addAchievementPoints(int points) {
		achievementsPoints = points;
	}

	public HashMap<AchievementList, Integer> getPlayerAchievements() {
		return playerAchievements;
	}

	public int getTeleportTo() {
		return teleportTo;
	}

	public void setTeleportTo(int teleportTo) {
		this.teleportTo = teleportTo;
	}

	public int[][] getSkillGoals() {
		return skillGoals;
	}

	public double getCounterExp() {
		return expCounter;
	}

	public void addCounterExp(double exp) {
		expCounter += exp;
	}

	public void setSkillGoals(int[][] skillGoals) {
		this.skillGoals = skillGoals;
	}

	public String getShopMotto() {
		return shopMotto;
	}

	public void setShopMotto(String shopMotto) {
		this.shopMotto = shopMotto;
	}

	public long getShopCollection() {
		return shopCollection;
	}

	public void setShopCollection(long shopCollection) {
		this.shopCollection = shopCollection;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getBountyPoints() {
		return bountyPoints;
	}
	
	public int getskillPoints() {
		return skillPoints;
	}
	
	public int getpvmPoints() {
		return pvmPoints;
	}

	public int setpvmPoints(int amount) {
		return pvmPoints = amount;
	}
	

	public int getbossPoints() {
		return bossPoints;
	}

	public int setbossPoints(int amount) {
		return bossPoints = amount;
	}
	
	public int getthievePoints() {
		return thievePoints;
	}
	
	public int setthievePoints(int amount) {
		return thievePoints = amount;
	}	
	
	public int getfarmingPoints() {
	return farmingPoints;
}
	
	public int setfarmingPoints(int amount) {
	return farmingPoints = amount;
}
	
	public int getwoodcuttingPoints() {
	return woodcuttingPoints;
}
	
	public int setwoodcuttingPoints(int amount) {
	return woodcuttingPoints = amount;
}
	
	public int getfiremakingPoints() {
	return firemakingPoints;
}
	
	public int setfiremakingPoints(int amount) {
	return firemakingPoints = amount;
}
	
	public int getcookingPoints() {
	return cookingPoints;
}
	
	public int setcookingPoints(int amount) {
	return cookingPoints = amount;
}
	
	public int getfishingPoints() {
	return fishingPoints;
}
	
	public int setfishingPoints(int amount) {
	return fishingPoints = amount;
}
	
	public int getSmithingPoints() {
	return smithingPoints;
}
	
	public int setSmithingPoints(int amount) {
	return smithingPoints = amount;
}
	
	
	public int settriviaPoints(int amount) {
	return triviaPoints = amount;
}
	public int getminingPoints() {
	return miningPoints;
}
	
	public int setminingPoints(int amount) {
	return miningPoints = amount;
}
	
	public int getfletchingPoints() {
	return fletchingPoints;
}
	
	public int setfletchingPoints(int amount) {
	return fletchingPoints = amount;
}
	
	public int getcraftingPoints() {
	return craftingPoints;
}
	
	public int setcraftingPoints(int amount) {
	return craftingPoints = amount;
}
	
	public int getherblorePoints() {
	return herblorePoints;
}
	
	public int setherblorePoints(int amount) {
	return herblorePoints = amount;
}
	
	public int getpuroPoints() {
	return puroPoints;
}
	
	public int setpuroPoints(int amount) {
	return puroPoints = amount;
}
	
	public int getrunecraftingPoints() {
	return runecraftingPoints;
}
	
	public int setrunecraftingPoints(int amount) {
	return runecraftingPoints = amount;
}
	
	public int getprayerPoints() {
	return prayerPoints;
}
	
	public int setprayerPoints(int amount) {
	return prayerPoints = amount;
}

	public int setBountyPoints(int amount) {
		return bountyPoints = amount;
	}
	
	public int setskillPoints(int amount) {
		return skillPoints = amount;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public int getMoneySpent() {
		return moneySpent;
	}

	public void setMoneySpent(int moneySpent) {
		this.moneySpent = moneySpent;
	}

	public AccountSecurity getSecurity() {
		return accountSecurity;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public ArrayList<String> getLastKilledPlayers() {
		return lastKilledPlayers;
	}

	public void setLastKilledPlayers(ArrayList<String> lastKilledPlayers) {
		this.lastKilledPlayers = lastKilledPlayers;
	}

	public int getTotalPrestiges() {
		return totalPrestiges;
	}

	public void setTotalPrestiges(int totalPrestiges) {
		this.totalPrestiges = totalPrestiges;
	}

	public int getPrestigePoints() {
		return prestigePoints;
	}

	public void setPrestigePoints(int prestigePoints) {
		this.prestigePoints = prestigePoints;
	}

	public int[] getSkillPrestiges() {
		return skillPrestiges;
	}

	public void setSkillPrestiges(int[] skillPrestiges) {
		this.skillPrestiges = skillPrestiges;
	}

	public Mob getBossPet() {
		return bossPet;
	}

	public void setBossPet(Mob bossPet) {
		this.bossPet = bossPet;
	}

	public boolean getProfilePrivacy() {
		return profilePrivacy;
	}

	public void setProfilePrivacy(boolean profilePrivacy) {
		this.profilePrivacy = profilePrivacy;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}

	public int getProfileViews() {
		return profileViews;
	}

	public void setProfileViews(int profileViews) {
		this.profileViews = profileViews;
	}


	public void setTriviaWinningStreak(int triviaWinningStreak) {
		this.triviaWinningStreak = triviaWinningStreak;
	}

	public boolean isWantTrivia() {
		return wantsTrivia;
	}

	public void setWantTrivia(boolean canAnswerTrivia) {
		this.wantsTrivia = canAnswerTrivia;
	}

	public String getYellTitle() {
		return yellTitle;
	}

	public void setYellTitle(String yellTitle) {
		this.yellTitle = yellTitle;
	}

	public void unlockCredit(CreditPurchase purchase) {
		unlockedCredits.add(purchase);
	}

	public boolean isCreditUnlocked(CreditPurchase purchase) {
		return unlockedCredits.contains(purchase);
	}

	public Set<CreditPurchase> getUnlockedCredits() {
		return unlockedCredits;
	}

	public void setUnlockedCredits(Set<CreditPurchase> unlockedCredits) {
		this.unlockedCredits = unlockedCredits;
	}

	public int getArenaPoints() {
		return arenaPoints;
	}

	public void setArenaPoints(int arenaPoints) {
		this.arenaPoints = arenaPoints;
	}

	public Stopwatch getDelay() {
		return delay;
	}

	public void setDelay(Stopwatch delay) {
		this.delay = delay;
	}

	public String getShopColor() {
		return shopColor;
	}

	public void setShopColor(String shopColor) {
		this.shopColor = shopColor;
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getLastKnownUID() {
		return lastKnownUID;
	}
	
	public void setLastKnownUID(String uid) {
		this.lastKnownUID = uid;
	}

	public long getJailLength() {
		return jailLength;
	}

	public void setJailLength(long jailLength) {
		this.jailLength = jailLength;
	}

	public boolean isJailed() {
		return jailed;
	}

	public void setJailed(boolean jailed) {
		this.jailed = jailed;
	}

	public int getBossID() {
		return bossID;
	}

	public void setBossID(int bossID) {
		this.bossID = bossID;
	}

	public int[] getCluesCompleted() {
		return cluesCompleted;
	}
	
	public void setCluesCompleted(int[] cluesCompleted) {
		this.cluesCompleted = cluesCompleted;
	}
	
	public void setCluesCompleted(int index, int value) {
		cluesCompleted[index] = value;
	}

	public long getMoneyPouch() {
		return moneyPouch;
	}

	public void setMoneyPouch(long moneyPouch) {
		this.moneyPouch = moneyPouch;
	}

	public boolean isUltimateIron() {
		return isUltimateIron;
	}

	public void setUltimateIron(boolean isUltimateIron) {
		this.isUltimateIron = isUltimateIron;
	}

	public boolean isIron() {
		return isIron;
	}

	public void setIron(boolean isIron) {
		this.isIron = isIron;
	}
	
	public boolean isExtreme() {
		return isExtreme;
	}
	
	public void setExtreme(boolean isExtreme) {
		this.isExtreme = isExtreme;
	}

	public boolean isPouchPayment() {
		return pouchPayment;
	}

	public void setPouchPayment(boolean pouchPayment) {
		this.pouchPayment = pouchPayment;
	}

	public int getWeaponKills() {
		return weaponKills;
	}

	public void setWeaponKills(int weaponKills) {
		this.weaponKills = weaponKills;
	}

	public boolean isMember() {
		return isMember;
	}

	public void setMember(boolean isMember) {
		this.isMember = isMember;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getRecovery() {
		return recovery;
	}

	public void setRecovery(String recovery) {
		this.recovery = recovery;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public int getWeaponPoints() {
		return weaponPoints;
	}

	public void setWeaponPoints(int weaponPoints) {
		this.weaponPoints = weaponPoints;
	}

	public boolean isHitZulrah() {
		return hitZulrah;
	}

	public void setHitZulrah(boolean hitZulrah) {
		this.hitZulrah = hitZulrah;
	}

	public SerpentineHelmet getSerpentineHelment() {
		return serpentineHelment;
	}

	public void setSerpentineHelment(SerpentineHelmet serpentineHelment) {
		this.serpentineHelment = serpentineHelment;
	}

	public TridentOfTheSeas getSeasTrident() {
		return seasTrident;
	}

	public void setSeasTrident(TridentOfTheSeas trident) {
		this.seasTrident = trident;
	}

	public int getHunterKills() {
		return hunterKills;
	}

	public void setHunterKills(int hunterKills) {
		this.hunterKills = hunterKills;
	}

	public int getRogueKills() {
		return rogueKills;
	}

	public void setRogueKills(int rogueKills) {
		this.rogueKills = rogueKills;
	}

	public int getRogueRecord() {
		return rogueRecord;
	}

	public void setRogueRecord(int rogueRecord) {
		this.rogueRecord = rogueRecord;
	}

	public int getHunterRecord() {
		return hunterRecord;
	}

	public void setHunterRecord(int hunterRecord) {
		this.hunterRecord = hunterRecord;
	}

	public byte getTransparentPanel() {
		return transparentPanel;
	}

	public void setTransparentPanel(byte transparentPanel) {
		this.transparentPanel = transparentPanel;
	}

	public byte getTransparentChatbox() {
		return transparentChatbox;
	}

	public void setTransparentChatbox(byte transparentChatbox) {
		this.transparentChatbox = transparentChatbox;
	}

	public byte getSideStones() {
		return sideStones;
	}

	public void setSideStones(byte sideStones) {
		this.sideStones = sideStones;
	}

	public boolean isPrestigeColors() {
		return prestigeColors;
	}

	public void setPrestigeColors(boolean prestigeColors) {
		this.prestigeColors = prestigeColors;
	}

	public TridentOfTheSwamp getSwampTrident() {
		return swampTrident;
	}

	public void setSwampTrident(TridentOfTheSwamp swampTrident) {
		this.swampTrident = swampTrident;
	}

	public boolean hasBoughtSmallPlot() {
		return boughtSmallPlot;
	}

	public void setBoughtSmallPlot(boolean boughtSmallPlot) {
		this.boughtSmallPlot = boughtSmallPlot;
	}

	public boolean hasBoughtMediumPlot() {
		return boughtMediumPlot;
	}

	public void setBoughtMediumPlot(boolean boughtMediumPlot) {
		this.boughtMediumPlot = boughtMediumPlot;
	}

	public boolean hasBoughtLargePlot() {
		return boughtLargePlot;
	}

	public void setBoughtLargePlot(boolean boughtLargePlot) {
		this.boughtLargePlot = boughtLargePlot;
	}
	
	public TabType getTabType() {
		return tabType;
	}

	public void setTabType(TabType tabType) {
		this.tabType = tabType;
	}
	
	public AchievementDifficulty getAchievement() {
		return achievementPage;
	}

	public void setAchievement(AchievementDifficulty achievementPage) {
		this.achievementPage = achievementPage;
	}
	
	public QuestManager getQuestManager() {
		return questManager;
	}

	/**
	 * Gets the sufferingCharges.
	 * @return the sufferingCharges
	 */
	public int getSufferingCharges() {
		return sufferingCharges;
	}

	/**
	 * Sets the sufferingCharges.
	 * @param sufferingCharges the sufferingCharges to set
	 */
	public void setSufferingCharges(int sufferingCharges) {
		this.sufferingCharges = sufferingCharges;
	}
	
	/**
	 * Gets the loyaltyManager.
	 * @return the loyaltyManager
	 */
	public LoyaltyManager getLoyaltyManager() {
		return loyaltyManager;
	}
	
	public boolean pointsReset;

	public long infernoLeaveTimer;

	public int[] waveInfo;

	public boolean singleRanger;
	
	public boolean singleMager;
	
	public boolean singleJad;
	
	public boolean healers;
	
	public void sendMessage(String s) {
		getClient().queueOutgoingPacket(new SendMessage(s));
	}

	

}