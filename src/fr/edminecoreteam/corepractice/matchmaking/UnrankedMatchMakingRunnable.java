package fr.edminecoreteam.corepractice.matchmaking;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.edorm.MySQL;
import fr.edminecoreteam.corepractice.edorm.SQLState;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class UnrankedMatchMakingRunnable extends BukkitRunnable
{
    public int timer;
    private Core core;

    public UnrankedMatchMakingRunnable(Core core) {
        this.core = core;
        this.timer = 10;
    }

    @Override
    public void run() {
        if (timer == 0)
        {
            this.timer = 10;
            GameCheck gameCheck = core.getGameCheck();

            HashMap<Player, String> getHashMap = gameCheck.getGame();


        }
        --timer;
    }
}
