package fr.kappacite.blockshuffle.commands;

import fr.kappacite.blockshuffle.BlockShuffle;
import fr.kappacite.blockshuffle.game.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(label.equalsIgnoreCase("start")){

            BlockShuffle blockShuffle = BlockShuffle.getInstance();
            if(GameState.isState(GameState.LOBBY)){
                blockShuffle.getTasksManager().startPregameTimer();
            }

        }

        return false;
    }
}
