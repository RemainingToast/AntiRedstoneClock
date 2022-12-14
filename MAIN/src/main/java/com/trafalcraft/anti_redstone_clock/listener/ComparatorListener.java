package com.trafalcraft.anti_redstone_clock.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

import com.trafalcraft.anti_redstone_clock.util.CheckTPS;

public class ComparatorListener implements Listener {

    private Material comparatorMaterial;

    public ComparatorListener(Material comparatorMaterial) {
        this.comparatorMaterial = comparatorMaterial;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRedstoneComparatorClock(BlockRedstoneEvent e) {
        if (CheckTPS.isTpsOK() && comparatorMaterial == e.getBlock().getType() && e.getOldCurrent() == 0 && !Util.checkIgnoreWorldsAndRegions(e.getBlock())) {
            Util.checkAndUpdateRedstoneClockState(e.getBlock());
        }
    }
}
