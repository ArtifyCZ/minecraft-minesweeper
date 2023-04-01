package cz.mensa.tichy.richard.util.gui.component;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;

public abstract class Component
{
    protected int x;

    protected int y;

    protected boolean isShowing;

    public Component()
    {
        this.x = 0;
        this.y = 0;
        this.isShowing = true;
    }

    public boolean isVisible()
    {
        return this.isShowing;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    protected ItemStack getNewItem(Material material, String label)
    {
        return this.getNewItem(material, label, 1);
    }

    protected ItemStack getNewItem(Material material, String label, int amount)
    {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(label);
        item.setItemMeta(meta);
        return item;
    }

    public abstract ItemStack render();


    /**
     * THIS IS CALLED WHEN SOMEONE CLICKS ON THIS ITEM IN INVENTORY
     * @return RETURNS FALSE IF PLAYER CANNOT MOVE ITEM
     */
    public boolean onClick(Player player)
    {
        return false;
    }

    public void moveTo(int toX, int toY)
    {
        this.x = toX;
        this.y = toY;
    }

    private reloadCallback reload;

    protected void reload()
    {
        System.out.println("FUCK1");

        if(this.reload == null)
            return;

        System.out.println("FUCK2");

        this.reload.callback(this);
    }

    public void setReloadCallback(reloadCallback callback)
    {
        this.reload = callback;
    }

    public static abstract class reloadCallback
    {
        public abstract void callback(Component component);
    }
}
