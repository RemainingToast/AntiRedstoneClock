package com.trafalcraft.antiRedstoneClock.util.plotSquared;

import com.trafalcraft.anti_redstone_clock.util.plotSquared.IPlotSquared;

import com.plotSquared.core.PlotSquared;

import org.bukkit.Location;

public class PlotSquared_6 implements IPlotSquared{

    @Override
    public void init() {
        PlotSquared.toString();
    }

    @Override
    public boolean isAllowedPlot(Location location) {
        return false;
    }

    @Override
    public String getVersion() {
        return null;
    }
}
