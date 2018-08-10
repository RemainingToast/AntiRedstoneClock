package com.trafalcraft.antiRedstoneClock;

import com.trafalcraft.antiRedstoneClock.commands.*;
import com.trafalcraft.antiRedstoneClock.listener.ComparatorListener;
import com.trafalcraft.antiRedstoneClock.listener.ObserverListener;
import com.trafalcraft.antiRedstoneClock.listener.PistonListener;
import com.trafalcraft.antiRedstoneClock.listener.RedstoneListener;
import com.trafalcraft.antiRedstoneClock.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.error.YAMLException;

import java.util.ArrayList;
import java.util.Collection;

public class Main extends JavaPlugin {

    private static Main instance;
    //Store region and world ignored by the plugin
    private static final ArrayList<String> ignoredWorlds = new ArrayList<>();
    private static final ArrayList<String> ignoredRegions = new ArrayList<>();

    public void onEnable() {
        long startTime = System.currentTimeMillis();

        instance = this;

        instance.saveDefaultConfig();
        instance.getConfig().options().copyDefaults(true);
        instance.saveConfig();
        instance.reloadConfig();

        try {
            Msg.load();
        } catch (YAMLException e) {
            e.printStackTrace();
        }

        //Register events depend on user preferences in config.yml file
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        if (instance.getConfig().getBoolean("checkedClock.comparator")) {
            Bukkit.getServer().getPluginManager().registerEvents(new ComparatorListener(), this);
        }
        try {
            Material checked = Material.OBSERVER;
            if (instance.getConfig().getBoolean("checkedClock.observer")) {
                Bukkit.getServer().getPluginManager().registerEvents(new ObserverListener(), this);
            }
        } catch (java.lang.NoSuchFieldError ignored) {
        }
        if (instance.getConfig().getBoolean("checkedClock.piston")) {
            Bukkit.getServer().getPluginManager().registerEvents(new PistonListener(), this);
        }
        if (instance.getConfig().getBoolean("checkedClock.redstoneAndRepeater")) {
            Bukkit.getServer().getPluginManager().registerEvents(new RedstoneListener(), this);
        }
        long endTime = System.currentTimeMillis();

        long duration = (endTime - startTime);
        this.getLogger().info("Plugin loaded in " + duration + "ms");  //2ms
    }

    public void onDisable() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("antiredstoneclock")) {
            if (sender.isOp() || sender.hasPermission("antiRedstoneClock.Admin")) {
                if (args.length == 0) {
                    Msg.getHelp(sender);
                    return false;
                }
                if (args[0].equalsIgnoreCase("Reload")) {
                    Reload.getInstance().performCMD(sender);
                } else if (args[0].equalsIgnoreCase("checkList")) {
                    CheckList.getInstance().performCMD(sender, args);
                } else if (args[0].equalsIgnoreCase("setMaxPulses")) {
                    SetMaxPulses.getInstance().performCMD(sender, args);
                } else if (args[0].equalsIgnoreCase("SetDelay")) {
                    SetDelay.getInstance().performCMD(sender, args);
                } else if (args[0].equalsIgnoreCase("NotifyAdmin")) {
                    NotifyAdmin.getInstance().performCMD(sender, args);
                } else if (args[0].equalsIgnoreCase("AutoRemoveDetectedClock")) {
                    AutoRemoveDetectedClock.getInstance().performCMD(sender, args);
                } else if (args[0].equalsIgnoreCase("CreateSignWhenClockIsBreak")) {
                    CreateSignWhenClockIsBreak.getInstance().performCMD(sender, args);
                } else {
                    Msg.getHelp(sender);
                }
            } else {
                sender.sendMessage(Msg.UNKNOWN_CMD.toString());
            }
        } else {
            sender.sendMessage(Msg.UNKNOWN_CMD.toString());
        }
        return false;
    }


    public static Main getInstance() {
        return instance;
    }

    public static Collection<String> getIgnoredWorlds() {
        return ignoredWorlds;
    }

    public static Collection<String> getIgnoredRegions() {
        return ignoredRegions;
    }

}