package cz.mensa.tichy.richard.minesweeper.plugin;

import cz.mensa.tichy.richard.minesweeper.plugin.gui.StartButton;
import cz.mensa.tichy.richard.util.gui.InventoryGui;
import cz.mensa.tichy.richard.util.gui.component.Component;
import org.bukkit.entity.Player;

public class MinesweeperGui extends InventoryGui
{
    private final MinesweeperPlugin plugin;

    private final MinesweeperGame game;

    public MinesweeperGui(MinesweeperPlugin plugin)
    {
        super();
        this.plugin = plugin;
        this.game = new MinesweeperGame(this.plugin, this);
    }

    public void addComponent(Component component)
    {
        if(this.components.contains(component))
        {
            return;
        }

        this.components.add(component);
        this.render();
    }

    @Override
    protected void init()
    {
        super.init();
        try
        {
            this.setSize(5, 6);
            this.components.add(new StartButton(new StartButton.callback() {
                @Override
                public void onClick(Player player)
                {
                    game.start(player);
                }
            }));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
