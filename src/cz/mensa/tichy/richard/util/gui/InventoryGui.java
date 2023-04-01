package cz.mensa.tichy.richard.util.gui;

import cz.mensa.tichy.richard.util.gui.component.Component;
import cz.mensa.tichy.richard.util.inventory.InvalidInventorySizeException;
import cz.mensa.tichy.richard.util.inventory.InventoryModel;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedList;

public class InventoryGui extends InventoryModel
{
    private final LinkedList<HumanEntity> players = new LinkedList<>();

    protected final ArrayList<Component> components = new ArrayList<>();

    private int width;

    private int height;

    public InventoryGui()
    {
        super();
        this.init();
    }

    protected void init()
    {
        try {
            this.setDisplayBorderX(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void render()
    {
        try
        {
            for(int y = 0; y < this.height; y++)
            {
                for(int x = 0; x < this.width; x++)
                {
                    this.setOnPos(x, y, new ItemStack(Material.AIR, 1));
                }
            }

            for (Component component : this.components)
            {
                component.setReloadCallback(new Component.reloadCallback() {
                    @Override
                    public void callback(Component component)
                    {
                        render();
                    }
                });

                if (!component.isVisible()) {
                    continue;
                }

                this.setOnPos(component.getX(), component.getY(), null);
                this.setOnPos(component.getX(), component.getY(), component.render());
            }

            for (HumanEntity entity : this.players)
            {
                super.setInventory(entity);
            }

            super.removePlayers();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setInventory(final HumanEntity entity)
    {
        this.players.push(entity);
    }

    @Override
    protected boolean isShowingTo(String nick)
    {
        for(HumanEntity entity : this.players)
        {
            if(entity.getName().equals(nick))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    protected boolean onClick(int x, int y, Player player)
    {
        for(Component component : this.components)
        {
            if(component.getX() == x && component.getY() == y && component.isVisible())
            {
                return !component.onClick(player);
            }
        }

        return false;
    }

    @Override
    protected void onMoveItem(int fromX, int fromY, int toX, int toY)
    {
        for(Component component : this.components)
        {
            if(component.getX() == fromX && component.getY() == fromY && component.isVisible())
            {
                component.moveTo(toX, toY);
                break;
            }
        }

        this.reload();
    }

    @Override
    protected void event(InventoryCloseEvent e)
    {
        this.players.remove(e.getPlayer());
    }

    @Override
    protected void setSize(int width, int height) throws InvalidInventorySizeException
    {
        super.setSize(width, height);
        this.width = width;
        this.height = height;
    }
}
