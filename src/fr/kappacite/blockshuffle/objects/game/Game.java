package fr.kappacite.blockshuffle.objects.game;

import fr.kappacite.blockshuffle.BlockShuffle;
import fr.kappacite.blockshuffle.objects.player.ShufflePlayer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

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

        blockShuffle.getWorldManager().eraseSpawn();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            ShufflePlayer.instance(onlinePlayer);
            onlinePlayer.setGameMode(GameMode.SURVIVAL);
        }
        this.round.newRound(false);
        this.teleport();
    }

    public void win(ShufflePlayer player){
        Bukkit.getScheduler().cancelAllTasks();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            blockShuffle.getPackets().sendTitle(onlinePlayer, ChatColor.AQUA + player.getPlayer().getName(), ChatColor.AQUA + "a gagné !", 20);
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(blockShuffle, () -> Bukkit.spigot().restart(), 100);
    }

    private void teleport(){
        int x = 300;
        int z = 300;

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            World world = Bukkit.getWorld("blockshuffle");
            Block block = world.getHighestBlockAt(x, z);

            onlinePlayer.setVelocity(new Vector(0, 0, 0));
            onlinePlayer.teleport(block.getLocation());

            if(x < z) { x+=300; continue; }
            if(z < x) { z+=300; continue; }
            if(z == x) x+=300;
        }
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
