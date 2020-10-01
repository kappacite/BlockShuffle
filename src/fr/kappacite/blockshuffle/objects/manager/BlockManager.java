package fr.kappacite.blockshuffle.objects.manager;

import fr.kappacite.blockshuffle.BlockShuffle;
import fr.kappacite.blockshuffle.objects.player.ShufflePlayer;
import fr.kappacite.blockshuffle.objects.game.Round;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.Random;

public class BlockManager {

    private final Material[] LEVEL_1 = new Material[]{Material.DIRT, Material.STONE, Material.CLAY, Material.SAND,
            Material.COBBLESTONE, Material.WOOD, Material.LOG, Material.WORKBENCH, Material.CHEST};

    private final Material[] LEVEL_2 = new Material[]{Material.GRAVEL, Material.FURNACE, Material.IRON_ORE,
            Material.COAL_ORE, Material.STAINED_GLASS, Material.WOOL, Material.GLASS, Material.LAPIS_ORE, Material.GOLD_ORE, Material.SMOOTH_BRICK, Material.REDSTONE_ORE};

    private final Material[] LEVEL_3 = new Material[]{Material.OBSIDIAN, Material.IRON_BLOCK, Material.GOLD_BLOCK, Material.BRICK,
            Material.DISPENSER, Material.CAULDRON, Material.BOOKSHELF, Material.ANVIL, Material.REDSTONE_BLOCK, Material.LAPIS_BLOCK, Material.SLIME_BLOCK};

    private final Material[] LEVEL_4 = new Material[]{Material.NETHERRACK, Material.ENCHANTMENT_TABLE, Material.DIAMOND_ORE, Material.ENDER_PORTAL,
            Material.NETHER_BRICK, Material.JUKEBOX, Material.NOTE_BLOCK, Material.PISTON_BASE};

    private final Material[] LEVEL_5 = new Material[]{Material.ENDER_STONE, Material.ENDER_CHEST,
            Material.EMERALD_ORE, Material.EMERALD_BLOCK, Material.DIAMOND_BLOCK, Material.BEACON};

    public boolean canSkipRound(){
        List<ShufflePlayer> shufflePlayerList = ShufflePlayer.getShufflePlayers();
        for (ShufflePlayer shufflePlayer : shufflePlayerList) {
            if(!shufflePlayer.hasFound()) return false;
        }
        return true;
    }

    public boolean found(ShufflePlayer shufflePlayer){
        FileConfiguration lang = YamlConfiguration.loadConfiguration(new File(BlockShuffle.getInstance().getDataFolder() + File.separator + "lang.yml"));
        Bukkit.broadcastMessage(ChatColor.DARK_AQUA + shufflePlayer.getPlayer().getName() + ChatColor.GREEN + " a trouvé son bloc "
                + ChatColor.DARK_GRAY + "(" + ChatColor.DARK_AQUA + lang.getString(shufflePlayer.getMaterial().name())
                + ChatColor.DARK_GRAY + ")" + ChatColor.GREEN + " !");
        return shufflePlayer.found();
    }

    public void giveBlocks(){

        Material[] blockArrays = this.getLevel();

        Material block = blockArrays[new Random().nextInt(blockArrays.length > 1 ? (blockArrays.length-1) : 0)];

        List<ShufflePlayer> shufflePlayerList = ShufflePlayer.getShufflePlayers();

        for (ShufflePlayer shufflePlayer : shufflePlayerList) {
            shufflePlayer.newRound(block);
        }

    }

    private Material[] getLevel(){

        Round round = BlockShuffle.getInstance().getGame().getRound();

        if(round.getId() >= 0 && round.getId() < 3){
            return this.LEVEL_1;
        }

        if(round.getId() >= 3 && round.getId() < 7){
            return this.LEVEL_2;
        }

        if(round.getId() >= 7 && round.getId() < 12){
            return this.LEVEL_3;
        }

        if(round.getId() >= 12 && round.getId() < 17){
            return this.LEVEL_4;
        }

        if(round.getId() >= 17){
            return this.LEVEL_5;
        }

        return this.LEVEL_1;

    }

}
