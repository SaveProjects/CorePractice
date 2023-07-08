package fr.edminecoreteam.corepractice.data.hashmap.ranked;

import fr.edminecoreteam.corepractice.data.hashmap.ranked.RankedWinData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RankedWinDataManager
{
    private final Map<UUID, RankedWinData> players = new HashMap<>();

    public RankedWinData getPlayerData(UUID playerId) {
        return players.computeIfAbsent(playerId, id -> new RankedWinData(id, 0));
    }

    public void addData(UUID playerId, int amount) {
        getPlayerData(playerId).addTime(amount);
    }

    public void removeData(UUID playerId, int amount) {
        getPlayerData(playerId).removeTime(amount);
    }

    public void resetData(UUID playerId) { getPlayerData(playerId).resetTime(); }

    public int getData(UUID playerId) { return getPlayerData(playerId).getTime(); }

    public Map<UUID, RankedWinData> returnPlayers()
    {
        return this.players;
    }
}
