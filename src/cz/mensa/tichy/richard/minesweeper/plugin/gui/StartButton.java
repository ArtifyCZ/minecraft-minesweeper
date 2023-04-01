package cz.mensa.tichy.richard.minesweeper.plugin.gui;

import cz.mensa.tichy.richard.util.gui.component.Button;
import cz.mensa.tichy.richard.util.gui.component.Component;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

public class StartButton extends Component
{
    private final callback callback;

    public StartButton(callback callback)
    {
        this.callback = callback;
        this.x = 0;
        this.y = 0;
        this.isShowing = true;
    }

    @Override
    public ItemStack render()
    {
        ItemStack item = new ItemStack(Material.WOOL, 1, DyeColor.LIME.getData());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("START");
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public boolean onClick(Player player)
    {
        this.callback.onClick(player);
        return false;
    }

    public static abstract class callback
    {
        public abstract void onClick(Player player);
    }
}
