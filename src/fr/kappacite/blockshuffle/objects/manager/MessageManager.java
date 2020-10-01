package fr.kappacite.blockshuffle.objects.manager;

import fr.kappacite.blockshuffle.BlockShuffle;
import fr.kappacite.blockshuffle.objects.player.ShufflePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class MessageManager {

    private final String PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "BlockShuffle" + ChatColor.DARK_GRAY + "] ";

    public void broadcastPlayerFoundBlock(ShufflePlayer shufflePlayer){
        Bukkit.broadcastMessage(this.PREFIX + ChatColor.DARK_AQUA + shufflePlayer.getPlayer().getName() + ChatColor.GREEN + " a trouvé son bloc "
                + ChatColor.DARK_GRAY + "(" + ChatColor.DARK_AQUA + shufflePlayer.getMaterialName()
                + ChatColor.DARK_GRAY + ")" + ChatColor.GREEN + " !");
    }

    public void sendPlayerBlock(ShufflePlayer shufflePlayer){
        shufflePlayer.getPlayer().sendMessage(this.PREFIX + "§bTon bloc a trouvé est§f: §3" + shufflePlayer.getMaterialName() + "§9.");
    }

    public void sendNotFoundBlocksPlayer(int numberNotFound){
        Bukkit.broadcastMessage(this.PREFIX + ChatColor.DARK_RED + " " + numberNotFound + ChatColor.RED + " joueur(s) n'ont pas trouvé leurs bloc.");
    }

    public String getJoinMessage(Player player){
       return this.PREFIX + "§a" + player.getName() + " §7a rejoint la partie ! §8(§a" +
                Bukkit.getOnlinePlayers().size() + "§8/§a" + BlockShuffle.getInstance().getGame().getMaxPlayers() + "§8)";
    }

    public String getQuitMessage(Player player){
        return this.PREFIX + "§c" + player.getName() + " §7a quitté la partie ! §8(§c" +
                (Bukkit.getOnlinePlayers().size()-1) + "§8/§c" + BlockShuffle.getInstance().getGame().getMaxPlayers() + "§8)";
    }

}
