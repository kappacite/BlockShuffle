package fr.kappacite.blockshuffle.events;

import fr.kappacite.blockshuffle.BlockShuffle;
import fr.kappacite.blockshuffle.game.Game;
import fr.kappacite.blockshuffle.game.GameState;
import fr.kappacite.blockshuffle.game.ShufflePlayer;
import fr.kappacite.blockshuffle.game.TasksManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Quit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){

        Game game = BlockShuffle.getInstance().getGame();
        TasksManager tasksManager = BlockShuffle.getInstance().getTasksManager();

        if(Bukkit.getOnlinePlayers().size() < game.getMinPlayers() && tasksManager.isStarted()){
            tasksManager.stopPregameTimer();
        }

        if(GameState.isState(GameState.GAME)){
            ShufflePlayer.deleteInstance(event.getPlayer());

            if(ShufflePlayer.getShufflePlayers().size() == 1){
                game.win(ShufflePlayer.getShufflePlayers().get(0));
            }
        }

    }

}
