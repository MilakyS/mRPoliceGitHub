package me.milaky.mrpolice.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerWantedSet extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final Player target;
    private final int Wanted;
    private boolean isCancelled;


    public static HandlerList getHandlerList() {
        return HANDLERS;
    }



    public PlayerWantedSet(Player player, Player target, int Wanted) {
        this.player = player;
        this.target = target;
        this.Wanted = Wanted;
    }


    public HandlerList getHandlers() {
        return HANDLERS;
    }


    public Player getPlayer() {
        return this.player;
    }

    public Player getTarget(){
        return this.target;
    }
    public int getSettledWantedLvl(){
        return Wanted;
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
