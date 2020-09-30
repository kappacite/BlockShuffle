package fr.kappacite.blockshuffle.game;

import fr.kappacite.blockshuffle.BlockShuffle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class Round {

    private BlockShuffle blockShuffle = BlockShuffle.getInstance();
    private BlockManager blockManager;

    public Round(){
        this.blockManager = blockShuffle.getBlockManager();
        System.out.println(blockManager);
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

            Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "BlockShuffle"
                    + ChatColor.DARK_GRAY + "]" + ChatColor.DARK_RED + " " + numberNotFound + ChatColor.RED + " joueurs n'ont pas trouvé leurs bloc.");
        }
        this.id++;
        this.timer = 30;
        this.blockManager.giveBlocks();

        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(BlockShuffle.getInstance(), () -> {

            Bukkit.broadcastMessage("timer " + this.timer);

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
