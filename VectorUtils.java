package org.adv.clickerflex.utils.mc;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VectorUtils {
    public static Vector fromTo(Location from, Location to){
        return to.toVector().subtract(from.toVector());
    }
    public static Vector fromToNormalize(Location from, Location to){
        return to.toVector().subtract(from.toVector()).normalize();
    }
    public static float getYaw(Vector vec) {
        return (float) Math.toDegrees(Math.atan2(-vec.getX(), vec.getZ()));
    }

    public static float getPitch(Vector vec) {
        double x = vec.getX();
        double y = vec.getY();
        double z = vec.getZ();
        double xz = Math.sqrt(x * x + z * z);

        return (float) Math.toDegrees(Math.atan2(-y, xz));
    }
}
