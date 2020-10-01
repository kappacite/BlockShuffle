package fr.kappacite.blockshuffle.events;

import fr.kappacite.blockshuffle.objects.state.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class MicroEvents implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event){
        if(!GameState.isState(GameState.GAME)) event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(!GameState.isState(GameState.GAME)) event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if(!GameState.isState(GameState.GAME)) event.setCancelled(true);
    }



}
