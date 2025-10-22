package org.adv.clickerflex.mob_abilities.all;

import org.adv.clickerflex.mob.LivingCustomMob;
import org.adv.clickerflex.player.CustomPlayer;
import org.adv.clickerflex.ultimate_utils.Common;
import org.adv.clickerflex.ultimate_utils.ParticleUtils;
import org.adv.clickerflex.utils.generic.RandomUtils;
import org.adv.clickerflex.utils.mc.VectorUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

import static org.adv.clickerflex.mob_abilities.CustomMobAbilities.isInvalidAbility;
import static org.adv.clickerflex.mob_abilities.MobAbilityUtils.playSound;
import static org.adv.clickerflex.ultimate_utils.Common.getCenterLocation;

public class MobAbilityWaterSweep {

    private static final double particleSphericalRange = 1.3;

    public static void cast(LivingCustomMob mob, CustomPlayer player, double damage){
        cast(mob,player, damage,true);
    }
    public static void cast(LivingCustomMob mob, CustomPlayer player, double damage, boolean targetOnly){
        if(isInvalidAbility(player, player.getAliveSession(), mob)) return;
        HashSet<String> names = new HashSet<>();
        Player p = player.getPlayer();
        Location mobLoc = mob.getEntity().getEyeLocation();
        Location playerLoc = getCenterLocation(p).add(0,0.2,0);

        playSound(mob, player, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 0.6f, 0f, targetOnly);
        playSound(mob, player, Sound.ENTITY_DOLPHIN_SPLASH, targetOnly);

        Vector v = VectorUtils.fromTo(mobLoc, playerLoc);
        float range = 8;
        float density = 6;

        v.normalize().multiply(1/density);

        AtomicInteger x = new AtomicInteger(0);

        Runnable[] runnable = new Runnable[1];
        runnable[0] = ()->{
            if(x.incrementAndGet()>range*density) return;

            Location hit = mobLoc.add(v);
            ParticleUtils.drawDust(hit, 1, new Vector(), get());
            //Log.info("targetOnly? "+targetOnly);
            if(targetOnly) {
                //Log.info("1: "+(!names.contains(p.getSkillName()))+"   2: "+(Common.dSquared(mobLoc,p) < particleSphericalRange*particleSphericalRange));
                if (!names.contains(p.getName()) && Common.dSquared(mobLoc,p) < particleSphericalRange*particleSphericalRange) {
                    names.add(p.getName());
                    player.damageKB(damage, mob);
                    ParticleUtils.drawDust(hit, 50, new Vector(1.2,1.5,1.2), get());
                    //Common.pushUpwards(p, 0.1d);
                    //Common.push(p, v, 0.6d);
                    return;
                }
            }else{
                for (Player target : Bukkit.getOnlinePlayers()) {
                    if (names.contains(target.getName())) continue;
                    if (Common.dSquared(mobLoc, target) > particleSphericalRange*particleSphericalRange) continue;
                    names.add(target.getName());
                    CustomPlayer.of(target).damage(damage, mob);
                    ParticleUtils.drawDust(hit, 50, new Vector(1.2,1.5,1.2), get());
                    Common.pushUpwards(p, 0.1d);
                    Common.push(p, v, 0.6d);
                    return;
                }
            }
            long ticksToWait = x.get()%density==0 ? 1 : 0;
            Common.runTaskLater(()->runnable[0].run(), ticksToWait);
        };
        runnable[0].run();
    }
    public static void damage(LivingCustomMob mob, CustomPlayer player, Location hit, double damage, boolean targetOnly){
        player.damage(damage, mob.getEntity());
        ParticleUtils.drawDust(hit, 50, new Vector(1.2,1.5,1.2), get());
        Common.push(player.getPlayer(), VectorUtils.fromTo(hit, getCenterLocation(player.getPlayer())), 0.6d);
        playSound(mob, player, Sound.ENTITY_WITCH_DRINK, 1f, 1.7f, targetOnly);
    }
    private static Particle.DustOptions get(){
        int n = RandomUtils.between(1, 2);
        Color color = (n==1) ? Color.AQUA : Color.BLUE;
        return new Particle.DustOptions(color, RandomUtils.between(1f, 2f));
    }
}
