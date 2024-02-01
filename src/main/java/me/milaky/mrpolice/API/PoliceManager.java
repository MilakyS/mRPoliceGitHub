package me.milaky.mrpolice.API;

import me.milaky.mrpolice.Data.DataManager;
import me.milaky.mrpolice.MRPolice;
import org.bukkit.entity.Player;




public class PoliceManager {

    DataManager manager = MRPolice.getInstance().data;
    public void setWanted(Player p, int WantedLevel){
        manager.setWanted(p, WantedLevel);
    }
    public int getWanted(Player p){
        return manager.getWanted(p);
    }

}
