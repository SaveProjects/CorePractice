package fr.edminecoreteam.corepractice.edorm;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.data.PracticeData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
    }

}
