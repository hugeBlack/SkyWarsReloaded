package com.walrusone.skywarsreloaded.game;

import com.walrusone.skywarsreloaded.enums.Vote;
import com.walrusone.skywarsreloaded.menus.gameoptions.objects.CoordLoc;
import com.walrusone.skywarsreloaded.menus.gameoptions.objects.GameKit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerCard {

    private TeamCard tCard;
    private UUID uuid;
    // The index at which the player used for this PlayerCard joined the game
    private int joinIndex;
    private boolean dead;
    private boolean ready;

    private CoordLoc spawn;

    private GameKit kitVote;
    private Vote gameTime;
    private Vote weather;
    private Vote chestVote;
    private Vote modifier;
    private Vote health;

    public PlayerCard(TeamCard tCard, UUID uuid, CoordLoc spawn) {
        this.tCard = tCard;
        this.spawn = spawn;
        this.uuid = uuid;
        this.joinIndex = -1;
        this.kitVote = null;
        this.gameTime = null;
        this.weather = null;
        this.chestVote = null;
        this.modifier = null;
        this.health = null;
        this.dead = false;
        this.ready = false;
    }

    public void reset() {
        this.uuid = null;
        this.joinIndex = -1;
        this.kitVote = null;
        this.gameTime = null;
        this.weather = null;
        this.chestVote = null;
        this.modifier = null;
        this.health = null;
        this.dead = false;
        this.ready = false;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Player getPlayer() {
        if (uuid == null) {
            return null;
        }
        return Bukkit.getPlayer(uuid);
    }

    @Deprecated
    public void setPlayer(Player player) {
        this.setPlayer(player, -1);
    }

    public void setPlayer(Player player, int joinIndexIn) {
        if (player != null) {
            this.uuid = player.getUniqueId();
            this.joinIndex = joinIndexIn;
            return;
        }
        this.uuid = null;
    }

    public CoordLoc getSpawn() {
        return this.spawn;
    }

    public void setSpawn(CoordLoc loc) {
        this.spawn = loc;
    }

    public GameKit getKitVote() {
        return this.kitVote;
    }

    void setKitVote(GameKit kitVote) {
        this.kitVote = kitVote;
    }

    public void setGameTime(Vote gameTime) {
        this.gameTime = gameTime;
    }

    public void setHealth(Vote health) {
        this.health = health;
    }

    public Vote getVote(String type) {
        if (type.equalsIgnoreCase("time")) {
            return this.gameTime;
        } else if (type.equalsIgnoreCase("chest")) {
            return this.chestVote;
        } else if (type.equalsIgnoreCase("weather")) {
            return this.weather;
        } else if (type.equalsIgnoreCase("modifier")) {
            return this.modifier;
        } else if (type.equalsIgnoreCase("health")) {
            return this.health;
        }
        return null;
    }

    public void setWeather(Vote weather) {
        this.weather = weather;
    }

    public void setChestVote(Vote chestVote) {
        this.chestVote = chestVote;
    }

    public void setModifier(Vote modifier) {
        this.modifier = modifier;
    }

    public UUID getUUID() {
        return uuid;
    }

    public TeamCard getTeamCard() {
        return tCard;
    }

    public int getJoinIndex() {
        return this.joinIndex;
    }

    public boolean getReady() {
        return this.ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}