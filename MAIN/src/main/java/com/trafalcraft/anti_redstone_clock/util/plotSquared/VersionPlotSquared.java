package com.trafalcraft.anti_redstone_clock.util.plotSquared;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.trafalcraft.anti_redstone_clock.Main;

public class VersionPlotSquared {

    private static VersionPlotSquared instance = null;
    private IPlotSquared plotSquared;

    private VersionPlotSquared() {
        super();
    }

    public static synchronized VersionPlotSquared getInstance() {
        if (VersionPlotSquared.instance == null) {
            VersionPlotSquared.instance = new VersionPlotSquared();
            VersionPlotSquared.instance.init();
        }
        return VersionPlotSquared.instance;
    }

    private void init() {
        if (Main.getInstance().getConfig().getBoolean("plotSquaredSupport")) {
            Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PlotSquared");
            if (plugin == null) {
                Main.getInstance().getLogger().warning("PlotSquared hasn't been found!");
                return;
            }
            String plotSquaredVersion = plugin.getDescription().getVersion().split("\\.")[0];
            try {
                ClassLoader classLoader = Main.class.getClassLoader();
                classLoader.loadClass("com.trafalcraft.antiRedstoneClock.util.plotSquared.PlotSquared_" + plotSquaredVersion);
                Class<?> aClass = Class.forName("com.trafalcraft.antiRedstoneClock.util.plotSquared.PlotSquared_" + plotSquaredVersion);
                plotSquared = (IPlotSquared) aClass.getDeclaredConstructors()[0].newInstance();
            } catch (Exception e) {
                Main.getInstance().getLogger().warning("PlotSquared " + plotSquaredVersion + " is not supported");
            }
        }
    }

    public IPlotSquared getPlotSquared() {
        return plotSquared;
    }
} 
