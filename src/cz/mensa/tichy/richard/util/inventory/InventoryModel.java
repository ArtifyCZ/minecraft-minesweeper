package cz.mensa.tichy.richard.util.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InventoryModel implements Listener
{
    private Inventory _instance;

    private int offsetXLeft = 0;

    private int offsetXRight = 0;

    private int offsetYTop = 0;

    private int offsetYBottom = 0;

    private int internalWidth = 0;

    private int internalHeight = 0;

    private int fullWidth = 0;

    private int fullHeight = 0;

    private String title = "";

    private final Map<Integer, Map<Integer, itemOnPos>> items;

    private final ArrayList<String> _players = new ArrayList<>();

    private boolean displayBorderX = false;

    private boolean displayBorderY = false;

    public InventoryModel()
    {
        this._instance = Bukkit.createInventory(null, 9, "Example");

        eventsClass events = new eventsClass(new eventsClass.Callback() {
            @Override
            public void on(InventoryClickEvent e) {
                event(e);
            }

            @Override
            public void on(InventoryCloseEvent e) {
                event(e);
            }

            @Override
            public void on(InventoryDragEvent e) {
                event(e);
            }
        });

        Bukkit.getPluginManager().registerEvents(events, Bukkit.getPluginManager().getPlugins()[0]);

        this.items = new HashMap<>();
    }

    protected void setInventory(final HumanEntity entity)
    {
        this.reload();
        entity.openInventory(this._instance);
        this._players.add(entity.getName());
    }

    protected void reload()
    {
        this._instance = Bukkit.createInventory(null, this.fullWidth * this.fullHeight, this.title);

        if(this.displayBorderX)
        {
            this.reloadBorder();
        }

        for(Map.Entry<Integer, Map<Integer, itemOnPos>> entry : this.items.entrySet())
        {
            for(Map.Entry<Integer, itemOnPos> entry2 : entry.getValue().entrySet())
            {
                itemOnPos item = entry2.getValue();

                int pos = (item.y + this.offsetYTop) * this.fullWidth;
                pos = pos + item.x + this.offsetXLeft - 1;
                pos++;
                this._instance.setItem(pos, item.get());
            }
        }
    }

    protected void removeItems()
    {
        this.items.clear();
    }

    private void reloadBorder()
    {
        for(int y = 0; y < this.fullHeight; y++)
        {
            int yPos = y * this.fullWidth;

            for(int x = 0; x < this.fullWidth; x++)
            {
                int pos = yPos + x;
                this._instance.setItem(pos, this.getItem(Material.STAINED_GLASS_PANE, " "));
            }
        }

        int offsetLeftAndWidth = this.fullWidth - this.offsetXRight;
        int offsetTopAndHeight = this.fullHeight - this.offsetYBottom;

        for(int y = this.offsetYTop; y < offsetTopAndHeight; y++)
        {
            int yPos = y * this.fullWidth;

            for(int x = this.offsetXLeft; x < offsetLeftAndWidth; x++)
            {
                int pos = yPos + x;
                this._instance.setItem(pos, null);
            }
        }
    }

    protected void setTitle(String title)
    {
        this.title = title;
    }

    protected void setSize(int width, int height) throws InvalidInventorySizeException
    {
        if(width < 9)
        {
            this.offsetXLeft = (9 - width) / 2;
            this.offsetXRight = this.offsetXLeft;

            if(width % 2 == 0)
            {
                this.offsetXRight++;
            }
        }
        else
        {
            if(width == 9)
            {
                this.offsetXLeft = 0;
                this.offsetXRight = 0;
            }
            else
            {
                throw new InvalidInventorySizeException(width, height, "Width of inventory cannot be higher than 9.");
            }
        }

        if(height < 9 && this.displayBorderY)
        {
            this.offsetYTop = 1;
            this.offsetYBottom = 1;
        }
        else
        {
            this.offsetYTop = 0;
            this.offsetYBottom = 0;
        }

        this.internalWidth = width;
        this.internalHeight = height;

        this.fullWidth = this.internalWidth + this.offsetXLeft + this.offsetXRight;
        this.fullHeight = this.internalHeight + this.offsetYTop + this.offsetYBottom;
    }

    private ItemStack getItem(Material material, String label, int amount)
    {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(label);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getItem(Material material, String label)
    {
        return this.getItem(material, label, 1);
    }

    protected void setOnPos(Material material, int x, int y)
    {
        this.setOnPos(material, x, y, "");
    }

    protected void setOnPos(Material material, int x, int y, String label)
    {
        this.setOnPos(material, label, x, y, 1);
    }

    protected void setOnPos(Material material, String label, int x, int y, int amount)
    {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(label);
        item.setItemMeta(meta);
        this.setOnPos(x, y, item);
    }

    protected void setOnPos(int x, int y, ItemStack item)
    {
        if(!this.items.containsKey(x))
        {
            this.items.put(x, new HashMap<>());
        }

        this.items.get(x).put(y, new itemOnPos(x, y, item));
    }

    protected void setDisplayBorder(boolean displayBorder) throws InvalidInventorySizeException
    {
        this.displayBorderY = displayBorder;
        this.setDisplayBorderX(displayBorder);
    }

    protected void setDisplayBorderX(boolean displayBorder) throws InvalidInventorySizeException
    {
        this.displayBorderX = displayBorder;
        this.setSize(this.internalWidth, this.internalHeight);
    }

    private static class itemOnPos
    {
        private final int x;

        private final int y;

        private final ItemStack item;

        public itemOnPos(int x, int y, ItemStack item)
        {
            this.x = x;
            this.y = y;
            this.item = item;
        }

        public int getX()
        {
            return this.x;
        }

        public int getY()
        {
            return this.y;
        }

        public ItemStack get()
        {
            return this.item;
        }
    }

    /**
     * RETURNS IF CLICK ON ITEM MAY BE CANCELLED.
     */
    protected boolean onClick(int x, int y, Player player)
    {
        return true;
    }

    private int lastClickX = 0;

    private int lastClickY = 0;

    protected void event(InventoryClickEvent e)
    {
        if(!this.isShowingTo(e.getWhoClicked().getName()) || !(e.getWhoClicked() instanceof Player))
        {
            return;
        }

        int x = e.getSlot() % 9;
        int y = (e.getSlot() - x) / 9;

        x = x - this.offsetXLeft;
        y = y - this.offsetYTop;

        if(!(x >= 0 && x < this.internalWidth && y >= 0 && y < this.internalHeight))
        {
            e.setCancelled(true);
            return;
        }

        this.lastClickX = x;
        this.lastClickY = y;

        e.setCancelled(this.onClick(x, y, (Player)e.getWhoClicked()));
    }

    protected boolean isShowingTo(String nick)
    {
        return this._players.contains(nick);
    }

    protected void event(InventoryCloseEvent e)
    {
        this.removePlayer(e.getPlayer().getName());
    }

    protected void removePlayer(String nick)
    {
        this._players.remove(nick);
    }

    protected void removePlayers()
    {
        this._players.clear();
    }

    protected Inventory getInventory()
    {
        return this._instance;
    }

    protected void event(InventoryDragEvent e)
    {
        int toX = 0;
        int toY = 0;
        int slot = (int) e.getRawSlots().toArray()[0];
        toX = slot % 9;
        toY = (slot - toX) / 9;

        this.onMoveItem(this.lastClickX, this.lastClickY, toX, toY);
    }

    protected void onMoveItem(int fromX, int fromY, int toX, int toY)
    {
    }

    private static class eventsClass implements Listener
    {
        private final Callback callback;

        public eventsClass(Callback callback)
        {
            this.callback = callback;
        }

        @EventHandler
        public void on(InventoryClickEvent e)
        {
            this.callback.on(e);
        }

        @EventHandler
        public void on(InventoryCloseEvent e)
        {
            this.callback.on(e);
        }

        @EventHandler
        public void on(InventoryDragEvent e)
        {
            this.callback.on(e);
        }

        public abstract static class Callback
        {
            public abstract void on(InventoryClickEvent e);

            public abstract void on(InventoryCloseEvent e);

            public abstract void on(InventoryDragEvent e);
        }
    }
}