package me.milaky.mrpolice.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerArrestedEvent extends Event implements Cancellable {
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
    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }
}
