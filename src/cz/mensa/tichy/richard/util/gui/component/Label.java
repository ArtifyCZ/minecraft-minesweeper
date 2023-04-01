package cz.mensa.tichy.richard.util.gui.component;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Label extends Component
{
    private String text = "";

    public Label(String text, int x, int y)
    {
        super();
        this.x = x;
        this.y = y;
        this.text = text;
    }

    @Override
    public ItemStack render()
    {
        return this.getNewItem(Material.PAPER, this.text);
    }

    @Override
    public boolean onClick(Player player)
    {
        return true;
    }
}
