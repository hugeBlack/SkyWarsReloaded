package com.walrusone.skywarsreloaded.api.impl;

import com.walrusone.skywarsreloaded.SkyWarsReloaded;
import com.walrusone.skywarsreloaded.api.SWRGameAPI;
import com.walrusone.skywarsreloaded.api.SkywarsReloadedAPI;
import com.walrusone.skywarsreloaded.enums.GameType;
import com.walrusone.skywarsreloaded.game.GameMap;
import com.walrusone.skywarsreloaded.managers.GameMapManager;
import com.walrusone.skywarsreloaded.managers.MatchManager;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class SWRGameImpl implements SWRGameAPI {

    private final SkywarsReloadedAPI swrAPI;
    private final MatchManager matchManager;

    public SWRGameImpl(SkywarsReloadedAPI swrAPIIn) {
        this.swrAPI = swrAPIIn;
        this.matchManager = MatchManager.get();
    }

    @Override
    public List<GameMap> getPlayableGames() {
        return SkyWarsReloaded.getGameMapMgr().getPlayableArenas(GameType.ALL);
    }

    @Override
    public List<GameMap> getPlayableGames(GameType type) {
        return SkyWarsReloaded.getGameMapMgr().getPlayableArenas(type);
    }

    @Override
    public List<GameMap> getGames() {
        return SkyWarsReloaded.getGameMapMgr().getMapsCopy();
    }

    @Override
    public List<GameMap> getGames(GameType type) {
        List<GameMap> maps = SkyWarsReloaded.getGameMapMgr().getMapsCopy();
        if (type == GameType.ALL) return maps;
        return maps.stream()
                .filter(gameMap ->
                        type == GameType.SINGLE ? gameMap.getTeamSize() == 1 : gameMap.getTeamSize() > 1)
                .collect(Collectors.toList());
    }

    @Override
    public GameMap getGame(String name) {
        return SkyWarsReloaded.getGameMapMgr().getMap(name);
    }

    @Override
    public List<GameMap> getSortedGames() {
        return SkyWarsReloaded.getGameMapMgr().getSortedArenas();
    }

    @Override
    public GameMap getGame(Player player) {
        return this.matchManager.getPlayerMap(player);
    }

    @Override
    public MatchManager getMatchManager() {
        return matchManager;
    }
}
