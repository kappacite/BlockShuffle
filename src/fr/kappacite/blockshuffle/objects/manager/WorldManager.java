package fr.kappacite.blockshuffle.objects.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

public class WorldManager {

    private final int SPAWN_RADIUS = 11;

    public void initialiseWorld(){
        this.generateLobby();
        World world = Bukkit.getWorld("blockshuffle");
        world.setPVP(false);
        world.setGameRuleValue("keepInventory", "true");
    }

    private void generateLobby(){
        this.createWorld();
        int wallRadius = 10;
        
        World world = Bukkit.getWorld("blockshuffle");

        for(double x = -SPAWN_RADIUS; x<= SPAWN_RADIUS; x++){
            for(double z = -SPAWN_RADIUS; z<= SPAWN_RADIUS; z++){
                world.getBlockAt((int) x, 200, (int) z).setType(Material.STAINED_GLASS);
            }
        }

        for(int y = 200; y < 200 + 2; y++) {

            //NORTH
            for (double x = -wallRadius; x <= wallRadius; x++) {
                world.getBlockAt((int) x, y + 1, -wallRadius).setType(Material.STAINED_GLASS_PANE);
            }

            //SOUTH
            for (double x = wallRadius; x >= -wallRadius; x--) {
                world.getBlockAt((int) x, y + 1, +wallRadius).setType(Material.STAINED_GLASS_PANE);
            }

            //WEST
            for (double z = -wallRadius; z <= wallRadius; z++) {
                world.getBlockAt(-wallRadius, y + 1, (int) z).setType(Material.STAINED_GLASS_PANE);
            }

            //EAST
            for (double z = wallRadius; z >= -wallRadius; z--) {
                world.getBlockAt(wallRadius, y + 1, (int) z).setType(Material.STAINED_GLASS_PANE);
            }
        }
    }

    public void eraseSpawn(){

        World world = Bukkit.getWorld("blockshuffle");

        for(int x = -SPAWN_RADIUS; x<= SPAWN_RADIUS; x++) {
            for(int y = 200; y <= 202; y++){
                for (int z = -SPAWN_RADIUS; z <= SPAWN_RADIUS; z++) {
                    world.getBlockAt(x, y, z).setType(Material.AIR);
                }
            }
        }

    }

    public void eraseWorld() throws URISyntaxException {

        String serverPath = Paths.get(Bukkit.getServer().getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().toFile().getPath() + "\\blockshuffle";

        File world = new File(serverPath);
        erase(world);

    }

    private void erase(File file){
        File[] list = file.listFiles();
        if (list != null) {
            for (File temp : list) {
                erase(temp);
            }
        }
        file.delete();
    }

    private void createWorld(){
        WorldCreator worldCreator = new WorldCreator("blockshuffle");
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.seed(ThreadLocalRandom.current().nextLong(100000, 100000000));
        worldCreator.createWorld();
    }

}
