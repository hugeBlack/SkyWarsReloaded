package com.walrusone.skywarsreloaded.nms.v1_19_R1;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.EnderChest;

public class NMSHandler extends com.walrusone.skywarsreloaded.nms.v1_18_R2.NMSHandler {

    public void playChestAction(Block block, boolean open) {
        Location location = block.getLocation();
        if (location.getWorld() == null) return;

        if (!(block.getState() instanceof EnderChest enderChest)) return;

        if (open) enderChest.open();
        else enderChest.close();
    }

    public int getVersion() {
        return 19;
    }

}
