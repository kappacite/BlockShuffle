package fr.kappacite.blockshuffle.game;

import fr.kappacite.blockshuffle.BlockShuffle;
import fr.kappacite.blockshuffle.game.GameState;
import fr.kappacite.blockshuffle.game.ShufflePlayer;
import fr.kappacite.blockshuffle.nms.Packets;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class TasksManager {

    private static BlockShuffle blockShuffle = BlockShuffle.getInstance();
    private static Packets packets = blockShuffle.getPackets();
    private boolean isStarted = false;
    private int pregameTimer = 15;
    private int pregameTask;

    public TasksManager(){
        blockShuffle = BlockShuffle.getInstance();
        packets = blockShuffle.getPackets();
    }

    private void startScoreboardTask(){
       Bukkit.getScheduler().runTaskTimerAsynchronously(blockShuffle, (() -> {
            Bukkit.getOnlinePlayers().forEach(player -> blockShuffle.getScoreboardManager().updateScoreboard(player));
        }), 20, 20);
    }

    public void startPregameTimer(){

        this.isStarted = true;
        GameState.setState(GameState.PREGAME);

        this.pregameTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(blockShuffle, (() -> {

            if(this.pregameTimer == 15 || this.pregameTimer == 10 || this.pregameTimer == 3 || this.pregameTimer == 2 ||this.pregameTimer == 1){
                Bukkit.getOnlinePlayers().forEach(player -> packets.sendTitle(player, "§eLa partie commence dans", "§e" + this.pregameTimer + "s", 20));
            }

            if(this.pregameTimer == 0){
                Bukkit.getOnlinePlayers().forEach(player -> {
                    packets.sendTitle(player, "", "§aLa partie commence !", 20);
                });
                this.stopPregameTimer();
                this.startGameTimer();
            }

            this.pregameTimer--;

        }), 20, 20);

    }

    public void stopPregameTimer(){
        Bukkit.getScheduler().cancelTask(pregameTask);
        this.isStarted = false;
        this.pregameTimer = 15;
    }

    private void startGameTimer(){
        GameState.setState(GameState.GAME);
        BlockShuffle.getInstance().getGame().start();
        this.startBlockDetection();
        this.startScoreboardTask();;
    }

    private void startBlockDetection(){

        Bukkit.getScheduler().runTaskTimerAsynchronously(BlockShuffle.getInstance(), (() -> {

            List<ShufflePlayer> shufflePlayerList = ShufflePlayer.getShufflePlayers();

            for (ShufflePlayer shufflePlayer : shufflePlayerList) {

                if(shufflePlayer.hasFound()) continue;

                Player player = shufflePlayer.getPlayer();

                if(player.getLocation().add(0, -1, 0).getBlock().getType() == shufflePlayer.getMaterial()){
                    boolean isWin = blockShuffle.getBlockManager().found(shufflePlayer);
                    System.out.println(isWin);
                    if(isWin){
                        blockShuffle.getGame().win(shufflePlayer);
                        return;
                    }
                    if(blockShuffle.getBlockManager().canSkipRound()) blockShuffle.getGame().getRound().newRound(true);
                }
            }

        }), 10, 10);

    }

    public boolean isStarted() {
        return this.isStarted;
    }
}
