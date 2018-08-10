package com.trafalcraft.antiRedstoneClock.listener;

import com.trafalcraft.antiRedstoneClock.Main;
import com.trafalcraft.antiRedstoneClock.object.RedstoneClock;
import com.trafalcraft.antiRedstoneClock.object.RedstoneClockController;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Powerable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class ObserverListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRedstoneComparatorClock(BlockRedstoneEvent e) {
        if (Util.checkIgnoreWorldsAndRegions(e.getBlock()))
            return;
        if (checkTypeAndItemPowered(e.getBlock())) {
            if (!RedstoneClockController.contains(e.getBlock().getLocation())) {
                try {
                    RedstoneClockController.addRedstone(e.getBlock().getLocation());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else {
                RedstoneClock redstoneClock = RedstoneClockController.getRedstoneClock(e.getBlock().getLocation());
                if (redstoneClock.getLastStatus() == 1) {
                    if (!redstoneClock.isEnd()) {
                        if (redstoneClock.getNumberOfClock() >= Main.getInstance().getConfig().getInt("MaxPulses")) {
                            Util.removeRedstoneClock(e.getBlock());
                        } else {
                            redstoneClock.addOneToClock();
                            redstoneClock.updateStatus(0);
                        }
                    }
                } else {
                    redstoneClock.updateStatus(1);
                }
            }
        }
    }

    private boolean checkTypeAndItemPowered(Block block) {
        boolean result = false;
        if (block.getType() == Material.OBSERVER) {
            Powerable powerable = (Powerable) block.getBlockData();
            if (powerable.isPowered()) {
                result = true;
            }
        }
        return result;
    }

}