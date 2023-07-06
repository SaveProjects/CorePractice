package fr.edminecoreteam.corepractice.matchmaking;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.kits.LoadKits;
import fr.edminecoreteam.corepractice.matchduels.GameListeners;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class FoundGame
{
    private static Core core = Core.getInstance();

    public static void start()
    {
        new BukkitRunnable() {
            int t = 0;
            public void run() {


                for (String gameMode : core.getConfig().getConfigurationSection("kits.unranked").getKeys(false))
                {
                    GameCheck gameCheck = core.getGameCheck();
                    List<Player> waitlist = new ArrayList<Player>();
                    for (Player pGame : core.getInWaiting())
                    {
                        if (gameCheck.getGame(pGame) != null && gameCheck.getGame(pGame).equalsIgnoreCase(gameMode))
                        {
                            waitlist.add(pGame);
                        }
                    }
                    if (waitlist.size() > 1)
                    {
                        List<Player> gameList = waitlist.subList(0, 2);
                        Player p1 = gameList.get(0);
                        Player p2 = gameList.get(1);
                        p1.getInventory().clear();
                        p2.getInventory().clear();
                        p1.sendMessage("§aJoueur trouvé ! §fVotre match contre §b" + p2.getName() + "§f va commencer...");
                        p2.sendMessage("§aJoueur trouvé ! §fVotre match contre §b" + p1.getName() + "§f va commencer...");

                        GameListeners.startGame(p1, p2);
                    }
                }

                ++t;
                if (t == 1) {
                    t = 0;
                }
            }
        }.runTaskTimer((Plugin)core, 0L, 40L);
    }
}
