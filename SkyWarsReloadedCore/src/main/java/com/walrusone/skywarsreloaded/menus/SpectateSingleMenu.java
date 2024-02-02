package com.walrusone.skywarsreloaded.menus;

import com.walrusone.skywarsreloaded.SkyWarsReloaded;
import com.walrusone.skywarsreloaded.enums.MatchState;
import com.walrusone.skywarsreloaded.game.GameMap;
import com.walrusone.skywarsreloaded.managers.GameMapManager;
import com.walrusone.skywarsreloaded.managers.MatchManager;
import com.walrusone.skywarsreloaded.utilities.Messaging;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class SpectateSingleMenu {

    private static final String menuName = new Messaging.MessageFormatter().format("menu.spectatesinglegame-menu-title");

    public SpectateSingleMenu() {

        int menuSize = 45;
        Inventory menu = Bukkit.createInventory(null, menuSize + 9, menuName);
        ArrayList<Inventory> invs = new ArrayList<>();
        invs.add(menu);

        SkyWarsReloaded.getIC().create("spectatesinglemenu", invs, event -> {
            Player player = event.getPlayer();
            GameMap gMap = MatchManager.get().getPlayerMap(player);
            if (gMap != null) {
                return;
            }

            String name = event.getName();
            if (name.equalsIgnoreCase(SkyWarsReloaded.getNMS().getItemName(SkyWarsReloaded.getIM().getItem("exitMenuItem")))) {
                player.closeInventory();
                return;
            }

            if (!JoinSingleMenu.arenaSlots.containsKey(event.getSlot())) {
                return;
            }

            gMap = SkyWarsReloaded.getGameMapMgr().getMap(JoinSingleMenu.arenaSlots.get(event.getSlot()));
            if (gMap == null) {
                return;
            }

            if (player.hasPermission("sw.spectate")) {
                player.closeInventory();
                if (gMap.getMatchState() != MatchState.OFFLINE && gMap.getMatchState() != MatchState.ENDING) {
                    GameMap finalGMap = gMap;
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            SkyWarsReloaded.get().getPlayerManager().addSpectator(finalGMap, player);
                        }
                    }.runTaskLater(SkyWarsReloaded.get(), 5);
                }
            }
        });
    }

}