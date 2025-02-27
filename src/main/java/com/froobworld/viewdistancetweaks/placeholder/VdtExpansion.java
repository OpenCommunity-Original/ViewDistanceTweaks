package com.froobworld.viewdistancetweaks.placeholder;

import com.froobworld.viewdistancetweaks.ViewDistanceTweaks;
import com.froobworld.viewdistancetweaks.hook.tick.PaperTickHook;
import com.froobworld.viewdistancetweaks.hook.viewdistance.SimulationDistanceHook;
import com.froobworld.viewdistancetweaks.hook.viewdistance.ViewDistanceHook;
import com.froobworld.viewdistancetweaks.util.ChunkCounter;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class VdtExpansion extends PlaceholderExpansion {
    private static final String IDENTIFIER = "viewdistancetweaks";
    private static final String AUTHOR = "froobynooby";
    private static final String VERSION = "1";

    private final ViewDistanceTweaks viewDistanceTweaks;

    public VdtExpansion(ViewDistanceTweaks viewDistanceTweaks) {
        this.viewDistanceTweaks = viewDistanceTweaks;
    }

    @Override
    public @NotNull String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public @NotNull String getAuthor() {
        return AUTHOR;
    }

    @Override
    public @NotNull String getVersion() {
        return VERSION;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        SimulationDistanceHook simulationDistanceHook = viewDistanceTweaks.getHookManager().getSimulationDistanceHook();
        ChunkCounter chunkCounter = viewDistanceTweaks.getHookManager().getChunkCounter();
        ViewDistanceHook viewDistanceHook = viewDistanceTweaks.getHookManager().getViewDistanceHook();
        ChunkCounter noTickChunkCounter = viewDistanceTweaks.getHookManager().getNoTickChunkCounter();
        if (params.startsWith("simulation_distance")) {
            World world = null;

            if (params.equalsIgnoreCase("simulation_distance")) {
                world = player.getWorld();
            } else if (params.startsWith("simulation_distance_")) {
                world = Bukkit.getWorld(params.replace("simulation_distance_", ""));
            }
            return world == null ? null : ("" + simulationDistanceHook.getDistance(world));
        }

        if (viewDistanceHook != null && params.startsWith("view_distance")) {
            World world = null;

            if (params.equalsIgnoreCase("view_distance")) {
                world = player.getWorld();
            } else if (params.startsWith("view_distance_")) {
                world = Bukkit.getWorld(params.replace("iew_distance_", ""));
            }
            return world == null ? null : ("" + viewDistanceHook.getDistance(world));
        }

        if (params.startsWith("tick_chunk_count")) {
            World world = null;

            if (params.equalsIgnoreCase("tick_chunk_count")) {
                world = player.getWorld();
            } else if (params.startsWith("tick_chunk_count_")) {
                world = Bukkit.getWorld(params.replace("tick_chunk_count_", ""));
            }
            return world == null ? null : ("" + (int) chunkCounter.countChunks(world, simulationDistanceHook.getDistance(world)));
        }

        if (noTickChunkCounter != null && viewDistanceHook != null && params.startsWith("no_tick_chunk_count")) {
            World world = null;

            if (params.equalsIgnoreCase("no_tick_chunk_count")) {
                world = player.getWorld();
            } else if (params.startsWith("no_tick_chunk_count_")) {
                world = Bukkit.getWorld(params.replace("no_tick_chunk_count_", ""));
            }
            return world == null ? null : ("" + (int) noTickChunkCounter.countChunks(world, viewDistanceHook.getDistance(world)));
        }

        if (params.startsWith("chunk_count")) {
            World world = null;

            if (params.equalsIgnoreCase("chunk_count")) {
                world = player.getWorld();
            } else if (params.startsWith("chunk_count_")) {
                world = Bukkit.getWorld(params.replace("chunk_count_", ""));
            }
            return world == null ? null : ("" + (noTickChunkCounter == null || viewDistanceHook == null ? 0 : (int) noTickChunkCounter.countChunks(world, viewDistanceHook.getDistance(world)) + (int) chunkCounter.countChunks(world, simulationDistanceHook.getDistance(world))));
        }

        if (params.equalsIgnoreCase("global_tick_chunk_count")) {
            int count = 0;
            for (World world : Bukkit.getWorlds()) {
                count += chunkCounter.countChunks(world, simulationDistanceHook.getDistance(world));
            }
            return "" + count;
        }

        if (noTickChunkCounter != null && viewDistanceHook != null && params.equalsIgnoreCase("global_no_tick_chunk_count")) {
            int count = 0;
            for (World world : Bukkit.getWorlds()) {
                count += noTickChunkCounter.countChunks(world, viewDistanceHook.getDistance(world));
            }
            return "" + count;
        }

        if (params.equalsIgnoreCase("global_chunk_count")) {
            int count = 0;
            for (World world : Bukkit.getWorlds()) {
                count += chunkCounter.countChunks(world, simulationDistanceHook.getDistance(world));
                if (noTickChunkCounter != null && viewDistanceHook != null) {
                    count += noTickChunkCounter.countChunks(world, viewDistanceHook.getDistance(world));
                }
            }
            return "" + count;
        }

        if (params.equalsIgnoreCase("tps_colour") || params.equalsIgnoreCase("tps_color")) {
            double tps = viewDistanceTweaks.getTaskManager().getTpsTracker().getTps();
            if (tps < 16) {
                return ChatColor.RED + "";
            } else if (tps < 18) {
                return ChatColor.YELLOW + "";
            } else {
                return ChatColor.GREEN + "";
            }
        }

        if (PaperTickHook.isCompatible() && (params.equalsIgnoreCase("mspt_colour") || params.equalsIgnoreCase("mspt_color"))) {
            double mspt = viewDistanceTweaks.getTaskManager().getMsptTracker().getMspt();
            if (mspt > 50) {
                return ChatColor.RED + "";
            } else if (mspt > 40) {
                return ChatColor.YELLOW + "";
            } else {
                return ChatColor.GREEN + "";
            }
        }

        if (params.equalsIgnoreCase("tps")) {
            return BigDecimal.valueOf(Math.min(viewDistanceTweaks.getTaskManager().getTpsTracker().getTps(), 20)).setScale(2, RoundingMode.HALF_UP).toString();
        }

        if (PaperTickHook.isCompatible() && params.equalsIgnoreCase("mspt")) {
            return BigDecimal.valueOf(viewDistanceTweaks.getTaskManager().getMsptTracker().getMspt()).setScale(2, RoundingMode.HALF_UP).toString();
        }

        return null;
    }

}
