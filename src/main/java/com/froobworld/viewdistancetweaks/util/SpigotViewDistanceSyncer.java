package com.froobworld.viewdistancetweaks.util;

import org.bukkit.Bukkit;
import org.bukkit.World;

import static org.joor.Reflect.on;

public class SpigotViewDistanceSyncer {

    public static void syncSpigotViewDistances(World world) {
        if (NmsUtils.getMinorVersion() < 18) {
            on(world).call("getHandle")
                    .field("spigotConfig")
                    .set("viewDistance", getProperSimulationDistance(world));
        } else {
            on(world).call("getHandle")
                    .field("spigotConfig")
                    .set("simulationDistance", getProperSimulationDistance(world));
            on(world).call("getHandle")
                    .field("spigotConfig")
                    .set("viewDistance", getProperViewDistance(world));
        }
    }

    public static void syncSpigotViewDistances() {
        for (World world : Bukkit.getWorlds()) {
            syncSpigotViewDistances(world);
        }
    }

    private static int getProperSimulationDistance(World world) {
        if (NmsUtils.getMinorVersion() == 18) {
            return on(world).call("getHandle")
                    .call("k")
                    .field("d") // ChunkMapDistance
                    .field("t").get();
        } else if (NmsUtils.getMinorVersion() == 17) {
            return (int) on(world).call("getHandle")
                    .call("getChunkProvider")
                    .field("a")
                    .field("J").get() - 1;
        } else if (NmsUtils.getMinorVersion() <= 17) {
            return (int) on(world).call("getHandle")
                    .call("getChunkProvider")
                    .field("playerChunkMap")
                    .field("viewDistance").get() - 1;
        }
        throw new IllegalStateException("Incompatible version");
    }

    private static int getProperViewDistance(World world) {
        if (NmsUtils.getMinorVersion() == 18) {
            return (int) on(world).call("getHandle")
                    .call("k")
                    .field("a")
                    .field("L").get() - 1;
        }
        throw new IllegalStateException("Incompatible version");
    }

}
