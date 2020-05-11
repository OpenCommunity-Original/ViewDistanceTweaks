package com.froobworld.viewdistancetweaks.config;

import com.froobworld.nabconfiguration.*;
import com.froobworld.nabconfiguration.annotations.Entry;
import com.froobworld.nabconfiguration.annotations.Section;
import com.froobworld.nabconfiguration.annotations.SectionMap;
import com.froobworld.viewdistancetweaks.ViewDistanceTweaks;
import com.froobworld.viewdistancetweaks.limiter.adjustmentmode.AdjustmentMode;
import org.bukkit.World;

import java.io.File;

public class VdtConfig extends NabConfiguration {
    public static final int VERSION = 2;

    public VdtConfig(ViewDistanceTweaks viewDistanceTweaks) {
        super(
                new File(viewDistanceTweaks.getDataFolder(), "config.yml"),
                () -> viewDistanceTweaks.getResource("resources/config.yml"),
                version -> viewDistanceTweaks.getResource("resources/config-patches/" + version + ".patch"),
                VERSION
        );
    }

    @Entry(key = "enabled")
    public final ConfigEntry<Boolean> enabled = new ConfigEntry<>();

    @Entry(key = "adjustment-mode")
    public final ConfigEntry<AdjustmentMode.Mode> adjustmentMode = ConfigEntries.enumEntry(AdjustmentMode.Mode.class);

    @Section(key = "proactive-mode-settings")
    public ProactiveModeSettings proactiveMode = new ProactiveModeSettings();

    public static class ProactiveModeSettings extends ConfigSection {

        @Entry(key = "global-chunk-count-target")
        public final ConfigEntry<Integer> globalChunkCountTarget = ConfigEntries.integerEntry();

    }

    @Section(key = "reactive-mode-settings")
    public ReactiveModeSettings reactiveMode = new ReactiveModeSettings();

    public static class ReactiveModeSettings extends ConfigSection {

        @Entry(key = "decrease-tps-threshold")
        public final ConfigEntry<Double> decreaseTpsThreshold = ConfigEntries.doubleEntry();

        @Entry(key = "increase-tps-threshold")
        public final ConfigEntry<Double> increaseTpsThreshold = ConfigEntries.doubleEntry();

        @Section(key = "tps-prediction")
        public final TpsPredictionSettings tpsPrediction = new TpsPredictionSettings();

        @Section(key = "tps-tracker-settings")
        public TpsTrackerSettings tpsTracker = new TpsTrackerSettings();

        public static class TpsTrackerSettings extends ConfigSection {

            @Entry(key = "collection-period")
            public final ConfigEntry<Integer> collectionPeriod = ConfigEntries.integerEntry();

            @Entry(key = "trim-outliers-to-within")
            public final ConfigEntry<Double> trimOutliersPercent= ConfigEntries.doubleEntry();

        }

        public static class TpsPredictionSettings extends ConfigSection {

            @Entry(key = "enabled")
            public final ConfigEntry<Boolean> enabled = new ConfigEntry<>();

            @Entry(key = "history-length")
            public final ConfigEntry<Long> historyLength = ConfigEntries.longEntry();

        }

    }

    @Entry(key = "ticks-per-check")
    public final ConfigEntry<Long> ticksPerCheck = ConfigEntries.longEntry();

    @Entry(key = "passed-checks-for-increase")
    public final ConfigEntry<Integer> passedChecksForIncrease = new ConfigEntry<>();

    @Entry(key = "passed-checks-for-decrease")
    public final ConfigEntry<Integer> passedChecksForDecrease = new ConfigEntry<>();

    @Entry(key = "log-view-distance-changes")
    public final ConfigEntry<Boolean> logViewDistanceChanges = new ConfigEntry<>();

    @SectionMap(key = "world-settings", defaultKey = "default")
    public final ConfigSectionMap<World, WorldSettings> worldSettings = new ConfigSectionMap<>(World::getName, WorldSettings.class);

    public static class WorldSettings extends ConfigSection {

        @Entry(key = "minimum-view-distance")
        public final ConfigEntry<Integer> minimumViewDistance = new ConfigEntry<>();

        @Entry(key = "maximum-view-distance")
        public final ConfigEntry<Integer> maximumViewDistance = new ConfigEntry<>();

        @Section(key = "chunk-counter-settings")
        public final ChunkCounterSettings chunkCounter = new ChunkCounterSettings();

        @Entry(key = "chunk-weight")
        public final ConfigEntry<Double> chunkWeight = ConfigEntries.doubleEntry();

        public static class ChunkCounterSettings extends ConfigSection {

            @Entry(key = "exclude-overlap")
            public final ConfigEntry<Boolean> excludeOverlap = new ConfigEntry<>();

        }

    }

    @Section(key = "paper-settings")
    public final PaperSettings paperSettings = new PaperSettings();

    public static class PaperSettings extends ConfigSection {

        @Section(key = "no-tick-view-distance")
        public final NoTickViewDistanceSettings noTickViewDistance = new NoTickViewDistanceSettings();

        public static class NoTickViewDistanceSettings extends ConfigSection {

            @Entry(key = "enabled")
            public final ConfigEntry<Boolean> enabled = new ConfigEntry<>();

            @Entry(key = "global-chunk-count-target")
            public final ConfigEntry<Integer> globalChunkCountTarget = new ConfigEntry<>();

        }

        @SectionMap(key = "world-settings", defaultKey = "default")
        public final ConfigSectionMap<World, WorldSettings> worldSettings = new ConfigSectionMap<>(World::getName, WorldSettings.class);

        public static class WorldSettings extends ConfigSection {

            @Entry(key = "minimum-no-tick-view-distance")
            public final ConfigEntry<Integer> minimumNoTickViewDistance = new ConfigEntry<>();

            @Entry(key = "maximum-no-tick-view-distance")
            public final ConfigEntry<Integer> maximumNoTickViewDistance = new ConfigEntry<>();

        }
    }

}
