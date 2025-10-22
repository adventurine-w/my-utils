package org.adv.clickerflex.ultimate_utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class ParticleUtils {

    public static void spawnParticle(Location loc, Particle particle, int amt) {
        spawnParticle(loc, particle, amt, new Vector(), 0, null);
    }

    public static void spawnParticle(Location loc, Particle particle, int amt, Vector offset) {
        spawnParticle(loc, particle, amt, offset, 0, null);
    }

    public static void spawnParticle(Location loc, Particle particle, int amt, Vector offset, double extra, Object data) {
        //Bukkit.broadcastMessage("hey0");
        if (Bukkit.isPrimaryThread()) {
            if (offset == null) offset = new Vector();
            //Bukkit.broadcastMessage("hey1");
            loc.getWorld().spawnParticle(particle, loc, amt,
                    offset.getX(), offset.getY(), offset.getZ(),
                    extra, data);
        } else {
            //Bukkit.broadcastMessage("hey2");
            Vector finalOffset = offset;
            Common.runMainThread(() -> spawnParticle(loc, particle, amt, finalOffset, extra, data));
        }
    }

    public static void spawnParticle(Location loc, Particle particle, int amt, double extra) {
        spawnParticle(loc, particle, amt, new Vector(), extra, null);
    }

    public static void spawnParticle(Location loc, Particle particle, int amt, Vector vector, double extra) {
        spawnParticle(loc, particle, amt, vector, extra, null);
    }

    public static void spawnParticle(Location loc, Particle particle, int amt, double extra, Object data) {
        spawnParticle(loc, particle, amt, new Vector(), extra, data);
    }

    public static void spawnParticle(Location loc, Particle particle, int amt, Object data) {
        spawnParticle(loc, particle, amt, new Vector(), 0, data);
    }

    public static void spawnParticle(Location loc, Particle particle, int amt, Vector offset, Object data) {
        spawnParticle(loc, particle, amt, offset, 0, data);
    }
    public static void drawDust(Location loc, int amt, Vector offset, Particle.DustOptions dustOptions){
        spawnParticle(loc,Particle.DUST,amt,offset,dustOptions);
    }
}
