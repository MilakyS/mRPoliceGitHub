package me.milaky.mrpolice.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerArrestedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final Player target;
    private boolean isCancelled;


    public static HandlerList getHandlerList() {
        return HANDLERS;
    }



    public PlayerArrestedEvent(Player player, Player target) {
        this.player = player;
        this.target = target;
    }


    public HandlerList getHandlers() {
        return HANDLERS;
    }


    public Player getArrestedPlayer() {
        return this.player;
    }

    public Player getPoliceMan(){
        return this.target;
    }
    public void setCancelled(boolean setCancelled){
        if(setCancelled){
            isCancelled = true;
        }
        else{
            isCancelled = false;
        }
    }
    public boolean isCancelled(){
        return isCancelled;
    }
}
