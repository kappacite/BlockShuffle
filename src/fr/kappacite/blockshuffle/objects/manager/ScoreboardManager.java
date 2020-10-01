package fr.kappacite.blockshuffle.objects.manager;

import fr.kappacite.blockshuffle.BlockShuffle;
import fr.kappacite.blockshuffle.objects.game.Round;
import fr.kappacite.blockshuffle.objects.player.ShufflePlayer;
import fr.kappacite.blockshuffle.objects.comparator.PointComparator;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class ScoreboardManager {

    private static BlockShuffle blockShuffle = BlockShuffle.getInstance();

    public void createScoreboard(Player player){

        BPlayerBoard scoreboard = Netherboard.instance().createBoard(player, ChatColor.BLUE + "BlockShuffle");
        scoreboard.set("§c", 15);
        scoreboard.set("§9Manche§f: 0", 14);
        scoreboard.set("§9Durée§f: 5m00s", 13);
        scoreboard.set("§d", 12);
        scoreboard.set("§aTop points§f: ", 11);
        scoreboard.set(" §f- §e1er§f: Personne", 10);
        scoreboard.set(" §f- §72eme§f: Personne", 9);
        scoreboard.set(" §f- §63eme§f: Personne", 8);
        scoreboard.set("§b", 7);
        scoreboard.set("§9Vos points§f: 0", 6);
        scoreboard.set("§a", 5);

    }

    public void updateScoreboard(Player player){

        List<ShufflePlayer> allPlayers = ShufflePlayer.getShufflePlayers();
        Collections.sort(allPlayers, new PointComparator());
        Collections.reverse(allPlayers);

        ShufflePlayer shufflePlayer = ShufflePlayer.instance(player);
        Round round = blockShuffle.getGame().getRound();
        int time = round.getTimer();
        String timeFormat = time/60 + "m" + time%60 + "s";

        BPlayerBoard scoreboard = Netherboard.instance().getBoard(player);
        scoreboard.set("§9Manche§f: " + round.getId(), 14);;
        scoreboard.set("§9Durée§f: " + timeFormat, 13);
        scoreboard.set(" §f- §e1er§f: " + allPlayers.get(0).getPlayer().getName(), 10);
        scoreboard.set(" §f- §72eme§f: " + (allPlayers.size() > 1 ? allPlayers.get(1).getPlayer().getName() : "Personne"), 9);
        scoreboard.set(" §f- §63eme§f: " +  (allPlayers.size() > 2 ? allPlayers.get(2).getPlayer().getName() : "Personne"), 8);
        scoreboard.set("§9Vos points§f: " + shufflePlayer.getPoint() , 6);

    }

}
