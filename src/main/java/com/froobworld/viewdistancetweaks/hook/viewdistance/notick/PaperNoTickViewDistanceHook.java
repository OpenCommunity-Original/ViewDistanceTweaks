package com.froobworld.viewdistancetweaks.hook.viewdistance.notick;

import com.froobworld.viewdistancetweaks.util.ViewDistanceUtils;
import org.bukkit.World;

public class PaperNoTickViewDistanceHook implements NoTickViewDistanceHook {

    @Override
    public int getViewDistance(World world) {
        return world.getNoTickViewDistance();
    }

    @Override
    public void setViewDistance(World world, int value) {
        value = ViewDistanceUtils.clampViewDistance(value);
        if (value != getViewDistance(world)) {
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
        return true;
    }

}
