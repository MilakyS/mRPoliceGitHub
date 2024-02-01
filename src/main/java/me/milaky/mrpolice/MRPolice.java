package me.milaky.mrpolice;

import me.milaky.mrpolice.Commands.handcuffs;
import me.milaky.mrpolice.Commands.prison;
import me.milaky.mrpolice.Commands.wanted;
import me.milaky.mrpolice.Data.DataManager;
import me.milaky.mrpolice.Events.PlayerWantedSet;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import me.milaky.mrpolice.API.*;
import org.bukkit.util.Vector;

import java.util.HashMap;

public final class MRPolice  extends JavaPlugin implements Listener {
    public MRPolice instance;
    public DataManager data;
    public HashMap<Player, Integer> wanted = new HashMap<>();
    public HashMap<Player, Player> handcuffs = new HashMap<>();



    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        data = new DataManager(this);
        this.getServer().getPluginCommand("wanted").setExecutor(new wanted(this));
        this.getServer().getPluginCommand("prison").setExecutor(new prison(this));
        this.getServer().getPluginCommand("handcuffs").setExecutor(new handcuffs(this));
        this.getServer().getPluginManager().registerEvents(this, this);
    }
    @EventHandler
    public void interact(PlayerInteractEntityEvent event){
        ItemStack item = event.getPlayer().getItemInHand();
        Player p = event.getPlayer();
        Player pl = (Player) event.getRightClicked();
        if(pl != null) {
            if(item.getType() == Material.PAPER) {
                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta.getDisplayName().equals(this.getConfig().getString("Settings.Handcuffs.Name").replace("&", "§"))) {
                    if (itemMeta.getCustomModelData() == this.getConfig().getInt("Settings.Handcuffs.CustomModelData")) {
                            pl.sendMessage(this.getConfig().getString("Messages.HandcuffNotify").replace("&", "§"));
                            handcuffs.put(p, pl);
                            p.sendMessage(this.getConfig().getString("Messages.Handcuffed").replace("&", "§"));
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player p = event.getPlayer();
        if(handcuffs.containsKey(p)){
            Player target = handcuffs.get(p);

            target.setVelocity(target.getVelocity().add(p.getLocation().toVector().subtract(target.getLocation().toVector()).normalize().multiply(0.1)));
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(handcuffs.containsValue(p)){
            p.sendMessage(this.getConfig().getString("Messages.InteractErrorHandcuffs").replace("&", "§"));
            e.setCancelled(true);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        data.Setup(p);
        if(data.getWanted(p) > 0){
            this.wanted.put(p, data.getWanted(p));
        }
        if(data.getPrisonTimer(p) != 0){
            data.PrisonTimer(p);
        }
    }

    public MRPolice getInstance() {
        return instance;
    }


}
