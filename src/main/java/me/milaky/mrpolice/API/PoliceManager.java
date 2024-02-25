package me.milaky.mrpolice.API;

import me.milaky.mrpolice.Data.DataManager;
import me.milaky.mrpolice.MRPolice;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;




public class PoliceManager {
    MRPolice plugin;

    public PoliceManager(){
        this.plugin = MRPolice.getInstance();
    }

    DataManager manager = this.plugin.data;
    public void setWanted(Player p, int WantedLevel){
        manager.setWanted(p, WantedLevel);
    }
    public int getWanted(Player p){
        return manager.getWanted(p);
    }

}
