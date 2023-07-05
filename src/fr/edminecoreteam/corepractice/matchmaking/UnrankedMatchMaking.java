package fr.edminecoreteam.corepractice.matchmaking;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.listeners.ItemListeners;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class UnrankedMatchMaking
{

    private Player p;

    private static Core core = Core.getInstance();

    public UnrankedMatchMaking(Player p)
    {
        this.p = p;
    }

    public void start(String game)
    {
        GameCheck gameCheck = core.getGameCheck();

        if (gameCheck.getGame(p) == null)
        {
            core.getInWaiting().add(p);
            gameCheck.searchGame(p, game);
            p.sendMessage("§fRecherche d'une partie en cours sur le mode §e§l" + game + "§f...");
            ItemListeners.foundGameItems(p);

            new BukkitRunnable() {
                int t = 0;
                public void run() {

                    if (gameCheck.getGame(p) == null) { cancel(); }
                    if (!core.getInWaiting().contains(p)) { cancel(); }

                    for (Player pGame : core.getInWaiting())
                    {
                        if (pGame != p)
                        {
                            if (gameCheck.getGame(pGame) == gameCheck.getGame(p))
                            {
                                p.sendMessage("§aJoueur trouvé ! §fVotre match contre §b" + pGame.getName() + "§f va commencer...");
                                pGame.sendMessage("§aJoueur trouvé ! §fVotre match contre §b" + p.getName() + "§f va commencer...");
                                core.getInWaiting().remove(p);
                                core.getInWaiting().remove(pGame);
                                gameCheck.removeSerchGame(p);
                                gameCheck.removeSerchGame(pGame);
                                cancel();
                            }
                        }
                    }

                    ++t;
                    if (t == 1) {
                        t = 0;
                    }
                }
            }.runTaskTimer((Plugin)core, 0L, 40L);

        }
        else
        {
            p.sendMessage("§cAction impossible, vous êtes déjà dans une file d'attente...");
        }
    }
}
