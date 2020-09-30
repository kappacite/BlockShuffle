package fr.kappacite.blockshuffle.game;

import fr.kappacite.blockshuffle.BlockShuffle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Game {

    private Round round;
    private BlockShuffle blockShuffle = BlockShuffle.getInstance();

    private final int POINT_LIMIT;
    private final int MIN_PLAYERS;
    private final int MAX_PLAYERS;

    public Game(){
        this.round = new Round();
        this.POINT_LIMIT = this.blockShuffle.getConfig().getInt("point-limit");
        this.MIN_PLAYERS = this.blockShuffle.getConfig().getInt("min-players");
        this.MAX_PLAYERS = this.blockShuffle.getConfig().getInt("max-players");
    }

    public void start(){
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            ShufflePlayer.instance(onlinePlayer);
            onlinePlayer.setGameMode(GameMode.SURVIVAL);
        }
        this.round.newRound(false);
    }

    public void win(ShufflePlayer player){
        Bukkit.getScheduler().cancelAllTasks();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            blockShuffle.getPackets().sendTitle(onlinePlayer, ChatColor.AQUA + player.getPlayer().getName(), ChatColor.AQUA + "a gagné !", 20);
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(blockShuffle, Bukkit::shutdown, 100);
    }

    public Round getRound() {
        return round;
    }

    public int getPointLimit() {
        return POINT_LIMIT;
    }

    public int getMinPlayers() {
        return MIN_PLAYERS;
    }

    public int getMaxPlayers() {
        return MAX_PLAYERS;
    }


}
