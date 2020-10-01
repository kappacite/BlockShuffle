package fr.kappacite.blockshuffle.objects.player;

import fr.kappacite.blockshuffle.BlockShuffle;
import fr.kappacite.blockshuffle.objects.game.Round;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ShufflePlayer {

    private static HashMap<UUID, ShufflePlayer> shufflePlayers = new HashMap<>();

    private Player player;
    private int point;
    private Material material;
    private boolean hasFound = false;

    private ShufflePlayer(Player player, int point, Material material) {
        this.player = player;
        this.point = point;
        this.material = material;
    }

    public static void deleteInstance(Player player){
        shufflePlayers.remove(player.getUniqueId());
    }

    public static ShufflePlayer instance(Player player){

        UUID uuid = player.getUniqueId();

        if(shufflePlayers.containsKey(uuid)) return shufflePlayers.get(uuid);

        ShufflePlayer shufflePlayer = new ShufflePlayer(player, 0, null);
        shufflePlayers.put(uuid, shufflePlayer);
        return shufflePlayer;

    }

    public boolean found(){

        Round round = BlockShuffle.getInstance().getGame().getRound();

        if(round.getTimer() <= 300 && round.getTimer() >= 270){
            this.addPoint(25);
        }

        if(round.getTimer() < 270 && round.getTimer() >= 210){
            this.addPoint(20);
        }

        if(round.getTimer() < 210 && round.getTimer() >= 160){
            this.addPoint(15);
        }

        if(round.getTimer() < 160 && round.getTimer() >= 120){
            this.addPoint(10);
        }

        if(round.getTimer() < 60){
            this.addPoint(5);
        }

        this.hasFound = true;
        this.setFoundListName();

        return this.getPoint() >= BlockShuffle.getInstance().getGame().getPointLimit();
    }

    public void newRound(Material material){
        this.hasFound = false;
        this.material = material;
        this.setNotFoundListName();
        FileConfiguration lang = YamlConfiguration.loadConfiguration(new File(BlockShuffle.getInstance().getDataFolder() + File.separator + "lang.yml"));
        this.player.sendMessage("§bTon bloc a trouvé est§f: §3" + lang.getString(material.name()) + "§9.");
    }

    private void setNotFoundListName(){
        FileConfiguration lang = YamlConfiguration.loadConfiguration(new File(BlockShuffle.getInstance().getDataFolder() + File.separator + "lang.yml"));
        this.player.setPlayerListName(ChatColor.GRAY + "[" + lang.getString(this.material.name()) + " : " + point + "] " + ChatColor.RED + player.getName());
    }

    private void setFoundListName(){

        FileConfiguration lang = YamlConfiguration.loadConfiguration(new File(BlockShuffle.getInstance().getDataFolder() + File.separator + "lang.yml"));
        this.player.setPlayerListName(ChatColor.GRAY + "[" + lang.getString(this.material.name()) + " : " + point + "] " + ChatColor.GREEN + player.getName());
    }


    public Integer getPoint() {
        return this.point;
    }

    public void addPoint(int point) {
        this.point+=point;
    }

    public Material getMaterial() {
        return this.material;
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean hasFound() {
        return this.hasFound;
    }

    public static List<ShufflePlayer> getShufflePlayers(){
        return new ArrayList<>(shufflePlayers.values());
    }
}
