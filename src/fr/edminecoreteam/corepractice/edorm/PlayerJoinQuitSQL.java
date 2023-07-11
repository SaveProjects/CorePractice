package fr.edminecoreteam.corepractice.edorm;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.data.PracticeData;
import fr.edminecoreteam.corepractice.data.hashmap.game.GameMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class PlayerJoinQuitSQL implements Listener
{
    private static final Core core = Core.getInstance();

    @EventHandler
    private void onJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        PracticeData data = new PracticeData(p);

        data.createData();
        data.updatePlayerName();

        core.getUnrankedPlayedDataManager().addData(p.getUniqueId(), data.getUnrankedPlayed());
        core.getUnrankedWinDataManager().addData(p.getUniqueId(), data.getUnrankedWin());
        core.getUnrankedLoseDataManager().addData(p.getUniqueId(), data.getUnrankedLose());

        core.getRankedPlayedDataManager().addData(p.getUniqueId(), data.getRankedPlayed());
        core.getRankedWinDataManager().addData(p.getUniqueId(), data.getRankedWin());
        core.getRankedLoseDataManager().addData(p.getUniqueId(), data.getRankedLose());

        core.getPlayerEloDataManager().addData(p.getUniqueId(), data.getPlayerElo());

        GameMap gameMap = core.getGameMap();
        HashMap<String, Integer> gameValues = new HashMap<>();
        for (String game : core.getConfig().getConfigurationSection("kits.normal").getKeys(false))
        {
            int getGameUnrankedWin = data.getGameData(game + "_unranked_win");
            int getGameRankedWin = data.getGameData(game + "_ranked_win");
            int getGameRankedLose = data.getGameData(game + "_ranked_lose");
            int getGameElo = data.getGameData(game + "_elo");
            int getGameBestWinStreak = data.getGameData(game + "_bestwinstreak");

            if (getGameElo == 0)
            {
                getGameElo = 800;
            }

            gameValues.put(game + "_unranked_win", getGameUnrankedWin);
            gameValues.put(game + "_ranked_win", getGameRankedWin);
            gameValues.put(game + "_ranked_lose", getGameRankedLose);
            gameValues.put(game + "_elo", getGameElo);
            gameValues.put(game + "_bestwinstreak", getGameBestWinStreak);
            gameValues.put(game + "_actualwinstreak", 0);

            gameMap.ajouterJoueur(p, gameValues);
        }
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        PracticeData data = new PracticeData(p);

        data.setUnrankedPlayed(core.getUnrankedPlayedDataManager().getData(p.getUniqueId()));
        data.setUnrankedWin(core.getUnrankedWinDataManager().getData(p.getUniqueId()));
        data.setUnrankedLose(core.getUnrankedLoseDataManager().getData(p.getUniqueId()));

        data.setRankedPlayed(core.getRankedPlayedDataManager().getData(p.getUniqueId()));
        data.setRankedWin(core.getRankedWinDataManager().getData(p.getUniqueId()));
        data.setRankedLose(core.getRankedLoseDataManager().getData(p.getUniqueId()));

        data.setPlayerElo(core.getPlayerEloDataManager().getData(p.getUniqueId()));

        core.getUnrankedPlayedDataManager().resetData(p.getUniqueId());
        core.getUnrankedWinDataManager().resetData(p.getUniqueId());
        core.getUnrankedLoseDataManager().resetData(p.getUniqueId());

        core.getRankedPlayedDataManager().resetData(p.getUniqueId());
        core.getRankedWinDataManager().resetData(p.getUniqueId());
        core.getRankedLoseDataManager().resetData(p.getUniqueId());

        core.getPlayerEloDataManager().resetData(p.getUniqueId());

        GameMap gameMap = core.getGameMap();
        HashMap<String, Integer> gameValues = new HashMap<>();
        for (String game : core.getConfig().getConfigurationSection("kits.normal").getKeys(false))
        {
            int getGameUnrankedWin = gameMap.rechercherValeurPourJoueur(p, game + "_unranked_win");
            int getGameRankedWin = gameMap.rechercherValeurPourJoueur(p, game + "_ranked_win");
            int getGameRankedLose = gameMap.rechercherValeurPourJoueur(p, game + "_ranked_lose");
            int getGameElo = gameMap.rechercherValeurPourJoueur(p, game + "_elo");
            int getGameBestWinStreak = gameMap.rechercherValeurPourJoueur(p, game + "_bestwinstreak");
            int getGameActualWinStreak = gameMap.rechercherValeurPourJoueur(p, game + "_actualwinstreak");

            data.updateGameData(game + "_unranked_win", getGameUnrankedWin);
            data.updateGameData(game + "_ranked_win", getGameRankedWin);
            data.updateGameData(game + "_ranked_lose", getGameRankedLose);
            data.updateGameData(game + "_elo", getGameElo);

            if (getGameActualWinStreak > getGameBestWinStreak)
            {
                data.updateGameData(game + "_bestwinstreak", getGameActualWinStreak);
            }
            else
            {
                data.updateGameData(game + "_bestwinstreak", getGameBestWinStreak);
            }
        }
        gameMap.supprimerJoueur(p);
    }

}
