package fr.edminecoreteam.corepractice.matchduels;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.kits.LoadKits;
import fr.edminecoreteam.corepractice.listeners.ItemListeners;
import fr.edminecoreteam.corepractice.matchmaking.GameCheck;
import fr.edminecoreteam.corepractice.utils.LoadWorld;
import fr.edminecoreteam.corepractice.utils.UnloadWorld;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.concurrent.ThreadLocalRandom;

public class GameListeners implements Listener
{
    private static Core core = Core.getInstance();
    public static void startGame(Player p1, Player p2)
    {
        GameCheck gameCheck = core.getGameCheck();
        core.getInWaiting().remove(p1);
        core.getInWaiting().remove(p2);
        core.getInLobby().remove(p1);
        core.getInLobby().remove(p2);

        int randomID = ThreadLocalRandom.current().nextInt(100000000, 999999999);

        LoadKits p1Kit = new LoadKits(p1);
        LoadKits p2Kit = new LoadKits(p2);

        core.getMatchOppenant().putMatchOppenant(p1, p2);
        core.getMatchOppenant().putMatchOppenant(p2, p1);

        core.getGameType().putInGame(p1, gameCheck.getGame(p1));
        core.getGameType().putInGame(p2, gameCheck.getGame(p2));

        core.getGameID().putGameID(p1, randomID);
        core.getGameID().putGameID(p2, randomID);

        core.getInDuel().add(p1);
        core.getInDuel().add(p2);
        gameCheck.removeSerchGame(p1);
        gameCheck.removeSerchGame(p2);

        String world = LoadWorld.getRandomSubfolderName("gameTemplate/");
        LoadWorld.createGameWorld(world, core.getGameID().getIDString(p1));
        core.getWorldName().putWorldName(p1, world);
        core.getWorldName().putWorldName(p2, world);

        Location p1Spawn = new Location(Bukkit.getWorld(core.getConfig().getString("Arenas." + core.getWorldName().getWorldName(p1))),
                (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team1.x")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team1.y")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team1.z")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team1.t")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team1.b"));

        Location p2Spawn = new Location(Bukkit.getWorld(core.getConfig().getString("Arenas." + core.getWorldName().getWorldName(p2))),
                (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p2) + ".team2.x")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p2) + ".team2.y")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p2) + ".team2.z")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p2) + ".team2.t")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p2) + ".team2.b"));

        p1.teleport(p1Spawn);
        p2.teleport(p2Spawn);

        p1Kit.equipUnrankedDefaultKit(gameCheck.getGame(p1));
        p2Kit.equipUnrankedDefaultKit(gameCheck.getGame(p2));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e)
    {
        Player pDeath = e.getEntity();
        if (core.getGameID() != null)
        {
            if (core.getInDuel().contains(pDeath))
            {
                e.getEntity().spigot().respawn();

                Player pVictory = core.getMatchOppenant().getMatchOppenant(pDeath);

                leaveGame(pDeath);
                leaveGame(pVictory);

                pDeath.sendTitle("§c§lDéfaite...", "§7Peut-être une prochaine fois.");
                pVictory.sendTitle("§a§lVictoire !", "§7C'étais moins une !");
                pVictory.playSound(pVictory.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.0f);

                UnloadWorld.deleteWorld(core.getGameID().getIDString(pVictory));

                core.getGameID().removeFromGameID(pVictory);
                core.getGameID().removeFromGameID(pDeath);
            }
        }
    }

    public static void leaveGame(Player p)
    {
        core.getWorldName().removeWorldName(p);
        core.getGameType().removeFromTypeGame(p);
        core.getInDuel().remove(p);
        if (!core.getInLobby().contains(p))
        {
            core.getInLobby().add(p);
        }

        Location lobbySpawn = new Location(Bukkit.getWorld(core.getConfig().getString("Lobby.world")),
                (float) core.getConfig().getDouble("Lobby.x")
                , (float) core.getConfig().getDouble("Lobby.y")
                , (float) core.getConfig().getDouble("Lobby.z")
                , (float) core.getConfig().getDouble("Lobby.t")
                , (float) core.getConfig().getDouble("Lobby.b"));

        p.setGameMode(GameMode.ADVENTURE);
        p.setFoodLevel(20);
        p.teleport(lobbySpawn);
        ItemListeners.getLobbyItems(p);
    }
}
