package org.adv.clickerflex.mob_abilities;

import org.adv.clickerflex.mob.LivingCustomMob;
import org.adv.clickerflex.player.CustomPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MobAbilityUtils {
    public static void playSound(LivingCustomMob mob, CustomPlayer target, Sound sound, boolean targetOnly){
        playSound(mob, target, sound, 1, 1, targetOnly);
    }
    public static void playSound(LivingCustomMob mob, CustomPlayer target, Sound sound, float volume, float pitch, boolean targetOnly){
        if(mob == null || target == null) return;
        LivingEntity m = mob.getEntity();
        Player p = target.getPlayer();
        if(m==null || p == null) return;
        if(targetOnly){
            p.playSound(m, sound, volume, pitch);
        }else {
            m.getWorld().playSound(m.getLocation(), sound, volume, pitch);
        }
    }

    public static List<Player> getAllPlayersInRadius(Location loc, double radius) {
        List<Player> list = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.getWorld().equals(loc.getWorld())) continue;
            if (loc.distanceSquared(player.getLocation()) <= radius * radius) {
                list.add(player);
            }
        }
        return list;
    }
}
