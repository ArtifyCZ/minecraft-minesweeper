package cz.mensa.tichy.richard.minesweeper.plugin;

import cz.mensa.tichy.richard.util.gui.InventoryGui;
import cz.mensa.tichy.richard.util.inventory.InvalidInventorySizeException;
import cz.mensa.tichy.richard.util.inventory.InventoryModel;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MinesweeperPlugin extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        // ENABLE
    }

    @Override
    public void onDisable()
    {
        // DISABLE
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(!(sender instanceof Player) || !label.equalsIgnoreCase("minesweeper") || args.length != 0)
        {
            return false;
        }

        Player p = (Player)sender;

        try {
            InventoryGui gui = new MinesweeperGui(this);
            gui.setInventory(p);
            gui.render();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}