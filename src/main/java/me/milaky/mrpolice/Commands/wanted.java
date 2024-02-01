package me.milaky.mrpolice.Commands;


import me.milaky.mrpolice.Data.DataManager;
import me.milaky.mrpolice.Events.PlayerWantedSet;
import me.milaky.mrpolice.MRPolice;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class wanted implements CommandExecutor {
    public MRPolice plugin;

    public wanted(MRPolice plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        DataManager dataManager = new DataManager(this.plugin);
        Player pl = (Player) commandSender;
        if (args[0].equalsIgnoreCase("set")) {
            if(pl.hasPermission("police.policeman") || pl.hasPermission("police.admin")) {
                if (args.length == 3) {
                    Player target = Bukkit.getPlayer(args[1]);
                    int WantedLvl = Integer.parseInt(args[2]);
                    if (target != null) {
                        PlayerWantedSet customEvent = new PlayerWantedSet(pl, target, WantedLvl);
                        Bukkit.getServer().getPluginManager().callEvent(customEvent);
                        if (!customEvent.isCancelled()) {
                            dataManager.setWanted(target, WantedLvl);
                            String message = this.plugin.getConfig().getString("Messages.WantedSet").replace("&", "§").replace("%player%", pl.getName()).replace("%target%", target.getName()).replace("%wantedlvl%", String.valueOf(dataManager.getWanted(target)));
                            Bukkit.broadcast(message, "police.policeman");
                        }
                    }
                }
            }
        }
        if (args[0].equalsIgnoreCase("get")) {
            Player targetPl = Bukkit.getPlayer(args[1]);
            if (targetPl != null) {
                pl.sendMessage(this.plugin.getConfig().getString("Messages.WantedGet").replace("&", "§").replace("%wanted%", String.valueOf(dataManager.getWanted(targetPl)).replace("&", "§")));
            }
        }
        if(args[0].equalsIgnoreCase("list")){
            boolean first = true;
            for(Player p : this.plugin.wanted.keySet()){
                String wantedList = p.getName() + ": " + this.plugin.wanted.get(p);
                if(first){
                    for(String msg : this.plugin.getConfig().getStringList("Messages.WantedList")){
                        pl.sendMessage(msg.replace("%wantedlist%", wantedList).replace("&", "§"));
                    }
                    first = false;
                } else {
                    pl.sendMessage(wantedList);
                }
            }
        }

        return true;
    }
}
