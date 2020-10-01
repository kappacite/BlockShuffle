package fr.kappacite.blockshuffle.objects.manager;

import org.bukkit.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

public class WorldManager {

    public void initialiseWorld(){
        this.generateLobby();
        World world = Bukkit.getWorld("blockshuffle");
        world.setPVP(false);
        world.setGameRuleValue("keepInventory", "true");
    }

    private void generateLobby(){
        this.createWorld();
        int radius = 11;
        int wallRadius = 10;
        
        World world = Bukkit.getWorld("blockshuffle");

        for(double x = -radius; x<= radius; x++){
            for(double z = -radius; z<= radius; z++){
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

        int radius = 11;
        World world = Bukkit.getWorld("blockshuffle");

        for(int x = -radius; x<= radius; x++) {
            for(int y = 200; y <= 202; y++){
                for (int z = -radius; z <= radius; z++) {
                    world.getBlockAt(x, y, z).setType(Material.AIR);
                }
            }
        }

    }

    public void eraseWorld() throws URISyntaxException {

        String serverPath = Paths.get(Bukkit.getServer().getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().toFile().getPath();

        System.out.println(serverPath + "\\blockshuffle");
        File world = new File(serverPath + "\\blockshuffle");
        erase(world);

    }

    private void erase(File file){
        File[] list = file.listFiles();
        if (list != null) {
            for (File temp : list) {
                //recursive delete
                System.out.println("Visit " + temp);
                erase(temp);
            }
        }

        if (file.delete()) {
            System.out.printf("Delete : %s%n", file);
        } else {
            System.err.printf("Unable to delete file or directory : %s%n", file);
        }
    }

    private void createWorld(){
        WorldCreator worldCreator = new WorldCreator("blockshuffle");
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.seed(ThreadLocalRandom.current().nextLong(100000, 100000000));
        worldCreator.createWorld();
    }

}
