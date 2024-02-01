package me.milaky.mrpolice.Data;

import me.milaky.mrpolice.MRPolice;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


import java.io.File;
import java.io.IOException;
import java.util.UUID;


public class DataManager {
    public MRPolice plugin;
    File f;
    File dataFile;
    YamlConfiguration yml;

    public DataManager(MRPolice plugin){
        this.plugin = plugin;
    }

    public void Setup(Player p){
        UUID uuid = p.getUniqueId();
        File f = new File(this.plugin.getDataFolder() + "/Data/");
        File dataFile = new File(f, File.separator + uuid + ".yml");
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);

        if(!f.exists()){
            try{
                f.mkdir();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        if(!dataFile.exists()){
            try {
                dataFile.createNewFile();
                yml.createSection("Stats");
                yml.createSection("Prison");
                yml.set("Stats.wantedLvl", 0);
                yml.set("Prison.BeenInPrison", 0);
                yml.save(dataFile);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    public File getPlayerYaml(UUID string)
    {
        return new File(this.plugin.getDataFolder()+ "/Data/" + string + ".yml");
    }

    public void setWanted(Player p, int wantedLvl){
        File file = getPlayerYaml(p.getUniqueId());
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        try {
            if (wantedLvl > 5) {
                yml.set("Stats.wantedLvl", 5);
                this.plugin.wanted.put(p, 5);
            }
            else if (wantedLvl == 0) {
                yml.set("Stats.wantedLvl", 0);
                this.plugin.wanted.remove(p);
            }
            else {
                yml.set("Stats.wantedLvl", wantedLvl);
                this.plugin.wanted.put(p, wantedLvl);
            }
            yml.save(file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setPrisonTime(Player p){
        File file = getPlayerYaml(p.getUniqueId());
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        try {
            switch (getWanted(p)) {
                case 1:
                    yml.set("Prison.ArrestTime", this.plugin.getConfig().getInt("ArrestTime.1"));
                    break;
                case 2:
                    yml.set("Prison.ArrestTime", this.plugin.getConfig().getInt("ArrestTime.2"));
                    break;
                case 3:
                    yml.set("Prison.ArrestTime", this.plugin.getConfig().getInt("ArrestTime.3"));
                    break;
                case 4:
                    yml.set("Prison.ArrestTime", this.plugin.getConfig().getInt("ArrestTime.4"));
                    break;
                case 5:
                    yml.set("Prison.ArrestTime", this.plugin.getConfig().getInt("ArrestTime.5"));
                    break;

            }
            yml.save(file);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public int getPrisonTimer(Player p){
        File file = getPlayerYaml(p.getUniqueId());
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        return yml.getInt("Prison.ArrestTime");

    }
    public void PrisonTimer(Player p){
        File file = getPlayerYaml(p.getUniqueId());
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        p.teleport(this.plugin.getConfig().getLocation("Settings.Prison"));
        new BukkitRunnable() {
            @Override
            public void run() {
                if(p.isOnline()) {

                    int b = yml.getInt("Prison.ArrestTime");
                    yml.set("Prison.ArrestTime", b -= 1);
                    try {
                        yml.save(file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    if (b <= 0) {
                        yml.set("Prison.ArrestTime", 0);
                        p.teleport(plugin.getConfig().getLocation("Settings.AfterPrison"));
                        try {
                            yml.save(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        this.cancel();
                    }
                }
                else{
                    this.cancel();
                }
            }
        }.runTaskTimer(this.plugin, 0L, 20L);
    }


    public int getWanted(Player p){
        File file = getPlayerYaml(p.getUniqueId());
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        try{
            return yml.getInt("Stats.wantedLvl");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return 0;

    }





}
