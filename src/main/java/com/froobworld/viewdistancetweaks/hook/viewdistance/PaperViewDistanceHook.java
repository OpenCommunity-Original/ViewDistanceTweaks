package com.froobworld.viewdistancetweaks.hook.viewdistance;

import com.froobworld.viewdistancetweaks.util.ViewDistanceUtils;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class PaperViewDistanceHook implements ViewDistanceHook {

    @Override
    public int getDistance(World world) {
        return world.getNoTickViewDistance();
    }

    @Override
    public void setDistance(World world, int value) {
        value = ViewDistanceUtils.clampViewDistance(value);
        if (value != getDistance(world)) {
            world.setNoTickViewDistance(value);
        }
    }

    public static boolean isCompatible() {
        try {
            Class.forName("org.bukkit.World")
                    .getMethod("setNoTickViewDistance", int.class);
        } catch (Exception ex) {
            return false;
        }
        try {
            for (World world : Bukkit.getWorlds()) {
                world.setNoTickViewDistance(0);
            }
        } catch (IllegalArgumentException e) {
            return true;
        } catch (Exception otherException) {
            return false;
        }
        return true;
    }

}
