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
            event.setQuitMessage("§8[§9BlockShuffle§8] §c" + player.getName() + " §7a quitté la partie ! §8(§c" + Bukkit.getOnlinePlayers().size()
                    + "§8/§c" + game.getMaxPlayers() + "§8)");
            tasksManager.stopPregameTimer();
        }

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
