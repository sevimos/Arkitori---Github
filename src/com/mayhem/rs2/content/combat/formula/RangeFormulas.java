package com.mayhem.rs2.content.combat.formula;

import com.mayhem.core.definitions.RangedWeaponDefinition;
import com.mayhem.rs2.content.skill.Skills;
import com.mayhem.rs2.content.skill.prayer.PrayerBook.Prayer;
import com.mayhem.rs2.content.skill.slayer.Slayer;
import com.mayhem.rs2.entity.Entity;
import com.mayhem.rs2.entity.World;
import com.mayhem.rs2.entity.item.Item;
import com.mayhem.rs2.entity.item.ItemCheck;
import com.mayhem.rs2.entity.mob.Mob;
import com.mayhem.rs2.entity.player.Player;
//import com.mayhem.rs2.entity.player.net.out.impl.SendMessage;

/**
 * @author Valiant (http://www.rune-server.org/members/Valiant)
 * @information Represents an attackers/victims rolls in ranged combat
 */
public class RangeFormulas {

    /**
     * Calculates the victims defence to ranged attacks
     *
     * @param player
     * @return
     */
    public static int calculateRangeDefence(Entity defending) {
        Player defender = null;

        if (!defending.isNpc()) {
            defender = World.getPlayers()[defending.getIndex()];
        } else {
            if (defending.getBonuses() != null && defending.getLevels() != null) {
                return defending.getLevels()[1] + defending.getBonuses()[9] + (defending.getBonuses()[9] / 2);
            }
            return 0;
        }

        int defenceLevel = defender.getSkill().getLevels()[1];
        if (defender.getPrayer().active(Prayer.THICK_SKIN)) {
            defenceLevel += defender.getMaxLevels()[1] * 0.05;
        } else if (defender.getPrayer().active(Prayer.ROCK_SKIN)) {
            defenceLevel += defender.getMaxLevels()[1] * 0.1;
        } else if (defender.getPrayer().active(Prayer.STEEL_SKIN)) {
            defenceLevel += defender.getMaxLevels()[1] * 0.15;
        } else if (defender.getPrayer().active(Prayer.CHIVALRY)) {
            defenceLevel += defender.getMaxLevels()[1] * 0.2;
        } else if (defender.getPrayer().active(Prayer.PIETY)) {
            defenceLevel += defender.getMaxLevels()[1] * 0.25;
        }
        return defenceLevel + defender.getBonuses()[9] + (defender.getBonuses()[9] / 2);
    }

    /**
     * Calculates the attackers attack
     *
     * @param c
     * @return
     */
    public static int calculateRangeAttack(Entity entity) {

        double rangeLevel = entity.getLevels()[4];

        if (!entity.isNpc()) { //if attacker is not a npc
            Player attacker = (Player) entity; //cast player
            Entity attacked = entity.getCombat().getAttacking(); //get the attacked person by entity
            if ((ItemCheck.hasDFireShield(attacker)) && (attacker.getMagic().isDFireShieldEffect())) {
                return 100;
            }
            if (attacker.getSpecialAttack().isInitialized()) {
                rangeLevel *= getRangedSpecialAccuracyModifier(attacker);
            }
            if (ItemCheck.wearingFullVoidRanged(attacker)) {
                rangeLevel += attacker.getMaxLevels()[4] * 15;
            }
            if (ItemCheck.wearingFullEliteVoidRanged(attacker)) {
                rangeLevel += attacker.getMaxLevels()[4] * 25;
            }
            if (attacker.getEquipment().isWearingItem(20997)) {
                int magicLevel = attacked.getLevels()[Skills.MAGIC];
                rangeLevel += !attacked.isNpc() ? attacker.getMaxLevels()[4] * 1 + Math.floor((magicLevel * 1.5) + (magicLevel * 1.5)) : attacker.getMaxLevels()[4] * 2 + Math.floor((magicLevel * 2) + (magicLevel * 2));
            }
            if (attacker.getPrayer().active(Prayer.SHARP_EYE)) {
                rangeLevel *= 1.05;
            } else if (attacker.getPrayer().active(Prayer.HAWK_EYE)) {
                rangeLevel *= 1.10;
            } else if (attacker.getPrayer().active(Prayer.EAGLE_EYE)) {
                rangeLevel *= 1.15;
            }
            if (attacker.getSpecialAttack().isInitialized()) {
                if (ItemCheck.wearingFullVoidRanged(attacker)) {
                    rangeLevel *= 15;
                }
            }
            if (attacker.getSpecialAttack().isInitialized()) {
                if (ItemCheck.wearingFullEliteVoidRanged(attacker)) {
                    rangeLevel *= 25;
                }
            }
            return (int) (rangeLevel + (attacker.getBonuses()[4] * 3.50));
        }
        //Must be NPC if it gets here
        if (entity.getLevels() != null) {
            rangeLevel = entity.getLevels()[4];

            return (int) (rangeLevel + (entity.getBonuses()[4] * 1.25));
        }
        return 0;
    }

    public static final double[][] RANGED_PRAYER_MODIFIERS = {{3.0D, 0.05D}, {11.0D, 0.1D}, {19.0D, 0.15D}};

    public static int getEffectiveRangedStrength(Player player) {
        Item weapon = player.getEquipment().getItems()[3];

        if ((weapon == null) || (weapon.getRangedDefinition() == null)) {
            return 0;
        }
        if (weapon.getId() == 12926) {
            if (player.getToxicBlowpipe().getBlowpipeAmmo() == null) {
                return 0;
            }
            return player.getToxicBlowpipe().getBlowpipeAmmo().getRangedStrengthBonus() + 40;
        }

        int rangedStrength = player.getBonuses()[12];

        if ((weapon.getRangedDefinition().getType() == RangedWeaponDefinition.RangedTypes.THROWN) || (weapon.getRangedDefinition().getArrows() == null) || (weapon.getRangedDefinition().getArrows().length == 0)) {
            rangedStrength = weapon.getRangedStrengthBonus();
        } else {
            Item ammo = player.getEquipment().getItems()[13];
            if (ammo != null) {
                rangedStrength = ammo.getRangedStrengthBonus();
            }
        }

        return rangedStrength;
    }

    public static int getRangedMaxHit(Player player) {
        double prayerBonus = 1.0D;
        int itemBonus = 0;
        int attackStyleBonus = 0;

        if (ItemCheck.wearingFullVoidRanged(player)) {
            itemBonus *= 115.0;
        }
        if (ItemCheck.wearingFullEliteVoidRanged(player)) {
            itemBonus *= 125.0;
        }
        if (player.getEquipment().isWearingItem(20997)) {
            itemBonus *= 250.0;
        }

        switch (player.getEquipment().getAttackStyle()) {
            case ACCURATE:
                attackStyleBonus = 3;
                break;
            case AGGRESSIVE:
                attackStyleBonus = 2;
                break;
            case DEFENSIVE:
                attackStyleBonus = 1;
                break;
            default:
                break;
        }

        for (int i = 0; i < RANGED_PRAYER_MODIFIERS.length; i++) {
            if (player.getPrayer().active(Prayer.values()[(int) RANGED_PRAYER_MODIFIERS[i][0]])) {
                prayerBonus += RANGED_PRAYER_MODIFIERS[i][1];
                break;
            }
        }

        int strength = player.getSkill().getLevels()[4];
        double effectiveStrength = (int) (strength * prayerBonus + attackStyleBonus + itemBonus);
        int rangedStrength = getEffectiveRangedStrength(player);
        double base = 5.0D + (effectiveStrength + 8.0D) * (rangedStrength + 64) / 64.0D;

        if (player.getSpecialAttack().isInitialized()) {
            base = (int) (base * getRangedSpecialModifier(player));
        }

        Item helm = player.getEquipment().getItems()[0];
        
        //twisted bow formula
        if (player.getEquipment().isWearingItem(20997)) {
            if(player.getCombat() != null && player.getCombat().getAttacking() != null) {
                base += 15.0D +
                        (!(player.getCombat().getAttacking().isNpc()) ? (32 * (player.getCombat().getAttacking().getLevels()[Skills.MAGIC] / 9))
                                : (64 * (player.getCombat().getAttacking().getLevels()[Skills.MAGIC] / 9)));
            } else {
                base += 24.0D;
            }
        }
        if ((helm != null) && (helm.getId() == 15492) && (player.getCombat().getAttacking().isNpc()) && (player.getSlayer().hasTask())) {
            Mob mob = World.getNpcs()[player.getCombat().getAttacking().getIndex()];
            if ((mob != null) && (Slayer.isSlayerTask(player, mob))) {
                base += base * 0.125D;
            }

        }

        return (int) base / 11;
    }

    public static double getRangedSpecialAccuracyModifier(Player player) {
        Item weapon = player.getEquipment().getItems()[3];
        Item arrow = player.getEquipment().getItems()[13];

        if (weapon == null) {
            return 1.0D;
        }

        switch (weapon.getId()) {
            case 11785:
                return 2D;
            case 20997:
                return 2D;
            case 12926:
                return 1.4D;
            case 12765:
            case 12766:
            case 12767:
            case 12768:
            case 20408:
            case 11235:
                if (arrow.getId() == 11212) {
                    return 1.5D;
                }
                return 1.3D;
            case 13883:
            case 15241:
            case 21012:
                return 1.2D;
            case 9185:
                if (arrow.getId() == 9243)
                    return 1.15D;
                if (arrow.getId() == 9244)
                    return 1.65D;
                if (arrow.getId() == 9245) {
                    return 1.15D;
                }
                return 1.0D;
        }

        return 1.0D;
    }

    public static double getRangedSpecialModifier(Player player) {
        Item weapon = player.getEquipment().getItems()[3];
        Item arrow = player.getEquipment().getItems()[13];

        if (weapon == null) {
            return 1.0D;
        }

        switch (weapon.getId()) {
            case 12926:
                return 1.5D;
            case 20997:
                return 1.8D;
            case 12765:
            case 12766:
            case 12767:
            case 12768:
            case 20408:
            case 11235:
                if (arrow.getId() == 11212) {
                    return 1.5D;
                }
                return 1.3D;
            case 13883:
            case 15241:
                return 1.2D;

            case 11864:
            case 19647:
            case 19639:
            case 19643:
                return 1.10D;

            case 11865:
            case 19649:
            case 19641:
            case 19645:
                return 1.15D;


            case 11785:
            case 9185:
                if (arrow.getId() == 9243)
                    return 1.15D;
                if (arrow.getId() == 9244)
                    return 1.45D;
                if (arrow.getId() == 9245) {
                    return 1.15D;
                }
                return 1.0D;
        }

        return 1.0D;
    }

}