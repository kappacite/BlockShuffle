package fr.kappacite.blockshuffle.events;

import fr.kappacite.blockshuffle.BlockShuffle;
import fr.kappacite.blockshuffle.objects.game.Game;
import fr.kappacite.blockshuffle.objects.manager.TasksManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class Join implements Listener {

    @EventHandler
    public void onAsyncPreLogin(AsyncPlayerPreLoginEvent event){
        Game game = BlockShuffle.getInstance().getGame();

        if(Bukkit.getOnlinePlayers().size() == game.getMaxPlayers()){
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.RED + "La partie est complète !");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();
        Game game = BlockShuffle.getInstance().getGame();
        TasksManager tasksManager = BlockShuffle.getInstance().getTasksManager();

        player.teleport(new Location(Bukkit.getWorld("blockshuffle"), 0, 201, 0));
        player.setGameMode(GameMode.ADVENTURE);
        player.setFoodLevel(20);
        player.setHealth(20);
        player.getInventory().clear();
        player.getEquipment().setArmorContents(new ItemStack[]{});

        event.setJoinMessage(BlockShuffle.getInstance().getMessageManager().getJoinMessage(player));

        BlockShuffle.getInstance().getScoreboardManager().createScoreboard(player);
        BlockShuffle.getInstance().getPackets().sendTablist(player);

        if(Bukkit.getOnlinePlayers().size() == game.getMinPlayers() && !tasksManager.isStarted()){
            tasksManager.startPregameTimer();
        }

    }

}
