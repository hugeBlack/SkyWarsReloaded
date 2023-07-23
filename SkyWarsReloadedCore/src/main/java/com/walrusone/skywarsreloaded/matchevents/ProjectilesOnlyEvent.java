package com.walrusone.skywarsreloaded.matchevents;

import com.walrusone.skywarsreloaded.SkyWarsReloaded;
import com.walrusone.skywarsreloaded.enums.MatchState;
import com.walrusone.skywarsreloaded.game.GameMap;
import com.walrusone.skywarsreloaded.managers.MatchManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;

public class ProjectilesOnlyEvent
        extends MatchEvent {
    private BukkitTask br;

    public ProjectilesOnlyEvent(GameMap map, boolean b) {
        gMap = map;
        enabled = b;
        File dataDirectory = SkyWarsReloaded.get().getDataFolder();
        File mapDataDirectory = new File(dataDirectory, "mapsData");

        if ((!mapDataDirectory.exists()) && (!mapDataDirectory.mkdirs())) {
            return;
        }

        File mapFile = new File(mapDataDirectory, gMap.getName() + ".yml");
        if (mapFile.exists()) {
            eventName = "ProjectilesOnlyEvent";
            slot = 11;
            material = SkyWarsReloaded.getNMS().getMaterial("SNOW_BALL");
            FileConfiguration fc = YamlConfiguration.loadConfiguration(mapFile);
            min = fc.getInt("events." + eventName + ".minStart");
            max = fc.getInt("events." + eventName + ".maxStart");
            length = fc.getInt("events." + eventName + ".length");
            chance = fc.getInt("events." + eventName + ".chance");
            title = fc.getString("events." + eventName + ".title");
            subtitle = fc.getString("events." + eventName + ".subtitle");
            startMessage = fc.getString("events." + eventName + ".startMessage");
            endMessage = fc.getString("events." + eventName + ".endMessage");
            announceEvent = fc.getBoolean("events." + eventName + ".announceTimer");
            repeatable = fc.getBoolean("events." + eventName + ".repeatable");
        }
    }

    public void onDoEvent() {
        if (gMap.getMatchState() == MatchState.PLAYING) {
            fired = true;
            sendTitle();
            gMap.setProjectilesOnly(true);
            if (length != -1) {


                br = new BukkitRunnable() {
                    public void run() {
                        endEvent(false);
                    }
                }.runTaskLater(SkyWarsReloaded.get(), length * 20L);
            }
        }
    }

    public void endEvent(boolean force) {
        if (fired) {
            if ((force) && (length != -1)) {
                br.cancel();
            }
            gMap.setProjectilesOnly(false);
            if (gMap.getMatchState() == MatchState.PLAYING) {
                MatchManager.get().message(gMap, ChatColor.translateAlternateColorCodes('&', endMessage));
            }
            if ((repeatable) || (force)) {
                resetStartTime();
                startTime += gMap.getTimer();
                fired = false;
            }
        }
    }

    public void saveEventData() {
        File dataDirectory = SkyWarsReloaded.get().getDataFolder();
        File mapDataDirectory = new File(dataDirectory, "mapsData");

        if ((!mapDataDirectory.exists()) && (!mapDataDirectory.mkdirs())) {
            return;
        }

        File mapFile = new File(mapDataDirectory, gMap.getName() + ".yml");
        if (mapFile.exists()) {
            FileConfiguration fc = YamlConfiguration.loadConfiguration(mapFile);
            fc.set("events." + eventName + ".enabled", (enabled));
            fc.set("events." + eventName + ".minStart", min);
            fc.set("events." + eventName + ".maxStart", (max));
            fc.set("events." + eventName + ".length", (length));
            fc.set("events." + eventName + ".chance", (chance));
            fc.set("events." + eventName + ".title", title);
            fc.set("events." + eventName + ".subtitle", subtitle);
            fc.set("events." + eventName + ".startMessage", startMessage);
            fc.set("events." + eventName + ".endMessage", endMessage);
            fc.set("events." + eventName + ".announceTimer", (announceEvent));
            fc.set("events." + eventName + ".repeatable", (repeatable));
            try {
                fc.save(mapFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
