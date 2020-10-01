package fr.kappacite.blockshuffle.events;

import fr.kappacite.blockshuffle.BlockShuffle;
import fr.kappacite.blockshuffle.objects.game.Game;
import fr.kappacite.blockshuffle.objects.state.GameState;
import fr.kappacite.blockshuffle.objects.player.ShufflePlayer;
import fr.kappacite.blockshuffle.objects.manager.TasksManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Quit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){

        Player player = event.getPlayer();
        Game game = BlockShuffle.getInstance().getGame();
        TasksManager tasksManager = BlockShuffle.getInstance().getTasksManager();

        if(Bukkit.getOnlinePlayers().size() < game.getMinPlayers() && tasksManager.isStarted()){
            tasksManager.stopPregameTimer();
        }

        event.setQuitMessage(BlockShuffle.getInstance().getMessageManager().getQuitMessage(player));

        if(GameState.isState(GameState.GAME)){

            ShufflePlayer.deleteInstance(player);

            if(ShufflePlayer.getShufflePlayers().size() == 1){
                game.win(ShufflePlayer.getShufflePlayers().get(0));
            }

            if(ShufflePlayer.getShufflePlayers().size() == 0){
                Bukkit.spigot().restart();
            }
        }

    }

}
