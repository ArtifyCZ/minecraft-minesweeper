package cz.mensa.tichy.richard.util.gui.component;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public class Button extends Component
{
    private final onClickCallback onClickCallback;

    private Material material;

    private String text;

    public Button(String text, int x, int y, onClickCallback onClick, Material material)
    {
        super();
        this.x = x;
        this.y = y;
        this.text = text;
        this.onClickCallback = onClick;
        this.material = material;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void setMaterial(Material material)
    {
        this.material = material;
    }

    @Override
    public ItemStack render() {
        return this.getNewItem(this.material, this.text);
    }

    @Override
    public boolean onClick(Player player)
    {
        this.onClickCallback.callback(player);

        return false;
    }

    public static abstract class onClickCallback
    {
        public abstract void callback(Player player);
    }
}
