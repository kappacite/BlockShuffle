package fr.kappacite.blockshuffle.objects.game;

import fr.kappacite.blockshuffle.BlockShuffle;
import fr.kappacite.blockshuffle.objects.manager.BlockManager;
import fr.kappacite.blockshuffle.objects.player.ShufflePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class Round {

    private BlockShuffle blockShuffle = BlockShuffle.getInstance();
    private BlockManager blockManager;

    public Round(){
        this.blockManager = blockShuffle.getBlockManager();
    }

    private int id = 0;
    private int timer = 30;
    private BukkitTask task;

    public void newRound(boolean skip){

        if(skip) this.task.cancel();

        if(!skip && id > 0){
            List<ShufflePlayer> shufflePlayerList = ShufflePlayer.getShufflePlayers();
            int numberNotFound = 0;
            for (ShufflePlayer shufflePlayer : shufflePlayerList) {
                if(!shufflePlayer.hasFound()) numberNotFound++;
            }
            this.blockShuffle.getMessageManager().sendNotFoundBlocksPlayer(numberNotFound);
        }

        this.id++;
        this.timer = 30;
        this.blockManager.giveBlocks();

        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(BlockShuffle.getInstance(), () -> {

            if(this.timer == 0){
                this.task.cancel();
                this.newRound(false);
            }

            this.timer--;
        }, 20, 20);
    }

    public int getId() {
        return id;
    }

    public int getTimer() {
        return timer;
    }
}
