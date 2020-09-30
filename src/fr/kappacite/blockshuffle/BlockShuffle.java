package fr.kappacite.blockshuffle;

import fr.kappacite.blockshuffle.commands.StartCommand;
import fr.kappacite.blockshuffle.events.Join;
import fr.kappacite.blockshuffle.events.MicroEvents;
import fr.kappacite.blockshuffle.events.Quit;
import fr.kappacite.blockshuffle.game.BlockManager;
import fr.kappacite.blockshuffle.game.Game;
import fr.kappacite.blockshuffle.game.GameState;
import fr.kappacite.blockshuffle.nms.Packets;
import fr.kappacite.blockshuffle.game.TasksManager;
import fr.kappacite.blockshuffle.objects.ScoreboardManager;
import fr.kappacite.blockshuffle.objects.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockShuffle extends JavaPlugin {

    private TasksManager tasksManager;
    private WorldManager worldManager;
    private BlockManager blockManager;
    private ScoreboardManager scoreboardManager;
    private Packets packets;
    private Game game;
    private static BlockShuffle instance;


    public void onEnable(){

        PluginManager pluginManager = Bukkit.getPluginManager();

        this.instance = this;

        this.saveDefaultConfig();
        this.saveResource("lang.yml", false);

        GameState.setState(GameState.LOBBY);
        this.packets = new Packets();
        this.tasksManager = new TasksManager();
        this.blockManager = new BlockManager();
        this.worldManager = new WorldManager();
        this.scoreboardManager = new ScoreboardManager();
        this.game = new Game();
        this.worldManager.initialiseWorld();

        this.getCommand("start").setExecutor(new StartCommand());

        pluginManager.registerEvents(new Join(), this);
        pluginManager.registerEvents(new Quit(), this);
        pluginManager.registerEvents(new MicroEvents(), this);

    }

    public void onDisable(){
        Bukkit.spigot().restart();
    }

    public WorldManager getWorldManager() {
        return this.worldManager;
    }

    public Game getGame() {
        return this.game;
    }

    public static BlockShuffle getInstance(){
        return instance;
    }

    public TasksManager getTasksManager() {
        return tasksManager;
    }

    public Packets getPackets() {
        return packets;
    }

    public BlockManager getBlockManager() {
        return blockManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
}