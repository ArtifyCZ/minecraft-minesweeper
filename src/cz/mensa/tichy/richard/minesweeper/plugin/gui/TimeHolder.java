package cz.mensa.tichy.richard.minesweeper.plugin.gui;

import cz.mensa.tichy.richard.util.gui.component.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TimeHolder extends Component
{
    private int time;

    public TimeHolder(int time)
    {
        super();
        this.x = 2;
        this.y = 0;
        this.isShowing = true;
        this.time = time;
    }

    public void changeTime(int newTime)
    {
        this.time = newTime;
        this.reload();
    }

    @Override
    public ItemStack render()
    {
        return this.getNewItem(Material.WATCH, Integer.toString(this.time));
    }

    @Override
    public boolean onClick(Player player)
    {
        return false;
    }
}
