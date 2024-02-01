package me.milaky.mrpolice.Commands;

import me.milaky.mrpolice.MRPolice;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;


public class handcuffs implements CommandExecutor {
    MRPolice plugin;

    public handcuffs(MRPolice plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player p = (Player) commandSender;
        if(p.hasPermission("police.policeman")){
            ItemStack handcuffs = new ItemStack(Material.PAPER);
            ItemMeta handcuffsmeta = handcuffs.getItemMeta();
            handcuffsmeta.setDisplayName(this.plugin.getConfig().getString("Settings.Handcuffs.Name").replace("&", "§"));
            handcuffsmeta.setCustomModelData(this.plugin.getConfig().getInt("Settings.Handcuffs.CustomModelData"));
            handcuffs.setItemMeta(handcuffsmeta);

            p.getInventory().addItem(handcuffs);
            p.sendMessage(plugin.getConfig().getString("Messages.HandcuffsGot").replace("&", "§"));
        }
        return true;
    }
}
