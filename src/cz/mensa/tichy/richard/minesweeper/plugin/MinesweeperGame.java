package cz.mensa.tichy.richard.minesweeper.plugin;

import cz.mensa.tichy.richard.minesweeper.plugin.gui.TimeHolder;
import cz.mensa.tichy.richard.util.gui.component.Button;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MinesweeperGame
{
    private final MinesweeperPlugin plugin;

    private int remaingTimeInSecs;

    private TimeHolder timeComponent;

    private MinesweeperGui gui;

    public MinesweeperGame(MinesweeperPlugin plugin, MinesweeperGui gui)
    {
        this.plugin = plugin;
        this.remaingTimeInSecs = 120;
        this.timeComponent = new TimeHolder(this.remaingTimeInSecs);
        this.gui = gui;
    }

    public void start(Player player)
    {
        this.gui.addComponent(this.timeComponent);
        this.updateTime();
    }

    private void updateTime()
    {
        this.remaingTimeInSecs--;

        if(this.remaingTimeInSecs == 0)
        {
            this.onEnd();
            return;
        }

        this.timeComponent.changeTime(this.remaingTimeInSecs);

        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, this::updateTime, 20);
    }

    private void onEnd()
    {
        System.out.println("END!");
    }
}
