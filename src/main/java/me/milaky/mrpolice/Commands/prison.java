package me.milaky.mrpolice.Commands;

import me.milaky.mrpolice.Data.DataManager;
import me.milaky.mrpolice.Events.PlayerArrestedEvent;
import me.milaky.mrpolice.MRPolice;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class prison implements CommandExecutor {
    public MRPolice plugin;

    public prison(MRPolice plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player p = (Player) commandSender;
        DataManager dataManager = new DataManager(this.plugin);
        if (args[0].equalsIgnoreCase("arrest")) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target != null && dataManager.getWanted(target) > 0) {
                PlayerArrestedEvent event = new PlayerArrestedEvent(target, p);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    dataManager.setPrisonTime(target);
                    dataManager.setWanted(target, 0);
                    this.plugin.wanted.remove(target);
                    dataManager.PrisonTimer(target);
                    String message = this.plugin.getConfig().getString("Messages.ArrestedPlayer").replace("&", "§").replace("%target%", target.getName()).replace("%player%", p.getName()).replace("&", "§");
                    Bukkit.broadcast(message, "police.policeman");
                }
            }
        }
        if (args[0].equalsIgnoreCase("setPrison")) {
            if (p.hasPermission("police.admin")) {
                Location loc = p.getLocation();
                plugin.getConfig().set("Settings.Prison", loc);
                plugin.saveConfig();
                p.sendMessage(this.plugin.getConfig().getString("Messages.SetPrison").replace("&", "§"));
            }
        }
        if (args[0].equalsIgnoreCase("setAfterPrison")) {
            if (p.hasPermission("police.admin")) {
                Location loc = p.getLocation();
                plugin.getConfig().set("Settings.AfterPrison", loc);
                plugin.saveConfig();
                p.sendMessage(this.plugin.getConfig().getString("Messages.SetAfterPrison").replace("&", "§"));
            }
        }

        return true;
    }
}
